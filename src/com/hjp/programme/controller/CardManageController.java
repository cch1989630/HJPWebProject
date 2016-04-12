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

import com.hjp.programme.service.ICardTypeService;
import com.hjp.programme.service.IMemberCardService;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Balance;
import com.hjp.programme.vo.CardType;
import com.hjp.programme.vo.MemberCard;
import com.hjp.programme.vo.Staff;


@Controller(value="CardManageController")
public class CardManageController {
	
	@Resource(name = "cardTypeService")
	private ICardTypeService cardTypeService;
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@Resource(name = "memberCardService")
	private IMemberCardService memberCardService;
	
	@RequestMapping(value = "/cardType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String cardType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "cardType";
	}
	
	@RequestMapping(value = "/addMemberCard.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String addMemberCard(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "addMemberCard";
	}
	
	@RequestMapping(value = "/cardConsume.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String cardConsume(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		model.put("merchantId", staff.getChildMerchantId());
		model.put("merchantName", staff.getMerchantName());
		model.put("staffId", staff.getStaffId());
		return "cardConsume";
	}
	
	@RequestMapping(value = "/mainMemberCardType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String mainMemberCardType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "mainMemberCardType";
	}
	
	/**
	 * 分页查询会员卡类型以及持有该卡的人数
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCardTypeNumber.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryCardTypeNumber(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		con.put("merchantId", staff.getMerchantId());
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<CardType> cardTypeList = cardTypeService.queryCardTypeNumber(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray cardTypeArray = new JSONArray();
		for (int i = 0; i < cardTypeList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("cardTypeCode", cardTypeList.get(i).getCardTypeCode());
			oneObject.put("cardTypeName", cardTypeList.get(i).getCardTypeName());
			oneObject.put("state", cardTypeList.get(i).getState());
			oneObject.put("stateName", cardTypeList.get(i).getStateName());
			oneObject.put("cardNumber", cardTypeList.get(i).getCardNumber());
			cardTypeArray.put(oneObject);
		}
		returnJson.put("total", cardTypeArray.length());
		returnJson.put("rows", cardTypeArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 会员卡类型下拉框初始化
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCardType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryCardType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		List<CardType> cardTypeList = cardTypeService.queryCardType(staff.getMerchantId());
		JSONArray cardTypeArray = new JSONArray();
		for (int i = 0; i < cardTypeList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", cardTypeList.get(i).getCardTypeCode());
			oneObject.put("text", cardTypeList.get(i).getCardTypeName());
			oneObject.put("state", cardTypeList.get(i).getState());
			cardTypeArray.put(oneObject);
		}
	
		//JSONObject returnJson = new JSONObject();
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(cardTypeArray.toString());
	}
	
	/**
	 * 根据会员卡类型查询出会员卡信息
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCardByType.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryCardByType(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		
		String cardTypeCode = req.getParameter("cardTypeCode");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		//con.put("merchantId", staff.getMerchantId());
		if (cardTypeCode != null && !"".equals(cardTypeCode)) {
			con.put("cardTypeCode", cardTypeCode);
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		JSONObject returnJson = new JSONObject();
		JSONArray memberCardArray = new JSONArray();
		List<MemberCard> memberCardList = memberCardService.queryMemberCardInfoByPage(page);
		for (int i = 0; i < memberCardList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("cardId", memberCardList.get(i).getCardId());
			oneObject.put("hodeCardName", memberCardList.get(i).getHodeCardName());
			oneObject.put("hodeCardPhone", memberCardList.get(i).getHodeCardPhone());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(memberCardList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			oneObject.put("cardTypeName", memberCardList.get(i).getCardTypeName());
			oneObject.put("cardBalance", DateStringUtils.getDoubleFormLong(memberCardList.get(i).getCardBalance(), 100, 2));
			memberCardArray.put(oneObject);
		}
		
		returnJson.put("total", page.getCountRecord());
		returnJson.put("rows", memberCardArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 新增会员卡类型
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveCardType(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		CardType cardType = new CardType();
		cardType.setCardTypeName(json.getString("cardTypeName"));
		cardType.setState("1");
		cardType.setMerchantId(json.getString("merchantId"));
		cardTypeService.insertCardType(cardType);
		
		return returnJson;
	}
	
	/**
	 * 新增会员卡
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject addMemberCard(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		MemberCard memberCard = new MemberCard();
		memberCard.setCardId(json.getString("cardId"));
		memberCard.setCardTypeCode(json.getString("cardTypeCode"));
		memberCard.setHodeCardName(json.getString("hodeCardName"));
		memberCard.setHodeCardPhone(json.getString("hodeCardPhone"));
		memberCard.setCardBalance((long)DateStringUtils.mul(json.getDouble("cardBalance"), 100.0));
		memberCard.setMerchantId(json.getString("merchantId"));
		memberCard.setState("1");
		memberCard.setCreateTime(DateStringUtils.getDateFromString(json.getString("createTime"), "yyyy-MM-dd HH:mm:ss"));
		memberCardService.insertMemberCard(memberCard);
		
		return returnJson;
	}
	
	/**
	 * 查询会员卡信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryCardInfo(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		String cardId = json.getString("cardId");
		List<MemberCard> memberCardList = memberCardService.queryMemberCardInfo(cardId);
		if (memberCardList.size()<=0) {
			throw new CCHException("0", "该["+ cardId +"]会员卡不存在");
		} else {
			returnJson.put("cardBalance", DateStringUtils.getDoubleFormLong(memberCardList.get(0).getCardBalance(), 100, 2));
			returnJson.put("cardTypeCode", memberCardList.get(0).getCardTypeCode());
			returnJson.put("hodeCardName", memberCardList.get(0).getHodeCardName());
			returnJson.put("hodeCardPhone", memberCardList.get(0).getHodeCardPhone());
			returnJson.put("createTime", DateStringUtils.getStringFromDate(memberCardList.get(0).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		return returnJson;
	}
	
	/**
	 * 会员卡消费
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkOutBalance(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		
		Balance balance = new Balance();
		balance.setCardId(json.getString("cardId"));
		balance.setChildMerchantId(json.getString("childMerchantId"));
		balance.setCost((long)DateStringUtils.mul(json.getDouble("cost"), 100.0));
		balance.setCostTime(DateStringUtils.getDateFromString(json.getString("costTime"), "yyyy-MM-dd HH:mm:ss"));
		balance.setMerchantId(json.getString("merchantId"));
		balance.setTradeStaffId(json.getString("staffId"));
		balance.setCardBalance((long)DateStringUtils.mul(json.getDouble("cardBalance"), 100.0));
		
		MemberCard memberCard = new MemberCard();
		memberCard.setCardId(json.getString("cardId"));
		memberCard.setCardBalance((long)DateStringUtils.mul(json.getDouble("cost"), 100.0));
		memberCardService.checkOutByMemberCard(balance, memberCard);
		
		
		return returnJson;
	}
}
