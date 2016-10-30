package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
abstract class AbstractDataHandler implements DataHandler {

    @Autowired
    DataRequestService service;

    @PostConstruct
    void init() {
        service.getBroker()
                .filter(this::filterForCache)
                .subscribe(
                        this::handleDataRequest,
                        throwable -> getLogger().warn(getErrorMessage(), throwable));
    }

    private String getErrorMessage() {
        return String.format("Unexpected %s error.", this.getClass().getSimpleName());
    }

    private boolean filterForCache(final DataRequest request) {
        return getAcceptedActions().contains(request.getAction());
    }

    void markHandled(final DataRequest request) {
        final DataRequest handledRequest = new DataRequest(getHandled(), Action.HANDLED, request.getData());
        request.getSource().onNext(handledRequest);
        getHandled().onNext(request);
    }

}
