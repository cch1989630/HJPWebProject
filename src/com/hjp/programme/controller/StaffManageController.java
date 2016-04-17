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

import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.util.Page;
import com.hjp.programme.util.PwdEncryptor;
import com.hjp.programme.vo.Role;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

@Controller(value="StaffManageController")
public class StaffManageController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@RequestMapping(value = "/addSaff.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String addSaff(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "addStaff";
	}
	
	@RequestMapping(value = "/changeSelfPassword.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String changeSelfPassword(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "changeSelfPassword";
	}
	
	@RequestMapping(value = "/queryStaffPassword.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String queryStaffPassword(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "queryStaffPassword";
	}
	
	/**
	 * 分页的方式查询操作员信息
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStaffInfo.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryStaffInfo(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		Staff staff = staffService.queryStaffByStaffId(userName);
		
		String pageSize = req.getParameter("rows");
		String currPage = req.getParameter("page");
		String queryStaffId = req.getParameter("queryStaffId");
		
		HashMap<String, Object> con = new HashMap<String, Object>();
		//con.put("merchantId", staff.getMerchantId());
		
		if (queryStaffId != null && !"".equals(queryStaffId)) {
			con.put("queryStaffId", queryStaffId);
		}
		Page page = new Page(Integer.parseInt(currPage), Integer.parseInt(pageSize));
		page.setT(con);
		
		List<Staff> staffList = staffService.queryStaffByPage(page);
		
		JSONObject returnJson = new JSONObject();
		JSONArray staffArray = new JSONArray();
		for (int i = 0; i < staffList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("merchantName", staffList.get(i).getMerchantName());
			oneObject.put("staffId", staffList.get(i).getStaffId());
			oneObject.put("staffName", staffList.get(i).getStaffName());
			oneObject.put("clearPassword", staffList.get(i).getClearPassword());
			oneObject.put("createTime", DateStringUtils.getStringFromDate(staffList.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			staffArray.put(oneObject);
		}
		returnJson.put("total", staffArray.length());
		returnJson.put("rows", staffArray);
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(returnJson.toString());
	}
	
	/**
	 * 菜单权限列表查询
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStaffMenuRole.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void queryStaffMenuRole(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		
		HashMap<String, Object> cond = new HashMap<String, Object>();
		cond.put("roleType", "MENU");
		List<Role> roleList = staffService.queryRole(cond);
		
		JSONArray menuRoleArray = new JSONArray();
		for (int i = 0; i < roleList.size(); i++) {
			JSONObject oneObject = new JSONObject();
			oneObject.put("id", roleList.get(i).getRoleCode());
			oneObject.put("text", roleList.get(i).getRoleName());
			oneObject.put("state", roleList.get(i).getState());
			menuRoleArray.put(oneObject);
		}
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().print(menuRoleArray.toString());
	}
	
	/**
	 * 新增贵宾卡类型
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject addStaff(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		Staff staff = new Staff();
		staff.setMerchantId(json.getString("merchantId"));
		staff.setChildMerchantId(json.getString("selectChildMerchantId"));
		staff.setStaffId(json.getString("selectStaffId"));
		staff.setStaffName(json.getString("staffName"));
		staff.setClearPassword(json.getString("password"));
		staff.setCreateTime(DateStringUtils.getDateFromString(json.getString("createTime"), "yyyy-MM-dd HH:mm:ss"));
		String password = PwdEncryptor.encryptByMD5(json.getString("password"),json.getString("selectStaffId"));
		staff.setPassword(password);
		
		StaffRole staffRole = new StaffRole();
		staffRole.setRoleCode(json.getString("roleCode"));
		
		staffService.insertStaff(staff, staffRole);
		
		return returnJson;
	}
	
	/**
	 * 修改自己的密码
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public JSONObject changePassword(JSONObject json) throws Exception {
		JSONObject returnJson = new JSONObject();
		String oldPassword = json.getString("oldPassword");
		String passwrod = json.getString("password");
		
		String oldPasswordString = PwdEncryptor.encryptByMD5(oldPassword,json.getString("staffId"));
		
		Staff staff = staffService.queryStaffByStaffId(json.getString("staffId"));
		if (!staff.getPassword().equals(oldPasswordString)) {
			throw new CCHException("0", "原密码不正确！");
		}
		
		String newPassword = PwdEncryptor.encryptByMD5(passwrod,json.getString("staffId"));
		staff.setPassword(newPassword);
		staff.setClearPassword(passwrod);
		staffService.updateStaffPassword(staff);
		
		return returnJson;
	}
	
}
