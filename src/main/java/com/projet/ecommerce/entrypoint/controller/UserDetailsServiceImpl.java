package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.persistance.entity.Utilisateur;
import com.projet.ecommerce.persistance.repository.UtilisateurRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("username is empty");
		}

		Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(username);

		if (!utilisateur.isPresent()) {
			throw new UsernameNotFoundException("Username not found");
		}

		Utilisateur utilisateurFound = utilisateur.get();

		utilisateurFound.toCurrentUserDetails();

		return null;
	}

}
