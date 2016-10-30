package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import com.roozen.core.dto.Payload;
import rx.subjects.PublishSubject;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDataHandlerTest {

    protected abstract PublishSubject<DataRequest> getHandled();

    protected abstract PublishSubject<DataRequest> getBroker();

    protected abstract List<Action> getAcceptedActions();

    protected abstract PublishSubject<DataRequest> getSource();

    protected DataRequest request(final Action action) {
        return new DataRequest(getSource(), action, new MockPayload());
    }

    protected AtomicInteger subscribeToHandledAndAssertAccepted() {
        final AtomicInteger numHandled = new AtomicInteger();
        getHandled().subscribe(request -> {
            numHandled.getAndIncrement();
            assertThat(getAcceptedActions()).contains(request.getAction());
        });
        return numHandled;
    }

    protected AtomicInteger subscribeToBrokerAndCountMessages() {
        final AtomicInteger numReceived = new AtomicInteger();
        getBroker().subscribe(request -> numReceived.getAndIncrement());
        return numReceived;
    }

    class MockPayload implements Payload {

        @Override
        public String getKey() {
            return null;
        }

        @Override
        public Object getData() {
            return null;
        }
    }
}
