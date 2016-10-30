package com.roozen.core.dto;

/**
 * A special type of Payload where the value is guaranteed to be a {@code String}.
 */
public final class StringKeyValuePair implements Payload {

    private String key = "";
    private String value = "";

    public StringKeyValuePair() { }

    public StringKeyValuePair(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public final String getKey() {
        return this.key;
    }

    @Override
    public final Object getData() {
        return this.key;
    }
}
