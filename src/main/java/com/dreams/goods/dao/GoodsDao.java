package com.dreams.goods.dao;

import com.dreams.goods.bo.GoodsBo;
import com.dreams.goods.po.GoodAndAttrPo;
import com.dreams.goods.po.GoodsAppraisePo;
import com.dreams.goods.po.GoodsIdentifyPo;
import com.dreams.goods.po.GoodsPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/25 16:44
 */
@Repository
public interface GoodsDao
{
    List<GoodsPo> getAllGoodsInfo(GoodsBo goodsBo);

    Integer addGoodsInfo(GoodsPo goodsPo);

    void addGoodsAndAttrInfo(GoodAndAttrPo goodAndAttrPo);

    int commitGoodsInfo(@Param("goodsId") String goodsId);

    List<GoodAndAttrPo> getAttrConfByGoodsId(@Param("goodsId") String goodsId);

    Integer surveyGoodsInfo(GoodsIdentifyPo goodsIdentify);

    Integer assessGoodsInfo(GoodsAppraisePo appraisePo);

    Integer updateGoodsInfo(GoodsPo goodsPo);

    List<GoodsIdentifyPo> getAllGoodsSurveyInfo(@Param("goodsId") String goodsId);

    List<GoodsAppraisePo> getAllGoodsAssessInfo(@Param("goodsId") String goodsId);

    void deleteAttrConfAndGoodInfo(Integer[] goodsIds);

    void delelteGoodsSurveyInfo(Integer[] goodsIds);

    void delelteGoodsAssessInfo(Integer[] goodsIds);

    Integer deleteGoodsInfoByGoodsIds(Integer[] goodsIds);

    void updateGoodsStatByGoodsId(@Param("goodsId") Integer goodsId,@Param("goodsStat")  String goodsStat);
}
