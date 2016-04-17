package com.hjp.programme.vo;

import java.util.Date;

public class Balance {
	private String cardId;
	
	private String costId;
	
	private String childMerchantId;
	
	private String merchantId;
	
	private Long cost;
	
	private String tradeStaffId;
	
	private Date costTime;
	
	private Long cardBalance;
	
	private String cardTypeName;
	
	private String editStaffId;
	
	private Date editTime;
	
	public String getHodeCardName() {
		return hodeCardName;
	}

	public void setHodeCardName(String hodeCardName) {
		this.hodeCardName = hodeCardName;
	}

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String isMonth;
	
	private Long allCost;
	
	private String hodeCardName;
	
	private String cardTypeCode;
	
	private Date createTime;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCostId() {
		return costId;
	}

	public void setCostId(String costId) {
		this.costId = costId;
	}

	public String getChildMerchantId() {
		return childMerchantId;
	}

	public void setChildMerchantId(String childMerchantId) {
		this.childMerchantId = childMerchantId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public String getTradeStaffId() {
		return tradeStaffId;
	}

	public void setTradeStaffId(String tradeStaffId) {
		this.tradeStaffId = tradeStaffId;
	}

	public Date getCostTime() {
		return costTime;
	}

	public Long getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(Long cardBalance) {
		this.cardBalance = cardBalance;
	}

	public void setCostTime(Date costTime) {
		this.costTime = costTime;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}

	public Long getAllCost() {
		return allCost;
	}

	public void setAllCost(Long allCost) {
		this.allCost = allCost;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getEditStaffId() {
		return editStaffId;
	}

	public void setEditStaffId(String editStaffId) {
		this.editStaffId = editStaffId;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
}
