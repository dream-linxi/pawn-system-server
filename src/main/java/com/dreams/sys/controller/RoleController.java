package com.dreams.sys.controller;

import com.dreams.sys.bo.RoleBo;
import com.dreams.sys.po.RolePo;
import com.dreams.sys.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/11 9:26
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController
{
    @Resource
    private RoleService roleService;

    @RequestMapping("/getAllRole.json")
    Map<String,Object> getAllRole(RoleBo roleBo){
        System.out.println(roleBo);
        return this.roleService.getAllRole(roleBo);
    }

    @RequestMapping("/addRole.json")
    Map<String,Object> addRole(RolePo rolePo){
        return this.roleService.addRole(rolePo);
    }

    @RequestMapping("/deleteRoleByRoleId.json")
    Map<String,Object> deleteRoleByRoleId(@RequestParam("roleIds[]")String[] roleIds){
        return this.roleService.deleteRoleByRoleId(roleIds);
    }

    @RequestMapping("/updateRoleByRoleId.json")
    Map<String,Object> updateRoleByRoleId(RolePo rolePo){
        return this.roleService.updateRoleByRoleId(rolePo);
    }

    @RequestMapping("/getRoleByRoleId.json")
    Map<String,Object> getRoleByRoleId(String roleId){
        return this.roleService.getRoleByRoleId(roleId);
    }

    @RequestMapping("/getAllAssigneeUser.json")
    Map<String,Object> getAllAssigneeUser(String roleId){
        return this.roleService.getAllAssigneeUser(roleId);
    }

    @RequestMapping("/addUserAndRole.json")
    Map<String,Object> addUserAndRole(@RequestParam("userIds[]") String[] userIds){
        return this.roleService.addUserAndRole(userIds);
    }
}
