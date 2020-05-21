package com.dreams.sys.service;

import com.dreams.sys.bo.UserBo;
import com.dreams.sys.po.UserPo;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/9 12:28
 */
public interface UserService
{
    UserPo getUserByUserId(@Value("userId") String userId);

    Map<String,Object> getAllUser(UserBo userBo);

    Map<String,Object> addUser(UserPo userPo);

    Map<String,Object> updateUserByUserId(UserPo userPo);

    Map<String,Object> deleteUserByUserIds(String[] userIds);

    List<UserPo> getUserByPhone(String phone);

    UserPo getUserByOpenId(String openId);

    Map<String, Object> bangdingQQ(String username, String password);
}
