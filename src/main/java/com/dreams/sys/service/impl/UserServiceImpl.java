package com.dreams.sys.service.impl;

import com.dreams.security.qqLogin.QQToken;
import com.dreams.sys.bo.UserBo;
import com.dreams.sys.dao.UserDao;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dreams-linxi
 * @date 2020/5/9 12:29
 */
@Service
public class UserServiceImpl implements UserService
{
    @Resource
    private UserDao userDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPo getUserByUserId(String userId)
    {
        return this.userDao.getUserByUserId(userId);
    }

    @Override
    public Map<String, Object> getAllUser(UserBo userBo)
    {
        Map<String,Object> result = new HashMap<>();
        // 页码值 和 每页显示条数

        PageHelper.startPage(userBo.getPage(),userBo.getLimit());
        List<UserPo> allUser = this.userDao.getAllUser(userBo);
        PageInfo<UserPo> userPageInfo = new PageInfo<UserPo>(allUser);
        //Integer count = this.userDao.getAllUserCount();
        result.put("msg","");
        result.put("data",allUser);
        result.put("code",0);
        result.put("count",userPageInfo.getTotal());
        return result;
    }

    @Override
    public Map<String, Object> addUser(UserPo userPo)
    {
        Map<String,Object> result = new HashMap<>();
        String password = userPo.getPassword();
		PasswordEncoder pe = new BCryptPasswordEncoder();
		userPo.setPassword(pe.encode(password));
        userPo.setCreateTime(new Date());
        Integer row = this.userDao.addUser(userPo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> updateUserByUserId(UserPo userPo)
    {
        Map<String,Object> result = new HashMap<>();
        userPo.setModifyTime(new Date());
        Integer row = this.userDao.updateUserByUserId(userPo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> deleteUserByUserIds(String[] userIds)
    {
        Map<String,Object> result = new HashMap<>();
        Integer row = this.userDao.deleteUserByUserIds(userIds);
        result.put("row",row);
        return result;
    }

    @Override
    public List<UserPo> getUserByPhone(String phone)
    {
        return this.userDao.getUserByPhone(phone);
    }

    @Override
    public UserPo getUserByOpenId(String openId)
    {
        return this.userDao.getUserByOpenId(openId);
    }

    @Override
    public Map<String, Object> bangdingQQ(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        UserPo userPo = this.userDao.getUserByUsername(username);
        if (passwordEncoder.matches(password,userPo.getPassword())){

        }else {
            result.put("state",3);
            result.put("msg","密码错误!!!");
            return result;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();
        if (userPo == null){
            result.put("state",2);
            result.put("msg","该账户不存在!!!");
            return result;
        } else {

            userPo.setOpenId(userBo.getOpenId());
            System.out.println(userBo.getOpenId());
            this.userDao.updateOpenIdByUserId(userPo.getUserId(),userBo.getOpenId());
            userBo.setUsername(username);
            QQToken qqToken = new QQToken(userBo, null);
            context.setAuthentication(qqToken);
            result.put("state",1);
            result.put("msg","绑定成功");
            result.put("data",userBo);
        }
        return result;
    }


}
