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
public class CacheService {

    @Autowired
    DataService dataService;

    private Logger logger = Logger.getLogger(CacheService.class);
    private PublishSubject<DataRequest> handled = PublishSubject.create();
    private List<Action> acceptedActions = Arrays.asList(Action.CHECK, Action.CACHE, Action.RETRIEVE);

    @PostConstruct
    public void init() {
        dataService.getBroker()
                .filter(this::filterForCache)
                .subscribe(
                        request -> handled.onNext(request),
                        throwable -> logger.warn("Unexpecte cache error.", throwable));
    }

    private boolean filterForCache(final DataRequest request) {
        return acceptedActions.contains(request.getAction());
    }

    public PublishSubject<DataRequest> getHandled() {
        return handled;
    }

    public List<Action> getAcceptedActions() {
        return new ArrayList<>(acceptedActions);
    }
}
