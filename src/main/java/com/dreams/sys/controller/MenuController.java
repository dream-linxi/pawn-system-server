package com.dreams.sys.controller;

import com.dreams.sys.po.MenuPo;
import com.dreams.sys.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/10 14:58
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController
{
    @Resource
    private MenuService menuService;

    /**
     * 获取所有菜单
     * @return
     */
    @RequestMapping("/getAllMenu.json")
    public Map<String ,Object> getAllMenu()
    {
        return this.menuService.getAllMenu();
    }

    /**
     * 获取父级菜单
     * @return
     */
    @RequestMapping("/getFmenu.json")
    public Map<String, Object> getFmenu() {
        return this.menuService.getFmenu();
    }

    /**
     * 新增菜单
     * @param menuPo
     * @return
     */
    @RequestMapping("/addMenu.json")
    public Map<String,Object> addMenu(MenuPo menuPo)
    {
        return this.menuService.addMenu(menuPo);
    }

    /**
     * 更新菜单
     * @param menuPo
     * @return
     */
    @RequestMapping("/updMenu.json")
    public Map<String,Object> updMenu(MenuPo menuPo)
    {
        return this.menuService.updMenu(menuPo);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @RequestMapping("/delMenu.json")
    public Map<String,Object> delMenu(String menuId)
    {
        return this.menuService.delMenu(menuId);
    }

    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping("/getMenuList.json")
    public Map<String, Object> getMenuList() {
        return this.menuService.getMenuList();
    }

    @RequestMapping("/addRoleOfMenu.json")
    public Map<String, Object> addRoleOfMenu(@RequestParam("menuIds[]")String[] menuIds) {
        return this.menuService.addRoleOfMenu(menuIds);
    }
    @RequestMapping("/getMenuListByCurrentUser.json")
    public Map<String, Object> getMenuListByCurrentUser() {
        return this.menuService.getMenuListByCurrentUser();
    }
}
