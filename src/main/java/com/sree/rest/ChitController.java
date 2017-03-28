package com.sree.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sree.rest.entity.ChitMaster;
import com.sree.share.service.RediffReaderService;

@RestController
public class ChitController {

	@Autowired
	RediffReaderService rediffReaderService;
	
	List<ChitMaster> chits = new  ArrayList<>();
	int id = 1 ;
	@RequestMapping(value="/api/chits",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChitMaster>> list(){
		System.out.println("List Called");
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			
			if(now.get(Calendar.HOUR_OF_DAY)>16){
			rediffReaderService.loadGainers("TODAY");
			rediffReaderService.loadLoosers("TODAY");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(chits.size() == 0){
			ChitMaster chit = new ChitMaster();
			chit.setId(BigInteger.valueOf(1L));
			chit.setAgentName("Sreehari");
			chit.setComments("Starting urgently");
			chit.setStartDate(new Date());
			Calendar endDate = Calendar.getInstance();
			endDate.setTime(new Date());
			endDate.add(Calendar.YEAR, 1);
			chit.setNoOfMoths(12);
			chit.setTotalValue(100000.0);
			chit.setStatus("OK");
			chit.setChitName("SreeChit Name 1");
			chits.add(chit);
		}
		return  new ResponseEntity<List<ChitMaster>>(chits,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/chits/{id}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChitMaster> getChit(@PathVariable("id") int id){
		System.out.println(" getChit Called");
		ChitMaster chit = find(new BigInteger(String.valueOf(id)));
			if(chit == null){
				return new ResponseEntity<ChitMaster>(HttpStatus.NOT_FOUND);
			}
			 return new ResponseEntity<ChitMaster>(chit,HttpStatus.OK);
	}


	private ChitMaster find(BigInteger id) {
	ChitMaster chitmaster = chits.stream()
		     .filter(item -> (item.getId().intValue() == id.intValue()))
		     .findAny()
		     .orElse(null);
		return chitmaster;
	}
	
	@RequestMapping(value="/api/chits",
					method=RequestMethod.POST,
					produces=MediaType.APPLICATION_JSON_VALUE,
					consumes=MediaType.APPLICATION_JSON_VALUE
					)
	public  ResponseEntity<ChitMaster> createChit(@RequestBody ChitMaster chit){
		System.out.println("Post Called");
		chit.setId(new BigInteger(String.valueOf(id++)));
		chits.add(chit);
		return new ResponseEntity<ChitMaster>(chit,HttpStatus.CREATED);
	}
	@RequestMapping(value="/api/chits/{id}",
			method=RequestMethod.PUT,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<ChitMaster> updateChit(@RequestBody ChitMaster chit){
	System.out.println("updateChit Called");
	ChitMaster find = find(chit.getId());
	chits.remove(find);
	chits.add(chit);
	return new ResponseEntity<ChitMaster>(chit,HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/chits/{id}",method=RequestMethod.DELETE)
		public ResponseEntity<ChitMaster> deleteChit(@PathVariable("id") int id){
		System.out.println(" deleteChit Called");
		ChitMaster chitMaster = find(new BigInteger(String.valueOf(id)));
		chits.remove(chitMaster);
		return new ResponseEntity<ChitMaster>(HttpStatus.OK);
	}

}
