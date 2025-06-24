package com.banking_app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class OnlineBankingApplicationApplication {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		var run = SpringApplication.run(OnlineBankingApplicationApplication.class, args);
	}

}
