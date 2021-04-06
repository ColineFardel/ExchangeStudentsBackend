package com.example.ExchangeStudentsBackend.model;

import javax.persistence.*;

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

	// Constructor with everything
	public Tip(String name, String description, String tag, String location, long img) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.tag = tag;
		this.img = img;
	}

	// Constructor without location and image
	public Tip(String name, String description, String tag) {
		super();
		this.name = name;
		this.description = description;
		this.tag = tag;
	}

	// Constructor without location
	public Tip(String name, String description, String tag, long img) {
		super();
		this.name = name;
		this.description = description;
		this.tag = tag;
		this.img = img;
	}

	// Constructor without image
	public Tip(String name, String description, String tag, String location) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.tag = tag;
	}

	public Tip() {
		super();
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

	public long getImg() {
		return img;
	}

	public void setImg(long img) {
		this.img = img;
	}

}
