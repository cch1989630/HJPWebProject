package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.MerchantMapper;
import com.hjp.programme.mapper.SequenceMapper;
import com.hjp.programme.service.IMerchantService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.MerchantRegister;

@Service(value="merchantService")
public class MerchantServiceImpl implements IMerchantService {

	@Resource(name="merchantMapper")  
    private MerchantMapper merchantMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Override
	public List<MerchantInfo> queryMerchantInfo(String merchantId) {
		return merchantMapper.queryMerchantInfo(merchantId);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoByPage(Page page) {
		return merchantMapper.queryMerchantInfoByPage(page);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertMerchantInfo(MerchantInfo merchantInfo, MerchantRegister merchantRegister)
			throws CCHException {
		try {
			Long childMerchantId = sequenceMapper.nextval("SEQ_MERCHANT_ID");
			merchantInfo.setMerchantId(childMerchantId.toString());
			merchantMapper.insertMerchantInfo(merchantInfo);
			
			merchantRegister.setChildMerchantId(childMerchantId.toString());
			merchantMapper.insertMerchantRegister(merchantRegister);
		} catch (Exception e) {
			throw new CCHException("0", "新增分店部门失败");
		}
	}

	@Override
	public void updateMerchantInfo(HashMap<String, Object> cond) {
		merchantMapper.updateMerchantInfo(cond);
	}

}
