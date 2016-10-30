package com.roozen.core.service;

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

}
