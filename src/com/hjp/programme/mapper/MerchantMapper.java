package com.hjp.programme.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.MerchantPrinter;
import com.hjp.programme.vo.MerchantRegister;

@Component("merchantMapper")
public interface MerchantMapper {
	
	List<MerchantInfo> queryMerchantInfo(HashMap<String, Object> cond);
	
	List<MerchantInfo> queryMerchantInfoByPage(Page page);
	
	List<MerchantPrinter> queryMerchantPrinter(HashMap<String, Object> cond);
	
	void insertMerchantInfo(MerchantInfo merchantInfo);
	
	void insertMerchantRegister(MerchantRegister merchantRegister);
	
	void insertMerchantrPrinter(MerchantPrinter merchantPrinter);
	
	void updateMerchantInfo(HashMap<String, Object> cond);
	
	void updateMerchantPrinter(HashMap<String, Object> cond);
	
	void deleteMerchantPrinter(HashMap<String, Object> cond);
	
}
