package com.saberpro.question.domain.model;

import com.saberpro.question.domain.state.IQuestionState;
import java.util.List;

/** Entidad de dominio. Java puro: sin @Entity, @Table ni @Id. Es el "Context" del patrón State. */
public class Question {

    private Long id;
    private final String statement;
    private final String justification;
    private final List<AnswerOption> options;
    private IQuestionState state;

    public Question(Long id, String statement, String justification,
                    List<AnswerOption> options, QuestionStatus status) {
        this.id = id;
        this.statement = statement;
        this.justification = justification;
        this.options = List.copyOf(options);
        this.state = status.newState();
    }

    /** Delegación al estado actual: no hay condicionales. */
    public void submitForReview() {
        state.submitForReview(this);
    }

    public void changeState(IQuestionState newState) {
        this.state = newState;
    }

    public QuestionStatus getStatus() { return state.status(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatement() { return statement; }
    public String getJustification() { return justification; }
    public List<AnswerOption> getOptions() { return options; }
}
