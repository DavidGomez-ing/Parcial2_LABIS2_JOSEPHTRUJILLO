package com.saberpro.question.application.dto;

import com.saberpro.question.domain.model.Question;
import java.util.List;

public record QuestionResponse(Long id, String statement, String justification,
                               String status, List<OptionRequest> options) {
    public static QuestionResponse from(Question q) {
        return new QuestionResponse(q.getId(), q.getStatement(), q.getJustification(),
                q.getStatus().name(),
                q.getOptions().stream().map(o -> new OptionRequest(o.text(), o.correct())).toList());
    }
}
