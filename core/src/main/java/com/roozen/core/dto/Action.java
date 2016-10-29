package com.roozen.core.dto;

public enum Action {

    /**
     * Check if the requested data is already in the cache.
     */
    CHECK,
    /**
     * Fetch the data from the data source.
     */
    FETCH,
    /**
     * Put the data in the cache.
     */
    CACHE,
    /**
     * Store the data with persistence.
     */
    STORE,
    /**
     * Retrieve the data from the cache and serve.
     */
    RETRIEVE;

}
