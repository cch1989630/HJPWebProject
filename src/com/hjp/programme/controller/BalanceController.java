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

import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.CardType;
import com.hjp.programme.vo.MemberCard;
import com.hjp.programme.vo.Staff;

@Controller(value="BalanceController")
public class BalanceController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "memberCardService")
	private IMemberCardService memberCardService;
	
	@RequestMapping(value = "/childMerchantConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String childMerchantConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "childMerchantConsume";
	}
	
	@RequestMapping(value = "/memberCardConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String memberCardConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "memberCardConsume";
	}
	
	@RequestMapping(value = "/mainConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String mainConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "mainConsume";
	}
	
	/**
	 * 查询分店的会员卡消费信息
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryChildMerchantBalance.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryStaffInfo(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.put("childMerchantId", staff.getChildMerchantId());
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<MemberCard> memberCardList = memberCardService.queryMemberCardBalanceByPage(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray memberCardArray = new JSONArray();
		for (int i = 0; i < memberCardList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("cardId", memberCardList.get(i).getCardId());
			oneObject.put("hodeCardName", memberCardList.get(i).getHodeCardName());
			oneObject.put("cardTypeName", memberCardList.get(i).getCardTypeName());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			oneObject.put("cost", DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCost(), 100, 2));
			oneObject.put("costTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getCostTime(), "yyyy-MM-dd HH:mm:ss"));
			memberCardArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", memberCardArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	@RequestMapping(value = "/queryMemberCardBalance.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryMemberCardBalance(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String cardId = req.getParameter("cardId");
		String hodeCardName = req.getParameter("hodeCardName");
		String cardTypeCode = req.getParameter("cardTypeCode");
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		String openTime = req.getParameter("openTime");
		String costMerchantId = req.getParameter("costMerchantId");
		String openBeginTime = req.getParameter("openBeginTime");
		String openEndTime = req.getParameter("openEndTime");
		String cardBalanceBegin = req.getParameter("cardBalanceBegin");
		String cardBalanceEnd = req.getParameter("cardBalanceEnd");
		String costTime = req.getParameter("costTime");
		String cost = req.getParameter("cost");
		String isMonth = req.getParameter("isMonth");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (costTime != null && !"".equals(costTime)) {
			con.put("costTime", costTime);
		}
		if (isMonth != null && !"".equals(isMonth)) {
			con.put("isMonth", isMonth);
		}
		if (cost != null && !"".equals(cost)) {
			con.put("cost", (long)DateStringUtils.mul(Double.parseDouble(cost), 100.0));
		}
		if (costMerchantId != null && !"".equals(costMerchantId)) {
			con.put("costMerchantId", costMerchantId);
		}
		if (openBeginTime != null && !"".equals(openBeginTime)) {
			con.put("openBeginTime", openBeginTime);
		}
		if (openEndTime != null && !"".equals(openEndTime)) {
			con.put("openEndTime", openEndTime);
		}
		if (cardBalanceBegin != null && !"".equals(cardBalanceBegin)) {
			con.put("cardBalanceBegin", (long)DateStringUtils.mul(Double.parseDouble(cardBalanceBegin), 100.0));
		}
		if (cardBalanceEnd != null && !"".equals(cardBalanceEnd)) {
			con.put("cardBalanceEnd", (long)DateStringUtils.mul(Double.parseDouble(cardBalanceEnd), 100.0));
		}
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
		}
		if (cardId != null && !"".equals(cardId)) {
			con.put("cardId", cardId);
		}
		if (hodeCardName != null && !"".equals(hodeCardName)) {
			con.put("hodeCardName", hodeCardName);
		}
		if (cardTypeCode != null && !"".equals(cardTypeCode)) {
			con.put("cardTypeCode", cardTypeCode);
		}
		if (openTime != null && !"".equals(openTime)) {
			con.put("openTime", openTime);
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<MemberCard> memberCardList = memberCardService.queryMemberCardBalanceByPage(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray memberCardArray = new JSONArray();
		for (int i = 0; i < memberCardList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("cardId", memberCardList.get(i).getCardId());
			oneObject.put("hodeCardName", memberCardList.get(i).getHodeCardName());
			oneObject.put("cardTypeName", memberCardList.get(i).getCardTypeName());
			oneObject.put("merchantName", memberCardList.get(i).getMerchantName());
			oneObject.put("staffName", memberCardList.get(i).getStaffName());
			oneObject.put("tradeStaffId", memberCardList.get(i).getTradeStaffId());
			oneObject.put("cost", DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCost(), 100, 2));
			oneObject.put("costTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getCostTime(), "yyyy-MM-dd HH:mm:ss"));
			oneObject.put("createTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			oneObject.put("hodeCardPhone", memberCardList.get(i).getHodeCardPhone());
			oneObject.put("costCardBalance", DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCostCardBalance(),100,2));
			oneObject.put("costId", memberCardList.get(i).getCostId());
			oneObject.put("editStaffName", memberCardList.get(i).getEditStaffName());
			oneObject.put("editStaffId", memberCardList.get(i).getEditStaffId());
			if (memberCardList.get(i).getEditTime() != null) {
				oneObject.put("editTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getEditTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			memberCardArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", memberCardArray);
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
}
