package com.dreams.sys.dao;

import com.dreams.sys.po.MenuPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/10 14:26
 */
@Repository
public interface MenuDao
{
    List<MenuPo> getAllMenu();

    List<MenuPo> getFmenu();

    Integer addMenu(MenuPo menuPo);

    Integer updMenu(MenuPo menuPo);

    Integer delMenu(@Value("menuId") String menuId);

    void deleteMenuByRoleId(@Value("roleId") String roleId);


    int addRoleOfMenu(@Value("roleId") String roleId,@Value("menuId") String menuId);

    List<MenuPo> getMenuListByCurrentUser(@Value("userId") String userId);
}
