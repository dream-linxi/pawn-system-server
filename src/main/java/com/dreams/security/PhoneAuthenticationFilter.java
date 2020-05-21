package com.dreams.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dreams-linxi
 * @date 2020/5/13 14:01
 */
public class PhoneAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";

    private String phoneParameter = SPRING_SECURITY_FORM_PHONE_KEY;
    private boolean postOnly = true;

    public PhoneAuthenticationFilter() {
        super(new AntPathRequestMatcher("/phoneLogin", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phone = obtainPhone(request);

        if (phone == null) {
            phone = "";
        }

        phone = phone.trim();

        PhoneToken phoneToken = new PhoneToken(phone);

        setDetails(request, phoneToken);

        return this.getAuthenticationManager().authenticate(phoneToken);
    }

    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(phoneParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              PhoneToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "Username parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getPhoneParameter() {
        return phoneParameter;
    }
}
