package com.example.ExchangeStudentsBackend.model;

import java.util.List;

public class EventResponse {
	
	private String date;
	private List<Event> data;

	public EventResponse(String date, List<Event> data) {
		super();
		this.date = date;
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Event> getData() {
		return data;
	}

	public void setData(List<Event> data) {
		this.data = data;
	}

}
