package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Image;
import com.example.ExchangeStudentsBackend.model.ImageRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ImageRepositoryTest {

	@Autowired
	private ImageRepository imgrepo;

	// Add a new Image
	@Test
	public void createImage() {
		byte[] temp = null;
		Image img = new Image("Image", "Image", temp);
		imgrepo.save(img);
		assertThat(img.getId()).isNotNull();
	}

	// Delete Image
	@Test
	public void deleteImage() {
		List<Image> imgs = (List<Image>) imgrepo.findAll();
		assertThat(imgs).hasSizeGreaterThan(0);
		int temp = imgs.size();

		imgrepo.deleteById(imgs.get(0).getId());
		assertThat(imgrepo.findAll()).hasSizeLessThan(temp);
	}
}
