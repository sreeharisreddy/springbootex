package com.sree.share.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReadNiftySectors {
	@Autowired
	JdbcTemplate jdbc;
	public	void loadData() throws Exception {
		String str = "http://money.rediff.com/indices/nse";
		URL page = new URL(str);
	    StringBuffer text = new StringBuffer();
	    HttpURLConnection conn = (HttpURLConnection) page.openConnection();
	    conn.connect();
	    InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
	    
	    BufferedReader buff = new BufferedReader(in);
	    String line = buff.readLine();
	    while (line != null) {
	        text.append(line);
	        line = buff.readLine();
	    }
	    System.out.println(text);
	    Connection con = getConnection();
	    Map	shareData = parseData(text,con);
	}

	  private  Map parseData(StringBuffer text,Connection con ) throws SQLException {
		  String token="<tbody>";
		    String[] split = text.toString().split(token);
		    String[] split3 = split[1].split("<tr>");
		 
		  PreparedStatement ps = con.prepareStatement("Insert into nsemarket_indics(id,Indices,Prev_Close,Last_Traded,Change,Change_percentage,Date) values(?,?,?,?,?,?,?)");
		    java.util.List li = new ArrayList();
		    int count = 40;
		    Set s = new HashSet();
		    //Map<String,Integer> countries = getCountries(con, "country_maaster");
		    //Map<String,Integer> markets = getCountries(con, "market_maaster");
		    //Map<String,Integer> exchangemaster = getCountries(con, "exchange_maaster");
		 for (int i = 2; i < split3.length; i++) {
			 GloableMraketVO vo = new GloableMraketVO();
				 String token1 ="<td?";
                     String[] split2 = split3[i].split(token1);
    					 vo.setCountry(split2[2].substring(split2[2].indexOf(">")+1, split2[2].indexOf("<")));
    					 vo.setMarket(split2[1].substring(split2[1].indexOf(">")+1, split2[1].indexOf("<")));
    					String sub="</td><td class=\"numericalColumn\">";
    					String exchange = split2[3].substring(split2[3].indexOf(">")+1, split2[3].indexOf("<"));
    					String marketValue =  split2[4].substring(split2[4].indexOf(">")+1, split2[4].indexOf("<"));
    					int st =  split2[5].indexOf("green")>=0?split2[5].indexOf("green"):split2[5].indexOf("red");
    					String change =  split2[5].substring(split2[5].indexOf(">",st)+1, split2[5].indexOf("<",st));
    					vo.setExchange(exchange);
    					vo.setTradeValue(new Double(marketValue.replace(",", "")));
    					System.out.println(vo.getMarket()+" "+vo.getCountry() +" "+vo.getExchange()+"  "+vo.getTradeValue());
    					i++;
    					ps.setInt(1, count);
    					ps.setString(2,vo.getMarket());
    					ps.setString(3, vo.getCountry());
    					ps.setString(4, vo.getExchange());
    					ps.setDouble(5, vo.getTradeValue());
    					Calendar cd = Calendar.getInstance();
    					cd.setTime(new java.util.Date());
    					ps.setDouble(6, new Double(change.replace(",", "")));
    					ps.setTimestamp(7, new Timestamp(cd.getTimeInMillis()));
    					ps.execute();
						count++;
    					//ps.setDate(5,   new Date());
    					/*if(!s.contains(vo.getExchange())){
    						s.add(vo.getExchange());
						ps.execute();
						count++;
    					}*/
					 }
		
		 return null;
	}

	  private Connection getConnection(){
		  try {
	          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	          Connection con = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=share","sa", "India@2016");
	          return con;
	}
	catch( Exception e ) {
	  e.printStackTrace();        
	}
	return null;
	 
	  }
	  
	  private Map getCountries(Connection con,String master){
		  Map m = new HashMap<Integer, String>(0);
		try {
			PreparedStatement prepareStatement = con.prepareStatement("select * from "+master);
			ResultSet executeQuery = prepareStatement.executeQuery();
			while(executeQuery.next()){
				m.put(executeQuery.getString(2),executeQuery.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  return m;
	  }
	  
	public static void main(String[] arguments) throws Exception{

		ReadNiftySectors t = new  ReadNiftySectors();
		try {
			t.loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	  }
}
