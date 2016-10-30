package com.roozen.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roozen.core.dto.DataRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * We watch the watchers.
 * https://en.wikipedia.org/wiki/Who_Watches_the_Watchers
 */
@Service
public class MintakaService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(MintakaService.class);

    @Autowired
    WatcherService watcherService;

    @PostConstruct
    void init() {
        watcherService.getHandled()
                .subscribe(
                        this::monitor,
                        throwable -> logger.warn("Unexpected Mintaka Error. Watching the Watcher Failed.", throwable));
    }

    private void monitor(final DataRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug(map(request));
        }
    }

    private String map(final DataRequest request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
