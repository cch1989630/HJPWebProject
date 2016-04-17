package com.hjp.programme.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.CardType;

@Component("cardTypeMapper")
public interface CardTypeMapper {
	
	int insertCardType(CardType cardType);
	
	List<CardType> queryCardTypeNumber(Page page);
	
	List<CardType> queryCardType(String merchantId);
	
	void updateCardType(HashMap<String, Object> cond);
}
