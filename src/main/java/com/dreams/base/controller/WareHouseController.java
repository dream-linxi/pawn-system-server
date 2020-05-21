package com.dreams.base.controller;

import com.dreams.base.bo.WareHouseBo;
import com.dreams.base.po.ShopPo;
import com.dreams.base.po.WareHousePo;
import com.dreams.base.service.WareHouseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/20 14:56
 */
@RestController
@RequestMapping("/base/warehouse")
public class WareHouseController
{
    @Resource
    private WareHouseService wareHouseService;

    /**
     * 根据条件获取所有的仓库资料信息
     * @param wareHouseBo 查询条件类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getAllWareHouseInfo.json")
    public Map<String, Object> getAllWareHouseInfo(WareHouseBo wareHouseBo)
    {
        return this.wareHouseService.getAllWareHouseInfo(wareHouseBo);
    }

    /**
     * 设置当前仓库信息的状态
     * @param wareHousePo 仓库信息实体类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/updateWareHouseStat.json")
    public Map<String, Object> updateWareHouseStat(WareHousePo wareHousePo)
    {
        return this.wareHouseService.updateWareHouseStat(wareHousePo);
    }

    /**
     * 根据仓库编号获取仓库信息
     * @param whCode 仓库编号
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/getWareHouseInfoByWhCode.json")
    public Map<String, Object> getWareHouseInfoByWhCode(@RequestParam("whCode") String whCode)
    {
        return this.wareHouseService.getWareHouseInfoByWhCode(whCode);
    }

    /**
     * 添加仓库信息
     * @param wareHousePo   仓库信息实体类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/addWareHouseInfo.json")
    public Map<String, Object> addWareHouseInfo(WareHousePo wareHousePo)
    {
        return this.wareHouseService.addWareHouseInfo(wareHousePo);
    }


    /**
     * 更新仓库信息
     * @param wareHousePo    仓库实体类
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/updateWareHouseInfo.json")
    public Map<String, Object> updateWareHouseInfo(WareHousePo wareHousePo)
    {
        return this.wareHouseService.updateWareHouseInfo(wareHousePo);
    }


    /**
     * 根据仓库编号删除对应数据
     * @param whCodes 仓库编号数组
     * @return Map 集合,封装返回对应的数据信息
     */
    @RequestMapping("/deleteWareHouseByWhCodes.json")
    public Map<String, Object> deleteWareHouseByWhCodes(@RequestParam("whCodes[]") String[] whCodes)
    {
        return this.wareHouseService.deleteWareHouseByWhCodes(whCodes);
    }
}
