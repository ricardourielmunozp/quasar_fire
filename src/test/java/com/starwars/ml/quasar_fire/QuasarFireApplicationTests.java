package com.starwars.ml.quasar_fire;

import com.starwars.ml.quasar_fire.controller.InfoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuasarFireApplicationTests {

	@Autowired
	private InfoController infoController ;

	@Test
	void contextLoads() {
		assertThat(infoController).isNotNull();
	}

}
