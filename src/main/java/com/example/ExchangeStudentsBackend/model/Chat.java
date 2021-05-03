package com.example.ExchangeStudentsBackend.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String text;
	private String date;
	private String time;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JsonBackReference(value = "topicChat")
	@JoinColumn(name = "topicid")
	private Topic topic;

	@ManyToOne
	@JsonBackReference(value = "courseChat")
	@JoinColumn(name = "courseid")
	private Course course;

	public Chat() {
		super();
	}

	public Chat(String text, String date, String time, Topic topic, User user) {
		super();
		this.text = text;
		this.date = date;
		this.time = time;
		this.topic = topic;
		this.user = user;
	}

	public Chat(String text, String date, String time, Course course, User user) {
		super();
		this.text = text;
		this.date = date;
		this.time = time;
		this.course = course;
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
