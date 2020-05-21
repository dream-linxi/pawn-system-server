package com.dreams.sys.service.impl;

import com.dreams.sys.bo.RoleBo;
import com.dreams.sys.bo.UserBo;
import com.dreams.sys.dao.RoleDao;
import com.dreams.sys.dao.UserDao;
import com.dreams.sys.po.RolePo;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dreams-linxi
 * @date 2020/5/11 9:09
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserDao userDao;

    @Override
    public Map<String, Object> getAllRole(RoleBo roleBo) {

        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(roleBo.getPage(),roleBo.getLimit());
        List<RolePo> rolePos = this.roleDao.getAllRole(roleBo);

        PageInfo<RolePo> userPageInfo = new PageInfo<RolePo>(rolePos);
        result.put("data",rolePos);
        result.put("code",0);
        result.put("msg","");
        result.put("count",userPageInfo.getTotal());

        return result;
    }

    @Override
    public Map<String, Object> addRole(RolePo rolePo) {
        Map<String, Object> result = new HashMap<>();
        rolePo.setCreateTime(new Date());
        Integer row = this.roleDao.addRole(rolePo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> deleteRoleByRoleId(String[] roleIds) {
        Map<String, Object> result = new HashMap<>();
        for (String roleId : roleIds) {
            this.roleDao.deleteUserOfRoleByRoleId(roleId);
        }
        Integer row = this.roleDao.deleteRoleByRoleId(roleIds);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> updateRoleByRoleId(RolePo rolePo) {
        Map<String, Object> result = new HashMap<>();
        rolePo.setModifyTime(new Date());
        Integer row = this.roleDao.updateRoleByRoleId(rolePo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> getRoleByRoleId(String roleId) {
        Map<String, Object> result = new HashMap<>();
        Integer row = this.roleDao.getRoleByRoleId(roleId);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> getAllAssigneeUser(String roleId) {
        Map<String, Object> result = new HashMap<>();
        UserBo userBo = new UserBo();
        List<UserPo> assignedUser = this.userDao.getUserByRoleId(roleId);
        List<UserPo> unAssignedUser = this.userDao.getUnAssignedUserByRoleId(roleId);
        result.put("assignedUser",assignedUser);
        result.put("unAssignedUser",unAssignedUser);
        return result;
    }

    @Override
    public Map<String, Object> addUserAndRole(String[] userIds) {
        Map<String, Object> result = new HashMap<>();
        int insertRow = 0;
        this.roleDao.deleteUserOfRoleByRoleId(userIds[0]);
        for (int i = 1; i < userIds.length; i++) {
            //Integer row = this.roleDao.getUserOfRole(userIds[0],userIds[i]);
                insertRow += this.roleDao.addUserOfRole(userIds[0],userIds[i]);

        }
        result.put("row",insertRow);
        return result;
    }


}
