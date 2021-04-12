package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Tip;
import com.example.ExchangeStudentsBackend.model.TipRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipRepositoryTest {

	@Autowired
	private TipRepository tiprepo;

	// Add a new Tip
	@Test
	public void createTip() {
		Tip tip = new Tip("Name", "Description", "Tag");
		tiprepo.save(tip);
		assertThat(tip.getId()).isNotNull();

		Tip tipWithLoc = new Tip("Name", "Description", "Tag", "Location");
		tiprepo.save(tipWithLoc);
		assertThat(tipWithLoc.getId()).isNotNull();

		Tip tipWithImg = new Tip("Name", "Description", "Tag", (long) 41);
		tiprepo.save(tipWithImg);
		assertThat(tipWithImg.getId()).isNotNull();
	}

	// Delete Tip
	@Test
	public void deleteTip() {
		List<Tip> tips = (List<Tip>) tiprepo.findAll();
		assertThat(tips).hasSizeGreaterThan(0);
		int temp = tips.size();

		tiprepo.deleteById(tips.get(0).getId());
		assertThat(tiprepo.findAll()).hasSizeLessThan(temp);
	}
}
