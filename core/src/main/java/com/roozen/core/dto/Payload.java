package com.roozen.core.dto;

import org.apache.http.annotation.Immutable;

@Immutable
public class Payload {

    private Object generic;

    public Payload() { }

    public Payload(final Object generic) {
        this.generic = generic;
    }

    public Object getGeneric() {
        return generic;
    }
}
