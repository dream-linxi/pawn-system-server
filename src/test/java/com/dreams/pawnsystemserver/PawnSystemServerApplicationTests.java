package com.dreams.pawnsystemserver;

import com.dreams.sys.po.MenuPo;
import com.dreams.sys.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PawnSystemServerApplicationTests {

    @Resource
    private MenuService menuService;

    @Test
    public void contextLoads() {
        Map<String, Object> allMenu = this.menuService.getAllMenu();
        List<MenuPo> rootMenu = (List<MenuPo>) allMenu.get("data");
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

        for (MenuPo menuPo : menuList) {
            System.out.println(menuPo);
        }
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
