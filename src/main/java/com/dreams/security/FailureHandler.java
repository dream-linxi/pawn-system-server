package com.dreams.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FailureHandler implements AuthenticationFailureHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e)
			throws IOException, ServletException {
		String xhr = req.getHeader("X-Requested-With");

		
		if (xhr != null && xhr.equals("XMLHttpRequest")) {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			Map<String,Object> map = new HashMap<>();
			map.put("state", 2);
			map.put("msg", e.getMessage());
			map.put("data", e);
			resp.getWriter().write(objectMapper.writeValueAsString(map));
			resp.getWriter().flush();
		} else {
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("application/json;charset=utf-8");
			Map<String,Object> map = new HashMap<>();
			map.put("state", 2);
			map.put("msg", e.getMessage());
			map.put("data", e);
			resp.getWriter().write(objectMapper.writeValueAsString(map));
			resp.getWriter().flush();
		}
	}

}
