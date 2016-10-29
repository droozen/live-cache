package com.roozen.core.dto;

import org.apache.commons.lang.Validate;
import org.apache.http.annotation.Immutable;

@Immutable
public final class DataRequest {

    private Action action;
    private Payload payload;
    private Object data;

    public DataRequest() { }

    public DataRequest(final Action action, final Payload payload, final Object data) {
        Validate.notNull(action, "Missing action for request.");
        Validate.notNull(payload, "Missing payload for request.");
        Validate.notNull(data, "Missing data for request.");

        this.action = action;
        this.payload = payload;
        this.data = data;
    }

    public final Action getAction() {
        return action;
    }

    public final Payload getPayload() {
        return payload;
    }

    public final Object getData() {
        return data;
    }
}
