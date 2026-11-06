package com.saberpro.question.domain.model;

/** Opción de respuesta. Java puro (sin anotaciones de persistencia). */
public record AnswerOption(Long id, String text, boolean correct) {}
