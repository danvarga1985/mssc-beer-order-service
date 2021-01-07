package com.danvarga.msscbeerorderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter messageConverter() {
        // Conversion goes both ways: Object -> JSON, JSON -> Object.
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        // Header property.
        converter.setTypeIdPropertyName("_type");

        return converter;
    }
}
