package com.roozen.core.service;

import com.roozen.core.dto.Action;
import com.roozen.core.dto.DataRequest;
import com.roozen.core.dto.Payload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;

@Service
public class DataService {

    private final PublishSubject<DataRequest> broker = PublishSubject.create();

    public PublishSubject<DataRequest> getBroker() {
        return broker;
    }

}
