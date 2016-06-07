package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.BalanceMapper;
import com.hjp.programme.mapper.MemberCardMapper;
import com.hjp.programme.mapper.ReturnCardMapper;
import com.hjp.programme.mapper.SequenceMapper;
import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;
import com.hjp.programme.vo.ReturnCard;

@Service(value="memberCardService")
public class MemberCardServiceImpl implements IMemberCardService {
	
	@Resource(name="memberCardMapper")  
    private MemberCardMapper memberCardMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Resource(name="balanceMapper")  
    private BalanceMapper balanceMapper;
	
	@Resource(name="returnCardMapper")  
    private ReturnCardMapper returnCardMapper;
	
	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertMemberCard(MemberCard memberCard) throws CCHException {
		List<MemberCard> existMemberCard = memberCardMapper.queryMemberCardInfo(memberCard.getCardId());
		if (existMemberCard.size() > 0) {
			throw new CCHException("0", "该[" + memberCard.getCardId() + "]贵宾卡已存在,请重新输入贵宾卡号");
		}
		try {
			memberCardMapper.insertMemberCard(memberCard);
		} catch (Exception e) {
			throw new CCHException("0", "新增贵宾卡失败");
		}
	}

	@Override
	public List<MemberCard> queryMemberCardInfo(String cardId) {
		return memberCardMapper.queryMemberCardInfo(cardId);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void checkOutByMemberCard(Balance balance, MemberCard memberCard) throws CCHException {
		try {
			Long costId = sequenceMapper.nextval("SEQ_COST_ID");
			balance.setCostId(costId.toString());
			balanceMapper.insertBalance(balance);
			memberCardMapper.updateMemberCardByCost(memberCard);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "贵宾卡消费失败");
		}
	}

	@Override
	public List<MemberCard> queryMemberCardBalanceByPage(Page page) {
		return memberCardMapper.queryMemberCardBalanceByPage(page);
	}

	@Override
	public List<MemberCard> queryMemberCardInfoByPage(Page page) {
		return memberCardMapper.queryMemberCardInfoByPage(page);
	}

	@Override
	public List<MemberCard> queryMemberCardBalance(HashMap<String, Object> cond) {
		return memberCardMapper.queryMemberCardBalance(cond);
	}

	@Override
	public List<MemberCard> queryMemberCardInfoByType(
			HashMap<String, Object> cond) {
		return memberCardMapper.queryMemberCardInfoByType(cond);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void updateMemberCard(MemberCard memberCard, String newCardId) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardId", memberCard.getCardId());
		List<Balance> balanceList = balanceMapper.queryBalanceInfo(cond);
		if (balanceList.size() > 0) {
			throw new CCHException("0", "该贵宾卡存在消费记录，不可修改！");
		}
		
		try {
			cond.put("cardId", newCardId);
			cond.put("hodeCardName", memberCard.getHodeCardName());
			cond.put("cardTypeCode", memberCard.getCardTypeCode());
			cond.put("hodeCardPhone", memberCard.getHodeCardPhone());
			cond.put("cardBalance", memberCard.getCardBalance());
			cond.put("updateCardId", memberCard.getCardId());
			memberCardMapper.updateMemberCard(cond);
		} catch (Exception e) {
			throw new CCHException("0", "贵宾卡修改失败！");
		}
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void deleteMemberCard(MemberCard memberCard) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardId", memberCard.getCardId());
		List<Balance> balanceList = balanceMapper.queryBalanceInfo(cond);
		if (balanceList.size() > 0) {
			throw new CCHException("0", "该贵宾卡存在消费记录，不可修改！");
		}
		
		try {
			memberCardMapper.deleteMemberCard(cond);
		} catch (Exception e) {
			throw new CCHException("0", "贵宾卡删除失败！");
		}
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void returnMemberCard(ReturnCard returnCard) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardId", returnCard.getCardId());
		List<ReturnCard> returnCardList = returnCardMapper.queryReturnCard(cond);
		if (returnCardList.size() > 0) {
			throw new CCHException("0", "该贵宾卡已退卡，无法再次退卡！");
		}
		
		try {
			cond.clear();
			cond.put("cardBalance", 0);
			cond.put("updateCardId", returnCard.getCardId());
			memberCardMapper.updateMemberCard(cond);
			
			returnCardMapper.insertReturnCard(returnCard);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "退卡失败！");
		}
	}

	@Override
	public List<MemberCard> queryReturnCardByPage(Page page) {
		return memberCardMapper.queryReturnCardByPage(page);
	}

	@Override
	public List<MemberCard> queryReturnCard(HashMap<String, Object> cond) {
		return memberCardMapper.queryReturnCard(cond);
	}

}
