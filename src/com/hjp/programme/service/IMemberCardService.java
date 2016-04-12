package com.hjp.programme.service;

import java.util.List;

import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;

public interface IMemberCardService {
	int insertMemberCard(MemberCard memberCard);
	
	List<MemberCard> queryMemberCardInfo(String cardId);
	
	public void checkOutByMemberCard(Balance balance, MemberCard memberCard) throws CCHException;
	
	List<MemberCard> queryMemberCardBalanceByPage(Page page);
	
	List<MemberCard> queryMemberCardInfoByPage(Page page);
}
