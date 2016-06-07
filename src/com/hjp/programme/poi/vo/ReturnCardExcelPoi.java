package com.hjp.programme.poi.vo;

import java.util.Date;

public class ReturnCardExcelPoi {
	private String cardId;
	
	private String cardTypeName;
	
	private String hodeCardPhone;
	
	private Date createTime;
	
	private Date returnDate;
	
	private String staffName;
	
	private Double cardBalance;

	public ReturnCardExcelPoi(String cardId, String cardTypeName,
			String hodeCardPhone, Date createTime, Date returnDate,
			String staffName, Double cardBalance) {
		super();
		this.cardId = cardId;
		this.cardTypeName = cardTypeName;
		this.hodeCardPhone = hodeCardPhone;
		this.createTime = createTime;
		this.returnDate = returnDate;
		this.staffName = staffName;
		this.cardBalance = cardBalance;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
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

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Double getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}
}
