package com.photoapp.discovery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	private Environment environment;

	public WebSecurity(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		System.out.println("spring.security.user.name:" + environment.getProperty("spring.security.user.name"));
		System.out.println("spring.security.user.password:" + environment.getProperty("spring.security.user.password"));
		http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		return http.build();
	}

}
