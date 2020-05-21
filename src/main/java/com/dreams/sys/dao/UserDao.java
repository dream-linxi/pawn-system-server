package com.dreams.sys.dao;

import com.dreams.sys.bo.UserBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dreams.sys.po.UserPo;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/9 12:31
 */
@Repository
public interface UserDao
{
    /**
     * 根据用户编号查找对应用户
     * @param userId
     * @return
     */
    UserPo getUserByUserId(@Value("userId") String userId);

    /**
     * 查询所有用户
     * @param userBo
     * @return
     */
    List<UserPo> getAllUser(UserBo userBo);

    /**
     * 新增用户
     * @param userPo
     * @return
     */
    Integer addUser(UserPo userPo);

    /**
     * 更加 Id 删除用户
     * @param userIds
     * @return
     */
    Integer deleteUserByUserIds(String[] userIds);

    /**
     * 更新用户
     * @param userPo
     * @return
     */
    Integer updateUserByUserId(UserPo userPo);

    Integer getAllUserCount();

    List<UserPo> getUserByPhone(@Value("phone") String phone);

    List<UserPo> getUserByRoleId(@Value("roleId") String roleId);

    List<UserPo> getUnAssignedUserByRoleId(@Value("roleId") String roleId);

    UserPo getUserByOpenId(@Value("openId") String openId);

    UserPo getUserByUsername(@Value("username") String username);

    void updateOpenIdByUserId(@Value("userId") String userId, @Value("openId") String openId);
}
