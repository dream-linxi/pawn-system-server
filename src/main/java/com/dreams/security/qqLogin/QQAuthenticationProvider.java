package com.dreams.security.qqLogin;

import com.dreams.security.PhoneToken;
import com.dreams.sys.bo.UserBo;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author dreams-linxi
 * @date 2020/5/13 14:16
 */
//@Component
public class QQAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    public QQAuthenticationProvider(){

    }

    public QQAuthenticationProvider(UserService userService){
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String phone = authentication.getPrincipal().toString();
//        UserPo userPo = this.userService.getUserByPhone(phone).get(0);
//        System.out.println(userPo.getUserName());

        Object principal = authentication.getPrincipal();
        UserPo userPo = this.userService.getUserByOpenId(String.valueOf(principal));
        System.out.println("++++++++++++++++++" + principal);
        UserBo userBo = null;
        if (userPo != null){
            userBo = new UserBo(userPo.getUserId(),userPo.getUserName(),null,null,String.valueOf(principal));
        }else {
            userBo = new UserBo();
            userBo.setOpenId(String.valueOf(principal));
        }

        QQToken qqToken = new QQToken(userBo, null);
        qqToken.setDetails(authentication.getDetails());
        return qqToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return QQToken.class.isAssignableFrom(authentication);
    }
}
