package com.saberpro.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SubmitReviewListener {

    private static final Logger log = LoggerFactory.getLogger(SubmitReviewListener.class);

    private final RestTemplate restTemplate;
    private final String questionServiceUrl;

    public SubmitReviewListener(RestTemplate restTemplate,
                                @Value("${question.service-url}") String questionServiceUrl) {
        this.restTemplate = restTemplate;
        this.questionServiceUrl = questionServiceUrl;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onSubmit(SubmitQuestionForReview command) {
        log.info("Comando recibido operationId={} questionId={}", command.operationId(), command.questionId());
        restTemplate.put(questionServiceUrl + "/internal/questions/{id}/pending-review",
                null, command.questionId());
        log.info("Pregunta {} -> PENDING_REVIEW (operationId={})", command.questionId(), command.operationId());
    }
}
