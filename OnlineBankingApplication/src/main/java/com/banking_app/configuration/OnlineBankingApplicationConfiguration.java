package com.banking_app.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.banking_app.util.CommonUtil;

@Configuration
public class OnlineBankingApplicationConfiguration implements WebMvcConfigurer {

	public void addViewController(ViewControllerRegistry registry) {
		registry.addViewController("/onlinebankapplication/welcome").setViewName("index");
		
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommonUtil commonUtil() {
		return new CommonUtil();
	}
}
