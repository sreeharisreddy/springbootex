package com.sree.share.service;

import java.util.Date;
import java.util.List;

import com.sree.share.entity.BSETradeMaster;
import com.sree.share.entity.ShareMaster;
import com.sree.share.entity.TradeMaster;

public interface ShareDAO{

	public void save(ShareMaster sm);
	
	public void saveTrade(int shid,StockPrice sm);

	public int count();

	public int findByName(String company);
	
	public List<TradeMaster> findTrades(String company,String startDate,String endDate , Double startPrice ,Double endPrice, String group);
	public  List<TradeMaster>  findByGrowthPercent();

	public void saveBSETrade(List<BSETradeMaster> smlist ,Date tradeDate);
}
