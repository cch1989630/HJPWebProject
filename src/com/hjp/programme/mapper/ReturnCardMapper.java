package com.hjp.programme.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.vo.ReturnCard;

@Component("returnCardMapper")
public interface ReturnCardMapper {
	
	void insertReturnCard(ReturnCard returnCard);
	
	void deleteReturnCard(HashMap<String, Object> cond);
	
	List<ReturnCard> queryReturnCard(HashMap<String, Object> cond);
}
