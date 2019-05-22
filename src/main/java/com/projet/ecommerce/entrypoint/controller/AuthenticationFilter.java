package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.entrypoint.authentification.Token;
import com.projet.ecommerce.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

	private UtilisateurBusiness utilisateurBusiness;

	public AuthenticationFilter(UtilisateurBusiness utilisateurBusiness) {
		this.utilisateurBusiness = utilisateurBusiness;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String auth = request.getHeader("Authorization");
		auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5IiwiaWF0IjoxNTU4NTM3Nzc1LCJzdWIiOiJzaW1wbGUgYXV0aCIsImlzcyI6ImIiLCJleHAiOjE1NTg1NDEzNzV9.oiSL3yWzOz2Vt97dkzbLvr1Z7uNKgeZXj_j-zRXDiAY";
		if (StringUtils.isBlank(auth)) {
			throw new AuthException("No authorization");
		}

		Token supposedToken = new Token();
		supposedToken.setToken(auth);

		if (!utilisateurBusiness.isLogged(supposedToken)) {
			throw new AuthException("Bad authorization");
		}
	}

}
