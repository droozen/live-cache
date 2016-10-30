package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import com.roozen.core.dto.Payload;
import com.roozen.core.dto.StringKeyValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService extends AbstractDataHandler implements DataHandler {

    private final Logger logger = Logger.getLogger(CacheService.class);
    private final PublishSubject<DataRequest> handled = PublishSubject.create();
    private final List<Action> acceptedActions = Arrays.asList(Action.CHECK, Action.CACHE, Action.RETRIEVE);

    final Map<String, Payload> inMemoryCache = new ConcurrentHashMap<>();

    @Override
    public void handleDataRequest(final DataRequest request) {
        markCaught(request);

        checkTypeAndRouteRequest(request);

        markHandled(request);
    }

    private void checkTypeAndRouteRequest(final DataRequest request) {
        switch (request.getAction()) {
            case CHECK:
                checkForCachePresence(request);
                break;
            case CACHE:
                storeInCache(request);
                break;
            case RETRIEVE:
                retrieveData(request);
                break;
            default:
                sendNotImplemented(request);
                break;
        }
    }

    private void checkForCachePresence(final DataRequest request) {
        if (inMemoryCache.containsKey(request.getPayload().getKey())) {
            cacheHit(request);
        } else {
            cacheMiss(request);
        }
    }

    private void cacheHit(final DataRequest request) {
        request.setAction(Action.RETRIEVE);
        send(request);
    }

    private void cacheMiss(final DataRequest request) {
        request.setAction(Action.FETCH);
        send(request);
    }

    private void storeInCache(final DataRequest request) {
        store(request);
        request.setAction(Action.RETRIEVE);
        send(request);
    }

    private void retrieveData(final DataRequest request) {
        retrieveAndPrepare(request);
        send(request);
        respond(request);
    }

    private void sendNotImplemented(final DataRequest request) {
        request.setAction(Action.ERROR);
        request.setPayload(new StringKeyValuePair("ERROR", "Action not implemented in CacheService.class"));
        send(request);
        respond(request);
    }

    private void retrieveAndPrepare(final DataRequest request) {
        final Payload payload = inMemoryCache.get(request.getPayload().getKey());
        request.setPayload(payload);
        request.setAction(Action.FOUND);
    }

    private void store(final DataRequest request) {
        final Payload payload = request.getPayload();
        inMemoryCache.put(payload.getKey(), payload);
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
