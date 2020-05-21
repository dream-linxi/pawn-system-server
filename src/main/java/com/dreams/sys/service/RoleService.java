package com.dreams.sys.service;

import com.dreams.sys.bo.RoleBo;
import com.dreams.sys.po.RolePo;

import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/11 9:08
 */
public interface RoleService {
    Map<String,Object> getAllRole(RoleBo roleBo);

    Map<String,Object> addRole(RolePo rolePo);

    Map<String,Object> deleteRoleByRoleId(String[] roleIds);

    Map<String,Object> updateRoleByRoleId(RolePo rolePo);

    Map<String,Object> getRoleByRoleId(String roleId);

    Map<String, Object> getAllAssigneeUser(String roleId);

    Map<String, Object> addUserAndRole(String[] userIds);
}
