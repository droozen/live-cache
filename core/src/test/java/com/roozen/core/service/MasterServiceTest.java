package com.roozen.core.service;

import com.roozen.core.dto.DataRequest;
import org.junit.Before;
import org.junit.Test;
import rx.Subscription;
import rx.subjects.PublishSubject;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MasterServiceTest {

    private MasterService service;
    private PublishSubject<DataRequest> broker;
    private AtomicInteger numReceived;

    @Before
    public void init() {
        service = new MasterService();
        broker = service.getBroker();
        numReceived = new AtomicInteger();
    }

    @Test
    public void testBroker_justOne() {
        final int expected = 1;
        subscribe(numReceived);

        broker.onNext(new DataRequest());

        assertThat(numReceived.get()).isEqualTo(expected);
    }

    @Test
    public void testBroker_several() {
        final int expected = 5;
        subscribe(numReceived);

        IntStream.range(0, expected).forEach(each -> broker.onNext(new DataRequest()));

        assertThat(numReceived.get()).isEqualTo(expected);
    }

    @Test
    public void testBroker_onlyNew() {
        broker.onNext(new DataRequest());

        final Subscription initialSubscription = subscribe(numReceived);
        assertThat(numReceived.get()).isEqualTo(0);

        sendAndAssert(2, 2);

        final Subscription finalSubscription = subscribe(numReceived);
        // 2 previous requests. 2 subscribers for 3 new requests. 8 total requests.
        sendAndAssert(3, 8);

        initialSubscription.unsubscribe();
        // 8 previous requests. 1 subscriber for 1 new requests. 9 total requests.
        sendAndAssert(1, 9);
    }

    private Subscription subscribe(final AtomicInteger numReceived) {
        return broker.subscribe(request -> numReceived.getAndIncrement());
    }

    private void sendAndAssert(final int numToSend, final int numToAssert) {
        IntStream.range(0, numToSend).forEach(each -> broker.onNext(new DataRequest()));
        assertThat(numReceived.get()).isEqualTo(numToAssert);
    }

}
