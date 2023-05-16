package com.springboot.quizz.app.service;

import java.util.List;

import com.springboot.quizz.app.model.Quiz;

public interface QuizService {
	public Quiz createQuiz(Quiz quiz);
	public List<Quiz> getActiveQuiz();
	public int getQuizResult(long id);
	public List<Quiz> getAllQuizzes();
	public void updateQuizStatus();
	
}
