package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CacheService extends AbstractDataHandler implements DataHandler {

    private final Logger logger = Logger.getLogger(CacheService.class);
    private final PublishSubject<DataRequest> handled = PublishSubject.create();
    private final List<Action> acceptedActions = Arrays.asList(Action.CHECK, Action.CACHE, Action.RETRIEVE);

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
        return new ArrayList<>(acceptedActions);
    }
}
