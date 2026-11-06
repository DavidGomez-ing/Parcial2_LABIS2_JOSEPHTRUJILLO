package com.saberpro.question.domain.builder;

import com.saberpro.question.domain.model.Question;

public interface IQuestionBuilder {
    IQuestionBuilder withStatement(String statement);
    IQuestionBuilder withJustification(String justification);
    IQuestionBuilder addOption(String text, boolean correct);
    Question build();
}
