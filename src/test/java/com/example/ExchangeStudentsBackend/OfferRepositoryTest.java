package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Offer;
import com.example.ExchangeStudentsBackend.model.OfferRepository;
import com.example.ExchangeStudentsBackend.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OfferRepositoryTest {

	@Autowired
	private OfferRepository offerrepo;

	// Add a new Offer
	@Test
	public void createOffer() {
		Offer offer = new Offer("Kettle", "I'm selling a kettle", "Somewhere", (long) 41, 10.00, new User());
		offerrepo.save(offer);
		assertThat(offer.getId()).isNotNull();
	}

	// Delete Offer
	@Test
	public void deleteOffer() {
		List<Offer> offers = (List<Offer>) offerrepo.findAll();
		assertThat(offers).hasSizeGreaterThan(0);
		int temp = offers.size();

		offerrepo.deleteById(offers.get(0).getId());
		assertThat(offerrepo.findAll()).hasSizeLessThan(temp);
	}

}
