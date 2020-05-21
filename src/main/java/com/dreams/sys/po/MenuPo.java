package com.dreams.sys.po;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/10 14:20
 */
public class MenuPo
{
    private String menuId;
    private String menuName;
    private String pMenuId;
    private String url;
    private String defaultImg;
    private Boolean isUsed;

    private List<MenuPo> children;

    public MenuPo() {
    }

    public MenuPo(String menuId, String menuName, String pMenuId, String url, String defaultImg, Boolean isUsed) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.pMenuId = pMenuId;
        this.url = url;
        this.defaultImg = defaultImg;
        this.isUsed = isUsed;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getpMenuId() {
        return pMenuId;
    }

    public void setpMenuId(String pMenuId) {
        this.pMenuId = pMenuId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public List<MenuPo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuPo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuPo{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", pMenuId='" + pMenuId + '\'' +
                ", url='" + url + '\'' +
                ", defaultImg='" + defaultImg + '\'' +
                ", isUsed=" + isUsed +
                ", children=" + children +
                '}';
    }
}
