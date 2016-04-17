package com.hjp.programme.poi.vo;

import java.util.Date;

public class MemberBalanceExcelPoi {
	
	private String merchantName;
	
	private String staffName;
	
	private Date costTime;
	
	private Double cost;
	
	private Double costCardBalance;

	public MemberBalanceExcelPoi(String merchantName, String staffName,
			Date costTime, Double cost, Double costCardBalance) {
		super();
		this.merchantName = merchantName;
		this.staffName = staffName;
		this.costTime = costTime;
		this.cost = cost;
		this.costCardBalance = costCardBalance;
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
