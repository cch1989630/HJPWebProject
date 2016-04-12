package com.hjp.programme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.BalanceMapper;
import com.hjp.programme.mapper.MemberCardMapper;
import com.hjp.programme.mapper.SequenceMapper;
import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;

@Service(value="memberCardService")
public class MemberCardServiceImpl implements IMemberCardService {
	
	@Resource(name="memberCardMapper")  
    private MemberCardMapper memberCardMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Resource(name="balanceMapper")  
    private BalanceMapper balanceMapper;
	
	@Override
	public int insertMemberCard(MemberCard memberCard) {
		return memberCardMapper.insertMemberCard(memberCard);
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
			throw new CCHException("0", "会员卡消费失败");
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

}
