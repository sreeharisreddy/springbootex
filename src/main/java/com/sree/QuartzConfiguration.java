package com.sree;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.sree.share.service.RediffReaderService;

@Configuration 
@ComponentScan("com.sree") 
public class QuartzConfiguration {
	
	private static final Logger logger =
			LoggerFactory.getLogger(QuartzConfiguration.class);

	@Autowired
	RediffReaderService rediffReaderService;
	
	@Autowired
	ShareConfig config; 
	
	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("jobone");
		obj.setTargetMethod("myTask");
		return obj;
	}
	//Job  is scheduled for 3+1 times with the interval of 30 seconds
	@Bean
	public SimpleTriggerFactoryBean simpleTriggerFactoryBean(){
		logger.info("Creating simple simpleTriggerFactoryBean");
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
		stFactory.setStartDelay(3000);
		stFactory.setRepeatInterval(30000);
		stFactory.setRepeatCount(0);
		return stFactory;
	}
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean(){
		logger.info("Creating simple jobDetailFactoryBean for MyJobTwo");
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(MyJobTwo.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "RAM");
		map.put(MyJobTwo.COUNT, 1);
		map.put("rediff", rediffReaderService);
		factory.setJobDataAsMap(map);
		factory.setGroup("mygroup");
		factory.setName("myjob");
		return factory;
	}
	//Job is scheduled after every 1 minute 
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean(){
		logger.info("Creating simple CronTriggerFactoryBean for MyJobTwo");
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(jobDetailFactoryBean().getObject());
		stFactory.setName("mytrigger");
		stFactory.setGroup("mygroup");
		logger.info("Expression : "+config.getExpresseion());
		stFactory.setCronExpression(config.getExpresseion());
		return stFactory;          //0 0/5 14 * * ?
	}
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		logger.info("Creating simple SchedulerFactoryBean for MyJobTwo");
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		return scheduler;
	}
} 