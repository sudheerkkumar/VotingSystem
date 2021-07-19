package com.gov.bills.votingsystem.service;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gov.bills.votingsystem.model.Bill;
import com.gov.bills.votingsystem.model.Member;
import com.gov.bills.votingsystem.model.Vote;
import com.gov.bills.votingsystem.repository.BillRepository;
import com.gov.bills.votingsystem.repository.VoteRepository;

@Service
public class BillsService {

	@Autowired
	public VoteRepository voteRepo;

	@Autowired
	public BillRepository billRepo;

	public boolean checkIfUserAlreadyVoted(String username, int billId) {
		List<Vote> li = voteRepo.findByBillAndMember(billId, username);

		return li.size() > 0 ? true : false;
	}

	public ResponseEntity<Bill> addVote(Bill bill, Bill currBill, Member member) throws ServerException {

		Vote vote = new Vote();
		if (currBill.getStatus().equals("InProgress")
				&& !checkIfUserAlreadyVoted(member.getUsername(), bill.getBillId())) {
			System.out.println(bill.getYesVote());
			if (bill.getYesVote() == 1)
				currBill.setYesVote(currBill.getYesVote() + 1);
			else
				currBill.setNoVote(currBill.getNoVote() + 1);

			// add vote to bill
			currBill = billRepo.save(currBill);
			// add user to votes table
			vote.setBill(bill.getBillId());
			vote.setMember(member.getUsername());
			voteRepo.save(vote);

		} else {
			return new ResponseEntity<Bill>(currBill, HttpStatus.NOT_ACCEPTABLE);
		}

		if (currBill == null) {
			throw new ServerException("Voting failed");
		} else {
			return new ResponseEntity<Bill>(currBill, HttpStatus.OK);
		}
	}

	@Transactional
	public ResponseEntity<Bill> restartVoting(Bill currBill) throws ServerException {

		voteRepo.deleteByBill(currBill.getBillId());
		currBill.setStatus("InProgress");
		currBill.setYesVote(0);
		currBill.setNoVote(0);
		currBill.setStartTime(java.time.LocalDateTime.now());
		currBill.setEndTime(java.time.LocalDateTime.now().plusMinutes(15));
		currBill = billRepo.save(currBill);

		if (currBill == null) {
			throw new ServerException("Bill restart failed");
		} else {
			return new ResponseEntity<Bill>(currBill, HttpStatus.OK);
		}

	}

	public ResponseEntity<Bill> closeVoting(Bill bill) throws ServerException {
		LocalDateTime estEndTime = bill.getStartTime().plusMinutes(15);
		if (!estEndTime.isBefore(java.time.LocalDateTime.now())) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Bill cannot be closed before 15mins of start");
		}

		bill.setStatus("Closed");
		bill.setEndTime(java.time.LocalDateTime.now());
		bill = billRepo.save(bill);

		if (bill == null) {
			throw new ServerException("Bill closing failed");
		} else {
			return new ResponseEntity<Bill>(bill, HttpStatus.OK);
		}
	}

	public HashMap<String, String> getVoteResult(int billId) throws ServerException {
		Bill bill = billRepo.findByBillId(billId);

		if (bill == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

		if (!bill.getStatus().equals("Closed"))
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Voting is InProgress/Open, result can not be calculated");

		int forVotes = bill.getYesVote();
		int againstVotes = bill.getNoVote();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("BillId", String.valueOf(bill.getBillId()));
		hm.put("forVotes", String.valueOf(forVotes));
		hm.put("againstVotes", String.valueOf(againstVotes));
		if (forVotes > againstVotes) {
			hm.put("result", "BillPassed");
		} else if (forVotes < againstVotes) {
			hm.put("result", "BillFailed");
		} else {
			hm.put("result", "Tied");
		}
		hm.put("Started", String.valueOf(bill.getStartTime()));
		hm.put("Closed", String.valueOf(bill.getEndTime()));

		return hm;
	}

}
