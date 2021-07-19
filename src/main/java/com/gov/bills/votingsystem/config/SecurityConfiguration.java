package com.gov.bills.votingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests()
				.antMatchers("/api/getMembers", "/api/createMember", "/api/closeVoting", "/api/restartVoting",
						"/api/createBill", "/api/getVoteResult/{id}")
				.hasRole("ADMIN").antMatchers("/api/startVoting").hasRole("ADMIN")
				.antMatchers("/api/getBill/{id}", "/api/getBills/live", "/api/vote", "/api/getAllBills")
				.hasAnyRole("ADMIN", "USER").antMatchers("/", "/h2-console/**").permitAll().and().formLogin().and()
				.headers().frameOptions().sameOrigin();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
