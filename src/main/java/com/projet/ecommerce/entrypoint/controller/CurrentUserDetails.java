package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.persistance.entity.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CurrentUserDetails extends Utilisateur implements UserDetails {

	private String password;
	private String username;
	private String role;

	public CurrentUserDetails(String password, String username, String role) {
		super();
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public static UserDetails create(Utilisateur entity) {
		return new CurrentUserDetails(entity.getMdp(), entity.getEmail(), entity.getRole().getNom());
	}

	@Override
	public String getPassword() {
		return super.getMdp();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	public String getRoleName() {
		return super.getRole().getNom();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
