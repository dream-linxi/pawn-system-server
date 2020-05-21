package com.dreams.security.qqLogin;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dreams-linxi
 * @date 2020/5/13 14:01
 */
public class QQAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "code";

    private String codeParameter = SPRING_SECURITY_FORM_PHONE_KEY;
    private boolean postOnly = true;

    public QQAuthenticationFilter() {
        super(new AntPathRequestMatcher("/qqlogin", "GET"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101780702&redirect_uri=http://www.pawntest.com/qqlogin&state=test";
        String code1 = restTemplate.getForObject(url, String.class);
        System.out.println("code1===================" + code1);
        String code = obtainCode(request);

        url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=101780702&client_secret=be3cf79b89eafe3f0b8f90462aed8065&code="+ code +"&redirect_uri=http://www.pawntest.com/qqlogin";


        String template = restTemplate.getForObject(url, String.class);
        System.out.println(template); //access_token=CDF476B8D665C26ECE2F98D85A46772F&expires_in=7776000&refresh_token=AB5BAD5427B5597F627B3026A1F4BC62
        String token = template.substring(template.indexOf("=")+1,template.indexOf("&"));

        url = "https://graph.qq.com/oauth2.0/me?access_token="+ token;

        String result = restTemplate.getForObject(url, String.class);
        String openId = result.substring(result.lastIndexOf(":")+2,result.lastIndexOf("\""));



        if (openId == null) {
            openId = "";
        }
        System.out.println(openId + "==================================");
        //request.getSession().setAttribute("openId",openId);
        openId = openId.trim();

        QQToken qqToken = new QQToken(openId);

        setDetails(request, qqToken);

        return this.getAuthenticationManager().authenticate(qqToken);
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              QQToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Username parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getCodeParameter() {
        return codeParameter;
    }
}
