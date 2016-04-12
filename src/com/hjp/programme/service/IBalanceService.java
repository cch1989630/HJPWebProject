package com.hjp.programme.service;

import java.util.HashMap;

public interface IBalanceService {
	void updateBalance(HashMap<String, Object> cond);
	
	void updateBalanceMonth(HashMap<String, Object> cond);
}
