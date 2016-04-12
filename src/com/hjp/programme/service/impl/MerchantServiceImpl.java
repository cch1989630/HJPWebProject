package com.hjp.programme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.programme.mapper.MerchantMapper;
import com.hjp.programme.service.IMerchantService;
import com.hjp.programme.vo.MerchantInfo;

@Service(value="merchantService")
public class MerchantServiceImpl implements IMerchantService {

	@Resource(name="merchantMapper")  
    private MerchantMapper merchantMapper;
	
	@Override
	public List<MerchantInfo> queryMerchantInfo(String merchantId) {
		return merchantMapper.queryMerchantInfo(merchantId);
	}

}
