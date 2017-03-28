package com.sree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sree.share.service.BSEGipReader;
import com.sree.share.service.RediffReaderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootexApplicationTests {

	@Autowired
	BSEGipReader bse; 
	@Autowired
	RediffReaderService rdf;
	
	@Test
	public void contextLoads() {
		
		rdf.readFromBSE();
	}

}
