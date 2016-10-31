package com.roozen.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import com.roozen.core.dto.Payload;
import org.junit.Before;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleControllerTest {

    MasterService service;
    CacheService cacheService;
    DataService dataService;

    final ObjectMapper mapper = new ObjectMapper();
    final Function<DataRequest, String> mapIt = request -> {
        try {
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            return "Error mapping request.";
        }
    };

    private PublishSubject<DataRequest> source = PublishSubject.create();

    @Before
    public void init() {
        service = new MasterService();
        cacheService = new CacheService();
        dataService = new DataService();

        cacheService.service = service;
        dataService.service = service;

        cacheService.init();
        dataService.init();
        formValidation();
    }

    @Test
    public void testCycle() throws Exception {
        final String inputCityId = "1234";
        final WeatherRequest weatherRequest = new WeatherRequest(inputCityId);
        final DataRequest dataRequest = new DataRequest(source, Action.CHECK, weatherRequest);

        final String expectedResult = "New York City, NY";
        final WeatherRequest cachedRequest = new WeatherRequest(inputCityId, expectedResult);
        cacheService.inMemoryCache.put(inputCityId, cachedRequest);

        // EXECUTE
        final Future<DataRequest> blockingResult = formCallback(dataRequest);
        service.getBroker().onNext(dataRequest);

        // VERIFY
        final DataRequest result = blockingResult.get(300, TimeUnit.MILLISECONDS);
        assertThat(result).isNotNull();
        assertThat(result.getAction()).isEqualTo(Action.FOUND);
        assertThat(result.getSourceKey()).isEqualTo(dataRequest.getSourceKey());
        assertThat(result.getPayload().getData()).isEqualTo(expectedResult);
    }

    private void formValidation() {
        source.subscribe(request -> System.out.println(mapIt.apply(request)));
    }

    private Future<DataRequest> formCallback(final DataRequest dataRequest) {
        return source
                .filter(request -> request.getAction() == Action.FOUND)
                .filter(request -> request.getSourceKey().equals(dataRequest.getSourceKey()))
                .first().toBlocking().toFuture();
    }

    private class WeatherRequest implements Payload {

        private String cityId = "";
        private String expectedResult;

        public WeatherRequest(final String cityId) {
            this.cityId = cityId;
        }

        public WeatherRequest(final String cityId, final String expectedResult) {
            this.cityId = cityId;
            this.expectedResult = expectedResult;
        }

        @Override
        public String getKey() {
            return this.cityId;
        }

        @Override
        public Object getData() {
            return expectedResult;
        }
    }

}
