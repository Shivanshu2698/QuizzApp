package com.springboot.quizz.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springboot.quizz.app.model.Quiz;
import com.springboot.quizz.app.repository.QuizRepository;


@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
    private QuizRepository quizRepository;
	
	@Override
	public Quiz createQuiz(Quiz quiz) {
	    LocalDateTime currentDate = LocalDateTime.now();
	    quiz.setStartDate(currentDate);  
	    quiz.setEndDate(currentDate.plusMinutes(5));  

	    quiz.setStatus(getQuizStatus(quiz));  
	    return quizRepository.save(quiz);
	}

	@Override
	@Cacheable("activeQuizzes")
	public List<Quiz> getActiveQuiz() {
	    List<Quiz> quizzes = quizRepository.findAll();
	    List<Quiz> activeQuizzes = new ArrayList<>();

	    for (Quiz quiz : quizzes) {
	        if (quiz.getStatus().equals("active")) {
	            activeQuizzes.add(quiz);
	        }
	    }
	    return activeQuizzes;
	}
	
	
	@Override
	public int getQuizResult(long id) {
	    Quiz quiz = quizRepository.findById(id).orElse(null);
	    if (quiz == null) {
	        throw new IllegalArgumentException("Quiz not found");
	    } else if (!quiz.getStatus().equals("finished")) {
	        throw new IllegalStateException("Quiz is not finished yet");
	    }
	    return quiz.getRightAnswer();
	}
	
	@Override
	public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

	@Override
	@Scheduled(fixedDelay = 60000) // Run every minute
	@CachePut("activeQuizzes")
	@CacheEvict(value = "activeQuizzes", allEntries = true)
	public void updateQuizStatus() {
	    List<Quiz> quizzes = quizRepository.findAll();
	    LocalDateTime currentDate = LocalDateTime.now();
	    
	    for (Quiz quiz : quizzes) {
	        LocalDateTime startDate = quiz.getStartDate();
	        LocalDateTime endDate = quiz.getEndDate();
	        
	        if (currentDate.isBefore(startDate)) {
	            quiz.setStatus("inactive");
	        } else if (currentDate.isAfter(endDate)) {
	            quiz.setStatus("finished");
	        } else {
	            quiz.setStatus("active");
	        }
	        quizRepository.save(quiz);
	    }
	}


	
	private String getQuizStatus(Quiz quiz) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(quiz.getStartDate())) {
            return "inactive";
        } else if (currentDate.isAfter(quiz.getEndDate())) {
            return "finished";
        } else {
            return "active";
        }
    }

}
