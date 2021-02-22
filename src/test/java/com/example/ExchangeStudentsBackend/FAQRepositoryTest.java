package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;

import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class FAQRepositoryTest {

	@Autowired
	private FAQRepository faqrepo;

	//Add a new FAQ
	@Test
	public void createFaq() {
		FAQ faq = new FAQ("Question", "Answer", "answered", "tag");
		faqrepo.save(faq);
		assertThat(faq.getId()).isNotNull();
	}
	
	
}
