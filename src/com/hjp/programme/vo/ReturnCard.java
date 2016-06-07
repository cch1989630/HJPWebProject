package com.hjp.programme.vo;

import java.util.Date;

public class ReturnCard {
	
	private String cardId;
	
	private String merchantId;
	
	private String returnStaffId;
	
	private Date returnDate;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getReturnStaffId() {
		return returnStaffId;
	}

	public void setReturnStaffId(String returnStaffId) {
		this.returnStaffId = returnStaffId;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	
}
