package com.example.ExchangeStudentsBackend.model;

import java.util.List;

public class ChatResponse {

	private String date;
	private List<Chat> data;

	public ChatResponse(String date, List<Chat> data) {
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

	public List<Chat> getData() {
		return data;
	}

	public void setData(List<Chat> data) {
		this.data = data;
	}

}
