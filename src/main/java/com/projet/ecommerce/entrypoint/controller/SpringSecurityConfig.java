package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Let Spring manage OPTION requests, so they won't be check for authentication
				.cors()
				.and()
				// Only new utilisateur REST
				.antMatcher("/utilisateur/**")
				.authorizeRequests()
				// All requests need to be connected
//				// TODO c'est le filter qui gère pour l'instant donc Spring autorise tout
				.anyRequest().permitAll()
				// à remplacer une fois l'utilisateur Spring utilisé
//				.authenticated()
				.and()
				.addFilterBefore(new AuthenticationFilter(utilisateurBusiness), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Content-Type", "x-auth-token", "Origin", "Accept", "X-Requested-With", "Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
