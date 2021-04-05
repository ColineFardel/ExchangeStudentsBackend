package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Chat;
import com.example.ExchangeStudentsBackend.model.ChatRepository;
import com.example.ExchangeStudentsBackend.model.Course;
import com.example.ExchangeStudentsBackend.model.CourseRepository;
import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ChatRepositoryTest {

	@Autowired
	private ChatRepository chatrepo;

	@Autowired
	private TopicRepository topicrepo;

	@Autowired
	private CourseRepository courserepo;

	// Add a new Chat
	@Test
	public void createChat() {
		List<Topic> topics = (List<Topic>) topicrepo.findAll();
		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", topics.get(0));
		chatrepo.save(chat);
		assertThat(chat.getId()).isNotNull();
	}

	// Search Chat by Topic
	@Test
	public void FindChatByTopic() {
		Topic topic = new Topic("Name");
		topicrepo.save(topic);

		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", topic);
		chatrepo.save(chat);

		List<Chat> chats = chatrepo.findByTopic(topic);
		assertThat(chats).hasSizeGreaterThan(0);
	}

	// Search Chat by Topic and Date
	@Test
	public void FindChatByTopicAndDate() {
		Topic topic = new Topic("Name");
		topicrepo.save(topic);

		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", topic);
		chatrepo.save(chat);

		List<Chat> chats = chatrepo.findByTopicAndDate(topic, "March 20, 2021");
		assertThat(chats).hasSizeGreaterThan(0);
	}

	// Search Chat by Course
	@Test
	public void FindChatByCourse() {
		Course course = new Course("Name", "Teacher", "University");
		courserepo.save(course);

		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", course);
		chatrepo.save(chat);

		List<Chat> chats = chatrepo.findByCourse(course);
		assertThat(chats).hasSizeGreaterThan(0);
	}

	// Search Chat by Course and Date
	@Test
	public void FindChatByCourseAndDate() {
		Course course = new Course("Name", "Teacher", "University");
		courserepo.save(course);

		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", course);
		chatrepo.save(chat);

		List<Chat> chats = chatrepo.findByCourseAndDate(course, "March 20, 2021");
		assertThat(chats).hasSizeGreaterThan(0);
	}
}
