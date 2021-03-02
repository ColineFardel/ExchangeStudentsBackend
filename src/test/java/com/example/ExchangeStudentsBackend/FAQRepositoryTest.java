package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;

import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FAQRepositoryTest {

	@Autowired
	private FAQRepository faqrepo;

	// Add a new FAQ
	@Test
	public void createFaq() {
		FAQ faq = new FAQ("Question", "Answer", "answered", "tag");
		faqrepo.save(faq);
		assertThat(faq.getId()).isNotNull();
	}

	// Search by status
	@Test
	public void findFAQByStatus() {
		List<FAQ> faqs = faqrepo.findByStatus("sent");
		assertThat(faqs).hasSizeGreaterThan(0);
	}

	// Delete FAQ
	@Test
	public void deleteFAQ() {
		List<FAQ> faqs = (List<FAQ>) faqrepo.findAll();
		assertThat(faqs).hasSizeGreaterThan(0);
		int temp = faqs.size();

		faqrepo.deleteById(faqs.get(0).getId());
		assertThat(faqrepo.findAll()).hasSizeLessThan(temp);
	}

}
