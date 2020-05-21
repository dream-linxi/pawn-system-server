package com.dreams.base.dao;

import com.dreams.base.bo.WareHouseBo;
import com.dreams.base.po.WareHousePo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/20 14:55
 */
@Repository
public interface WareHouseDao
{
    List<WareHousePo> getAllWareHouseInfo(WareHouseBo wareHouseBo);

    int updateWareHouseStat(WareHousePo wareHousePo);

    int getShopInfoByShopCode(@Value("whCode") String whCode);

    Integer addWareHouseInfo(WareHousePo wareHousePo);

    Integer updateWareHouseInfo(WareHousePo wareHousePo);

    void deleteShopAndWareHouseByWhCodes(String[] whCodes);

    int deleteWareHouseByWhCodes(String[] whCodes);
}
