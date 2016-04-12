package com.hjp.programme.controller;

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
import com.hjp.programme.vo.MerchantInfo;
import com.hjp.programme.vo.Staff;

@Controller(value="MerchantManageController")
public class MerchantManageController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "merchantService")
	private IMerchantService merchantService;
	
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
	
}
