package com.saberpro.question.infrastructure.mapper;

import com.saberpro.question.domain.model.AnswerOption;
import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.model.QuestionStatus;
import com.saberpro.question.infrastructure.jpa.record.AnswerOptionRecord;
import com.saberpro.question.infrastructure.jpa.record.QuestionRecord;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

/** Mapeador bidireccional Question (dominio) <-> QuestionRecord (JPA). */
@Component
public class QuestionDomainMapper {

    public QuestionRecord toRecord(Question q) {
        QuestionRecord r = new QuestionRecord();
        r.setId(q.getId());
        r.setStatement(q.getStatement());
        r.setJustification(q.getJustification());
        r.setStatus(q.getStatus().name());
        r.setOptions(new ArrayList<>(q.getOptions().stream().map(o -> {
            AnswerOptionRecord a = new AnswerOptionRecord();
            a.setId(o.id());
            a.setText(o.text());
            a.setCorrect(o.correct());
            return a;
        }).toList()));
        return r;
    }

    public Question toDomain(QuestionRecord r) {
        return new Question(
                r.getId(),
                r.getStatement(),
                r.getJustification(),
                r.getOptions().stream()
                        .map(a -> new AnswerOption(a.getId(), a.getText(), a.isCorrect()))
                        .toList(),
                QuestionStatus.valueOf(r.getStatus()));
    }
}
