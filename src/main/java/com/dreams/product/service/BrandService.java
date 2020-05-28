package com.dreams.product.service;

import com.dreams.product.bo.BrandBo;
import com.dreams.product.dao.BrandDao;
import com.dreams.product.po.BrandPo;
import com.dreams.product.po.ProductCatPo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/21 21:38
 */
@Service
public class BrandService
{
    @Resource
    private BrandDao brandDao;

    /**
     * 获取所有品牌信息
     * @param brandBo 品牌实体类
     * @return Map 集合,封装了对应的数据
     *  - msg: 消息
     *  - data: 对应数据
     *  - code: 响应码
     *  - count: 总记录数
     */
    public Map<String, Object> getAllBranInfo(BrandBo brandBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(brandBo.getPage(), brandBo.getLimit());
        List<BrandPo> brandPos = this.brandDao.getAllBranInfo(brandBo);
        PageInfo<BrandPo> brandPoPageInfo = new PageInfo<BrandPo>(brandPos);

        // 响应数据
        result.put("msg","");
        result.put("data",brandPos);
        result.put("code",0);
        result.put("count",brandPoPageInfo.getTotal());

        return result;
    }

    /**
     * 检查数据库是否存在相同编号的品牌
     * @param brandCode 品牌编号
     * @return Map 集合,
     *  - row: 统计的行数
     */
    public Map<String, Object> checkBrandCode(String brandCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.brandDao.checkBrandCode(brandCode);

        result.put("row",row);
        return result;
    }

    /**
     * 新增品牌信息
     * @param brandPo 品牌实体类
     * @param bigCategoryCodes 一级分类编号数组
     * @return  Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> addBrandInfo(BrandPo brandPo, String[] bigCategoryCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.brandDao.addBrandInfo(brandPo);

        if (row > 0)
        {
            if (bigCategoryCodes != null)
            {
                for (int i = 0; i < bigCategoryCodes.length; i++) {
                    this.brandDao.addBrandAndCatInfo(brandPo.getBrandCode(),bigCategoryCodes[i]);
                }
            }

        }

        result.put("row",row);
        return result;
    }

    /**
     * 根据品牌编号获取关联的一级分类编号
     * @param brandCode 品牌编号
     * @return Map 集合,封装了对应的数据
     *  - result: 一级分类编号集合
     */
    public Map<String, Object> getAllCatCodeByBrandCode(String brandCode)
    {
        Map<String, Object> result = new HashMap<>();

        List<String> catCodes = this.brandDao.getAllCatCodeByBrandCode(brandCode);


        result.put("result",catCodes);
        return result;
    }

    /**
     * 更新品牌信息
     * @param brandPo 品牌实体类
     * @param bigCategoryCodes 一级分类编号数组
     * @return  Map 集合,封装了对应的数据
     *  - row: 受影响行数
     */
    public Map<String, Object> updateBrandInfo(BrandPo brandPo, String[] bigCategoryCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.brandDao.updateBrandInfo(brandPo);

        this.brandDao.deleteBrandAndCatInfoByBrandCode(brandPo.getBrandCode());

        if (row > 0)
        {
            if (bigCategoryCodes != null)
            {
                for (int i = 0; i < bigCategoryCodes.length; i++) {
                    this.brandDao.addBrandAndCatInfo(brandPo.getBrandCode(),bigCategoryCodes[i]);
                }
            }

        }

        result.put("row",row);
        return result;
    }

    /**
     * 根据品牌编号删除对应数据
     * @param brandCodes 品牌编号数组
     * @return  Map 集合,封装了对应的数据
     *  - row 受影响行数
     */
    public Map<String, Object> deleteBrandInfoByBrandCodes(String[] brandCodes)
    {
        Map<String, Object> result = new HashMap<>();

        for (int i = 0; i < brandCodes.length; i++) {
            this.brandDao.deleteBrandAndCatInfoByBrandCode(brandCodes[i]);
        }

        Integer row = this.brandDao.deleteBrandInfoByBrandCodes(brandCodes);

        result.put("row",row);
        return result;
    }

    public Map<String, Object> getAllBrandByCatCode(String catCode)
    {
        Map<String, Object> result = new HashMap<>();

        List<BrandPo> brandPos = this.brandDao.getAllBrandByCatCode(catCode);

        result.put("result",brandPos);
        return result;
    }
}
