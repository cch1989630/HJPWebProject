package com.hjp.programme.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;
import com.hjp.programme.vo.ReturnCard;

public interface IMemberCardService {
	void insertMemberCard(MemberCard memberCard) throws CCHException;
	
	List<MemberCard> queryMemberCardInfo(String cardId);
	
	public void checkOutByMemberCard(Balance balance, MemberCard memberCard) throws CCHException;
	
	List<MemberCard> queryMemberCardBalanceByPage(Page page);
	
	List<MemberCard> queryMemberCardInfoByPage(Page page);
	
	List<MemberCard> queryMemberCardBalance(HashMap<String, Object> cond);
	
	List<MemberCard> queryMemberCardInfoByType(HashMap<String, Object> cond);
	
	void updateMemberCard(MemberCard memberCard, String newCardId) throws CCHException;
	
	void deleteMemberCard(MemberCard memberCard) throws CCHException;
	
	void returnMemberCard(ReturnCard returnCard) throws CCHException;
	
	List<MemberCard> queryReturnCardByPage(Page page);
	
	List<MemberCard> queryReturnCard(HashMap<String, Object> cond);
}
