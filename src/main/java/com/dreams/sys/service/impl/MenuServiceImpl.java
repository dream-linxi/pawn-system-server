package com.dreams.sys.service.impl;

import com.dreams.sys.bo.UserBo;
import com.dreams.sys.dao.MenuDao;
import com.dreams.sys.po.MenuPo;
import com.dreams.sys.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/10 14:32
 */
@Service
public class MenuServiceImpl implements MenuService
{
    @Resource
    private MenuDao menuDao;

    @Override
    public Map<String,Object> getAllMenu()
    {
        Map<String,Object> result = new HashMap<>();
        List<MenuPo> allMenu = this.menuDao.getAllMenu();
        result.put("code",0);
        result.put("data",allMenu);
        return result;
    }

    @Override
    public Map<String, Object> getFmenu() {
        Map<String,Object> result = new HashMap<>();
        List<MenuPo> allMenu = this.menuDao.getFmenu();
        result.put("result",allMenu);
        return result;
    }

    @Override
    public Map<String, Object> addMenu(MenuPo menuPo) {
        Map<String,Object> result = new HashMap<>();
        if (!menuPo.getpMenuId().equals("-1")){
            String pid = menuPo.getpMenuId();
            menuPo.setMenuId(pid +"."+ menuPo.getMenuId());
        }
        Integer row = this.menuDao.addMenu(menuPo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> updMenu(MenuPo menuPo) {
        Map<String,Object> result = new HashMap<>();
        Integer row = this.menuDao.updMenu(menuPo);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> delMenu(String menuId) {
        Map<String,Object> result = new HashMap<>();
        Integer row = this.menuDao.delMenu(menuId);
        result.put("row",row);
        return result;
    }

    @Override
    public Map<String, Object> getMenuList(){
        Map<String,Object> result = new HashMap<>();
        List<MenuPo> rootMenu = this.menuDao.getAllMenu();
        List<MenuPo> menuList = new ArrayList<MenuPo>();

        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单没有 pMenuId
            if (rootMenu.get(i).getpMenuId().equals("-1")) {
                menuList.add(rootMenu.get(i));
            }
        }

        for (MenuPo menuPo : menuList) {
            menuPo.setChildren(getChild(menuPo.getMenuId(), rootMenu));
        }
        result.put("result",menuList);
        return result;
    }

    @Override
    public Map<String, Object> addRoleOfMenu(String[] menuIds) {
        Map<String,Object> result = new HashMap<>();

        //删除所有角色关联的菜单
        this.menuDao.deleteMenuByRoleId(menuIds[0]);
        int resultRow = 0;
        for (int i = 1; i < menuIds.length; i++) {
            resultRow += this.menuDao.addRoleOfMenu(menuIds[0],menuIds[i]);
        }

        result.put("row",resultRow);
        return result;
    }

    @Override
    public Map<String, Object> getMenuListByCurrentUser() {
        Map<String,Object> result = new HashMap<>();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();

        List<MenuPo> rootMenu = this.menuDao.getMenuListByCurrentUser(userBo.getUserId());
        List<MenuPo> menuList = new ArrayList<MenuPo>();

        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单没有 pMenuId
            if (rootMenu.get(i).getpMenuId().equals("-1")) {
                menuList.add(rootMenu.get(i));
            }
        }

        for (MenuPo menuPo : menuList) {
            menuPo.setChildren(getChild(menuPo.getMenuId(), rootMenu));
        }
        result.put("result",menuList);
        return result;
    }


    private List<MenuPo> getChild(String id, List<MenuPo> rootMenu) {
        List<MenuPo> childList = new ArrayList<>();
        for (MenuPo menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getpMenuId().equals(id)) {
                childList.add(menu);
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (MenuPo menu : childList) {// 没有url子菜单还有子菜单
            // 递归
            menu.setChildren(getChild(menu.getMenuId(), rootMenu));

        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
