package com.saberpro.question.domain.state;

import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.model.QuestionStatus;

public class DraftState implements IQuestionState {

    @Override
    public QuestionStatus status() { return QuestionStatus.DRAFT; }

    @Override
    public void submitForReview(Question question) {
        question.changeState(new PendingReviewState());
    }
}
