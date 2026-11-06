package com.saberpro.question.infrastructure.jpa.record;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class QuestionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String statement;

    @Column(nullable = false, length = 1000)
    private String justification;

    @Column(nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<AnswerOptionRecord> options = new ArrayList<>();

    public QuestionRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<AnswerOptionRecord> getOptions() { return options; }
    public void setOptions(List<AnswerOptionRecord> options) { this.options = options; }
}
