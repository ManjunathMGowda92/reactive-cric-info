package org.fourstack.reactivecricinfo.battinginfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class BattingInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattingInfoServiceApplication.class, args);
	}

}
