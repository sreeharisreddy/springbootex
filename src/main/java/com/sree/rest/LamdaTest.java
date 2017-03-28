package com.sree.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class LamdaTest {
	static String x = "hari";
	public static void main(String[] args) {
		new LamdaTest().extracted();
	}
	private  void extracted() {
		String s[]= new String[]{"hi"};
		String temp="";
		BiConsumer<String,String> con = (x,y)->{System.out.println(x+y);}; 
		BiFunction<String,String,String> biFunction = (x,y)->{return x+y;};
		Function<String,String>  f = x -> x+"test";
		  Predicate<String> moreThan5  = (p)-> p.length() > 5;
		  Predicate<String> lessThan8  = (p)-> p.length() < 8;
		  Predicate<String> equal =Predicate.isEqual("hiHellotest");
		Arrays.asList(s).forEach(t-> System.out.println(biFunction.andThen(f).apply(t, "Hello")));
		Arrays.asList(s).forEach(t-> System.out.println(moreThan5 .test((biFunction.andThen(f).apply(t, "Hello")))));
		Arrays.asList(s).forEach(t-> System.out.println(moreThan5.and(lessThan8).test((biFunction.andThen(f).apply(t, "Hello")))));
		Arrays.asList(s).forEach(t-> System.out.println("Equal to hiHellotest: "+equal.test((biFunction.andThen(f).apply(t, "Hello")))));
		Arrays.asList(s).forEach(t -> evol(t, moreThan5));
		
		
		
		List<String> items = new ArrayList<String>();

		items.add("one");
		items.add("two");
		items.add("three");

		Stream<String> stream = items.stream();
		
		List<String> filtered = items.stream()
			    .filter( item -> item.startsWith("o") )
			    .collect(Collectors.toList());
		System.out.println("Filtered :"+ filtered);
		
		 String shortest = items.stream()
			        .min(Comparator.comparing(item -> item.length()))
			        .get();
		 System.out.println("Shortest:"+shortest);
		 
		 String reduced2 = items.stream()
			        .reduce((acc, item) -> acc + " " + item)
			        .get();
		System.out.println("reduced2 :"+reduced2);
		
	}
	private  boolean evol(String t,Predicate<String> p) {
		return p.test(t);
	}
}
