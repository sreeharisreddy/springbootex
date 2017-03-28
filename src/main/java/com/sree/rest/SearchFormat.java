package com.sree.rest;

import java.util.ArrayList;
import java.util.List;

public class SearchFormat {
	int id;
	String name;
	List<Price> prices = new ArrayList<Price>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
}
