package com.hjp.programme.controller;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hjp.programme.service.IBalanceService;
import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.Staff;

@Controller(value="FinanceController")
public class FinanceController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "memberCardService")
	private IMemberCardService memberCardService;
	
	@Resource(name = "balanceService")
	private IBalanceService balanceService;
	
	@RequestMapping(value = "/financeConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String financeConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		model.addAttribute("loginStaffId", staff.getStaffId());
		model.addAttribute("loginStaffName", staff.getStaffName());
		return "financeConsume";
	}
	
	@RequestMapping(value = "/financeMonth.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String financeMonth(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "financeMonth";
	}
	
	/**
	 * 财务修改数据
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	public JSONObject updateFinanceConsume(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		String costId = json.getString("costId");
		String cost = json.getString("cost");
		String cardId = json.getString("cardId");
		String costCardBalance = json.getString("costCardBalance");
		String oldCardId = json.getString("oldCardId");
		
		Balance balance = new Balance();
		balance.setCostId(costId);
		balance.setCost((long)DateStringUtils.mul(Double.parseDouble(cost), 100.0));
		balance.setCardBalance((long)DateStringUtils.mul(Double.parseDouble(costCardBalance), 100.0));
		balance.setEditStaffId(json.getString("staffId"));
		balance.setEditTime(new Date());
		balance.setCardId(oldCardId);
		
		balanceService.updateBalanceByMonth(balance, cardId);
		
		return returnJson;
	}
	
	/**
	 * 对没有月结的数据进行月结
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject monthCheckOut(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		String beginTime = json.getString("beginTime");
		String endTime = json.getString("endTime");
		
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("merchantId", json.get("merchantId"));
		cond.put("isMonth", "1");
		if (beginTime != null && !"".equals(beginTime)) {
			cond.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			cond.put("endTime", endTime);
		}
		
		balanceService.updateBalanceMonth(cond);
		
		return returnJson;
	}
}
