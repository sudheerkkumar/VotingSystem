package com.gov.bills.votingsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gov.bills.votingsystem.model.Member;
import com.gov.bills.votingsystem.repository.MemberRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	MemberRepository memberRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Member> member = memberRepo.findByusername(username);

		member.orElseThrow(() -> new UsernameNotFoundException("Member not found"));

		return member.map(MyUserDetails::new).get();
	}

}
