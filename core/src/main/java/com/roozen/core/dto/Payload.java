package com.roozen.core.dto;

public interface Payload {

    /**
     * Payload identifier.
     * This key may be used as an ID in a database or a key in a {@code Map}.
     *
     * @return {@link String}
     */
    String getKey();

    /**
     * Payload data.
     * What this object is depends on the type of cache we're implementing.
     *
     * @return {@link Object}
     */
    Object getData();

}
