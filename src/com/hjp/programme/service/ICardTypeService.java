package com.hjp.programme.service;

import java.util.List;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.CardType;

public interface ICardTypeService {
	
	public void insertCardType(CardType cardType);
	
	public List<CardType> queryCardTypeNumber(Page page);
	
	List<CardType> queryCardType(String merchantId);
}
