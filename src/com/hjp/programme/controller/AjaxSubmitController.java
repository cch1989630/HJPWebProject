package com.hjp.programme.controller;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.vo.Staff;

/*
 * 用于给Ajax统一调用的controller，并且做到
 */
@Controller(value="AjaxSubmitController")
public class AjaxSubmitController {
	
	@Resource(name = "staffService")
	private IStaffService staffService;
	
	@RequestMapping("/ajaxNotUrl.do")
	public void ajaxNotUrl(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setContentType("text/html;charset=UTF-8"); 
		try {
			String data = req.getParameter("data");
			JSONObject json = new JSONObject(data);
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			Staff staff = staffService.queryStaffByStaffId(userName);
			json.put("merchantId", staff.getMerchantId());
			json.put("childMerchantId", staff.getChildMerchantId());
			json.put("staffId", staff.getStaffId());
			json.put("merchantName", staff.getMerchantName());
			
			String className = req.getParameter("beanName");
			String methodName = req.getParameter("functionName");
			
			if(className==null || "".equals(className)) {
				throw new Exception();
			}
			if(methodName==null || "".equals(methodName)) {
				throw new Exception();
			}
			
			Class[] argsClassType = new Class[1];
			argsClassType[0] = json.getClass();
			
			Object[] argsObject = new Object[1];
			argsObject[0] = json;
			
			
			WebApplicationContext web = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
			Object classObject = web.getBean(className);
			
			Class<?> ajaxClass = classObject.getClass();
			Method method = ajaxClass.getMethod(methodName, argsClassType);
			
			JSONObject jo = new JSONObject();
		
			jo = (JSONObject) method.invoke(classObject, argsObject);
			jo.put("state", 1);
			res.getWriter().print(jo.toString());
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				InvocationTargetException targerException = (InvocationTargetException) e;
				if (targerException.getTargetException() instanceof CCHException) {
					//提示信息不需要前台抛出
					e.printStackTrace();
					CCHException cchException = (CCHException) targerException.getTargetException();
					res.setStatus(500);
					res.getWriter().print(cchException.getExceptionMessage());
				} else {
					/*系统出错，应该是代码问题，不能直接把错误抛给用户
					 * 需要用日志把错误记录下来，方便查看
					*/
					e.printStackTrace();
					res.setStatus(500);
					res.getWriter().print("系统出错，工程师马上到！请稍后");
				}
			} else {
				/*系统出错，应该是代码问题，不能直接把错误抛给用户
				 * 需要用日志把错误记录下来，方便查看
				*/
				e.printStackTrace();
				res.setStatus(500);
				res.getWriter().print("系统出错，工程师马上到！请稍后");
			}
		}
	}
	
	@RequestMapping("/ajaxRedirect.do")
	public String ajaxRedirect(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		res.setContentType("text/html;charset=UTF-8"); 
		String returnString="";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			Staff staff = staffService.queryStaffByStaffId(userName);
			String data = req.getParameter("data");
			JSONObject json = new JSONObject(data);
			
			json.put("merchantId", staff.getMerchantId());
			json.put("childMerchantId", staff.getChildMerchantId());
			json.put("staffId", staff.getStaffId());
			json.put("merchantName", staff.getMerchantName());
			
			String className = req.getParameter("beanName");
			String methodName = req.getParameter("functionName");
			
			if(className==null || "".equals(className)) {
				throw new Exception();
			}
			if(methodName==null || "".equals(methodName)) {
				throw new Exception();
			}
			
			HashMap map = new HashMap();
			
			Class[] argsClassType = new Class[2];
			argsClassType[0] = map.getClass();
			argsClassType[1] = json.getClass();
			
			Object[] argsObject = new Object[2];
			argsObject[0] = map;
			argsObject[1] = json;
			
			WebApplicationContext web = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
			Object classObject = web.getBean(className);
			
			Class<?> ajaxClass = classObject.getClass();
			Method method = ajaxClass.getMethod(methodName, argsClassType);
			
			returnString = (String) (method.invoke(classObject, argsObject));
			model.addAllAttributes(map);
			return returnString;
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				InvocationTargetException targerException = (InvocationTargetException) e;
				if (targerException.getTargetException() instanceof CCHException) {
					//提示信息不需要前台抛出
					//e.printStackTrace();
					CCHException cchException = (CCHException) targerException.getTargetException();
					res.setStatus(500);
					res.getWriter().print(cchException.getExceptionMessage());
				} else {
					/*系统出错，应该是代码问题，不能直接把错误抛给用户
					 * 需要用日志把错误记录下来，方便查看
					*/
					//e.printStackTrace();
					res.setStatus(500);
					res.getWriter().print("系统出错，工程师马上到！请稍后");
				}
			} else {
				/*系统出错，应该是代码问题，不能直接把错误抛给用户
				 * 需要用日志把错误记录下来，方便查看
				*/
				//e.printStackTrace();
				res.setStatus(500);
				res.getWriter().print("系统出错，工程师马上到！请稍后");
			}
		}
		return returnString;
	}
}
