package com.roozen.core.dto;

import org.apache.http.annotation.Immutable;

import javax.validation.constraints.NotNull;

@Immutable
public class DataRequest {

    private Action action;
    private Payload payload;
    private Object data;

    public DataRequest() { }

    public DataRequest(@NotNull final Action action,
                       @NotNull final Payload payload,
                       @NotNull final Object data) {
        this.action = action;
        this.payload = payload;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public Payload getPayload() {
        return payload;
    }

    public Object getData() {
        return data;
    }
}
