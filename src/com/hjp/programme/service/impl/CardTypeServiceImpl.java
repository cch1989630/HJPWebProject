package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.CardTypeMapper;
import com.hjp.programme.mapper.MemberCardMapper;
import com.hjp.programme.mapper.SequenceMapper;
import com.hjp.programme.service.ICardTypeService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.CardType;
import com.hjp.programme.vo.MemberCard;

@Service(value="cardTypeService")
public class CardTypeServiceImpl implements ICardTypeService {

	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Resource(name="cardTypeMapper")  
    private CardTypeMapper cardTypeMapper;
	
	@Resource(name="memberCardMapper")
	private MemberCardMapper memberCardMapper;
	
	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertCardType(CardType cardType) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardTypeName", cardType.getCardTypeName());
		List<CardType> existCardTypeList = cardTypeMapper.queryCardTypeByCond(cond);
		if (existCardTypeList.size() > 0) {
			throw new CCHException("0", "该贵宾卡类型已存在，请重新输入！");
		}
		
		cond.clear();
		cond.put("cardTypeCode", cardType.getCardTypeCode());
		existCardTypeList = cardTypeMapper.queryCardTypeByCond(cond);
		if (existCardTypeList.size() > 0) {
			throw new CCHException("0", "该贵宾卡类型编码已存在，请重新输入！");
		}
		
		try {
			cardTypeMapper.insertCardType(cardType);
		} catch (Exception e) {
			throw new CCHException("0", "新增贵宾卡类型失败");
		}
	}

	@Override
	public List<CardType> queryCardTypeNumber(Page page) {
		return cardTypeMapper.queryCardTypeNumber(page);
	}

	@Override
	public List<CardType> queryCardType(String merchantId) {
		return cardTypeMapper.queryCardType(merchantId);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void updateCardType(CardType cardType, String newCardTypeCode) throws CCHException {
		List<CardType> existCardTypeList = null;
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardTypeCode", cardType.getCardTypeCode());
		//当编码发生改变时，需要校验是否该类型下已经存在会员卡
		if (!cardType.getCardTypeCode().equals(newCardTypeCode)) {
			List<MemberCard> existMemberCardList = memberCardMapper.queryMemberCardInfoByType(cond);
			if (existMemberCardList.size() > 0) {
				throw new CCHException("0", "该贵宾卡类型已存在贵宾卡，不可改变编码，请重新输入！");
			}
			
			cond.put("cardTypeCode", newCardTypeCode);
			existCardTypeList = cardTypeMapper.queryCardTypeByCond(cond);
			if (existCardTypeList.size() > 0) {
				throw new CCHException("0", "该贵宾卡类型编码已存在，请重新输入！");
			}
		}
		
		cond.clear();
		cond.put("cardTypeName", cardType.getCardTypeName());
		existCardTypeList = cardTypeMapper.queryCardTypeByCond(cond);
		if (existCardTypeList.size() > 0) {
			//当查询到的不是自己的卡类型
			if (!existCardTypeList.get(0).getCardTypeCode().equals(cardType.getCardTypeCode())) {
				throw new CCHException("0", "该贵宾卡类型已存在，请重新输入！");
			}
		}
		
		try {
			cond.put("updateCardTypeCode", cardType.getCardTypeCode());
			cond.put("cardTypeName", cardType.getCardTypeName());
			cond.put("cardTypeBalance", cardType.getCardTypeBalance());
			cond.put("merchantId", cardType.getMerchantId());
			cond.put("cardTypeCode", newCardTypeCode);
			cardTypeMapper.updateCardType(cond);
		} catch (Exception e) {
			throw new CCHException("0", "该贵宾卡类型修改失败！");
		}
		
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void deleteCardType(CardType cardType) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardTypeCode", cardType.getCardTypeCode());
		List<MemberCard> existMemberCardList = memberCardMapper.queryMemberCardInfoByType(cond);
		if (existMemberCardList.size() > 0) {
			throw new CCHException("0", "该贵宾卡类型已存在贵宾卡，不可删除！");
		}
		
		try {
			cardTypeMapper.deleteCardType(cond);
		} catch (Exception e) {
			throw new CCHException("0", "该贵宾卡类型删除失败！");
		}
	}

}
