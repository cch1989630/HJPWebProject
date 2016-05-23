package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.BalanceMapper;
import com.hjp.programme.mapper.CardTypeMapper;
import com.hjp.programme.mapper.MemberCardMapper;
import com.hjp.programme.service.IBalanceService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.CardType;
import com.hjp.programme.vo.MemberCard;

@Service(value="balanceService")
public class BalanceServiceImpl implements IBalanceService {

	@Resource(name="balanceMapper")  
    private BalanceMapper balanceMapper;
	
	@Resource(name="memberCardMapper")  
    private MemberCardMapper memberCardMapper;
	
	@Resource(name="cardTypeMapper")  
    private CardTypeMapper cardTypeMapper;
	
	@Override
	public void updateBalance(HashMap<String, Object> cond) {
		balanceMapper.updateBalance(cond);
	}

	@Override
	public void updateBalanceMonth(HashMap<String, Object> cond) {
		balanceMapper.updateBalanceMonth(cond);
	}

	@Override
	public Balance queryMemberCardAllBalance(HashMap<String, Object> cond) {
		return balanceMapper.queryMemberCardAllBalance(cond);
	}

	@Override
	public List<Balance> queryBalanceInfo(HashMap<String, Object> cond) {
		return balanceMapper.queryBalanceInfo(cond);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void updateBalanceByMonth(Balance balance, String newCardId) throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("costId", balance.getCostId());
		List<Balance> updateBalanceList = balanceMapper.queryBalanceInfo(cond);
		if (updateBalanceList.size() > 0) {
			if (updateBalanceList.get(0).getIsMonth().equals("1")) {
				throw new CCHException("0", "该消费记录已月结不可修改，请刷新页面");
			}
		} else {
			throw new CCHException("0", "不存在该消费记录，请刷新页面重试");
		}
		
		List<MemberCard> existMemberCardList = memberCardMapper.queryMemberCardInfo(newCardId);
		if (existMemberCardList.size() <= 0) {
			throw new CCHException("0", "不存在[" + newCardId + "]该贵宾卡，请重新输入");
		}
		
		try {
			cond.clear();
			//当输入了新的卡号时，需要进行更新那条数据的cardId
			if (balance.getCardId().equals(newCardId)) {
				cond.put("cardBalance", balance.getCardBalance());
				Long changeBalance = balance.getCardBalance() - updateBalanceList.get(0).getCardBalance();
				updateBalanceStream(changeBalance, updateBalanceList.get(0));
			} else {
				cond.put("cardId", newCardId);
				//将最开始的消费记录的cardId，后面发生的消费余额都增加cost
				updateBalanceStream(updateBalanceList.get(0).getCost(), updateBalanceList.get(0));
				Balance updateBalance = new Balance();
				updateBalance.setCardId(newCardId);
				updateBalance.setCostTime(updateBalanceList.get(0).getCostTime());
				updateBalanceStream(0-updateBalanceList.get(0).getCost(), updateBalance);
				
				//当修改了会员卡号后，需要将当前记录更改成对应卡号记录
				//需要重新计算该卡在该条消费时的余额
				cond.put("endTime", updateBalance.getCostTime());
				List<Balance> beforeCostBalanceList = balanceMapper.queryBalanceInfo(cond);
				if (beforeCostBalanceList.size() > 0) {
					//当存在消费记录时，需要根据前一条记录计算余额
					cond.put("cardBalance", beforeCostBalanceList.get(0).getCardBalance() - balance.getCost());
				} else {
					//当不存在消费记录时，直接使用实际余额进行计算
					cond.put("cardTypeCode", existMemberCardList.get(0).getCardTypeCode());
					List<CardType> cardTypeList = cardTypeMapper.queryCardTypeByCond(cond);
					cond.put("cardBalance", cardTypeList.get(0).getCardTypeBalance() - balance.getCost());
				}
			}
			cond.put("cost", balance.getCost());
			cond.put("costId", balance.getCostId());
			cond.put("editStaffId", balance.getEditStaffId());
			cond.put("editTime", balance.getEditTime());
			balanceMapper.updateBalance(cond);
			
		} catch (Exception e) {
			throw new CCHException("0", "修改会员卡消费失败");
		}
		
	}
	
	public void updateBalanceStream(Long changeBalance, Balance updateBalance) {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("cardId", updateBalance.getCardId());
		cond.put("beginTime", updateBalance.getCostTime());
		List<Balance> afterCostBalanceList = balanceMapper.queryBalanceInfo(cond);
		for (int i = 0; i < afterCostBalanceList.size(); i++) {
			if (afterCostBalanceList.get(i).getIsMonth().equals("1")) {
				continue;
			} else {
				cond.clear();
				cond.put("costId", afterCostBalanceList.get(i).getCostId());
				cond.put("cardBalance", afterCostBalanceList.get(i).getCardBalance() + changeBalance);
				balanceMapper.updateBalance(cond);
			}
		}
		
		MemberCard memberCard = new MemberCard();
		memberCard.setCardId(updateBalance.getCardId());
		memberCard.setCardBalance(0 - changeBalance);
		memberCardMapper.updateMemberCardByCost(memberCard);
	}

}
