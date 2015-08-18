package com.logging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logging.user.events.EventLogInfo;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class EventLogHandler {
    private static final Logger log = LoggerFactory.getLogger(EventLogHandler.class);

    @Autowired
    private MongoTemplate template;

    @Autowired
    private ObjectMapper mapper;

    public void handleMessage(EventLogInfo eventLogInfo) {
        log.debug("Received: " + eventLogInfo);
        // Any logic to validate user events before persisting to MongoDB
        DBCollection collection = template.getCollection("events");
        try {
            collection.insert((DBObject) JSON.parse(mapper.writeValueAsString(eventLogInfo)));
        } catch (Exception e) {
            log.error("message : {}", e.getMessage(), e);
        }
    }

}