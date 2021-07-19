package com.gov.bills.votingsystem.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.bills.votingsystem.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

	List<Bill> findAllByStatus(String status);

	Bill findByBillId(int billId);
}
