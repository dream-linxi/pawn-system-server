package com.dreams.sys.service;

import com.dreams.sys.po.MenuPo;

import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/10 14:32
 */
public interface MenuService
{
    Map<String,Object> getAllMenu();

    Map<String,Object> getFmenu();

    Map<String, Object> addMenu(MenuPo menuPo);

    Map<String, Object> updMenu(MenuPo menuPo);

    Map<String, Object> delMenu(String menuId);

    Map<String, Object> getMenuList();

    Map<String, Object> addRoleOfMenu(String[] menuIds);

    Map<String, Object> getMenuListByCurrentUser();
}
