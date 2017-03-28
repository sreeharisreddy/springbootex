package com.sree.rest;

import java.util.ArrayList;
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

import com.sree.rest.entity.ChitAuctions;
import com.sree.share.service.RediffReaderService;

@RestController
public class ChitAuctionsController {

	@Autowired
	RediffReaderService rediffReaderService;
	
	List<ChitAuctions> auctions = new  ArrayList<>();
	int id = 1 ;
	@RequestMapping(value="/api/auctions",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChitAuctions>> list(){
		System.out.println("List Called");
		try {
			rediffReaderService.loadGainers("TODAY");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(auctions.size() == 0){
			ChitAuctions auction = new ChitAuctions();
			auction.setId(1);
			auction.setAuctionDate(new Date());
			auction.setTotalAuctionAmount(10000);
			auction.setIndividualAmount(1000);
			auctions.add(auction);
		}
		return  new ResponseEntity<List<ChitAuctions>>(auctions,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/auctions/{id}",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChitAuctions> getChit(@PathVariable("id") int id){
		System.out.println(" getChit Called");
		ChitAuctions auction = find(id);
			if(auction == null){
				return new ResponseEntity<ChitAuctions>(HttpStatus.NOT_FOUND);
			}
			 return new ResponseEntity<ChitAuctions>(auction,HttpStatus.OK);
	}


	private ChitAuctions find(Integer id) {
	ChitAuctions auctionmaster = auctions.stream()
		     .filter(item -> (item.getId().intValue() == id.intValue()))
		     .findAny()
		     .orElse(null);
		return auctionmaster;
	}
	
	@RequestMapping(value="/api/auctions",
					method=RequestMethod.POST,
					produces=MediaType.APPLICATION_JSON_VALUE,
					consumes=MediaType.APPLICATION_JSON_VALUE
					)
	public  ResponseEntity<ChitAuctions> createAuction(@RequestBody ChitAuctions auction){
		System.out.println("Post Called");
		auction.setId(1);
		auctions.add(auction);
		return new ResponseEntity<ChitAuctions>(auction,HttpStatus.CREATED);
	}
	@RequestMapping(value="/api/auctions/{id}",
			method=RequestMethod.PUT,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<ChitAuctions> updateAuction(@RequestBody ChitAuctions auction){
	System.out.println("updateChit Called");
	ChitAuctions find = find(auction.getId());
	auctions.remove(find);
	auctions.add(auction);
	return new ResponseEntity<ChitAuctions>(auction,HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/auctions/{id}",method=RequestMethod.DELETE)
		public ResponseEntity<ChitAuctions> deleteChit(@PathVariable("id") int id){
		System.out.println(" deleteChit Called");
		ChitAuctions auctionMaster = find(id);
		auctions.remove(auctionMaster);
		return new ResponseEntity<ChitAuctions>(HttpStatus.OK);
	}

}
