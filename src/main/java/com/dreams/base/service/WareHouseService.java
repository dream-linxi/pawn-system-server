package com.dreams.base.service;

import com.dreams.base.bo.WareHouseBo;
import com.dreams.base.dao.WareHouseDao;
import com.dreams.base.po.ShopPo;
import com.dreams.base.po.WareHousePo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/20 14:56
 */
@Service
public class WareHouseService
{

    @Resource
    private WareHouseDao wareHouseDao;

    /**
     * 根据条件获取所有的仓库资料信息
     * @param wareHouseBo 查询条件类
     * @return Map 集合,封装返回对应的数据信息
     *  - msg: 响应消息
     *  - data: 响应的数据
     *  - code: 响应码
     *  - count: 总记录条数
     */
    public Map<String, Object> getAllWareHouseInfo(WareHouseBo wareHouseBo)
    {
        Map<String,Object> result = new HashMap<>();
        // 设置分页
        PageHelper.startPage(wareHouseBo.getPage(),wareHouseBo.getLimit());
        List<WareHousePo> wareHousePos = this.wareHouseDao.getAllWareHouseInfo(wareHouseBo);
        PageInfo<WareHousePo> shopPoPageInfo = new PageInfo<WareHousePo>(wareHousePos);

        // 响应数据
        result.put("msg","");
        result.put("data",wareHousePos);
        result.put("code",0);
        result.put("count",shopPoPageInfo.getTotal());

        return result;
    }

    /**
     * 设置当前仓库信息的状态
     * @param wareHousePo 仓库信息实体类
     * @return Map 集合,封装返回对应的数据信息
     *      - rwo : 受影响行数
     */
    public Map<String, Object> updateWareHouseStat(WareHousePo wareHousePo) {
        Map<String, Object> result = new HashMap<>();

        int row = this.wareHouseDao.updateWareHouseStat(wareHousePo);

        result.put("row",row);
        return result;
    }

    /**
     * 根据仓库编号获取仓库信息
     * @param whCode 仓库编号
     * @return Map 集合,封装返回对应的数据信息
     *  - row: 统计的行数
     */
    public Map<String, Object> getWareHouseInfoByWhCode(String whCode) {
        Map<String, Object> result = new HashMap<>();

        int row = this.wareHouseDao.getShopInfoByShopCode(whCode);

        result.put("row",row);
        return result;
    }

    /**
     * 添加仓库信息
     * @param wareHousePo   仓库信息实体类
     * @return Map 集合,封装返回对应的数据信息
     */
    public Map<String, Object> addWareHouseInfo(WareHousePo wareHousePo) {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.wareHouseDao.addWareHouseInfo(wareHousePo);

        result.put("row",row);
        return result;
    }

    /**
     * 更新仓库信息
     * @param wareHousePo    仓库实体类
     * @return Map 集合,封装返回对应的数据信息
     *      - row : 受影响行数
     */
    public Map<String, Object> updateWareHouseInfo(WareHousePo wareHousePo)
    {
        Map<String, Object> result = new HashMap<>();

        // 更新门店基本信息
        Integer row = this.wareHouseDao.updateWareHouseInfo(wareHousePo);

        result.put("row",row);
        return result;
    }

    /**
     * 根据仓库编号删除对应数据
     * @param whCodes 仓库编号数组
     * @return Map 集合,封装返回对应的数据信息
     *      - row : 受影响行数
     */
    public Map<String, Object> deleteWareHouseByWhCodes(String[] whCodes) {
        Map<String, Object> result = new HashMap<>();

        this.wareHouseDao.deleteShopAndWareHouseByWhCodes(whCodes);

        int row = this.wareHouseDao.deleteWareHouseByWhCodes(whCodes);

        result.put("row",row);
        return result;
    }


}
