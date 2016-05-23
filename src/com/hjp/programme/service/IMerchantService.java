package com.hjp.programme.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.MerchantPrinter;
import com.hjp.programme.vo.MerchantRegister;

public interface IMerchantService {
	List<MerchantInfo> queryMerchantInfo(HashMap<String, Object> cond);
	
	List<MerchantInfo> queryMerchantInfoByPage(Page page);
	
	List<MerchantPrinter> queryMerchantPrinter(HashMap<String, Object> cond);
	
	void insertMerchantInfo(MerchantInfo merchantInfo, MerchantRegister merchantRegister) throws CCHException;
	
	void insertMerchantrPrinter(MerchantPrinter merchantPrinter) throws CCHException;
	
	void updateMerchantInfo(HashMap<String, Object> cond) throws CCHException;
	
	void updateMerchantPrinter(HashMap<String, Object> cond) throws CCHException;
}
