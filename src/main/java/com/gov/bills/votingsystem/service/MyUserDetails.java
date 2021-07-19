package com.gov.bills.votingsystem.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gov.bills.votingsystem.model.Member;

public class MyUserDetails implements UserDetails {

	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public MyUserDetails(Member member) {
		this.username = member.getUsername();
		this.password = member.getPassword();
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRole()));
	}

	public MyUserDetails() {

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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

}
