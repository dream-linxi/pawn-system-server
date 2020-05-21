package com.dreams.base.service;

import com.dreams.base.bo.ShopBo;
import com.dreams.base.dao.ShopDao;
import com.dreams.base.po.ShopPo;
import com.dreams.base.po.WareHousePo;
import com.dreams.sys.po.UserPo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/19 10:06
 */
@Service
public class ShopService
{
    @Resource
    private ShopDao shopDao;

    /**
     * 根据条件获取所有的门店资料信息
     * @param shopBo 查询条件类
     * @return Map 集合,封装返回对应的数据信息
     *  - msg: 响应消息
     *  - data: 响应的数据
     *  - code: 响应码
     *  - count: 总记录条数
     */
    public Map<String, Object> getAllShopInfo(ShopBo shopBo)
    {
        Map<String,Object> result = new HashMap<>();
        // 设置分页
        PageHelper.startPage(shopBo.getPage(),shopBo.getLimit());
        List<ShopPo> shopPos = this.shopDao.getAllShopInfo(shopBo);
        PageInfo<ShopPo> shopPoPageInfo = new PageInfo<ShopPo>(shopPos);

        // 响应数据
        result.put("msg","");
        result.put("data",shopPos);
        result.put("code",0);
        result.put("count",shopPoPageInfo.getTotal());

        return result;
    }

    /**
     * 设置当前门店信息的状态
     * @param shopPo 门店信息实体类
     * @return Map 集合,封装返回对应的数据信息
     *      - rwo : 受影响行数
     */
    public Map<String, Object> updateShopStat(ShopPo shopPo)
    {
        Map<String, Object> result = new HashMap<>();

        int row = this.shopDao.updateShopStat(shopPo);

        result.put("row",row);
        return result;
    }

    /**
     * 获取所有仓库的信息
     * @return  Map 集合,封装返回对应的数据信息
     */
    public Map<String, Object> getAllWareHouse() {
        Map<String, Object> result = new HashMap<>();

        List<WareHousePo> wareHousePos = this.shopDao.getAllWareHouse();

        result.put("result",wareHousePos);
        return result;
    }

    /**
     * 根据门店编号获取仓库信息
     * @param shopCode 门店编号
     * @return Map 集合,封装返回对应的数据信息
     *  - row: 统计的行数
     */
    public Map<String, Object> getShopInfoByShopCode(String shopCode)
    {
        Map<String, Object> result = new HashMap<>();

        int row = this.shopDao.getShopInfoByShopCode(shopCode);

        result.put("row",row);
        return result;
    }

    /**
     * 添加门店信息
     * @param shopPo   门店信息实体类
     * @param wareHouseCode  门店关联的仓库编号字符串数组
     * @return Map 集合,封装返回对应的数据信息
     */
    public Map<String, Object> addShopInfo(ShopPo shopPo, String[] wareHouseCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.shopDao.addShopInfo(shopPo);

        if (row > 0)
        {
            for (int i = 0; i < wareHouseCode.length; i++) {
                this.shopDao.addShopAndWareHouse(shopPo.getShopCode(),wareHouseCode[i]);
            }
        }

        result.put("row",row);
        return result;
    }

    /**
     * 根据门店编号获取关联的仓库编号
     * @param shopCode 门店编号
     * @return Map 集合,封装返回对应的数据信息
     *  - whCodes[]: 仓库编号数组
     */
    public Map<String, Object> getWareHouseByShopCode(String shopCode) {
        Map<String, Object> result = new HashMap<>();
        List<String> whCodes = this.shopDao.getWareHouseByShopCode(shopCode);

        result.put("result",whCodes);
        return result;
    }

    /**
     * 更新门店信息
     * @param shopPo    门店实体类
     * @param wareHouseCode 仓库编号数组
     * @return Map 集合,封装返回对应的数据信息
     *      - row : 受影响行数
     */
    public Map<String, Object> wareHouseCode(ShopPo shopPo, String[] wareHouseCode) {
        Map<String, Object> result = new HashMap<>();

        // 更新门店基本信息
        Integer row = this.shopDao.updateShopInfo(shopPo);

        // 删除中建表数据
        this.shopDao.deleteShopAndWareHouse(shopPo.getShopCode());

        // 更新中间表数据
        if (row > 0)
        {
            for (int i = 0; i < wareHouseCode.length; i++) {
                this.shopDao.addShopAndWareHouse(shopPo.getShopCode(),wareHouseCode[i]);
            }
        }

        result.put("row",row);
        return result;
    }

    /**
     * 根据门店编号删除对应数据
     * @param shopCodes 门店编号数组
     * @return Map 集合,封装返回对应的数据信息
     */
    public Map<String, Object> deleteShopInfoByShopCodes(String[] shopCodes) {
        Map<String, Object> result = new HashMap<>();

        this.shopDao.deleteShopAndWareHouseByShopCodes(shopCodes);

        int row = this.shopDao.deleteShopInfoByShopCodes(shopCodes);

        result.put("row",row);
        return result;
    }
}
