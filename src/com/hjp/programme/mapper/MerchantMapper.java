package com.hjp.programme.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.vo.MerchantInfo;

@Component("merchantMapper")
public interface MerchantMapper {
	
	List<MerchantInfo> queryMerchantInfo(String merchantId);
	
}
