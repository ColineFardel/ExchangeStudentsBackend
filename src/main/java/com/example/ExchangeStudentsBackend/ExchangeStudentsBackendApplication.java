package com.example.ExchangeStudentsBackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.ExchangeStudentsBackend.model.ChatRepository;
import com.example.ExchangeStudentsBackend.model.EventRepository;
import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;
import com.example.ExchangeStudentsBackend.model.OfferRepository;
import com.example.ExchangeStudentsBackend.model.RequestRepository;
import com.example.ExchangeStudentsBackend.model.TipRepository;

@SpringBootApplication
public class ExchangeStudentsBackendApplication {
	private static final Logger log = LoggerFactory.getLogger(ExchangeStudentsBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExchangeStudentsBackendApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(FAQRepository faqrepo, EventRepository eventrepo, TipRepository tiprepo, ChatRepository chatrepo, OfferRepository offerrepo, RequestRepository requestrepo){
		return(args)->{
			
			/*
			 * log.info("save a couple of faq"); faqrepo.save(new
			 * FAQ("Where do I have to go to make an HSL card?","sent")); faqrepo.save(new
			 * FAQ("Question","Answer","answered","#university"));
			 */
			/*
			 * eventrepo.deleteAll(); tiprepo.deleteAll(); chatrepo.deleteAll();
			 * offerrepo.deleteAll(); requestrepo.deleteAll();
			 */
			
			
			log.info("fetch all faq");
			for (FAQ faq : faqrepo.findAll()) {
				log.info(faq.toString());
			}
	};

}

}
