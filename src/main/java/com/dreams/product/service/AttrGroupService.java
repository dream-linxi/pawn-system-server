package com.dreams.product.service;

import com.dreams.product.bo.AttrGroupBo;
import com.dreams.product.dao.AttrGroupDao;
import com.dreams.product.po.AttrGroupPo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:26
 */
@Service
@Transactional
public class AttrGroupService
{
    @Resource
    private AttrGroupDao attrGroupDao;

    public Map<String, Object> getAllAttrGroupInfo(AttrGroupBo attrGroupBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(attrGroupBo.getPage(),attrGroupBo.getLimit());
        List<AttrGroupPo> attrGroupPos = this.attrGroupDao.getAllAttrGroupInfo(attrGroupBo);
        PageInfo<AttrGroupPo> attrGroupPoPageInfo = new PageInfo<>(attrGroupPos);

        // 响应数据
        result.put("msg","");
        result.put("data",attrGroupPos);
        result.put("code",0);
        result.put("count",attrGroupPoPageInfo.getTotal());

        return result;
    }

    public Map<String, Object> checkGroupCode(String groupCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrGroupDao.checkGroupCode(groupCode);

        result.put("row",row);
        return result;
    }

    public Map<String, Object> addAttrGroupInfo(AttrGroupPo attrGroupPo, String[] bigCategoryCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrGroupDao.addAttrGroupInfo(attrGroupPo);

        if (bigCategoryCodes != null)
        {
            for (int i = 0; i < bigCategoryCodes.length; i++) {
                this.attrGroupDao.addAttrGroupCatRel(attrGroupPo.getGroupCode(),bigCategoryCodes[i]);
            }

        }

        result.put("row",row);
        return result;
    }

    public Map<String, Object> getAllCatCodeByGroupCode(String groupCode)
    {
        Map<String, Object> result = new HashMap<>();

        List<String> catCodes = this.attrGroupDao.getAllCatCodeByGroupCode(groupCode);

        result.put("result",catCodes);

        return result;
    }

    public Map<String, Object> updateAttrGroupInfo(AttrGroupPo attrGroupPo, String[] bigCategoryCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrGroupDao.updateAttrGroupInfo(attrGroupPo);

        if (bigCategoryCodes != null)
        {
            this.attrGroupDao.deleteAttrGroupCatRelByGroupCode(attrGroupPo.getGroupCode());
            for (int i = 0; i < bigCategoryCodes.length; i++) {
                this.attrGroupDao.addAttrGroupCatRel(attrGroupPo.getGroupCode(),bigCategoryCodes[i]);
            }

        }

        result.put("row",row);
        return result;
    }

    public Map<String, Object> deleteAttrGroupInfoByGroupCodes(String[] groupCodes)
    {
        Map<String, Object> result = new HashMap<>();

        this.attrGroupDao.deleteAttrGroupCatRelByGroupCodes(groupCodes);

        this.attrGroupDao.deleteAttrConfigInfoByGroupCodes(groupCodes);

        Integer row = this.attrGroupDao.deleteAttrGroupInfoByGroupCodes(groupCodes);

        result.put("row",row);
        return result;
    }

    public Map<String, Object> getAllAttrGroupByCatCode(String catCode)
    {
        Map<String, Object> result = new HashMap<>();

        List<AttrGroupPo> attrGroupPos = this.attrGroupDao.getAllAttrGroupByCatCode(catCode);

        result.put("result",attrGroupPos);
        return result;
    }
}
