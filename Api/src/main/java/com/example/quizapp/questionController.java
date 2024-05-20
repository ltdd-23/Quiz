package com.example.quizapp;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/questions")
public class questionController {
    private final questionRepository QuestionRepository;

    public questionController(questionRepository QuestionRepository) {
        this.QuestionRepository = QuestionRepository;
    }

    @GetMapping
    public List<question> getAllquestion() {
        return QuestionRepository.findAll();
    }

    @PostMapping
    public question createUser(@RequestBody question Question) {
        return QuestionRepository.save(Question);
    }

    @PutMapping("/{id}")
    public question updateUser(@PathVariable Long id, @RequestBody question Question) {
        question existingQuestion = QuestionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        existingQuestion.setA(Question.getA());
        existingQuestion.setB(Question.getB());
        existingQuestion.setC(Question.getC());
        existingQuestion.setD(Question.getD());
        existingQuestion.setQues(Question.getQues());
        existingQuestion.setType(Question.getType());
        existingQuestion.setDifficulty(Question.getDifficulty());
        existingQuestion.setAnswer(Question.getAnswer());

        return QuestionRepository.save(existingQuestion);
    }

    // Các phương thức xóa và các phương thức tùy chỉnh khác
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            QuestionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Question not found");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<question> getQuestionById(@PathVariable Long id) {
        question Question = QuestionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        return ResponseEntity.ok().body(Question);
    }
    @PostMapping("/batch")
    public ResponseEntity<List<question>> createQuestions(@RequestBody List<question> questions) {
        List<question> savedQuestions = QuestionRepository.saveAll(questions);
        return ResponseEntity.ok().body(savedQuestions);
    }

}
