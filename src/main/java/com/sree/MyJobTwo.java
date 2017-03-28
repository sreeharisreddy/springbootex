package com.sree;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sree.share.service.RediffReaderService;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJobTwo extends QuartzJobBean {
	public static final String COUNT = "count";
	private String name;

	private static final Logger logger = LoggerFactory.getLogger(MyJobTwo.class);
	RediffReaderService rediffReaderService;

	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
		int cnt = dataMap.getInt(COUNT);
		JobKey jobKey = ctx.getJobDetail().getKey();
		SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yy hh:mm:sss");
		logger.info("*********************JOB RUNNING FOR************{}", f.format(new Date()));
		rediffReaderService = (RediffReaderService) dataMap.get("rediff");

		System.out.println(jobKey + ": " + name + ": " + cnt);
		cnt++;
		dataMap.put(COUNT, cnt);
		try {
			try {
				rediffReaderService.readFromBSE();
			} catch (Exception e) {
				logger.error("Error While reading from readFromBSE ()", e);

			}
			logger.info("*********************Loading Gainers************{}", f.format(new Date()));
			try {
				//rediffReaderService.loadGainers("Today");
			} catch (Exception e) {
				logger.error("Error While reading from loadGainers ()", e);

			}
			logger.info("*********************Loading Loosers************{}", f.format(new Date()));
			try {
				//rediffReaderService.loadLoosers("Today");
			} catch (Exception e) {
				logger.error("Error While reading from loadLoosers ()", e);

			}
			logger.info("*********************Loading global Markets************{}", f.format(new Date()));
			try {
				//rediffReaderService.loadGlobalMarkets();
				/*
				 * findTrades = rediffReaderService.findTrades("%Tata%", null,
				 * "2016-09-16", 1000.00, null); for (TradeMaster tradeMaster :
				 * findTrades) {
				 * System.out.println(tradeMaster.getCurrent_price()); }
				 */
				logger.info("*********************JOB completed FOR************{}", f.format(new Date()));
			} catch (Exception e) {
				logger.error("Error While reading from loadGlobalMarkets ()", e);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error While reading from rediff ()", e);
		}
	}

	public void setName(String name) {
		this.name = name;
	}
}