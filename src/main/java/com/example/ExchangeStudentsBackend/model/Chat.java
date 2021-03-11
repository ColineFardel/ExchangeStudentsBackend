package com.example.ExchangeStudentsBackend.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String text;
	private String date;
	private String time;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "topicid")
	private Topic topic;

	public Chat() {
		super();
	}

	public Chat(String text, String date, String time, Topic topic) {
		super();
		this.text = text;
		this.date = date;
		this.time = time;
		this.topic = topic;
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

}
