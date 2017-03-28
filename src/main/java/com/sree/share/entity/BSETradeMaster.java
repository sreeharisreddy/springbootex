package com.sree.share.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BSETradeMaster implements Serializable,Cloneable{

    /** identifier field */
    private Integer id;
	
    private Long scCode;
    
    /** persistent field */
    private String scName;

    /** persistent field */
    private String sctype;
    
    private String scgroup;
    
    private double high;
    private double low;
    private double last;
    private double open;
    
    private long noOfTrades;
    
    private long noOfShares;
    
    private BigDecimal netTurnover;
    
    /** persistent field */
    private Date tradeDate;
    
    
    private Double prevClosed;
    
    //close
    private Double closed;
    
    private Double change_percent;
    
    private String url;
    
    public BSETradeMaster(){
    	
    }
    public BSETradeMaster(Integer id,Long shid,String name,String stype,Date createddate,Double value,ShareMaster share){
    	this.id = id;
    	this.scCode = shid;
    	this.scName = name;
    	this.sctype = stype;
    	this.tradeDate = createddate;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSname() {
		return scName;
	}

	public void setSname(String sname) {
		this.scName = sname;
	}

	public String getStype() {
		return sctype;
	}

	public void setStype(String stype) {
		this.sctype = stype;
	}
	public Long getShareId() {
		return scCode;
	}
	public void setShareId(Long shareId) {
		this.scCode = shareId;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public Double getPrev_pric() {
		return prevClosed;
	}
	public void setPrev_pric(Double prev_pric) {
		this.prevClosed = prev_pric;
	}
	public Double getCurrent_price() {
		return closed;
	}
	public void setCurrent_price(Double current_price) {
		this.closed = current_price;
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
	public String getScgroup() {
		return scgroup;
	}
	public void setScgroup(String scgroup) {
		this.scgroup = scgroup;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getLast() {
		return last;
	}
	public void setLast(double last) {
		this.last = last;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public long getNoOfTrades() {
		return noOfTrades;
	}
	public void setNoOfTrades(long noOfTrades) {
		this.noOfTrades = noOfTrades;
	}
	public long getNoOfShares() {
		return noOfShares;
	}
	public void setNoOfShares(long noOfShares) {
		this.noOfShares = noOfShares;
	}
	public BigDecimal getNetTurnover() {
		return netTurnover;
	}
	public void setNetTurnover(BigDecimal netTurnover) {
		this.netTurnover = netTurnover;
	}
}
