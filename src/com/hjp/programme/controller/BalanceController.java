package com.hjp.programme.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hjp.programme.poi.vo.BalanceExcelPoi;
import com.hjp.programme.poi.vo.MainMemberBalanceExcelPoi;
import com.hjp.programme.poi.vo.MemberBalanceExcelPoi;
import com.hjp.programme.service.IBalanceService;
import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.service.IMerchantService;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.util.ExcelPoiUtil;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.MemberCard;
import com.hjp.programme.vo.MerchantPrinter;
import com.hjp.programme.vo.Staff;

@Controller(value="BalanceController")
public class BalanceController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "memberCardService")
	private IMemberCardService memberCardService;
	
	@Resource(name = "balanceService")
	private IBalanceService balanceService;
	
	@Resource(name = "merchantService")
	private IMerchantService merchantService;
	
	@RequestMapping(value = "/childMerchantConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String childMerchantConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "childMerchantConsume";
	}
	
	@RequestMapping(value = "/memberCardConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String memberCardConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("merchantId", staff.getChildMerchantId());
		List<MerchantPrinter> merchantPrinterList = merchantService.queryMerchantPrinter(cond);
		
		if (merchantPrinterList.size() > 0) {
			model.put("printerName", merchantPrinterList.get(0).getPrinterName());
		} else {
			model.put("printerName", "");
		}
		return "memberCardConsume";
	}
	
	@RequestMapping(value = "/mainConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String mainConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "mainConsume";
	}
	
	/**
	 * 查询分店的贵宾卡消费信息
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryChildMerchantBalance.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryChildMerchantBalance(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.put("costMerchantId", staff.getChildMerchantId());
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		Balance balance = balanceService.queryMemberCardAllBalance(con);
		
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
			oneObject.put("costCardBalance", DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCostCardBalance(), 100, 2));
			memberCardArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", memberCardArray);
		if (balance != null) {
			returnJson.put("allCost", DateStringUtils.getDoubleFormLong(balance.getAllCost(),100,2));
		} else {
			returnJson.put("allCost", "0");
		}
		
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
		
		Balance balance = balanceService.queryMemberCardAllBalance(con);
		
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
			oneObject.put("isMonth", memberCardList.get(i).getIsMonth());
			oneObject.put("balanceMerchantId", memberCardList.get(i).getBalanceMerchantId());
			if (memberCardList.get(i).getIsMonth().equals("1")) {
				oneObject.put("isMonthName", "未月结");
			} else {
				oneObject.put("isMonthName", "已月结");
			}
			if (memberCardList.get(i).getEditTime() != null) {
				oneObject.put("editTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getEditTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			memberCardArray.put(oneObject);
		}
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", memberCardArray);
		if (balance != null) {
			if (balance.getAllCost() != null) {
				returnJson.put("allCost", DateStringUtils.getDoubleFormLong(balance.getAllCost(),100,2));
			} else {
				returnJson.put("allCost", 0);
			}
			if (cardId != null && !"".equals(cardId)) {
				if (balance.getCardTypeCode() != null && !"".equals(balance.getCardTypeCode())) {
					returnJson.put("cardTypeCode",balance.getCardTypeCode());
					returnJson.put("hodeCardName",balance.getHodeCardName());
					returnJson.put("createTime",DateStringUtils.getStringFromDate(balance.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				}
			}
		} 
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	
	@RequestMapping(value = "/exportChildConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void exportChildConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/vnd.ms-excel");
		String fileName = "门店会员卡消费明细";
		res.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.put("costMerchantId", staff.getChildMerchantId());
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
		}
		Balance balance = balanceService.queryMemberCardAllBalance(con);
		
		List<MemberCard> memberCardList = memberCardService.queryMemberCardBalance(con);
		
		List<BalanceExcelPoi> balanceExcelPoiList = new ArrayList<BalanceExcelPoi>();
		for (int i = 0; i < memberCardList.size(); i++) {
			balanceExcelPoiList.add(new BalanceExcelPoi(memberCardList.get(i).getCardId(), memberCardList.get(i).getHodeCardName(), memberCardList.get(i).getCreateTime(),
					memberCardList.get(i).getCostTime(), DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCost(), 100, 2), 
					memberCardList.get(i).getCardTypeName()));
		}
		
		Map<String, String> publicMap = new HashMap<String, String>();
		publicMap.put("门店", staff.getMerchantName());
		publicMap.put("操作日期", DateStringUtils.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		publicMap.put("操作员", staff.getStaffId());
		publicMap.put("总计", DateStringUtils.getDoubleFormLong(balance.getAllCost(),100,2) + "");
		String[] headers = { "卡号", "持卡人", "开卡日期", "消费日期", "消费金额", "卡类型"};
		
		OutputStream out = res.getOutputStream();
		ExcelPoiUtil.exportExcel("门店会员卡消费明细",publicMap, headers, balanceExcelPoiList, out);
		out.flush();
		out.close();
	}
	
	
	@RequestMapping(value = "/exportMemberConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void exportMemberConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/vnd.ms-excel");
		String fileName = "会员卡消费明细";
		res.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
		
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		String cardId = req.getParameter("cardId");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
		}
		if (cardId != null && !"".equals(cardId)) {
			con.put("cardId", cardId);
		}
		Balance balance = balanceService.queryMemberCardAllBalance(con);
		
		List<MemberCard> memberCardList = memberCardService.queryMemberCardBalance(con);
		
		List<MemberBalanceExcelPoi> memberBalanceExcelPoiList = new ArrayList<MemberBalanceExcelPoi>();
		for (int i = 0; i < memberCardList.size(); i++) {
			memberBalanceExcelPoiList.add(new MemberBalanceExcelPoi(memberCardList.get(i).getCardTypeName(), memberCardList.get(i).getCardId(),
					memberCardList.get(i).getMerchantName(),memberCardList.get(i).getStaffName(),
					memberCardList.get(i).getCostTime(), DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCost(), 100, 2), 
					DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCostCardBalance(),100,2)));
		}
		
		Map<String, String> publicMap = new HashMap<String, String>();
		if (balance != null) {
			if (cardId != null && !"".equals(cardId)) {
				if (balance.getCardTypeCode() != null && !"".equals(balance.getCardTypeCode())) {
					publicMap.put("卡号", balance.getCardId());
					publicMap.put("持卡人", balance.getHodeCardName());
					publicMap.put("开卡日期", DateStringUtils.getStringFromDate(balance.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					publicMap.put("卡类型", balance.getCardTypeName());
				}
			}
			if (balance.getAllCost() != null) {
				publicMap.put("总计", DateStringUtils.getDoubleFormLong(balance.getAllCost(),100,2) + "");
			} else {
				publicMap.put("总计", "0");
			}
		} 
		
		String[] headers = {"卡类型", "卡号", "门店", "操作员", "消费日期", "消费金额", "卡余额"};
		
		OutputStream out = res.getOutputStream();
		ExcelPoiUtil.exportExcel("会员卡消费明细",publicMap, headers, memberBalanceExcelPoiList, out);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/exportMainConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void exportMainConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/vnd.ms-excel");
		String fileName = "总部会员卡消费明细";
		res.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
		
		String beginTime = req.getParameter("beginTime");
		String endTime = req.getParameter("endTime");
		String cardId = req.getParameter("cardId");
		String costMerchantId = req.getParameter("costMerchantId");
		String openBeginTime = req.getParameter("openBeginTime");
		String openEndTime = req.getParameter("openEndTime");
		String cardBalanceBegin = req.getParameter("cardBalanceBegin");
		String cardBalanceEnd = req.getParameter("cardBalanceEnd");
		String cardTypeCode = req.getParameter("cardTypeCode");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		if (beginTime != null && !"".equals(beginTime)) {
			con.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			con.put("endTime", endTime);
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
		if (cardId != null && !"".equals(cardId)) {
			con.put("cardId", cardId);
		}
		if (cardTypeCode != null && !"".equals(cardTypeCode)) {
			con.put("cardTypeCode", cardTypeCode);
		}
		Balance balance = balanceService.queryMemberCardAllBalance(con);
		
		List<MemberCard> memberCardList = memberCardService.queryMemberCardBalance(con);
		
		List<MainMemberBalanceExcelPoi> mainMemberBalanceExcelPoiList = new ArrayList<MainMemberBalanceExcelPoi>();
		for (int i = 0; i < memberCardList.size(); i++) {
			mainMemberBalanceExcelPoiList.add(new MainMemberBalanceExcelPoi(memberCardList.get(i).getCardId(), memberCardList.get(i).getHodeCardName(), 
					memberCardList.get(i).getBalanceMerchantId(),memberCardList.get(i).getHodeCardPhone(),
					memberCardList.get(i).getCreateTime(), memberCardList.get(i).getCardTypeName(),
					memberCardList.get(i).getMerchantName(), memberCardList.get(i).getStaffName(),
					memberCardList.get(i).getCostTime(), DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCost(), 100, 2), 
					DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCostCardBalance(),100,2)));
		}
		
		Map<String, String> publicMap = new HashMap<String, String>();
		if (balance != null) {
			if (balance.getAllCost() != null) {
				publicMap.put("总计", DateStringUtils.getDoubleFormLong(balance.getAllCost(),100,2) + "");
			} else {
				publicMap.put("总计", "0");
			}
		} 
		String[] headers = { "卡号", "持卡人", "消费部门", "持卡人联系方式", "开卡日期", "卡类型", "门店", 
				"操作员", "消费日期", "消费金额",  "卡余额"};
		
		OutputStream out = res.getOutputStream();
		ExcelPoiUtil.exportExcel("总部会员卡消费明细",publicMap, headers, mainMemberBalanceExcelPoiList, out);
		out.flush();
		out.close();
	}
}
