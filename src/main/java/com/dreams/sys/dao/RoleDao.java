package com.dreams.sys.dao;

import com.dreams.sys.bo.RoleBo;
import com.dreams.sys.po.RolePo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/11 8:28
 */
@Repository
public interface RoleDao
{
    List<RolePo> getAllRole(RoleBo roleBo);

    Integer addRole(RolePo rolePo);

    Integer deleteRoleByRoleId(String[] roleIds);

    Integer updateRoleByRoleId(RolePo rolePo);

    Integer getRoleByRoleId(@Value("roleId") String roleId);

    Integer getUserOfRole(@Value("roleId") String roleId, @Value("userId") String userId);

    int addUserOfRole(@Value("roleId") String roleId, @Value("userId") String userId);

    void deleteUserOfRoleByRoleId(@Value("roleId") String roleId);
}
