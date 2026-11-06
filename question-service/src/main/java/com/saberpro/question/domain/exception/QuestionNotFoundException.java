package com.saberpro.question.domain.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) { super("Pregunta no encontrada: " + id); }
}
