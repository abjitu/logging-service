package com.logging.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.logging.app.log.ApplicationLogInfo;
import com.logging.consumer.EventLogHandler;
import com.logging.user.events.EventLogInfo;

@Configuration
@ComponentScan("com.logging")
public class LoggingRabbitMQConfig {
    private @Value("${logging.rabbit_host}") String rabbitHost;
    private @Value("${logging.rabbit_port}") int rabbitPort;
    private @Value("${logging.rabbit_username}") String rabbitUser;
    private @Value("${logging.rabbit_password}") String rabbitPassword;
    private @Value("${logging.rabbit_exchange_name}") String logExchange;

    private @Value("${logging.event.rabbit_queue_name}") String eventQueue;
    
    private @Value("${logging.event.concurrent_consumers}") int concurrentConsumers;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate applicationLogAMQP() {
        RabbitTemplate applicationLogAMQP = new RabbitTemplate(connectionFactory());
        applicationLogAMQP.setExchange(logExchange);
        applicationLogAMQP.setMessageConverter(jackson2JsonMessageConverter());
        return applicationLogAMQP;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper());
        return jackson2JsonMessageConverter;
    }

    @Bean
    public DefaultClassMapper defaultClassMapper() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
        idClassMapping.put("eventLogInfo", EventLogInfo.class);
        idClassMapping.put("appLogInfo", ApplicationLogInfo.class);
        typeMapper.setIdClassMapping(idClassMapping);

        defaultClassMapper.setDefaultType(ApplicationLogInfo.class);
        return defaultClassMapper;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(eventQueue);
        container.setConcurrentConsumers(concurrentConsumers);
        container.setMaxConcurrentConsumers(concurrentConsumers);
        container.setMessageListener(new MessageListenerAdapter(eventLogHandler(), jackson2JsonMessageConverter()));
        return container;
    }

    @Bean
    public EventLogHandler eventLogHandler() {
        return new EventLogHandler();
    }
}
