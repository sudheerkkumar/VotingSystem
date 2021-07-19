package com.gov.bills.votingsystem.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bill {
	@Id
	private int billId;
	private String description;
	private String status = "Open";
	private int yesVote = 0;
	private int noVote = 0;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bill(int billId, String description, String status, int yesVote, int noVote, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.billId = billId;
		this.description = description;
		this.status = status;
		this.yesVote = yesVote;
		this.noVote = noVote;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getYesVote() {
		return yesVote;
	}

	public void setYesVote(int yesVote) {
		this.yesVote = yesVote;
	}

	public int getNoVote() {
		return noVote;
	}

	public void setNoVote(int noVote) {
		this.noVote = noVote;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime localDateTime) {
		this.startTime = localDateTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime localDateTime) {
		this.endTime = localDateTime;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", description=" + description + ", status=" + status + ", yesVote=" + yesVote
				+ ", noVote=" + noVote + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
