package com.sree.share.service;

import java.util.List;

import com.sree.share.entity.TradeMaster;

public interface RediffReaderService {
	
	
	public void loadGainers(String day) throws Exception;
	
	public void loadLoosers(String day) throws Exception;
	
	public List<TradeMaster> findTrades(String company,String startDate,String endDate , Double start , Double end, String group);
	
	public void loadGlobalMarkets();

	public List<TradeMaster> growthPercent();

	public void readFromBSE();
}
