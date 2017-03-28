package com.sree.rest;

public interface Calculator {
	
	public default int add(int x,int y){
		return x+y;
	}
	
	public default int sub(int x,int y){
		return x-y;
	}
	
	public float div (int x,int y);

}
