package com.example.ExchangeStudentsBackend;
import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import com.example.ExchangeStudentsBackend.web.ExchangeStudentsController;

//@RunWith(SpringRunner.class)
@SpringBootTest
class ExchangeStudentsBackendApplicationTests {
	
	@Autowired
	private ExchangeStudentsController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
