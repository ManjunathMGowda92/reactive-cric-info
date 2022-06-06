package org.fourstack.reactivecricinfo.bowlinginfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class BowlingInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingInfoServiceApplication.class, args);
	}

}
