package com.dreams.goods.service;

import com.dreams.base.bo.ShopBo;
import com.dreams.base.dao.ShopDao;
import com.dreams.base.po.ShopPo;
import com.dreams.goods.bo.GoodsBo;
import com.dreams.goods.dao.GoodsDao;
import com.dreams.goods.po.GoodAndAttrPo;
import com.dreams.goods.po.GoodsAppraisePo;
import com.dreams.goods.po.GoodsIdentifyPo;
import com.dreams.goods.po.GoodsPo;
import com.dreams.product.bo.ProductCatBo;
import com.dreams.product.dao.ProductCatDao;
import com.dreams.product.po.ProductCatPo;
import com.dreams.sys.bo.UserBo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/25 16:44
 */
@Service
@Transactional
public class GoodsService
{
    @Resource
    private GoodsDao goodsDao;


    public Map<String, Object> getAllGoodsInfo(GoodsBo goodsBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(goodsBo.getPage(),goodsBo.getLimit());
        List<GoodsPo> goodsPos = this.goodsDao.getAllGoodsInfo(goodsBo);
        PageInfo<GoodsPo> goodsPoPageInfo = new PageInfo<>(goodsPos);

        // 响应数据
        result.put("msg","");
        result.put("data",goodsPos);
        result.put("code",0);
        result.put("count",goodsPoPageInfo.getTotal());

        return result;
    }

    public Map<String, Object> addGoodsInfo(GoodsPo goodsPo)
    {
        Map<String,Object> result = new HashMap<>();
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();

        goodsPo.setCreateBy(userBo.getUserId());
        goodsPo.setCreateTime(new Date());
        goodsPo.setGoodsStat("待提交");
        Integer row =  this.goodsDao.addGoodsInfo(goodsPo);

        String attrConfs = goodsPo.getAttrConfs();
        String[] split = attrConfs.split("&");


        for (int i = 0; i < split.length; i++) {
            GoodAndAttrPo goodAndAttrPo = new GoodAndAttrPo(split[i].split("=")[0],goodsPo.getGoodsId(),split[i].split("=")[1]);
            this.goodsDao.addGoodsAndAttrInfo(goodAndAttrPo);
        }

        result.put("result",row);

        return result;
    }

    public Map<String, Object> commitGoodsInfo(String[] goodsIds)
    {
        Map<String,Object> result = new HashMap<>();

        int row = 0;

        for (int i = 0; i < goodsIds.length; i++) {
            row += this.goodsDao.commitGoodsInfo(goodsIds[i]);
        }

        result.put("row", row);
        return result;
    }

    public Map<String, Object> getAttrConfByGoodsId(String goodsId) {
        Map<String,Object> result = new HashMap<>();

        List<GoodAndAttrPo> goodAndAttrPos = this.goodsDao.getAttrConfByGoodsId(goodsId);

        result.put("result", goodAndAttrPos);
        return result;
    }

    public Map<String, Object> surveyGoodsInfo(GoodsIdentifyPo goodsIdentify)
    {
        Map<String,Object> result = new HashMap<>();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();

        goodsIdentify.setCreateTime(new Date());
        goodsIdentify.setCreateBy(userBo.getUsername());

        Integer row = this.goodsDao.surveyGoodsInfo(goodsIdentify);

        this.goodsDao.updateGoodsStatByGoodsId(goodsIdentify.getGoodsId(),"待评估");

        result.put("row", row);
        return result;
    }

    public Map<String, Object> assessGoodsInfo(GoodsAppraisePo appraisePo) {
        Map<String,Object> result = new HashMap<>();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();

        appraisePo.setCreateTime(new Date());
        appraisePo.setCreateBy(userBo.getUsername());

        Integer row = this.goodsDao.assessGoodsInfo(appraisePo);

        this.goodsDao.updateGoodsStatByGoodsId(appraisePo.getGoodsId(),"已评估");

        result.put("row", row);
        return result;
    }

    public Map<String, Object> updateGoodsInfo(GoodsPo goodsPo)
    {
        Map<String,Object> result = new HashMap<>();

        Integer row = this.goodsDao.updateGoodsInfo(goodsPo);

        result.put("row", row);
        return result;
    }


    public Map<String, Object> getAllGoodsSurveyInfo(String goodsId, Integer page, Integer limit) {

            Map<String, Object> result = new HashMap<>();

            PageHelper.startPage(page,limit);
            List<GoodsIdentifyPo> goodsIdentifyPos = this.goodsDao.getAllGoodsSurveyInfo(goodsId);
            PageInfo<GoodsIdentifyPo> goodsIdentifyPoPageInfo = new PageInfo<GoodsIdentifyPo>(goodsIdentifyPos);

            // 响应数据
            result.put("msg","");
            result.put("data",goodsIdentifyPos);
            result.put("code",0);
            result.put("count",goodsIdentifyPoPageInfo.getTotal());

            return result;

    }

    public Map<String, Object> getAllGoodsAssessInfo(String goodsId, Integer page, Integer limit) {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(page,limit);
        List<GoodsAppraisePo> goodsIdentifyPos = this.goodsDao.getAllGoodsAssessInfo(goodsId);
        PageInfo<GoodsAppraisePo> goodsAppraisePoPageInfo = new PageInfo<GoodsAppraisePo>(goodsIdentifyPos);

        // 响应数据
        result.put("msg","");
        result.put("data",goodsIdentifyPos);
        result.put("code",0);
        result.put("count",goodsAppraisePoPageInfo.getTotal());

        return result;
    }

    public Map<String, Object> deleteGoodsInfoByGoodsIds(Integer[] goodsIds)
    {
        Map<String, Object> result = new HashMap<>();

        this.goodsDao.deleteAttrConfAndGoodInfo(goodsIds);
        this.goodsDao.delelteGoodsSurveyInfo(goodsIds);
        this.goodsDao.delelteGoodsAssessInfo(goodsIds);
        Integer row = this.goodsDao.deleteGoodsInfoByGoodsIds(goodsIds);

        result.put("row",row);
        return result;
    }
}
