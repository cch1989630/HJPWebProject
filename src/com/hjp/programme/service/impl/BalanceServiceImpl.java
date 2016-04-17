package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.BalanceMapper;
import com.hjp.programme.mapper.MemberCardMapper;
import com.hjp.programme.service.IBalanceService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;

@Service(value="balanceService")
public class BalanceServiceImpl implements IBalanceService {

	@Resource(name="balanceMapper")  
    private BalanceMapper balanceMapper;
	
	@Resource(name="memberCardMapper")  
    private MemberCardMapper memberCardMapper;
	
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
	public void updateBalanceByMonth(Balance balance) throws CCHException {
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
		
		try {
			cond.clear();
			cond.put("cardId", updateBalanceList.get(0).getCardId());
			cond.put("beginTime", updateBalanceList.get(0).getCostTime());
			List<Balance> afterCostBalanceList = balanceMapper.queryBalanceInfo(cond);
			long changeBalance = balance.getCardBalance() - updateBalanceList.get(0).getCardBalance();
			cond.clear();
			cond.put("cost", balance.getCost());
			cond.put("costId", balance.getCostId());
			cond.put("cardBalance", balance.getCardBalance());
			cond.put("editStaffId", balance.getEditStaffId());
			cond.put("editTime", balance.getEditTime());
			balanceMapper.updateBalance(cond);
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
			memberCard.setCardId(updateBalanceList.get(0).getCardId());
			memberCard.setCardBalance(0 - changeBalance);
			memberCardMapper.updateMemberCardByCost(memberCard);
			
		} catch (Exception e) {
			throw new CCHException("0", "修改会员卡消费失败");
		}
		
	}

}
