package com.solutionladder.ethearts;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class EtheartsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtheartsApplication.class, args);
	}
	
	  @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.addAllowedOrigin("http://localhost:9090");
	        corsConfiguration.setAllowedMethods(Arrays.asList(
	                HttpMethod.GET.name(),
	                HttpMethod.HEAD.name(),
	                HttpMethod.POST.name(),
	                HttpMethod.PUT.name(),
	                HttpMethod.DELETE.name()));
	        corsConfiguration.setMaxAge(1800L);
	        source.registerCorsConfiguration("/**", corsConfiguration); // you restrict your path here
	        return source;
	    }
}
