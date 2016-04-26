package com.hjp.programme.poi.vo;

import java.util.Date;

public class BalanceExcelPoi {
	
	private String cardId;
	
	private String hodeCardName;
	
	private Date createTime;
	
	private Date costTime;
	
	private Double cost;
	
	private String cardTypeName;
	
	public BalanceExcelPoi(String cardId, String hodeCardName, Date createTime,
			Date costTime, Double cost, String cardTypeName) {
		super();
		this.cardId = cardId;
		this.hodeCardName = hodeCardName;
		this.createTime = createTime;
		this.costTime = costTime;
		this.cost = cost;
		this.cardTypeName = cardTypeName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getHodeCardName() {
		return hodeCardName;
	}

	public void setHodeCardName(String hodeCardName) {
		this.hodeCardName = hodeCardName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCostTime() {
		return costTime;
	}

	public void setCostTime(Date costTime) {
		this.costTime = costTime;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

}
