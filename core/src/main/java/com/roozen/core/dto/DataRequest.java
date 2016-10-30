package com.roozen.core.dto;

import org.apache.commons.lang.Validate;
import org.apache.http.annotation.Immutable;
import rx.subjects.PublishSubject;

import java.util.LinkedHashSet;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Stream;

@Immutable
public final class DataRequest {

    private static final PublishSubject<DataRequest> GENERIC_SOURCE = PublishSubject.create();
    private static final Payload GENERIC_DATA = new StringKeyValuePair("", "");

    private String sourceKey = UUID.randomUUID().toString();
    private PublishSubject<DataRequest> source = GENERIC_SOURCE;
    private Action action = Action.ERROR;
    private Payload payload = GENERIC_DATA;
    private Stack<String> stack = new Stack<>();

    public DataRequest() { }

    public DataRequest(final PublishSubject<DataRequest> source, final Action action, final Payload payload) {
        Validate.notNull(source, "Missing source for data request.");
        Validate.notNull(action, "Missing action for data request.");
        Validate.notNull(payload, "Missing data for data request.");

        this.source = source;
        this.action = action;
        this.payload = payload;
    }

    public final String getSourceKey() {
        return sourceKey;
    }

    public final PublishSubject<DataRequest> getSource() {
        return source;
    }

    public final Action getAction() {
        return action;
    }

    public final void setAction(final Action action) {
        this.action = action;
    }

    public final Payload getPayload() {
        return payload;
    }

    public final void setPayload(final Payload payload) {
        this.payload = payload;
    }

    public final Stream<String> getStackStream() {
        return stack.stream();
    }

    public final void addStack(final String trace) {
        if (trace != null) {
            stack.add(trace);
        }
    }

}
