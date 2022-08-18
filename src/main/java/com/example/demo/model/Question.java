package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="questions")
public class Question {
	@Id
	@SequenceGenerator(
			name="question_sequence",
			sequenceName="question_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "question_sequence"
			)
    private Long id;
	private String ques;
	public Question(String ques) {
		super();
		this.ques = ques;
	}
	public Question() {
		super();
	}
	public String getQues() {
		return ques;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	public Long getId() {
		return id;
	}
}
