package com.springboot.price;

import org.slf4j.Logger;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringbootBlogRestApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringbootBlogRestApiApplication.class);


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
		logger.info("SpringbootBlogRestApiApplication has started.");

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
