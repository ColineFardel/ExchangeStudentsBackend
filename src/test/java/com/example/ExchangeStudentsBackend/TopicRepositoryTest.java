package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TopicRepositoryTest {

	@Autowired
	private TopicRepository topicrepo;

	// Add a new Topic
	@Test
	public void createTopic() {
		Topic topic = new Topic("Test");
		topicrepo.save(topic);
		assertThat(topic.getId()).isNotNull();
	}

	// Delete Topic
	@Test
	public void deleteTopic() {
		List<Topic> topics = (List<Topic>) topicrepo.findAll();
		assertThat(topics).hasSizeGreaterThan(0);
		int temp = topics.size();

		topicrepo.deleteById(topics.get(0).getId());
		assertThat(topicrepo.findAll()).hasSizeLessThan(temp);
	}
}
