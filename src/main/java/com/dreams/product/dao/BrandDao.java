package com.dreams.product.dao;

import com.dreams.product.bo.BrandBo;
import com.dreams.product.po.BrandPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/21 21:37
 */
@Repository
public interface BrandDao
{
    List<BrandPo> getAllBranInfo(BrandBo brandBo);

    Integer checkBrandCode(@Value("brandCode") String brandCode);

    Integer addBrandInfo(BrandPo brandPo);

    void addBrandAndCatInfo(@Value("brandCode") String brandCode, @Value("catCode") String catCode);

    List<String> getAllCatCodeByBrandCode(@Value("brandCode") String brandCode);

    Integer updateBrandInfo(BrandPo brandPo);

    void deleteBrandAndCatInfoByBrandCode(@Value("brandCode") String brandCode);

    Integer deleteBrandInfoByBrandCodes(String[] brandCodes);
}
