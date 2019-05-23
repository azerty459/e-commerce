package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.entrypoint.authentification.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UtilisateurBusiness utilisateurBusiness;

	public AuthenticationFilter(UtilisateurBusiness utilisateurBusiness) {
		this.utilisateurBusiness = utilisateurBusiness;
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = ((HttpServletRequest) request).getHeader("Authorization");
		if (StringUtils.isBlank(auth)) {
            throw new UsernameNotFoundException("Not connected");
		}

		Token supposedToken = new Token();
		supposedToken.setToken(auth);
		if (!utilisateurBusiness.isLogged(supposedToken)) {
            throw new UsernameNotFoundException("Bad authorization");
        }

        filterChain.doFilter(request, response);
	}
}
