package com.saberpro.question.web;

import com.saberpro.question.application.QuestionService;
import com.saberpro.question.application.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) { this.service = service; }

    @PostMapping("/questions")
    public ResponseEntity<QuestionResponse> create(@RequestBody CreateQuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(QuestionResponse.from(service.create(request)));
    }

    @GetMapping("/questions/{id}")
    public QuestionResponse get(@PathVariable Long id) {
        return QuestionResponse.from(service.get(id));
    }

    @PostMapping("/questions/{id}/submit")
    public ResponseEntity<OperationResponse> submit(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.submit(id));
    }

    /** Endpoint interno usado por Lifecycle Service. */
    @PutMapping("/internal/questions/{id}/pending-review")
    public QuestionResponse pendingReview(@PathVariable Long id) {
        return QuestionResponse.from(service.moveToPendingReview(id));
    }
}
