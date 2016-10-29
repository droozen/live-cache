package com.roozen.core.service;

import com.roozen.core.dto.DataRequest;
import org.springframework.stereotype.Service;
import rx.subjects.PublishSubject;

@Service
public class DataService {

    private final PublishSubject<DataRequest> broker = PublishSubject.create();

    public PublishSubject<DataRequest> getBroker() {
        return broker;
    }

}
