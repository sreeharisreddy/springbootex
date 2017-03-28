package com.sree.share.service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sree.share.entity.BSETradeMaster;
import com.sree.share.entity.ShareMaster;
import com.sree.share.entity.TradeMaster;

@Repository
public class ShareDAOImpl implements ShareDAO{
	
	@Autowired
	JdbcTemplate jdbc;
	private static final Logger logger =
			LoggerFactory.getLogger(ShareDAOImpl.class);
	@Override
	public void save(ShareMaster sm) {
		String qry ="INSERT INTO share_master (sname, stype,url,createddate) values (?, ?,?,?)";
		System.out.println(sm.getSname());
		Object[] params = new Object[] {sm.getSname(), sm.getStype(), sm.getUrl(), new Date() };
		int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
		jdbc.update(qry,params,types);
	}
	
	
	@Override
	public void saveTrade(int shid,StockPrice sm) {
		String qry ="insert into trade_details (shareid, tradeDate,current_price,prev_pric,change_percent,createddate) values (?, ?, ?,?,?,?)";
		Date date = new Date();
		Object[] params = new Object[] {shid, new Date(date.getYear(),date.getMonth(),date.getDate()), sm.getCurrentPrice(),sm.getPrevClose(),sm.getChange() ,new Timestamp(date.getTime())};
		jdbc.update(qry,params);
		
	}
	
	public List<TradeMaster> findTrades(String company,String startDate,String endDate , Double startPrice , Double endPrice, String group){
		
		StringBuilder sb = new StringBuilder("Select * from share_master sm ,trade_details td  where sm.shareid=td.shareid ");
		boolean and = false;
		if(company != null){
			sb.append(" and sname like '"+company+"'");
			and = true;
		}
		if(startDate != null){
			sb.append(" and tradeDate >='"+startDate+"'");
			and = true;
		}
		
		if(endDate != null){
			sb.append(" and tradeDate <='"+endDate+"'");
			and = true;
		}
		
		if(startPrice != null){
			sb.append(" and current_price >='"+startPrice+"'");
			and = true;
		}
		if(endPrice != null){
			sb.append(" and current_price <='"+endPrice+"'");
			and = true;
		}
		if(group != null){
			sb.append(" and stype >='"+group+"'");
			and = true;
		}
		sb.append(" order by sm.shareid,tradeDate desc");
		logger.info(sb.toString());
		List<TradeMaster> list = (List<TradeMaster>) jdbc.query(sb.toString() , new BeanPropertyRowMapper(TradeMaster.class));
		logger.info("No of records {}",list.size());
		return list;
	}
	
	
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
	Iterator<Map<String, Object>> iterator = jdbc.queryForList("Select max(shareid) as max from share_master").iterator();
		if(iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			int max = next.get("max") ==null ? 0 :(int) next.get("max");
			return max;
		}
		return 0;
	}

	@Override
	public int findByName(String company) {
		try{
		 int id  = jdbc.queryForObject("Select shareid as max from share_master where sname=?",new Object[] {company},new int[]{Types.VARCHAR},Integer.class);
		 return id;
		}catch(DataAccessException e){
			return 0;
		}
		}
		
	@Override
	@SuppressWarnings("unchecked")
	public  List<TradeMaster>  findByGrowthPercent() {
		try{
			logger.error("findByGrowthPercent ");
			List<TradeMaster> list = (List<TradeMaster>) jdbc.query("select sm.shareid,sname,url,sum(change_percent) as change_percent"
					+ " from share_master sm , trade_details td where sm.shareid=td.shareid group by sm.shareid ,sm.sname,url order by sum(change_percent) desc", new BeanPropertyRowMapper(TradeMaster.class));
			logger.error("findByGrowthPercent {} ",list.size());
			return list;
		}catch(DataAccessException e){
			return null;
		}
		}


	@Override
	public void saveBSETrade(List<BSETradeMaster> smlist ,Date tradeDate) {
		
		//SC_CODE	SC_NAME	SC_GROUP	SC_TYPE	OPEN	HIGH	LOW	CLOSE	LAST	PREVCLOSE	NO_TRADES	NO_OF_SHRS	NET_TURNOV
		String qry ="insert into BSE_INDIA "
				+ "(SC_CODE,SC_NAME,SC_GROUP,SC_TYPE,OPEN,HIGH,LOW,CLOSE,LAST,PREVCLOSE,"
				+ "NO_TRADES,NO_OF_SHRS,TRADE_DATE,CREATED_DATE) "
				+ "values (?, ?, ?,?,?,?,?,?,?, ?, ?,?,?,?)" ;
		Date date = new Date();
		List<Object[]> li = new ArrayList<>();
		System.out.println("iNSERTING");
		for (BSETradeMaster sm : smlist) {
			Object[] params = new Object[] {sm.getShareId(),sm.getSname(),sm.getScgroup(),sm.getStype(),sm.getOpen(),sm.getHigh(),sm.getLow(),sm.getCurrent_price(),sm.getPrev_pric(),sm.getNoOfTrades(),sm.getNoOfShares(),sm.getNetTurnover(),new Date(tradeDate.getYear(),tradeDate.getMonth(),tradeDate.getDate()),new Timestamp(date.getTime())};
			li.add(params);
		}
		
		try{
			jdbc.batchUpdate(qry, li);
			logger.error("INserting BSE DATA {} records",li.size());
		}catch(Exception e){
			logger.error("UNable to insert bse data ",e);
		}
	
	}
		
	}
