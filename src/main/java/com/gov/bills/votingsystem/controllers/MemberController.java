package com.gov.bills.votingsystem.controllers;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gov.bills.votingsystem.model.Member;
import com.gov.bills.votingsystem.repository.MemberRepository;

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private MemberRepository repository;

	@GetMapping("/getMembers")
	public List<Member> getAllMembers() {
		List<Member> members = repository.findAll();

		return members;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@PostMapping(path = "/createMember", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> createMember(@RequestBody Member newMember) throws ServerException {
		Member member = repository.save(newMember);

		if (member == null) {
			throw new ServerException("User creation failed");
		} else {
			return new ResponseEntity<Member>(member, HttpStatus.CREATED);
		}
	}

}
