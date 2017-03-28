package com.sree.rest.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name="chit")
@Data
public class ChitMaster {

	BigInteger id;
	String chitName;
	String agentName;
	int noOfMoths;
	Date startDate;
	Date endDate;
	double totalValue;
	double receivedAmount;
	String status;
	String comments;
	String contactNo;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getChitName() {
		return chitName;
	}
	public void setChitName(String chitName) {
		this.chitName = chitName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public int getNoOfMoths() {
		return noOfMoths;
	}
	public void setNoOfMoths(int noOfMoths) {
		this.noOfMoths = noOfMoths;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public double getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

}
