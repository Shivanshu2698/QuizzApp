package com.springboot.quizz.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.quizz.app.model.Quiz;
import com.springboot.quizz.app.service.QuizService;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
	
	@Autowired
	private QuizService quizService;

	public QuizController(QuizService quizService) {
		super();
		this.quizService = quizService;
	}
	
	@PostMapping
	public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz) {
		try{
			Quiz createdQuiz = quizService.createQuiz(quiz);
			return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>("Invalid quiz data", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveQuiz() {
		try{
			List<Quiz> activeQuiz = quizService.getActiveQuiz();
			if (activeQuiz != null) {
				return new ResponseEntity<>(activeQuiz, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No active quiz found", HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 @GetMapping("/{id}/result")
	    public ResponseEntity<?> getQuizResult(@PathVariable("id") Long id) {
	        try {
	            int quizResult = quizService.getQuizResult(id);
	            return new ResponseEntity<>(quizResult, HttpStatus.OK);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>("Invalid quiz ID", HttpStatus.BAD_REQUEST);
	        } catch (IllegalStateException e) {
	            return new ResponseEntity<>("Quiz is not finished yet", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	 @GetMapping("/all")
	    public ResponseEntity<?> getAllQuizzes() {
	        try {
	            List<Quiz> allQuizzes = quizService.getAllQuizzes();
	            return new ResponseEntity<>(allQuizzes, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
