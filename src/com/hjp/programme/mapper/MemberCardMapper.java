package com.hjp.programme.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MemberCard;

@Component("memberCardMapper")
public interface MemberCardMapper {
	int insertMemberCard(MemberCard memberCard);
	
	List<MemberCard> queryMemberCardInfo(String cardId);
	
	void updateMemberCardByCost(MemberCard memberCard);
	
	List<MemberCard> queryMemberCardBalanceByPage(Page page);
	
	List<MemberCard> queryMemberCardInfoByPage(Page page);
}
