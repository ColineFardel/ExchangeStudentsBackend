package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.User;
import com.example.ExchangeStudentsBackend.model.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userrepo;

	// Add a new User
	@Test
	public void createTopic() {
		User user = new User("RandomUser", "random@gmail.com", "password", "USER", "+41792739284");
		userrepo.save(user);
		assertThat(user.getId()).isNotNull();
	}

	// Delete User
	@Test
	public void deleteTopic() {
		List<User> users = (List<User>) userrepo.findAll();
		assertThat(users).hasSizeGreaterThan(0);
		int temp = users.size();

		userrepo.deleteById(users.get(0).getId());
		assertThat(userrepo.findAll()).hasSizeLessThan(temp);
	}

	// Find user by username
	@Test
	public void findByUsername() {
		User user = new User("RandomUser", "random@gmail.com", "password", "USER", "+41792739284");
		userrepo.save(user);
		assertThat(userrepo.findByUsername("RandomUser")).isNotNull();
	}

	// Check if username already exists
	@Test
	public void usernameExists() {
		User user = new User("RandomUser", "random@gmail.com", "password", "USER", "+41792739284");
		userrepo.save(user);
		assertThat(userrepo.existsByUsername("RandomUser")).isTrue();
	}

	// Check if email already exists
	@Test
	public void emailExists() {
		User user = new User("RandomUser", "random@gmail.com", "password", "USER", "+41792739284");
		userrepo.save(user);
		assertThat(userrepo.existsByEmail("random@gmail.com")).isTrue();
	}
}
