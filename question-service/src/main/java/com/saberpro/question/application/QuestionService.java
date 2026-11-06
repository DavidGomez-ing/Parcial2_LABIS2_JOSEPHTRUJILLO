package com.saberpro.question.application;

import com.saberpro.question.application.dto.*;
import com.saberpro.question.config.RabbitConfig;
import com.saberpro.question.domain.builder.IQuestionBuilder;
import com.saberpro.question.domain.builder.QuestionBuilder;
import com.saberpro.question.domain.exception.QuestionNotFoundException;
import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.repository.QuestionRepository;
import com.saberpro.question.messaging.SubmitQuestionForReview;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final String validationUrl;

    public QuestionService(QuestionRepository repository, RestTemplate restTemplate,
                           RabbitTemplate rabbitTemplate,
                           @Value("${validation.url}") String validationUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.validationUrl = validationUrl;
    }

    /** 2.1 REST síncrono: Question -> Validation Service. */
    public Question create(CreateQuestionRequest req) {
        IQuestionBuilder builder = new QuestionBuilder()
                .withStatement(req.statement())
                .withJustification(req.justification());
        if (req.options() != null) {
            req.options().forEach(o -> builder.addOption(o.text(), o.correct()));
        }
        Question question = builder.build();           // valida estructura (4 opciones, 1 correcta)

        ValidationResponse validation = restTemplate.postForObject(
                validationUrl, new ValidationRequest(req.justification()), ValidationResponse.class);
        if (validation == null || !validation.valid()) {
            throw new ValidationRejectedException(
                    validation == null ? "Sin respuesta del servicio de validación" : validation.message());
        }
        return repository.save(question);              // se guarda en DRAFT
    }

    /** 2.2 AMQP asíncrono: publica el comando y responde de inmediato. */
    public OperationResponse submit(Long id) {
        repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        String operationId = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY,
                new SubmitQuestionForReview(operationId, id));
        return new OperationResponse(operationId, "Solicitud de revisión en proceso");
    }

    /** Invocado por Lifecycle Service tras consumir el mensaje: aplica el patrón State. */
    public Question moveToPendingReview(Long id) {
        Question question = repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        question.submitForReview();                    // DraftState -> PendingReviewState
        return repository.save(question);
    }

    public Question get(Long id) {
        return repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
