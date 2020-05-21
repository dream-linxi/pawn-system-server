package com.dreams.security;

import com.dreams.sys.po.CodePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dreams-linxi
 * @date 2020/5/13 15:08
 */
@Component
public class PhoneCodeFilter extends OncePerRequestFilter {

    @Autowired
    private FailureHandler failureHandler;

    private void validate(HttpServletRequest req) throws AuthenticationException {
        String ic = req.getParameter("phoneCode");
        if(ic == null || ic.trim().equals(""))
        {
            throw new CodeException("短信码为空");
        }else {
            Object phoneCode = req.getSession().getAttribute("phoneCode");
            if(phoneCode == null)
            {
                throw new CodeException("短信码不存在");
            } else {
                CodePo code = (CodePo)phoneCode;
                if (System.currentTimeMillis() > code.getTime()) {
                    req.getSession().removeAttribute("phoneCode");
                    throw new CodeException("短信码过期");
                } else if(!ic.equals(code.getValue()))
                {
                    throw new CodeException("短信码不相等");
                }
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {
        if (req.getRequestURI().equals("/phoneLogin")) {
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
