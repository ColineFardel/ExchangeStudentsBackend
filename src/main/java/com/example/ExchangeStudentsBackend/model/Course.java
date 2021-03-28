package com.example.ExchangeStudentsBackend.model;

import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String teacher;
	private String university;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	@JsonIgnore
	private List<Chat> chats;

	public Course() {
		super();
	}

	public Course(String name, String teacher, String university) {
		super();
		this.name = name;
		this.teacher = teacher;
		this.university = university;
	}

	public Course(String name, String teacher, String university, List<Chat> chats) {
		super();
		this.name = name;
		this.teacher = teacher;
		this.university = university;
		this.chats = chats;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

}
