package com.example.quizapp;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface questionRepository extends JpaRepository<question,Long> {
    question findByques(String ques);

	
    }
