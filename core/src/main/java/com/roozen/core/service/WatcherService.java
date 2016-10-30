package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.apache.log4j.Logger;
import rx.subjects.PublishSubject;

import java.util.Arrays;
import java.util.List;

public class WatcherService extends AbstractDataHandler implements DataHandler {

    private final Logger logger = Logger.getLogger(DataService.class);
    private final PublishSubject<DataRequest> handled = PublishSubject.create();
    private final List<Action> acceptedActions = Arrays.asList(Action.HANDLED, Action.ERROR, Action.UNKNOWN);

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
