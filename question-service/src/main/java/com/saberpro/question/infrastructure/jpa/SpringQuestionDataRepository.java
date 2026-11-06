package com.saberpro.question.infrastructure.jpa;

import com.saberpro.question.infrastructure.jpa.record.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringQuestionDataRepository extends JpaRepository<QuestionRecord, Long> {
}
