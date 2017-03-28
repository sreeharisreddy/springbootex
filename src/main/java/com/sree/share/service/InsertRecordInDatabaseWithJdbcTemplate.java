package com.sree.share.service;


import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class InsertRecordInDatabaseWithJdbcTemplate {
	
	private static final String driverClassName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:sqlserver://192.168.0.12:1433;databaseName=share";
	private static final String dbUsername = "sa";
	private static final String dbPassword = "India@2016";

	private static final String insertSql =

  "INSERT INTO employee (" +

  "	name, " +

  "	surname, " +

  "	title, " +

  "	created) " +

  "VALUES (?, ?, ?, ?)";
	
	private static final String insertSql1 ="INSERT INTO share_master (shareid, sname, stype,url,createddate) values (?, ?, ?,?,?)";

	private static DataSource dataSource;
	
	public static void main(String[] args) throws Exception {
	
		dataSource = getDataSource();
		
		//saveRecord("John", "Black", "Software developer", new Date());
		//saveRecord("Tom", "Green", "Project Manager", new Date());
		insertTrade();
		
	}
	
	public static void saveRecord(String name, String surname, String title, Date created) {
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		// define query arguments
		Object[] params = new Object[] { 1,name, surname, title, new Date() };
		
		// define SQL types of the arguments
		int[] types = new int[] {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		
		int row = template.update(insertSql1, params, types);
		System.out.println(row + " row inserted.");
		
	}
	
	
	public static void insertTrade(){
		StockPrice sm = new StockPrice();
		sm.setCompany("sdsdsd");
		sm.setGroup("A");
		sm.setCurrentPrice(10.0);
		sm.setPrevClose(10.0);
		sm.setChange(1.0);
		String qry ="insert into trade_details(shareid, sname,tradeDate,current_price,prev_pric,change_percent) values (?, ?, ?,?,?,?)";
		System.out.println(sm.getCompany());
		Object[] params = new Object[] {1,sm.getCompany(), new Date(), sm.getCurrentPrice(),sm.getPrevClose(),sm.getChange() };
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		int row = template.update(qry, params);
	}
	
	public static DriverManagerDataSource getDataSource() {

  DriverManagerDataSource dataSource = new DriverManagerDataSource();

  dataSource.setDriverClassName(driverClassName);

  dataSource.setUrl(url);

  dataSource.setUsername(dbUsername);

  dataSource.setPassword(dbPassword);

  return dataSource;
    }
		
}