package com.dreams.goods.controller;

import com.dreams.goods.bo.GoodsBo;
import com.dreams.goods.po.GoodsAppraisePo;
import com.dreams.goods.po.GoodsIdentifyPo;
import com.dreams.goods.po.GoodsPo;
import com.dreams.goods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/25 16:37
 */
@RestController
@RequestMapping("/goods/goods")
public class GoodsController
{
    @Resource
    private GoodsService goodsService;

    /**
     * 查询所有商品信息(带分页)
     * @param goodsBo 商品信息实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllGoodsInfo.json")
    public Map<String, Object> getAllGoodsInfo(GoodsBo goodsBo)
    {
        return this.goodsService.getAllGoodsInfo(goodsBo);
    }

    /**
     * 添加商品信息
     * @param goodsPo 商品信息实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/addGoodsInfo.json")
    public Map<String, Object> addGoodsInfo(GoodsPo goodsPo)
    {
        return this.goodsService.addGoodsInfo(goodsPo);
    }

    /**
     * 提交商品信息
     * @param goodsIds 商品编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/commitGoodsInfo.json")
    public Map<String, Object> commitGoodsInfo(@RequestParam("goodsIds[]") String[] goodsIds)
    {
        return this.goodsService.commitGoodsInfo(goodsIds);
    }

    /**
     * 更新商品信息
     * @param goodsPo 商品信息实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/updateGoodsInfo.json")
    public Map<String, Object> updateGoodsInfo(GoodsPo goodsPo)
    {
        System.out.println(goodsPo);
        return this.goodsService.updateGoodsInfo(goodsPo);
    }


    /**
     * 商品鉴定
     * @param goodsIdentify 商品鉴定实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/surveyGoodsInfo.json")
    public Map<String, Object> surveyGoodsInfo(GoodsIdentifyPo goodsIdentify)
    {
        return this.goodsService.surveyGoodsInfo(goodsIdentify);
    }

    /**
     * 商品评估
     * @param appraisePo 商品评估实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/assessGoodsInfo.json")
    public Map<String, Object> assessGoodsInfo(GoodsAppraisePo appraisePo)
    {
        return this.goodsService.assessGoodsInfo(appraisePo);
    }

    /**
     * 根据商品编号删除对应的商品信息
     * @param goodsId 商品编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/deleteGoodsInfo.json")
    public Map<String, Object> deleteGoodsInfo(@RequestParam("goodsId[]") String [] goodsId)
    {
        return null;
    }

    /**
     * 文件上传
     * @param file
     * @param defName
     * @return
     */
    @RequestMapping("/upFiles.json")
    public Map<String, Object> upFiles(MultipartFile file, @RequestParam("defName") String defName)
    {
        Map<String, Object> result = new HashMap<>();
        String path = (this.getClass().getResource("").toString()).replace("file:/","").replace("com/dreams/goods/controller/","");
        //.replace("/com/dreams/goods/controller","static/upfile/");
        path += "static/upfile/";
        String fileName = file.getOriginalFilename().split("\\.")[0]
                + "-" + new Date().getTime()
                + "." + file.getOriginalFilename().split("\\.")[1];
        fileName = defName +"-"+fileName;
        try {
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.put("fileName","static/upfile/" + fileName);
        result.put("defName",defName);
        result.put("code",0);
        return result;
    }

    @RequestMapping("/getAttrConfByGoodsId.json")
    public Map<String, Object> getAttrConfByGoodsId(@RequestParam("goodsId") String goodsId)
    {
        return this.goodsService.getAttrConfByGoodsId(goodsId);
    }


    @RequestMapping("/getAllGoodsSurveyInfo.json")
    public Map<String, Object> getAllGoodsSurveyInfo(@RequestParam("goodsId") String goodsId,@RequestParam("page") Integer page,@RequestParam("limit")  Integer limit)
    {
        return this.goodsService.getAllGoodsSurveyInfo(goodsId,page,limit);
    }

    @RequestMapping("/getAllGoodsAssessInfo.json")
    public Map<String, Object> getAllGoodsAssessInfo(@RequestParam("goodsId") String goodsId,@RequestParam("page") Integer page,@RequestParam("limit")  Integer limit)
    {
        return this.goodsService.getAllGoodsAssessInfo(goodsId,page,limit);
    }

    @RequestMapping("/deleteGoodsInfoByGoodsIds.json")
    public Map<String, Object> deleteGoodsInfoByGoodsIds(@RequestParam("goodsIds[]") Integer[] goodsIds)
    {
        return this.goodsService.deleteGoodsInfoByGoodsIds(goodsIds);
    }
}
