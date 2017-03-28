package com.sree.share.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RediffReader {
	private static final Logger logger =
			LoggerFactory.getLogger(RediffReader.class);
public	List<StockPrice> getData(String address) throws Exception {
	
	URL page = new URL(address);
    StringBuffer text = new StringBuffer();
    System.setProperty("http.proxyHost", "proxy.ncs.com.sg");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.proxyUser", "ncs\\sreeharis");
    System.setProperty("http.proxyPassword", "Jul@2016");
    HttpURLConnection conn = (HttpURLConnection) page.openConnection();
    conn.connect();
    InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
    
    BufferedReader buff = new BufferedReader(in);
    String line = buff.readLine();
    while (line != null) {
        text.append(line);
        line = buff.readLine();
    }
    List<StockPrice>	shareData = parseData(text);
    	return shareData;
   }

  private  List<StockPrice> parseData(StringBuffer text) {
	  try {
	  String token="<table class=\"dataTable\">";
	    String[] split = text.toString().split(token);
	    String last = split[1];
	    last = last.substring(last.indexOf("<tbody>")+7,last.indexOf("</tbody>"));
//	    System.out.println(split[split.length-1]);
	    split[split.length-1] = last;
	    List<String[]> li = new ArrayList<>();
	    String[] rows = last.split("<tr>");
	 for (int i = 1; i < rows.length; i++) {
    	//System.out.println(split[i]);
    	String[] split1 = rows[i].toString().split("<td>");
    	li.add(split1);
	}
	 List<StockPrice>  stocks = new ArrayList<>();
	 for (Iterator<String[]> iterator = li.iterator(); iterator.hasNext();) {
		 StockPrice stockPrice = new StockPrice();
		String[] cols = iterator.next();
		String  rawName = cols[1].replaceAll("\t", "");
		String cname = rawName.substring(rawName.indexOf(">")+1,rawName.indexOf("</a>")).trim();
		stockPrice.setCompany(cname);
		String url = rawName.substring(2,rawName.indexOf("\">"));
		stockPrice.setUrl(url);
		String rawGrp= cols[2].replaceAll("\t", "");
		String grp = rawGrp.substring(0,rawGrp.indexOf("</td>"));
		stockPrice.setGroup(grp);
		String prevClose = cols[3].substring(0,cols[3].indexOf("</td>"));
		stockPrice.setPrevClose(Double.parseDouble(prevClose.replace(",", "")));
		String currentPrice = cols[4].substring(0,cols[4].indexOf("</td>"));
		stockPrice.setCurrentPrice(Double.parseDouble(currentPrice.replace(",", "")));
		String change = cols[5].substring(cols[5].indexOf(">")+1,cols[5].indexOf("</"));
		logger.info(cname  +"     "+change);
		stockPrice.setChange(Double.parseDouble(change.replace(" ", "")));
		stocks.add(stockPrice);
	}
	 return stocks;
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	 return new  ArrayList<>();
	 
}

public static void main(String[] arguments){

	RediffReader t = new  RediffReader();
	try {
		t.getData("http://money.rediff.com/gainers");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
  }
}
