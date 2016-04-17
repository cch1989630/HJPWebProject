package com.hjp.programme.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hjp.programme.service.IMerchantService;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.MerchantRegister;
import com.hjp.programme.vo.Staff;

@Controller(value="MerchantManageController")
public class MerchantManageController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "merchantService")
	private IMerchantService merchantService;
	
	@RequestMapping(value = "/addMerchantInfo.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String addMerchantInfo(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "addMerchantInfo";
	}
	
	/**
	 * 查询店铺信息列表
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMerchantList.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryMerchantList(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		JSONArray merchantArray = new JSONArray();
		List<MerchantInfo> merchantList = merchantService.queryMerchantInfo(staff.getMerchantId());
		for (int i = 0; i < merchantList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", merchantList.get(i).getMerchantId());
			oneObject.put("text", merchantList.get(i).getMerchantName());
			merchantArray.put(oneObject);
		}
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(merchantArray.toString());
	}
	
	@RequestMapping(value = "/queryMerchantInfo.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryMerchantInfo(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<MerchantInfo> merchantInfoList = merchantService.queryMerchantInfoByPage(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		for (int i = 0; i < merchantInfoList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("merchantName", merchantInfoList.get(i).getMerchantName());
			oneObject.put("merchantId", merchantInfoList.get(i).getMerchantId());
			oneObject.put("state", merchantInfoList.get(i).getState());
			if (merchantInfoList.get(i).getState().equals("1")) {
				oneObject.put("stateName", "生效");
			} else {
				oneObject.put("stateName", "失效");
			}
			merchantArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", merchantArray);
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 保存分店部门信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveMerchantInfo(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setMerchantName(json.getString("selectMerchantName"));
		merchantInfo.setState("1");
		
		MerchantRegister merchantRegister = new MerchantRegister();
		merchantRegister.setMerchantId(json.getString("merchantId"));
		merchantService.insertMerchantInfo(merchantInfo, merchantRegister);
		
		return returnJson;
	}
	
	/**
	 * 更新分店部门信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateMerchantInfo(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("merchantId", json.get("selectMerchantId"));
		cond.put("merchantName", json.get("selectMerchantName"));
		merchantService.updateMerchantInfo(cond);
		
		return returnJson;
	}
}
