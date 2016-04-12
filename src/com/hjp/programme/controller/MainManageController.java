package com.hjp.programme.controller;

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

import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.vo.Staff;

@Controller(value="MainManageController")
public class MainManageController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String login(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "login";
	}
	
	@RequestMapping(value = "/checkUserExist.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void checkUserExist(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		JSONObject returnJson = new JSONObject();
		String staffId = req.getParameter("username");
		
		Staff staff = staffService.queryStaffByStaffId(staffId);
		if (staff == null) {
			returnJson.put("status", "0");
			returnJson.put("message", "该工号不存在");
		} else {
			returnJson.put("status", "1");
			returnJson.put("message", "验证通过");
		}
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	@RequestMapping(value = "/welcome.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String welocme(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "welcome";
	}
	
	public JSONObject checkUserExist(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		String staffId = json.getString("staffId");
		
		
		Staff staff = staffService.queryStaffByStaffId(staffId);
		if (staff == null) {
			throw new CCHException("0", "该工号不存在");
		}
		
		return returnJson;
	}
	
}
