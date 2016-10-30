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

}
