package com.solutionladder.ethearts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@SpringBootApplication
public class EtheartsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtheartsApplication.class, args);
	}
	
	@Bean
	public GlobalAuthenticationConfigurerAdapter authenticationConfiguration() {
	    return new GlobalAuthenticationConfigurerAdapter() {
	        @Override
	        public void init(AuthenticationManagerBuilder auth) throws Exception {
	        }
	    };
	}
}
