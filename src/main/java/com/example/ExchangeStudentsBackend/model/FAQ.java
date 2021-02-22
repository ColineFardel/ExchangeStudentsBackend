package com.example.ExchangeStudentsBackend.model;

import javax.persistence.*;

@Entity
public class FAQ {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String question;
	private String answer;
	private String status;
	private String tag;

	public FAQ() {

	}

	public FAQ(String question, String answer, String status, String tag) {
		super();
		this.question = question;
		this.answer = answer;
		this.status = status;
		this.tag = tag;
	}

	/**
	 * Constructor for student to ask a question
	 * 
	 * @param question
	 * @param status
	 */
	public FAQ(String question, String status) {
		super();
		this.question = question;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "FAQ [id= " + id + ", question= " + question + ", answer= " + answer + ", status= " + status + ", tag= "
				+ tag + "]";
	}

}
