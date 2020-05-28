package com.dreams.product.controller;

import com.dreams.product.bo.AttrConfBo;
import com.dreams.product.po.AttrConfPo;
import com.dreams.product.service.AttrConfService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/25 1:23
 */
@RestController
@RequestMapping("/product/attrconf")
public class AttrConfController
{
    @Resource
    private AttrConfService attrConfService;

    @RequestMapping("/getAllAttrConfInfo.json")
    public Map<String,Object> getAllAttrConfInfo(AttrConfBo attrConfBo)
    {
        return this.attrConfService.getAllAttrConfInfo(attrConfBo);
    }

    @RequestMapping("/checkAttrCode.json")
    public Map<String,Object> checkAttrCode(@RequestParam("attrCode") String attrCode)
    {
        return this.attrConfService.checkAttrCode(attrCode);
    }

    @RequestMapping("/addAttrConfInfo.json")
    public Map<String,Object> addAttrConfInfo(AttrConfPo attrConfPo)
    {
        return this.attrConfService.addAttrConfInfo(attrConfPo);
    }

    @RequestMapping("/updateAttrConfInfo.json")
    public Map<String,Object> updateAttrConfInfo(AttrConfPo attrConfPo)
    {
        return this.attrConfService.updateAttrConfInfo(attrConfPo);
    }

    @RequestMapping("/deleteAttrConfInfoByAttrCodes.json")
    public Map<String,Object> deleteAttrConfInfoByAttrCodes(@RequestParam("attrCodes[]") String[] attrCodes)
    {
        return this.attrConfService.deleteAttrConfInfoByAttrCodes(attrCodes);
    }

    @RequestMapping("/getAllAttrConfByGroupCode.json")
    public Map<String,Object> getAllAttrConfByGroupCode(@RequestParam("groupCode") String groupCode)
    {
        return this.attrConfService.getAllAttrConfByGroupCode(groupCode);
    }
}
