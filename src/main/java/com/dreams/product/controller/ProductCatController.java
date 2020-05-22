package com.dreams.product.controller;

import com.dreams.product.bo.ProductCatBo;
import com.dreams.product.po.ProductCatPo;
import com.dreams.product.service.ProductCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/21 9:20
 */
@RestController
@RequestMapping("/product/productcat")
public class ProductCatController
{
    @Resource
    private ProductCatService productCatService;

    /**
     * 获取所有商品大类信息
     * @param productCatBo 商品分类条件实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllBigCategoryInfo.json")
    public Map<String,Object> getAllBigCategoryInfo(ProductCatBo productCatBo)
    {
        return this.productCatService.getAllBigCategoryInfo(productCatBo);
    }

    /**
     * 获取所有一级分类,不分页
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllBigCategoryInfoByNoPage.json")
    public Map<String, Object> getAllBigCategoryInfoByNoPage()
    {
        return this.productCatService.getAllBigCategoryInfoByNoPage();
    }

    /**
     * 查看当前编号的分类信息是否存储
     * @param catCode 分类编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/checkCatCode.json")
    public Map<String,Object> checkCatCode(@RequestParam("catCode") String catCode)
    {
        return this.productCatService.checkCatCode(catCode);
    }

    /**
     * 添加商品分类信息
     * @param productCatPo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/addProductCat.json")
    public Map<String,Object> addProductCat(ProductCatPo productCatPo)
    {
        return this.productCatService.addProductCat(productCatPo);
    }

    /**
     * 更新大类信息
     * @param productCatPo 商品分类实体类
     * @return  Map 集合,封装了对应的数据
     */
    @RequestMapping("/updateBigCateGoryProductCat.json")
    public Map<String,Object> updateBigCateGoryProductCat(ProductCatPo productCatPo)
    {

        return this.productCatService.updateBigCateGoryProductCat(productCatPo);
    }

    /**
     * 获取所有二级分类信息
     * @param productCatBo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllSmallCategoryInfo.json")
    public Map<String,Object> getAllSmallCategoryInfo(ProductCatBo productCatBo)
    {
        return this.productCatService.getAllSmallCategoryInfo(productCatBo);
    }

    /**
     * 更新二级分类信息
     * @param productCatPo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/updateSmallCategoryInfo.json")
    public  Map<String,Object> updateSmallCategoryInfo(ProductCatPo productCatPo)
    {
        return this.productCatService.updateSmallCategoryInfo(productCatPo);
    }

    /**
     * 根据分类编号删除二级分类及其子类信息
     * @param catCodes 分类编号数组
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/deleteSmallCategoryInfo.json")
    public  Map<String,Object> deleteSmallCategoryInfo(@RequestParam("catCodes[]") String [] catCodes)
    {
        return this.productCatService.deleteSmallCategoryInfo(catCodes);
    }
}
