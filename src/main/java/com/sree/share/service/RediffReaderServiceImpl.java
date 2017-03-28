package com.sree.share.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sree.ShareConfig;
import com.sree.share.entity.BSETradeMaster;
import com.sree.share.entity.ShareMaster;
import com.sree.share.entity.TradeMaster;

@Service
public class RediffReaderServiceImpl implements RediffReaderService{
	private static final Logger logger =
			LoggerFactory.getLogger(RediffReaderServiceImpl.class);
	@Autowired
	RediffReader reader;
	@Autowired
	ShareDAO shareDao;
	@Autowired ReadGloableMarkets gloableMarkets;
	Integer count = 0;
	@Autowired
	BSEGipReader bsegip;
	@Autowired
	ShareConfig shareCOnfig;
	@Override
	public void loadGainers(String day) throws Exception {
		List<StockPrice> data = reader.getData("http://money.rediff.com/gainers");
		data.forEach(saveAll());
	}

	private Consumer<StockPrice> saveAll() {
		return new Consumer<StockPrice>() {
			@Override
			public void accept(StockPrice stock) {
				int shId = shareDao.findByName(stock.getCompany());
				if (shId == 0) {
					logger.info("New Stock id {} name {}",shId,stock.getCompany());
					ShareMaster m = new ShareMaster();
					m.setSname(stock.getCompany());
					m.setStype(stock.getGroup());
					m.setUrl(stock.getUrl());
					shareDao.save(m);
					shId = shareDao.findByName(stock.getCompany());
					shareDao.saveTrade(shId, stock);
				}else{
					shareDao.saveTrade(shId,stock);
				}
			}
		};
	}

	@Override
	public void loadLoosers(String day) throws Exception {
		List<StockPrice> data = reader.getData("http://money.rediff.com/losers?src=all_pg");
		data.parallelStream().forEach(saveAll());
	}
	
	@Override
	public void loadGlobalMarkets(){
		try {
			gloableMarkets.loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<TradeMaster> findTrades(String company, String startDate, String endDate, Double startPrice ,Double endPrice, String group) {
		return shareDao.findTrades(company, startDate, endDate, startPrice,endPrice, group);
	}

	@Override
	public List<TradeMaster> growthPercent() {
		return shareDao.findByGrowthPercent();
	}

	@Override
	public void readFromBSE() {
			String downloadDaterange = shareCOnfig.getDownloadDaterange();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fromDate = null;
			Date toDate = null;
			if(downloadDaterange!=null && downloadDaterange.trim().length() != 0){
				try {
				String from= downloadDaterange.split(",")[0];
				String to= downloadDaterange.split(",")[1];
					if(from != null && from.length() >3){
					 fromDate = sdf.parse(from);
					}
					if(to != null && to.length() >3){
						 toDate = sdf.parse(to);
					}
				} catch (Exception e) {
					logger.error("unable to parse ",e);
				}
				
			}
			if(fromDate == null){
				fromDate = new Date();
				toDate = fromDate;
			}
				
			if(fromDate != null){
				try {
					while(toDate.compareTo(fromDate) >=0 ){
					try{List<BSETradeMaster> list = bsegip.download(fromDate);
					if(list != null && list.size() >0)
					shareDao.saveBSETrade(list,fromDate);
					}catch(Exception e){
						logger.error("Exception while reading BSE for {}",fromDate,e);
					}
					Calendar instance = Calendar.getInstance();
					instance.setTime(fromDate);
					instance.add(Calendar.DATE, 1);
					fromDate = instance.getTime();
					}
				} catch (Exception e) {
					logger.error("Exception while reading BSE",e);
				}
			}
	}

}
