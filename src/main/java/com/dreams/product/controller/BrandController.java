package com.dreams.product.controller;

import com.dreams.product.bo.BrandBo;
import com.dreams.product.po.BrandPo;
import com.dreams.product.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/21 21:38
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController
{
    @Resource
    private BrandService brandService;

    /**
     * 获取所有品牌信息
     * @param brandBo 品牌实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllBranInfo.json")
    public Map<String, Object> getAllBranInfo(BrandBo brandBo)
    {
        return this.brandService.getAllBranInfo(brandBo);
    }

    /**
     * 检查数据库是否存在相同编号的品牌
     * @param brandCode 品牌编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/checkBrandCode.json")
    public Map<String, Object> checkBrandCode(@RequestParam("brandCode") String brandCode)
    {
        return this.brandService.checkBrandCode(brandCode);
    }

    /**
     * 新增品牌信息
     * @param brandPo 品牌实体类
     * @param bigCategoryCodes 一级分类编号数组
     * @return  Map 集合,封装了对应的数据
     */
    @RequestMapping("/addBrandInfo.json")
    public Map<String, Object> addBrandInfo(BrandPo brandPo,@RequestParam(value="bigCategoryCodes[]", required = false) String [] bigCategoryCodes)
    {

        return this.brandService.addBrandInfo(brandPo, bigCategoryCodes);
    }

    /**
     * 根据品牌编号获取关联的一级分类编号
     * @param brandCode 品牌编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllCatCodeByBrandCode.json")
    public Map<String, Object> getAllCatCodeByBrandCode(@RequestParam(value="brandCode") String brandCode)
    {

        return this.brandService.getAllCatCodeByBrandCode(brandCode);
    }

    /**
     * 更新品牌信息
     * @param brandPo 品牌实体类
     * @param bigCategoryCodes 一级分类编号数组
     * @return  Map 集合,封装了对应的数据
     */
    @RequestMapping("/updateBrandInfo.json")
    public Map<String, Object> updateBrandInfo(BrandPo brandPo, @RequestParam(value="bigCategoryCodes[]", required = false) String [] bigCategoryCodes)
    {

        return this.brandService.updateBrandInfo(brandPo, bigCategoryCodes);
    }

    /**
     * 根据品牌编号删除对应数据
     * @param brandCodes 品牌编号数组
     * @return  Map 集合,封装了对应的数据
     */
    @RequestMapping("/deleteBrandInfoByBrandCodes.json")
    public Map<String, Object> deleteBrandInfoByBrandCodes(@RequestParam(value="brandCodes[]") String[] brandCodes)
    {

        return this.brandService.deleteBrandInfoByBrandCodes(brandCodes);
    }

}
