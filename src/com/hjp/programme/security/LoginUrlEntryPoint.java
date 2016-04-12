package com.hjp.programme.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class LoginUrlEntryPoint implements AuthenticationEntryPoint {

	/**
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
		String targetUrl = null;
		String url = request.getRequestURI();

		targetUrl = "/login.do";

		targetUrl = request.getContextPath() + targetUrl;
		response.sendRedirect(targetUrl);
	}

}
