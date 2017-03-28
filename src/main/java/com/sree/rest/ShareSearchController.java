package com.sree.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sree.share.entity.SearchRequest;
import com.sree.share.entity.TradeMaster;
import com.sree.share.service.RediffReaderService;

@Controller
public class ShareSearchController {
	
	private static final Logger logger =
			LoggerFactory.getLogger(ShareSearchController.class);
	@Autowired
	RediffReaderService rediffReaderService;
	SimpleDateFormat fm = new SimpleDateFormat("MMdd");
	@RequestMapping(value="/api/shares",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public String search(){

   	StringBuilder sb = new StringBuilder();
   	try {
   		List<TradeMaster> findTrades = rediffReaderService.findTrades("%Tantia Constructions%", "2016-10-15",null, 0.00,100.0, null);
   		
   		int cnt = 5;
   		int match = 1;
   		int shid= 0;
   		boolean fail =false;
   		String share="";
   		SimpleDateFormat sdf = new SimpleDateFormat("dd");
   		for (TradeMaster tradeMaster : findTrades) {
   			if(shid==0 || tradeMaster.getShareId() != shid){
   				if(match==cnt)
   				sb.append(share+"\r\n");
   				shid = tradeMaster.getShareId() ;
   				match = 1;
   				 fail = false;
   				 share = tradeMaster.getSname()+" ";
   				 
   			}
			   if(tradeMaster.getCurrent_price()>tradeMaster.getPrev_pric() &!fail){
				   match++;
				   share  =   share + sdf.format(tradeMaster.getTradeDate())+" "+tradeMaster.getCurrent_price()+",";
				   if(match== cnt){
					   System.out.println(tradeMaster.getSname());
				   }
			   }else{
				   fail = true;
			   }
   		}
   		System.out.println(sb.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		return sb.toString();
   }
	
	@RequestMapping(value="/api/allshares",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<SearchFormat>> searchALL(){
		logger.info("Reading All Shares ");
   		List<TradeMaster> findTrades = rediffReaderService.findTrades("%%", "2016-10-01",null, 0.00,50000.0, null);
   		int shid= 0;
   		SearchFormat format = null;
   		List<SearchFormat> searchr = new ArrayList<SearchFormat> ();
   		int leng = findTrades.size();
   		int i =0;
   		for (TradeMaster tradeMaster : findTrades) {
   			i++;
   			if(shid==0 || tradeMaster.getShareId() != shid){
	   			format = new SearchFormat();
	   			format.setId(tradeMaster.getShareId());
	   			shid = tradeMaster.getShareId();
	   			format.setName(tradeMaster.getSname());
	   			searchr.add(format);
	   			addtorestult(format,  tradeMaster);
   			}else{
   				addtorestult(format, tradeMaster);
   			}
   		}
   		logger.info("Reading All Shares  completed");
   		return  new ResponseEntity<List<SearchFormat>>(searchr,HttpStatus.OK);
   }

	@RequestMapping(value="/api/growth",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<TradeMaster>> growthPrecent(){
   		List<TradeMaster> findTrades = rediffReaderService.growthPercent();
   		return  new ResponseEntity<List<TradeMaster>>(findTrades,HttpStatus.OK);
   }
	
	@RequestMapping(value="/api/search",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<SearchFormat>> searchRequest(@RequestBody SearchRequest req){
		List<TradeMaster> findTrades = rediffReaderService.findTrades(req.getSname(), req.getStartDate(),null,req.getMinPrice(),10000.0, null);
		int shid= 0;
   		SearchFormat format = null;
   		List<SearchFormat> searchr = new ArrayList<SearchFormat> ();
   		int leng = findTrades.size();
   		int i =0;
   		for (TradeMaster tradeMaster : findTrades) {
   			i++;
   			if(shid==0 || tradeMaster.getShareId() != shid){
	   			format = new SearchFormat();
	   			format.setId(tradeMaster.getShareId());
	   			shid = tradeMaster.getShareId();
	   			format.setName(tradeMaster.getSname());
	   			searchr.add(format);
	   			addtorestult(format,  tradeMaster);
   			}else{
   				addtorestult(format, tradeMaster);
   			}
   		}
   		return  new ResponseEntity<List<SearchFormat>>(searchr,HttpStatus.OK);
   }
	
	@RequestMapping(value="/api/dailyraisers",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<SearchFormat>> searchRaising(){
		
   		List<TradeMaster> findTrades = rediffReaderService.findTrades("%%", "2016-09-01",null, 0.00,10000.0, null);
   		List<Integer> raisingIds = getRaisingIds(findTrades);
   		List<SearchFormat> searchr = processMapper(findTrades,  raisingIds);
   		return  new ResponseEntity<List<SearchFormat>>(searchr,HttpStatus.OK);
   }
	
	@RequestMapping(value="/api/dailyloosers",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<SearchFormat>> searchLoosers(){
   		List<TradeMaster> findTrades = rediffReaderService.findTrades("%%", "2016-09-01",null, 0.00,10000.0, null);
   		List<Integer> raisingIds = getLoosingIds(findTrades);
   		List<SearchFormat> searchr = processMapper(findTrades,  raisingIds);
   		return  new ResponseEntity<List<SearchFormat>>(searchr,HttpStatus.OK);
   }


	private List<SearchFormat> processMapper(List<TradeMaster> findTrades,List<Integer> mappingIds) {
		int shid= 0;
		SearchFormat format = null;
   		List<SearchFormat> searchr = new ArrayList<SearchFormat> ();
   		for (TradeMaster tradeMaster : findTrades) {
   			if(mappingIds.contains(tradeMaster.getShareId())){
   				if(shid==0 || tradeMaster.getShareId() != shid){
   				format = new SearchFormat();
   				shid = tradeMaster.getShareId();
	   			format.setId(tradeMaster.getShareId());
	   			format.setName(tradeMaster.getSname());
	   			searchr.add(format);
	   			addtorestult(format,  tradeMaster);
   				}else{
   	   				addtorestult(format,  tradeMaster);
   	   			}
   			}
		}
		return searchr;
	}

	private List<Integer> getRaisingIds(List<TradeMaster> findTrades) {
		int shid= 0;
   		int cnt = 0;
   		int matchCnt = 4;
		List<Integer> tmp = new ArrayList<>();
		boolean setFalse =true;
   		for (TradeMaster tradeMaster : findTrades) {
   			if(shid==0 || tradeMaster.getShareId() != shid){
   				shid = tradeMaster.getShareId() ;
   				cnt = 0;
   				setFalse = true;
   			}
	   			if(tradeMaster.getCurrent_price() > tradeMaster.getPrev_pric()){
	   				cnt++;
	   			}else {setFalse = false;}
	   			
	   			if(setFalse && cnt == matchCnt){
	   				tmp.add(shid);
	   			}
	   			
   		}
   		return tmp;
	}

	private List<Integer> getLoosingIds(List<TradeMaster> findTrades) {
		int shid= 0;
   		int cnt = 0;
   		int matchCnt = 4;
		List<Integer> tmp = new ArrayList<>();
		boolean setFalse =true;
   		for (TradeMaster tradeMaster : findTrades) {
   			if(shid==0 || tradeMaster.getShareId() != shid){
   				shid = tradeMaster.getShareId() ;
   				cnt = 0;
   				setFalse = true;
   			}
	   			if(tradeMaster.getCurrent_price() < tradeMaster.getPrev_pric()){
	   				cnt++;
	   			}else {setFalse = false;}
	   			
	   			if(setFalse && cnt == matchCnt){
	   				tmp.add(shid);
	   			}
	   			
   		}
   		return tmp;
	}

	private void addtorestult(SearchFormat format, TradeMaster tradeMaster) {
	
		String txt = tradeMaster.getCurrent_price()>tradeMaster.getPrev_pric() ? "green":"red";
		Price p = new Price();
		p.setColor(txt);
		p.setPrice(tradeMaster.getCurrent_price());
		p.setPrevPrice(tradeMaster.getPrev_pric());
		p.setDate(""+fm.format(tradeMaster.getTradeDate()));
		format.getPrices().add(p);
	}

}
