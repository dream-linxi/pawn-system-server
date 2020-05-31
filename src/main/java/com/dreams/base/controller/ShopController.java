package com.dreams.base.controller;

import com.dreams.base.bo.ShopBo;
import com.dreams.base.po.ShopPo;
import com.dreams.base.service.ShopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/base/shop")
public class ShopController
{
    @Resource
    private ShopService shopService;

    /**
     * 根据条件获取所有的门店资料信息
     * @param shopBo 查询条件类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getAllShopInfo.json")
    public Map<String, Object> getAllShopInfo(ShopBo shopBo)
    {
        return this.shopService.getAllShopInfo(shopBo);
    }

    /**
     * 设置当前门店信息的状态
     * @param shopPo 门店信息实体类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/updateShopStat.json")
    public Map<String, Object> updateShopStat(ShopPo shopPo)
    {
        return this.shopService.updateShopStat(shopPo);
    }

    /**
     * 获取所有仓库的信息
     * @return  Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getAllWareHouse.json")
    public Map<String,Object> getAllWareHouse()
    {
        return this.shopService.getAllWareHouse();
    }

    /**
     * 根据门店编号获取仓库信息
     * @param shopCode 门店编号
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getShopInfoByShopCode.json")
    public Map<String, Object> getShopInfoByShopCode(@RequestParam("shopCode") String shopCode)
    {
        return this.shopService.getShopInfoByShopCode(shopCode);
    }

    /**
     * 添加门店信息
     * @param shopPo   门店信息实体类
     * @param wareHouseCode  门店关联的仓库编号字符串数组
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/addShopInfo.json")
    public Map<String, Object> addShopInfo(ShopPo shopPo, @RequestParam("wareHouseCode[]") String[] wareHouseCode)
    {
        return this.shopService.addShopInfo(shopPo,wareHouseCode);
    }

    /**
     * 根据门店编号获取关联的仓库编号
     * @param shopCode 门店编号
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getWareHouseByShopCode.json")
    public Map<String, Object> getWareHouseByShopCode(@RequestParam("shopCode") String shopCode)
    {
        return this.shopService.getWareHouseByShopCode(shopCode);
    }

    /**
     * 更新门店信息
     * @param shopPo    门店实体类
     * @param wareHouseCode 仓库编号数组
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/updateShopInfo.json")
    public Map<String, Object> updateShopInfo(ShopPo shopPo, @RequestParam("wareHouseCode[]") String[] wareHouseCode)
    {
        return this.shopService.wareHouseCode(shopPo,wareHouseCode);
    }

    /**
     * 根据门店编号删除对应数据
     * @param shopCodes 门店编号数组
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/deleteShopInfoByShopCodes.json")
    public Map<String, Object> deleteShopInfoByShopCodes(@RequestParam("shopCodes[]") String[] shopCodes)
    {
        return this.shopService.deleteShopInfoByShopCodes(shopCodes);
    }

    @RequestMapping("/getAllShopInfoByNoPage.json")
    public Map<String, Object> getAllShopInfoByNoPage()
    {
        return this.shopService.getAllShopInfoByNoPage();
    }
}
