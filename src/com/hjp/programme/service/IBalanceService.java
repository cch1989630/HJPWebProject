package com.hjp.programme.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.programme.util.CCHException;
import com.hjp.programme.vo.Balance;

public interface IBalanceService {
	void updateBalance(HashMap<String, Object> cond);
	
	void updateBalanceMonth(HashMap<String, Object> cond);
	
	Balance queryMemberCardAllBalance(HashMap<String, Object> cond);
	
	List<Balance> queryBalanceInfo(HashMap<String, Object> cond);
	
	void updateBalanceByMonth(Balance balance, String newCardId) throws CCHException;
}
