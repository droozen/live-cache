package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

public class DataService extends AbstractDataHandler implements DataHandler {

    private final Logger logger = Logger.getLogger(DataService.class);
    private final PublishSubject<DataRequest> handled = PublishSubject.create();
    private final List<Action> acceptedActions = Arrays.asList(Action.FETCH, Action.STORE);

    @Override
    public void handleDataRequest(final DataRequest request) {
        markHandled(request);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public PublishSubject<DataRequest> getHandled() {
        return handled;
    }

    @Override
    public List<Action> getAcceptedActions() {
        return acceptedActions;
    }
}
