package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class CacheService {

    @Autowired
    DataService dataService;

    PublishSubject<DataRequest> handled = PublishSubject.create();

    private List<Action> acceptedActions = Arrays.asList(Action.CHECK, Action.CACHE, Action.RETRIEVE);

    @PostConstruct
    public void init() {
        dataService.getBroker()
                .filter(this::filterForCache)
                .subscribe((request) -> {
                    // TODO: Handle Request
                    handled.onNext(request);
                });
    }

    private boolean filterForCache(final DataRequest request) {
        return acceptedActions.contains(request.getAction());
    }
}
