package com.saberpro.question.messaging;

/** Comando AMQP enviado a Lifecycle Service. */
public record SubmitQuestionForReview(String operationId, Long questionId) {}
