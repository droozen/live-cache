package com.roozen.core.dto;

public enum Action {

    /**
     * Indicates back to the source that the request was handled.
     */
    HANDLED,
    /**
     * Check if the requested data is already in the cache.
     */
    CHECK,
    /**
     * Fetch the data from the data source.
     */
    FETCH,
    /**
     * Store the data with persistence.
     */
    STORE,
    /**
     * Put the data in the cache.
     */
    CACHE,
    /**
     * Retrieve the data from the cache and serve.
     */
    RETRIEVE,
    /**
     * Found the requested object.
     */
    FOUND,
    /**
     * Indicates an error happened in processing.
     */
    ERROR,
    /**
     * Indicates an unknown action.
     */
    UNKNOWN;

}
