package com.hjp.programme.poi.vo;

import java.util.Date;

public class MemberTypeBalanceExcelPoi {
	private String cardId;
	
	private String hodeCardName;
	
	private String hodeCardPhone;
	
	private Date createTime;
	
	private String cardTypeName;
	
	private Double cardBalance;

	public MemberTypeBalanceExcelPoi(String cardId, String hodeCardName,
			String hodeCardPhone, Date createTime, String cardTypeName,
			Double cardBalance) {
		super();
		this.cardId = cardId;
		this.hodeCardName = hodeCardName;
		this.hodeCardPhone = hodeCardPhone;
		this.createTime = createTime;
		this.cardTypeName = cardTypeName;
		this.cardBalance = cardBalance;
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

	public Double getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}
}
