package com.saberpro.question.infrastructure.jpa;

import com.saberpro.question.domain.model.Question;
import com.saberpro.question.domain.repository.QuestionRepository;
import com.saberpro.question.infrastructure.jpa.record.QuestionRecord;
import com.saberpro.question.infrastructure.mapper.QuestionDomainMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuestionRepositoryJpaImpl implements QuestionRepository {

    private final SpringQuestionDataRepository dataRepository;
    private final QuestionDomainMapper mapper;

    public QuestionRepositoryJpaImpl(SpringQuestionDataRepository dataRepository,
                                     QuestionDomainMapper mapper) {
        this.dataRepository = dataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Question save(Question question) {
        QuestionRecord saved = dataRepository.save(mapper.toRecord(question));
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Question> findById(Long id) {
        return dataRepository.findById(id).map(mapper::toDomain);
    }
}
