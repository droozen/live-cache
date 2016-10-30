package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import com.roozen.core.dto.Payload;
import org.junit.Before;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheServiceTest {

    private CacheService service;
    private DataRequestService dataService;

    private PublishSubject<DataRequest> source = PublishSubject.create();
    private PublishSubject<DataRequest> broker;
    private PublishSubject<DataRequest> handled;
    private List<Action> acceptedActions;

    @Before
    public void init() {
        dataService = new DataRequestService();
        service = new CacheService();
        service.service = dataService;
        broker = dataService.getBroker();
        handled = service.getHandled();
        acceptedActions = service.getAcceptedActions();

        service.init();
    }

    @Test
    public void testAcceptableRequestTypes() {
        final AtomicInteger numHandled = subscribeToHandledAndAssertAccepted();
        final AtomicInteger numReceived = subscribeToBrokerAndCountMessages();

        Arrays.stream(Action.values()).forEach(action -> broker.onNext(request(action)));

        assertThat(numReceived.get()).isEqualTo(Action.values().length);
        assertThat(numHandled.get()).isEqualTo(3);
    }

    private AtomicInteger subscribeToHandledAndAssertAccepted() {
        final AtomicInteger numHandled = new AtomicInteger();
        handled.subscribe(request -> {
            numHandled.getAndIncrement();
            assertThat(acceptedActions).contains(request.getAction());
        });
        return numHandled;
    }

    private AtomicInteger subscribeToBrokerAndCountMessages() {
        final AtomicInteger numReceived = new AtomicInteger();
        broker.subscribe(request -> numReceived.getAndIncrement());
        return numReceived;
    }

    private DataRequest request(final Action action) {
        return new DataRequest(source, action, new Object());
    }

}
