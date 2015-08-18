package com.logging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logging.Response;
import com.logging.Response.ResultType;
import com.logging.app.log.ApplicationLogInfo;
import com.logging.user.events.EventLogInfo;

@RestController
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    private @Value("${logging.app.rabbit_routing_key}") String appRoutingKey;

    private @Value("${logging.event.rabbit_routing_key}") String eventRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String ping() {
        log.debug("Entering ping");
        return "Logging service is up and running!!!";
    }

    @RequestMapping(value = "/applog", method = RequestMethod.POST)
    public Response applicationLog(@RequestBody ApplicationLogInfo appLogInfo) {
        log.debug("Inside applicationLog");
        rabbitTemplate.convertAndSend(appRoutingKey, appLogInfo);
        return Response.get(ResultType.SUCCESS);
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public Response logUserEvent(@RequestBody EventLogInfo eventLogInfo) {
        log.debug("Inside logUserEvent");
        rabbitTemplate.convertAndSend(eventRoutingKey, eventLogInfo);
        return Response.get(ResultType.SUCCESS);
    }
}
