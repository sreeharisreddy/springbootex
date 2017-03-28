package com.sree;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ShareConfig {
	 @Value("${batch.corn.expresseion}")
	 private String expresseion;
	 
	 @Value("${spring.datasource.url}")
	 private String dbURL;
	 @Value("${spring.datasource.username}")
	 private String dbUser;
	 
	 @Value("${spring.datasource.password}")
	 private String dbpassword;
	 
	 @Value("${bse.zip.downloadpath}")
	 private String bseDownloadpath;
	 
	 @Value("${bse.zip.downloadDaterange}")
	 private String downloadDaterange;
	 
	public String getExpresseion() {
		return expresseion;
	}

	public void setExpresseion(String expresseion) {
		this.expresseion = expresseion;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getBseDownloadpath() {
		return bseDownloadpath;
	}

	public void setBseDownloadpath(String bseDownloadpath) {
		this.bseDownloadpath = bseDownloadpath;
	}

	public String getDownloadDaterange() {
		return downloadDaterange;
	}

	public void setDownloadDaterange(String downloadDaterange) {
		this.downloadDaterange = downloadDaterange;
	}
	
	
}
