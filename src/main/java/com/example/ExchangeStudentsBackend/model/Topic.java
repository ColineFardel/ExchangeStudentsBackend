package com.example.ExchangeStudentsBackend.model;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
	@JsonIgnore
	private List<Chat> chats;

	public Topic(String name, List<Chat> chats) {
		super();
		this.name = name;
		this.chats = chats;
	}

	public Topic(String name) {
		super();
		this.name = name;
	}

	public Topic() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	@Override
	public String toString() {
		return "Topic [id= " + id + ", name= " + name + "]";
	}

}
