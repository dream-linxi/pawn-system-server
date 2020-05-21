package com.dreams.security.qqLogin;

import com.dreams.sys.bo.UserBo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dreams-linxi
 * @date 2020/5/18 9:42
 */
@Component
public class QQSuccessHandler implements AuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();
        String username = userBo.getUsername();
        if (username == null || username.trim().equals("")){
            httpServletResponse.sendRedirect("/bangding.html");
        } else {
            httpServletResponse.sendRedirect("/index.html");
        }
    }
}
