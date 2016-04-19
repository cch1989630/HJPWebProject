package com.hjp.programme.vo;

public class CardType {
	
	private String cardTypeCode;
	
	private String merchantId;
	
	private String state;
	
	private String cardTypeName;
	
	private String cardNumber;
	
	private String stateName;
	
	private Long cardTypeBalance;

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getCardTypeBalance() {
		return cardTypeBalance;
	}

	public void setCardTypeBalance(Long cardTypeBalance) {
		this.cardTypeBalance = cardTypeBalance;
	}
	
}
