package com.hjp.programme.mapper;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.hjp.programme.vo.Balance;

@Component("balanceMapper")
public interface BalanceMapper {
	
 	void insertBalance(Balance balance);
	
 	void updateBalance(HashMap<String, Object> cond);
 	
 	void updateBalanceMonth(HashMap<String, Object> cond);
}
