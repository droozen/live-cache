package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.apache.log4j.Logger;
import rx.subjects.PublishSubject;

import java.util.List;

interface DataHandler {

    Logger getLogger();

    PublishSubject<DataRequest> getHandled();

    List<Action> getAcceptedActions();

    void handleDataRequest(DataRequest request);

    default String getErrorMessage() {
        return String.format("Unexpected %s error.", this.getClass().getSimpleName());
    }

    default boolean filterForCache(final DataRequest request) {
        return getAcceptedActions().contains(request.getAction());
    }

    default void markHandled(final DataRequest request) {
        final DataRequest handledRequest = new DataRequest(getHandled(), Action.HANDLED, request.getData());
        request.getSource().onNext(handledRequest);
        getHandled().onNext(request);
    }

}
