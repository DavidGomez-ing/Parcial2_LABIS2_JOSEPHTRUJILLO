package com.saberpro.question.domain.repository;

import com.saberpro.question.domain.model.Question;
import java.util.Optional;

/** Puerto del dominio; la infraestructura lo implementa. */
public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
}
