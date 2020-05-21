package com.dreams.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler
{
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication)
			throws IOException, ServletException {
		String xhr = req.getHeader("X-Requested-With");

		if (xhr != null && xhr.equals("XMLHttpRequest")) {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			Map<String,Object> map = new HashMap<>();
			map.put("state", 1);
			map.put("msg", "登录成功");
			map.put("data", authentication);
			resp.getWriter().write(objectMapper.writeValueAsString(map));
			resp.getWriter().flush();
		} else {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			Map<String,Object> map = new HashMap<>();
			map.put("state", 1);
			map.put("msg", "登录成功");
			map.put("data", authentication);
			resp.getWriter().write(objectMapper.writeValueAsString(map));
			resp.getWriter().flush();
		}

	}

}
