package com.gov.bills.votingsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
	@Id
	private String username;
	private String password;
	private String role;

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(int id, String username, String fullname, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Member [username=" + username + ", password=" + password + ", role=" + role + "]";
	}

}
