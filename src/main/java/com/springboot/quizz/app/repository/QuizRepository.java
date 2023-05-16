package com.springboot.quizz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.quizz.app.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
