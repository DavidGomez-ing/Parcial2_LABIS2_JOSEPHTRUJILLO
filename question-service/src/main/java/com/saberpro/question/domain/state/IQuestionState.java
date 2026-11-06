package com.saberpro.question.domain.state;

import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.model.QuestionStatus;

public interface IQuestionState {
    QuestionStatus status();
    void submitForReview(Question question);
}
