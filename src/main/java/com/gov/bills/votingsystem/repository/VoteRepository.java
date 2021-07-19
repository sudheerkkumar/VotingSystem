package com.gov.bills.votingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.bills.votingsystem.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	List<Vote> findByBillAndMember(int billId, String username);

	void deleteByBill(int billId);
}
