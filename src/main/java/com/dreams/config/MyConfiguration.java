package com.dreams.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration implements WebMvcConfigurer{

    @Override
	public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("https://graph.qq.com/oauth2.0/authorize")
					.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
					.maxAge(3600)
					.allowCredentials(true);

	}

}