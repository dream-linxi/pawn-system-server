package com.dreams.product.service;

import com.dreams.product.bo.AttrConfBo;
import com.dreams.product.dao.AttrConfDao;
import com.dreams.product.po.AttrConfPo;
import com.dreams.product.po.AttrGroupPo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/25 1:23
 */
@Service
public class AttrConfService
{
    @Resource
    private AttrConfDao attrConfDao;

    public Map<String, Object> getAllAttrConfInfo(AttrConfBo attrConfBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(attrConfBo.getPage(),attrConfBo.getLimit());
        List<AttrConfPo> attrConfPos = this.attrConfDao.getAllAttrConfInfo(attrConfBo);
        PageInfo<AttrConfPo> attrConfPoPageInfo = new PageInfo<AttrConfPo>(attrConfPos);

        // 响应数据
        result.put("msg","");
        result.put("data",attrConfPos);
        result.put("code",0);
        result.put("count",attrConfPoPageInfo.getTotal());

        return result;
    }

    public Map<String, Object> checkAttrCode(String attrCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrConfDao.checkAttrCode(attrCode);

        result.put("row",row);

        return result;
    }

    public Map<String, Object> addAttrConfInfo(AttrConfPo attrConfPo)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrConfDao.addAttrConfInfo(attrConfPo);

        result.put("row",row);

        return result;
    }

    public Map<String, Object> updateAttrConfInfo(AttrConfPo attrConfPo)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrConfDao.updateAttrConfInfo(attrConfPo);

        result.put("row",row);

        return result;
    }

    public Map<String, Object> deleteAttrConfInfoByAttrCodes(String[] attrCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.attrConfDao.deleteAttrConfInfoByAttrCodes(attrCodes);

        result.put("row",row);

        return result;
    }
}
