package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Request;
import com.example.ExchangeStudentsBackend.model.RequestRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RequestRepositoryTest {

	@Autowired
	private RequestRepository requestrepo;

	// Add a new Request
	@Test
	public void createRequest() {
		Request request = new Request("Kettle", "I'm looking for a kettle", "+41792223366", "Somewhere", (long) 41);
		requestrepo.save(request);
		assertThat(request.getId()).isNotNull();
	}

	// Delete Request
	@Test
	public void deleteRequest() {
		List<Request> requests = (List<Request>) requestrepo.findAll();
		assertThat(requests).hasSizeGreaterThan(0);
		int temp = requests.size();

		requestrepo.deleteById(requests.get(0).getId());
		assertThat(requestrepo.findAll()).hasSizeLessThan(temp);
	}

}
