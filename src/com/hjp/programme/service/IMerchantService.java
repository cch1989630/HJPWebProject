package com.hjp.programme.service;

import java.util.List;

import com.hjp.programme.vo.MerchantInfo;

public interface IMerchantService {
	List<MerchantInfo> queryMerchantInfo(String merchantId);
}
