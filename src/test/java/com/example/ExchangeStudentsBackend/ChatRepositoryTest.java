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
import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ChatRepositoryTest {

	@Autowired
	private ChatRepository chatrepo;

	@Autowired
	private TopicRepository topicrepo;

	// Add a new Chat
	@Test
	public void createChat() {
		List<Topic> topics = (List<Topic>) topicrepo.findAll();
		Chat chat = new Chat("Test", "March 20, 2021", "2:32 PM", topics.get(0));
		assertThat(chat.getId()).isNotNull();
	}

	// Search Chat by Topic
	@Test
	public void FindChatByTopic() {
		List<Topic> topics = (List<Topic>) topicrepo.findAll();
		List<Chat> chats = chatrepo.findByTopic(topics.get(0));
		assertThat(chats).hasSizeGreaterThan(0);
	}

	// Search Chat by Topic and Date
	@Test
	public void FindChatByTopicAndDate() {
		List<Topic> topics = (List<Topic>) topicrepo.findAll();
		List<Chat> chats = chatrepo.findByTopicAndDate(topics.get(0), "March 20, 2021");
		assertThat(chats).hasSizeGreaterThan(0);
	}
}
