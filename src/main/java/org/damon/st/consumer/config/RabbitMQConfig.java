package org.damon.st.consumer.config;

import org.damon.st.consumer.dto.UserOperationDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Exchange userInfoExchange() {
        return new DirectExchange("user-info-exchange");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(
                "org.damon.st.producer.dto.UserOperationDto", UserOperationDto.class);
        classMapper.setIdClassMapping(idClassMapping);
        messageConverter.setClassMapper(classMapper);

        return messageConverter;
    }
}