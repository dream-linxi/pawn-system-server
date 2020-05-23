package com.dreams.product.service;

import com.dreams.base.po.ShopPo;
import com.dreams.product.bo.ProductCatBo;
import com.dreams.product.dao.ProductCatDao;
import com.dreams.product.po.ProductCatPo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/21 9:22
 */
@Service
public class ProductCatService
{
    @Resource
    private ProductCatDao productCatDao;

    /**
     * 获取所有商品大类信息
     * @param productCatBo 商品分类条件实体类
     * @return Map 集合,封装了对应的数据
     *   - msg: 消息
     *   - data: 对应数据
     *   - code: 响应码
     *   - count: 总记录数
     */
    public Map<String, Object> getAllBigCategoryInfo(ProductCatBo productCatBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(productCatBo.getPage(), productCatBo.getLimit());
        List<ProductCatPo> productCatPos = this.productCatDao.getAllBigCategoryInfo(productCatBo);
        PageInfo<ProductCatPo> productCatPoPageInfo = new PageInfo<ProductCatPo>(productCatPos);

        // 响应数据
        result.put("msg","");
        result.put("data",productCatPos);
        result.put("code",0);
        result.put("count",productCatPoPageInfo.getTotal());

        return result;
    }

    /**
     * 添加商品大类信息
     * @param productCatPo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> addProductCat(ProductCatPo productCatPo)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.productCatDao.addProductCat(productCatPo);

        result.put("row",row);
        return result;
    }

    /**
     * 查看当前编号的分类信息是否存储
     * @param catCode 分类编号
     * @return Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> checkCatCode(String catCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.productCatDao.checkCatCode(catCode);

        result.put("row",row);
        return result;
    }

    /**
     * 更新大类信息
     * @param productCatPo 商品分类实体类
     * @return  Map 集合,封装了对应的数据
     */
    public Map<String, Object> updateBigCateGoryProductCat(ProductCatPo productCatPo)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.productCatDao.updateBigCateGoryProductCat(productCatPo);

        result.put("row",row);
        return result;
    }

    /**
     * 获取所有一级分类,不分页
     * @return Map 集合,封装了对应的数据
     */
    public Map<String, Object> getAllBigCategoryInfoByNoPage() {
        Map<String, Object> result = new HashMap<>();

        ProductCatBo productCatBo = new ProductCatBo();
        List<ProductCatPo> productCatPos = this.productCatDao.getAllBigCategoryInfo(productCatBo);

        // 响应数据
        result.put("result",productCatPos);

        return result;
    }

    /**
     * 获取所有二级分类信息
     * @param productCatBo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     *   - msg: 消息
     *   - data: 对应数据
     *   - code: 响应码
     *   - count: 总记录数
     */
    public Map<String, Object> getAllSmallCategoryInfo(ProductCatBo productCatBo)
    {
        Map<String, Object> result = new HashMap<String, Object>();

        PageHelper.startPage(productCatBo.getPage(), productCatBo.getLimit());
        List<ProductCatPo> productCatPos = this.productCatDao.getAllSmallCategoryInfo(productCatBo);
        PageInfo<ProductCatPo> productCatPoPageInfo = new PageInfo<ProductCatPo>(productCatPos);

        // 响应数据
        result.put("msg","");
        result.put("data",productCatPos);
        result.put("code",0);
        result.put("count",productCatPoPageInfo.getTotal());

        return result;
    }

    /**
     * 更新二级分类信息
     * @param productCatPo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> updateSmallCategoryInfo(ProductCatPo productCatPo)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.productCatDao.updateSmallCategoryInfo(productCatPo);

        result.put("row",row);
        return result;
    }

    /**
     * 根据分类编号删除二级分类及其子类信息
     * @param catCodes 分类编号数组
     * @return Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> deleteSmallCategoryInfo(String[] catCodes)
    {

        Map<String, Object> result = new HashMap<>();

        this.productCatDao.deleteSmallCategoryInfoByPCatCodes(catCodes);

        Integer row = this.productCatDao.deleteSmallCategoryInfoByCatCodes(catCodes);


        result.put("row",row);
        return result;
    }


    /**
     * 获取所有三级分类信息
     * @param productCatBo 商品分类实体类
     * @return Map 集合,封装了对应的数据
     *   - msg: 消息
     *   - data: 对应数据
     *   - code: 响应码
     *   - count: 总记录数
     */
    public Map<String, Object> getAllSubCategoryInfo(ProductCatBo productCatBo)
    {
        Map<String, Object> result = new HashMap<String, Object>();

        PageHelper.startPage(productCatBo.getPage(), productCatBo.getLimit());
        List<ProductCatPo> productCatPos = this.productCatDao.getAllSubCategoryInfo(productCatBo);
        PageInfo<ProductCatPo> productCatPoPageInfo = new PageInfo<ProductCatPo>(productCatPos);

        // 响应数据
        result.put("msg","");
        result.put("data",productCatPos);
        result.put("code",0);
        result.put("count",productCatPoPageInfo.getTotal());

        return result;
    }

    /**
     * 获取所有二级分类,不分页
     * @return Map 集合,封装了对应的数据
     */
    public Map<String, Object> getAllSubCategoryInfoByNoPage()
    {
        Map<String, Object> result = new HashMap<>();

        ProductCatBo productCatBo = new ProductCatBo();
        List<ProductCatPo> productCatPos = this.productCatDao.getAllSmallCategoryInfo(productCatBo);

        // 响应数据
        result.put("result",productCatPos);

        return result;
    }

    /**
     * 根据分类编号删除三分类
     * @param catCodes 分类编号数组
     * @return Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> deleteSubCategoryInfo(String[] catCodes) {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.productCatDao.deleteSmallCategoryInfoByCatCodes(catCodes);

        result.put("row",row);
        return result;
    }
}
