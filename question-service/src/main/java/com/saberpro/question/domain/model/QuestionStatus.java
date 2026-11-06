package com.saberpro.question.domain.model;

import com.saberpro.question.domain.state.DraftState;
import com.saberpro.question.domain.state.IQuestionState;
import com.saberpro.question.domain.state.PendingReviewState;
import java.util.function.Supplier;

/**
 * Enum con fábrica de estados: permite reconstruir el State desde la BD
 * sin if/else ni switch.
 */
public enum QuestionStatus {
    DRAFT(DraftState::new),
    PENDING_REVIEW(PendingReviewState::new);

    private final Supplier<IQuestionState> factory;

    QuestionStatus(Supplier<IQuestionState> factory) {
        this.factory = factory;
    }

    public IQuestionState newState() {
        return factory.get();
    }
}
