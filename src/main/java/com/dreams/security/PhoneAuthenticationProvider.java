package com.dreams.security;

import com.dreams.sys.bo.UserBo;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/13 14:16
 */
//@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    public PhoneAuthenticationProvider(){

    }

    public PhoneAuthenticationProvider(UserService userService){
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getPrincipal().toString();
        List<UserPo> userByPhone = this.userService.getUserByPhone(phone);
        UserPo userPo = null;
        if (userByPhone.size()==1){
            userPo = userByPhone.get(0);
        }
        if (userPo != null)
        {
            UserBo userBo = new UserBo(userPo.getUserId(),userPo.getUserName(),null,userPo.getPhone());
            PhoneToken phoneToken = new PhoneToken(userBo, null);
            phoneToken.setDetails(authentication.getDetails());
            return phoneToken;
        }
        throw new InternalAuthenticationServiceException("手机号码未注册!!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneToken.class.isAssignableFrom(authentication);
    }
}
