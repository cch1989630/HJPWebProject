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
import com.hjp.programme.vo.MerchantPrinter;
import com.hjp.programme.vo.MerchantRegister;

@Service(value="merchantService")
public class MerchantServiceImpl implements IMerchantService {

	@Resource(name="merchantMapper")  
    private MerchantMapper merchantMapper;
	
	@Resource(name="sequenceMapper")  
    private SequenceMapper sequenceMapper;
	
	@Override
	public List<MerchantInfo> queryMerchantInfo(HashMap<String, Object> cond) {
		return merchantMapper.queryMerchantInfo(cond);
	}

	@Override
	public List<MerchantInfo> queryMerchantInfoByPage(Page page) {
		return merchantMapper.queryMerchantInfoByPage(page);
	}

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertMerchantInfo(MerchantInfo merchantInfo, MerchantRegister merchantRegister)
			throws CCHException {
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("merchantId", merchantInfo.getMerchantId());
		List<MerchantInfo> existMerchantList = merchantMapper.queryMerchantInfo(cond);
		if (existMerchantList.size() > 0) {
			throw new CCHException("0", "您输入的部门代码已存在，请重新输入");
		}
		cond.clear();
		cond.put("merchantName", merchantInfo.getMerchantName());
		existMerchantList = merchantMapper.queryMerchantInfo(cond);
		if (existMerchantList.size() > 0) {
			throw new CCHException("0", "您输入的部门名称已存在，请重新输入");
		}
		
		try {
			merchantMapper.insertMerchantInfo(merchantInfo);
			merchantMapper.insertMerchantRegister(merchantRegister);
		} catch (Exception e) {
			throw new CCHException("0", "新增分店部门失败");
		}
	}

	@Override
	public void updateMerchantInfo(HashMap<String, Object> cond) throws CCHException {
		HashMap<String, Object> queryCond = new HashMap<String, Object>();
		queryCond.put("merchantName", cond.get("merchantName"));
		List<MerchantInfo> existMerchantList = merchantMapper.queryMerchantInfo(queryCond);
		if (existMerchantList.size() > 0) {
			throw new CCHException("0", "您输入的部门名称已存在，请重新输入");
		}
		merchantMapper.updateMerchantInfo(cond);
	}

	@Override
	public void insertMerchantrPrinter(MerchantPrinter merchantPrinter)
			throws CCHException {
		try {
			HashMap<String, Object> cond = new HashMap<String, Object>();
			cond.put("merchantId", merchantPrinter.getMerchantId());
			merchantMapper.deleteMerchantPrinter(cond);
			
			merchantMapper.insertMerchantrPrinter(merchantPrinter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CCHException("0", "修改打印机失败");
		}
	}

	@Override
	public void updateMerchantPrinter(HashMap<String, Object> cond)
			throws CCHException {
		try {
			merchantMapper.updateMerchantPrinter(cond);
		} catch (Exception e) {
			throw new CCHException("0", "修改打印机失败");
		}
	}

	@Override
	public List<MerchantPrinter> queryMerchantPrinter(
			HashMap<String, Object> cond) {
		return merchantMapper.queryMerchantPrinter(cond);
	}

}
