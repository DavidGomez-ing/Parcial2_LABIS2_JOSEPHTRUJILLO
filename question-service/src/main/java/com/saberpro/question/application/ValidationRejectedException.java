package com.saberpro.question.application;

public class ValidationRejectedException extends RuntimeException {
    public ValidationRejectedException(String message) { super(message); }
}
