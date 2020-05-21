package com.dreams.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dreams.sys.po.CodePo;

@Component
public class ImageCodeFilter extends OncePerRequestFilter {
	
	@Autowired
	private FailureHandler failureHandler;
	
	private void validate(HttpServletRequest req) throws AuthenticationException{
		String ic = req.getParameter("imageCode");
		if(ic == null || ic.trim().equals(""))
		{
			throw new CodeException("验证码为空");
		}else {
			Object imageCode = req.getSession().getAttribute("imageCode");
			if(imageCode == null)
			{
				throw new CodeException("验证码不存在");
			} else {
				CodePo code = (CodePo)imageCode;
				if (System.currentTimeMillis() > code.getTime()) {
					req.getSession().removeAttribute("imageCode");
					throw new CodeException("验证码过期");
				} else if(!ic.equals(code.getValue()))
				{
					throw new CodeException("验证码不相等");
				}
			}
		}
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		if (req.getRequestURI().equals("/login")) {
			try {
				validate(req);
				filterChain.doFilter(req, resp);
			} catch (AuthenticationException e) {
				failureHandler.onAuthenticationFailure(req, resp, e);
			}
		} else {
			filterChain.doFilter(req, resp);
		}
//		
	}

}
