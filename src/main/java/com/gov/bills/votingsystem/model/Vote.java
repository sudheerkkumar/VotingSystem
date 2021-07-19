package com.gov.bills.votingsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int bill;
	private String member;

	public Vote() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vote(int id, int bill, String member) {
		super();
		this.id = id;
		this.bill = bill;
		this.member = member;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", bill=" + bill + ", member=" + member + "]";
	}

}
