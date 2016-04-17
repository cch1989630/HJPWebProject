package com.hjp.programme.mapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.MerchantRegister;

@Component("merchantMapper")
public interface MerchantMapper {
	
	List<MerchantInfo> queryMerchantInfo(String merchantId);
	
	List<MerchantInfo> queryMerchantInfoByPage(Page page);
	
	void insertMerchantInfo(MerchantInfo merchantInfo);
	
	void insertMerchantRegister(MerchantRegister merchantRegister);
	
	void updateMerchantInfo(HashMap<String, Object> cond);
}
