package org.fourstack.reactivecricinfo.playerinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class PlayerInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerInfoServiceApplication.class, args);
	}

}
