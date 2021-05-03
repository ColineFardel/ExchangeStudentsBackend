package com.example.ExchangeStudentsBackend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Tip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private String location;
	private String tag;
	private long img;

	@ManyToOne
	@JsonManagedReference(value = "user")
	@JoinColumn(name = "userId")
	private User user;

	// Constructor with everything
	public Tip(String name, String description, String tag, String location, long imgId, User user) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.tag = tag;
		this.img = imgId;
		this.user = user;
	}

	// Constructor without location and image
	public Tip(String name, String description, String tag, User user) {
		super();
		this.name = name;
		this.description = description;
		this.tag = tag;
		this.user = user;
	}

	// Constructor without location
	public Tip(String name, String description, String tag, long imgId, User user) {
		super();
		this.name = name;
		this.description = description;
		this.tag = tag;
		this.img = imgId;
		this.user = user;
	}

	// Constructor without image
	public Tip(String name, String description, String tag, String location, User user) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.tag = tag;
		this.user = user;
	}

	public Tip() {
		super();
	}

	public long getImgId() {
		return img;
	}

	public void setImgId(long imgId) {
		this.img = imgId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
