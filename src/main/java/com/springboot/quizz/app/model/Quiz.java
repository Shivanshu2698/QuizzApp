package com.springboot.quizz.app.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="quizzes")
public class Quiz {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "question")
	private String question;
	
	@ElementCollection
	@Column(name = "options")
	private List<String> options;
	
	@Column(name = "right_answer")
	private int rightAnswer;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	
	@Column(name = "status")
	private String status;
	
	
	public Quiz() {
		super();
	}
	
}
