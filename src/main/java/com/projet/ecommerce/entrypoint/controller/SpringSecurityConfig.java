package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.antMatcher("/utilisateur/*")
				.authorizeRequests()
				.antMatchers("/utilisateur/*")
				.permitAll()
				.and()
				.addFilterBefore(new AuthenticationFilter(utilisateurBusiness), BasicAuthenticationFilter.class);
	}

}
