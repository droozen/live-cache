package com.roozen.core.dto;

import org.apache.commons.lang.Validate;
import org.apache.http.annotation.Immutable;
import rx.subjects.PublishSubject;

@Immutable
public final class DataRequest {

    private static final PublishSubject<DataRequest> GENERIC_SOURCE = PublishSubject.create();
    private static final Object GENERIC_DATA = new Object();

    private PublishSubject<DataRequest> source = GENERIC_SOURCE;
    private Action action = Action.ERROR;
    private Object data = GENERIC_DATA;

    public DataRequest() { }

    public DataRequest(final PublishSubject<DataRequest> source, final Action action, final Object data) {
        Validate.notNull(source, "Missing source for data request.");
        Validate.notNull(action, "Missing action for data request.");
        Validate.notNull(data, "Missing data for data request.");

        this.source = source;
        this.action = action;
        this.data = data;
    }

    public PublishSubject<DataRequest> getSource() {
        return source;
    }

    public final Action getAction() {
        return action;
    }

    public final Object getData() {
        return data;
    }
}
