package com.dreams.product.dao;

import com.dreams.product.bo.AttrGroupBo;
import com.dreams.product.po.AttrGroupPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:25
 */
@Repository
public interface AttrGroupDao
{
    List<AttrGroupPo> getAllAttrGroupInfo(AttrGroupBo attrGroupBo);

    Integer checkGroupCode(@Value("groupCode") String groupCode);

    Integer addAttrGroupInfo(AttrGroupPo attrGroupPo);

    void addAttrGroupCatRel(@Value("groupCode") String groupCode,@Value("catCode") String catCode);

    List<String> getAllCatCodeByGroupCode(@Value("groupCode") String groupCode);

    Integer updateAttrGroupInfo(AttrGroupPo attrGroupPo);

    void deleteAttrGroupCatRelByGroupCode(@Value("groupCode") String groupCode);

    Integer deleteAttrGroupInfoByGroupCodes(String[] groupCodes);

    void deleteAttrGroupCatRelByGroupCodes(String[] groupCodes);

    void deleteAttrConfigInfoByGroupCodes(String[] groupCodes);
}
