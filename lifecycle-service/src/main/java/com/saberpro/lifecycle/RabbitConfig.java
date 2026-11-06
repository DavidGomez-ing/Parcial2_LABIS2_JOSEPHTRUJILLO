package com.saberpro.lifecycle;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "saberpro.direct.exchange";
    public static final String QUEUE = "question.submit.review.queue";
    public static final String ROUTING_KEY = "question.submit";

    @Bean DirectExchange saberproExchange() { return new DirectExchange(EXCHANGE); }
    @Bean Queue submitReviewQueue() { return new Queue(QUEUE, true); }
    @Bean Binding submitBinding(Queue submitReviewQueue, DirectExchange saberproExchange) {
        return BindingBuilder.bind(submitReviewQueue).to(saberproExchange).with(ROUTING_KEY);
    }
    @Bean MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }
    @Bean RestTemplate restTemplate() { return new RestTemplate(); }
}
