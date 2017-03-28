package com.sree.share.service;

public class StockPrice {

	String company;
	String group;
	String url;
double PrevClose;
double currentPrice;
double change;
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public String getGroup() {
	return group;
}
public void setGroup(String group) {
	this.group = group;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public double getPrevClose() {
	return PrevClose;
}
public void setPrevClose(double prevClose) {
	PrevClose = prevClose;
}
public double getCurrentPrice() {
	return currentPrice;
}
public void setCurrentPrice(double currentPrice) {
	this.currentPrice = currentPrice;
}
public double getChange() {
	return change;
}
public void setChange(double change) {
	this.change = change;
}

	
}
