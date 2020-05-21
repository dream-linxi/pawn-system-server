package com.dreams.base.dao;

import com.dreams.base.bo.ShopBo;
import com.dreams.base.po.ShopPo;
import com.dreams.base.po.WareHousePo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/19 10:06
 */
@Repository
public interface ShopDao
{
    List<ShopPo> getAllShopInfo(ShopBo shopBo);

    int updateShopStat(ShopPo shopPo);

    List<WareHousePo> getAllWareHouse();

    int getShopInfoByShopCode(@Value("shopCode") String shopCode);

    Integer addShopInfo(ShopPo shopPo);

    void addShopAndWareHouse(@Value("shopCode") String shopCode,@Value("whCode") String whCode);

    List<String> getWareHouseByShopCode(@Value("shopCode") String shopCode);

    Integer updateShopInfo(ShopPo shopPo);

    void deleteShopAndWareHouse(@Value("shopCode") String shopCode);

    void deleteShopAndWareHouseByShopCodes(String[] shopCodes);

    int deleteShopInfoByShopCodes(String[] shopCodes);
}
