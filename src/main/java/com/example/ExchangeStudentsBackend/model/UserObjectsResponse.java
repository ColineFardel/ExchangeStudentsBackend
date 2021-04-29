package com.example.ExchangeStudentsBackend.model;

import java.util.List;

public class UserObjectsResponse {

	private List<Event> events;
	private List<Tip> tips;
	private List<Offer> offers;
	private List<Request> requests;

	public UserObjectsResponse() {
		super();
	}

	public UserObjectsResponse(List<Event> events, List<Tip> tips, List<Offer> offers, List<Request> requests) {
		super();
		this.events = events;
		this.tips = tips;
		this.offers = offers;
		this.requests = requests;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Tip> getTips() {
		return tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

}
