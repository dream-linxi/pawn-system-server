package com.dreams.product.dao;

import com.dreams.product.bo.ProductCatBo;
import com.dreams.product.po.ProductCatPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/21 9:22
 */
@Repository
public interface ProductCatDao
{

    List<ProductCatPo> getAllBigCategoryInfo(ProductCatBo productCatBo);

    Integer addProductCat(ProductCatPo productCatPo);

    Integer checkCatCode(@Value("catCode") String catCode);

    Integer updateBigCateGoryProductCat(ProductCatPo productCatPo);

    List<ProductCatPo> getAllSmallCategoryInfo(ProductCatBo productCatBo);

    Integer updateSmallCategoryInfo(ProductCatPo productCatPo);

    void deleteSmallCategoryInfoByPCatCodes(String[] pCatCodes);

    Integer deleteSmallCategoryInfoByCatCodes(String[] catCodes);

    List<ProductCatPo> getAllSubCategoryInfo(ProductCatBo productCatBo);

    void deleteAttrGroupCatRelByCatCodes(String[] catCodes);

    void deleteBrandCatRelByCatCodes(String[] catCodes);

    Integer deleteBigCategoryByCatCodes(String[] catCodes);

    List<String> getSmallCategoryInfoByCatCode(@Value("catCode") String catCode);
}
