package org.fourstack.reactivecricinfo.apigateway;

import org.fourstack.reactivecricinfo.apigateway.config.GatewayPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApiGatewayApplication.class, args);

		GatewayPropertiesConfig bean = context.getBean(GatewayPropertiesConfig.class);
		System.out.println(bean.getPlayerByBattingStyleURL());
		System.out.println(bean.getPlayerByBowlingStyleURL());
		System.out.println(bean.getPlayerByGenderURL());
	}

}
