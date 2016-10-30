package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.junit.Before;
import org.junit.Test;
import rx.subjects.PublishSubject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class DataServiceTest extends AbstractDataHandlerTest {

    private PublishSubject<DataRequest> source = PublishSubject.create();
    private PublishSubject<DataRequest> broker;
    private PublishSubject<DataRequest> handled;
    private List<Action> acceptedActions;

    @Before
    public void init() {
        final DataService service = new DataService();
        final DataRequestService dataRequestService = new DataRequestService();

        service.service = dataRequestService;
        broker = dataRequestService.getBroker();
        handled = service.getHandled();
        acceptedActions = service.getAcceptedActions();

        service.init();
    }

    @Test
    public void testAcceptableRequestTypes() {
        final AtomicInteger numHandled = subscribeToHandledAndAssertAccepted();
        final AtomicInteger numReceived = subscribeToBrokerAndCountMessages();

        // EXECUTE
        Arrays.stream(Action.values()).forEach(action -> broker.onNext(request(action)));

        // VERIFY
        assertThat(numReceived.get()).isEqualTo(Action.values().length);
        assertThat(numHandled.get()).isEqualTo(2);
    }

    @Override
    protected PublishSubject<DataRequest> getHandled() {
        return handled;
    }

    @Override
    protected PublishSubject<DataRequest> getBroker() {
        return broker;
    }

    @Override
    protected List<Action> getAcceptedActions() {
        return acceptedActions;
    }

    @Override
    protected PublishSubject<DataRequest> getSource() {
        return source;
    }
}
