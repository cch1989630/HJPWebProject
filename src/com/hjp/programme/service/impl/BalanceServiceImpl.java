package com.hjp.programme.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.programme.mapper.BalanceMapper;
import com.hjp.programme.service.IBalanceService;

@Service(value="balanceService")
public class BalanceServiceImpl implements IBalanceService {

	@Resource(name="balanceMapper")  
    private BalanceMapper balanceMapper;
	
	@Override
	public void updateBalance(HashMap<String, Object> cond) {
		balanceMapper.updateBalance(cond);
	}

	@Override
	public void updateBalanceMonth(HashMap<String, Object> cond) {
		balanceMapper.updateBalanceMonth(cond);
	}

}
