package com.sree.rest.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class DataExample {
	String name;
	@Setter(AccessLevel.PACKAGE)
	private int age;
	private double score;
	private String[] tags;
}