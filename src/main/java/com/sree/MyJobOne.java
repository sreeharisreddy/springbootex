package com.sree;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.share.entity.TradeMaster;
import com.sree.share.service.RediffReaderService;

@Service("jobone")
public class MyJobOne {
	
	@Autowired
	RediffReaderService rediffReaderService;
    
	protected void myTask() {
    	System.out.println("This is my task");
    	try {
    		int nofDays = 5;
    		List<TradeMaster> findTrades = rediffReaderService.findTrades("%%", null, "2016-09-23", 50.00,10000.0, null);
    		
    		int cnt = 7;
    		int match = 1;
    		int shid= 0;
    		boolean fail =false;
    		String share="";
    		StringBuilder sb = new StringBuilder();
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
    }
} 