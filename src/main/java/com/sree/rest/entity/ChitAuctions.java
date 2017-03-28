package com.sree.rest.entity;

import java.util.Date;

public class ChitAuctions {
	
	Integer id;
	Integer chitId;
	Date auctionDate;
	double totalAuctionAmount;
	double individualAmount;
	String comments;
	Integer Integererest;
	Date paidDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInterest() {
		return Integererest;
	}
	public void setInterest(Integer Integererest) {
		this.Integererest = Integererest;
	}
	public Integer getChitId() {
		return chitId;
	}
	public void setChitId(Integer chitId) {
		this.chitId = chitId;
	}
	public Date getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}
	public double getTotalAuctionAmount() {
		return totalAuctionAmount;
	}
	public void setTotalAuctionAmount(double totalAuctionAmount) {
		this.totalAuctionAmount = totalAuctionAmount;
	}
	public double getIndividualAmount() {
		return individualAmount;
	}
	public void setIndividualAmount(double individualAmount) {
		this.individualAmount = individualAmount;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	
	
}
