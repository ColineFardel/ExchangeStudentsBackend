package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Event;
import com.example.ExchangeStudentsBackend.model.EventRepository;
import com.example.ExchangeStudentsBackend.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventrepo;

	// Add a new event
	@Test
	public void createEvent() {
		Event event = new Event("Name", "Description", "Location", "Date", "Time", new User());
		eventrepo.save(event);
		assertThat(event.getId()).isNotNull();
	}

	// Delete an event
	@Test
	public void deleteEvent() {
		List<Event> events = (List<Event>) eventrepo.findAll();
		assertThat(events).hasSizeGreaterThan(0);
		int temp = events.size();

		eventrepo.deleteById(events.get(0).getId());
		assertThat(eventrepo.findAll()).hasSizeLessThan(temp);
	}

	// Search event by date
	@Test
	public void findEventsByDate() {
		Event event = new Event("Name", "Description", "Location", "Date", "Time", new User());
		eventrepo.save(event);

		List<Event> events = eventrepo.findByDate("Date");
		assertThat(events).hasSizeGreaterThan(0);
	}
}
