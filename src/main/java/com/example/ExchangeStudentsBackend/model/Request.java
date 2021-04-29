package com.example.ExchangeStudentsBackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String description;
	private String location;
	private long imgId;
	@ManyToOne
	@JsonBackReference(value = "user")
	@JoinColumn(name = "userId")
	private User user;

	public Request() {
		super();
	}

	public Request(String name, String description,String location, long imgId, User user) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.imgId = imgId;
		this.user = user;
	}


	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getImgId() {
		return imgId;
	}

}
