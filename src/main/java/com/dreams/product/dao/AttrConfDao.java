package com.dreams.product.dao;

import com.dreams.product.bo.AttrConfBo;
import com.dreams.product.po.AttrConfPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/25 1:21
 */
@Repository
public interface AttrConfDao
{

    List<AttrConfPo> getAllAttrConfInfo(AttrConfBo attrConfBo);

    Integer checkAttrCode(@Value("attrCode") String attrCode);

    Integer addAttrConfInfo(AttrConfPo attrConfPo);

    Integer updateAttrConfInfo(AttrConfPo attrConfPo);

    Integer deleteAttrConfInfoByAttrCodes(String[] attrCodes);

    List<AttrConfPo> getAllAttrConfByGroupCode(@Value("groupCode") String groupCode);
}
