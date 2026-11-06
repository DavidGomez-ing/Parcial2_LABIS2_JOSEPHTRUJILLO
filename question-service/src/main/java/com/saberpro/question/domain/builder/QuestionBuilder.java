package com.saberpro.question.domain.builder;

import com.saberpro.question.domain.exception.InvalidQuestionException;
import com.saberpro.question.domain.model.AnswerOption;
import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.model.QuestionStatus;
import java.util.ArrayList;
import java.util.List;

public class QuestionBuilder implements IQuestionBuilder {

    private static final int REQUIRED_OPTIONS = 4;

    private String statement;
    private String justification;
    private final List<AnswerOption> options = new ArrayList<>();

    @Override
    public IQuestionBuilder withStatement(String statement) {
        this.statement = statement;
        return this;
    }

    @Override
    public IQuestionBuilder withJustification(String justification) {
        this.justification = justification;
        return this;
    }

    @Override
    public IQuestionBuilder addOption(String text, boolean correct) {
        options.add(new AnswerOption(null, text, correct));
        return this;
    }

    /** Valida la estructura del reactivo ANTES de instanciar el objeto de dominio. */
    @Override
    public Question build() {
        if (statement == null || statement.isBlank()) {
            throw new InvalidQuestionException("El enunciado es obligatorio");
        }
        if (justification == null || justification.isBlank()) {
            throw new InvalidQuestionException("La justificación es obligatoria");
        }
        if (options.size() != REQUIRED_OPTIONS) {
            throw new InvalidQuestionException(
                    "La pregunta debe tener exactamente " + REQUIRED_OPTIONS + " opciones");
        }
        if (options.stream().anyMatch(o -> o.text() == null || o.text().isBlank())) {
            throw new InvalidQuestionException("Todas las opciones deben tener texto");
        }
        long correctCount = options.stream().filter(AnswerOption::correct).count();
        if (correctCount != 1) {
            throw new InvalidQuestionException("Debe haber exactamente 1 respuesta correcta");
        }
        return new Question(null, statement, justification, options, QuestionStatus.DRAFT);
    }
}
