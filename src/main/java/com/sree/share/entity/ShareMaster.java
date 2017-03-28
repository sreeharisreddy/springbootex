package com.sree.share.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class ShareMaster implements Serializable {

	 private Integer id;

    /** persistent field */
    private String sname;

    /** persistent field */
    private String stype;
    
    /** persistent field */
    private Date createddate;
    
    private String url;
    
    
    public ShareMaster(){
    	
    }
    
    public ShareMaster(Integer id,String sname,String stype,Date createddate){
    	this.id = id;
    	this.sname = sname;
    	this.stype = stype;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
   
}
