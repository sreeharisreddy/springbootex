package com.sree.share.entity;

import java.io.Serializable;
import java.util.Date;

public class TradeMaster implements Serializable,Cloneable{

    /** identifier field */
    private Integer id;
	
    private Integer shareId;
    
    /** persistent field */
    private String sname;

    /** persistent field */
    private String stype;
    
    /** persistent field */
    private Date tradeDate;
    
    private Double prev_pric;
    
    private Double current_price;
    
    private Double change_percent;
    
    private String url;
    
    public TradeMaster(){
    	
    }
    public TradeMaster(Integer id,Integer shid,String name,String stype,Date createddate,Double value,ShareMaster share){
    	this.id = id;
    	this.shareId = shid;
    	this.sname = name;
    	this.stype = stype;
    	this.tradeDate = createddate;
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
	public Integer getShareId() {
		return shareId;
	}
	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public Double getPrev_pric() {
		return prev_pric;
	}
	public void setPrev_pric(Double prev_pric) {
		this.prev_pric = prev_pric;
	}
	public Double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(Double current_price) {
		this.current_price = current_price;
	}
	public Double getChange_percent() {
		return change_percent;
	}
	public void setChange_percent(Double change_percent) {
		this.change_percent = change_percent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url != null ? url.substring(7):null;
	}

   
}
