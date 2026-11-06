package com.saberpro.question.application.dto;

import java.util.List;

public record CreateQuestionRequest(String statement, String justification, List<OptionRequest> options) {}
