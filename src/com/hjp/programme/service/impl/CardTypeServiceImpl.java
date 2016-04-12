package com.hjp.programme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.programme.mapper.CardTypeMapper;
import com.hjp.programme.mapper.SequenceMapper;
import com.hjp.programme.service.ICardTypeService;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.CardType;

@Service(value="cardTypeService")
public class CardTypeServiceImpl implements ICardTypeService {

	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Resource(name="cardTypeMapper")  
    private CardTypeMapper cardTypeMapper;
	
	@Override
	public void insertCardType(CardType cardType) {
		
		Long cardTypeCode = sequenceMapper.nextval("SEQ_CARD_TYPE_CODE");
		cardType.setCardTypeCode(cardTypeCode.toString());
		
		cardTypeMapper.insertCardType(cardType);
	}

	@Override
	public List<CardType> queryCardTypeNumber(Page page) {
		return cardTypeMapper.queryCardTypeNumber(page);
	}

	@Override
	public List<CardType> queryCardType(String merchantId) {
		return cardTypeMapper.queryCardType(merchantId);
	}

}
