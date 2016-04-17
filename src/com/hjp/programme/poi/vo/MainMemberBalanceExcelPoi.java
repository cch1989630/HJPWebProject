package com.hjp.programme.poi.vo;

import java.util.Date;

public class MainMemberBalanceExcelPoi {
	private String cardId;
	
	private String hodeCardName;
	
	private String hodeCardPhone;
	
	private Date createTime;
	
	private String cardTypeName;
	
	private String merchantName;
	
	private String staffName;
	
	private Date costTime;
	
	private Double cost;
	
	private Double costCardBalance;

	public MainMemberBalanceExcelPoi(String cardId, String hodeCardName,
			String hodeCardPhone, Date createTime, String cardTypeName,
			String merchantName, String staffName, Date costTime, Double cost,
			Double costCardBalance) {
		super();
		this.cardId = cardId;
		this.hodeCardName = hodeCardName;
		this.hodeCardPhone = hodeCardPhone;
		this.createTime = createTime;
		this.cardTypeName = cardTypeName;
		this.merchantName = merchantName;
		this.staffName = staffName;
		this.costTime = costTime;
		this.cost = cost;
		this.costCardBalance = costCardBalance;
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

	public String getHodeCardPhone() {
		return hodeCardPhone;
	}

	public void setHodeCardPhone(String hodeCardPhone) {
		this.hodeCardPhone = hodeCardPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
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

	public Double getCostCardBalance() {
		return costCardBalance;
	}

	public void setCostCardBalance(Double costCardBalance) {
		this.costCardBalance = costCardBalance;
	}
}
