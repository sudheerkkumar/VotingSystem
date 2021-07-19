package com.gov.bills.votingsystem.controllers;

import java.rmi.ServerException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gov.bills.votingsystem.model.Bill;
import com.gov.bills.votingsystem.model.Member;
import com.gov.bills.votingsystem.model.Vote;
import com.gov.bills.votingsystem.repository.BillRepository;
import com.gov.bills.votingsystem.repository.MemberRepository;
import com.gov.bills.votingsystem.repository.VoteRepository;
import com.gov.bills.votingsystem.service.BillsService;

@RestController
@RequestMapping("/api")
public class BillsController {

	@Autowired
	public BillRepository billRepo;

	@Autowired
	public MemberRepository memRepo;

	@Autowired
	public VoteRepository voteRepo;

	@Autowired
	public BillsService billService;

	@GetMapping("/getAllBills")
	public List<Bill> getAllBills() {

		return billRepo.findAll();
	}

	@GetMapping("/getBills/live")
	public List<Bill> getInProgressBills() {

		return billRepo.findAllByStatus("InProgress");
	}

	@GetMapping("/getBill/{id}")
	public Bill getBillById(@PathVariable(value = "id") int billId) {
		Bill bill = billRepo.findByBillId(billId);
		if (bill == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		} else {
			return bill;
		}
	}

	@PostMapping(path = "/createBill", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> createBill(@RequestBody Bill newBill) throws ServerException {
		Bill bill = billRepo.save(newBill);

		if (bill == null) {
			throw new ServerException("Bill creation failed");
		} else {
			return new ResponseEntity<Bill>(bill, HttpStatus.CREATED);
		}
	}

	@PostMapping(path = "/startVoting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> startVotingById(@RequestBody Bill bill) throws ServerException {
		Bill currBill = billRepo.findByBillId(bill.getBillId());

		if (currBill == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

		currBill.setStatus("InProgress");
		currBill.setStartTime(java.time.LocalDateTime.now());
		currBill.setEndTime(java.time.LocalDateTime.now().plusMinutes(15));
		currBill = billRepo.save(currBill);

		if (currBill == null) {
			throw new ServerException("Bill updation failed");
		} else {
			return new ResponseEntity<Bill>(currBill, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/closeVoting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> closeVoting(@RequestBody Bill bill) throws ServerException {
		Bill currBill = billRepo.findByBillId(bill.getBillId());

		if (currBill == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

		return billService.closeVoting(currBill);
	}

	@PostMapping(path = "/restartVoting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> restartVoting(@RequestBody Bill bill) throws ServerException {
		Bill currBill = billRepo.findByBillId(bill.getBillId());

		if (currBill == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

		return billService.restartVoting(currBill);

	}

	@PostMapping(path = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> vote(@RequestBody Bill bill, Principal principal) throws ServerException {
		Bill currBill = billRepo.findByBillId(bill.getBillId());

		if (currBill == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

		Optional<Member> currMember = memRepo.findByusername(principal.getName());

		currMember.orElseThrow(() -> new UsernameNotFoundException("Member not found"));

		return billService.addVote(bill, currBill, currMember.get());

	}

	@GetMapping("/getVoteResult/{id}")
	public HashMap<String, String> getClosedBillResult(@PathVariable(value = "id") int billId) throws ServerException {
		return billService.getVoteResult(billId);
	}

}
