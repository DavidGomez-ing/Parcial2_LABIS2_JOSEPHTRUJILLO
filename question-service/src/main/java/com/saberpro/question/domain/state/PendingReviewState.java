package com.saberpro.question.domain.state;

import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.model.QuestionStatus;

public class PendingReviewState implements IQuestionState {

    @Override
    public QuestionStatus status() { return QuestionStatus.PENDING_REVIEW; }

    @Override
    public void submitForReview(Question question) {
        throw new IllegalStateException("La pregunta ya está en estado PENDING_REVIEW");
    }
}
