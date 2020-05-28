package com.dreams.product.controller;

import com.dreams.product.bo.AttrGroupBo;
import com.dreams.product.po.AttrGroupPo;
import com.dreams.product.service.AttrGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.dc.pr.PRError;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:27
 */
@RestController
@RequestMapping("/product/attrgroup")
public class AttrgroupController
{
    @Resource
    private AttrGroupService attrGroupService;

    @RequestMapping("/getAllAttrGroupInfo.json")
    public Map<String, Object> getAllAttrGroupInfo(AttrGroupBo attrGroupBo)
    {
        return this.attrGroupService.getAllAttrGroupInfo(attrGroupBo);
    }

    @RequestMapping("/checkGroupCode.json")
    public Map<String, Object> checkGroupCode(@RequestParam("groupCode") String groupCode)
    {
        return this.attrGroupService.checkGroupCode(groupCode);
    }

    @RequestMapping("/addAttrGroupInfo.json")
    public Map<String, Object> addAttrGroupInfo(AttrGroupPo attrGroupPo,
                                                @RequestParam(value = "bigCategoryCodes[]" ,required = false) String[] bigCategoryCodes)
    {
        return this.attrGroupService.addAttrGroupInfo(attrGroupPo,bigCategoryCodes);
    }

    @RequestMapping("/getAllCatCodeByGroupCode.json")
    public Map<String, Object> getAllCatCodeByGroupCode(@RequestParam("groupCode") String groupCode)
    {
        return this.attrGroupService.getAllCatCodeByGroupCode(groupCode);
    }

    @RequestMapping("/updateAttrGroupInfo.json")
    public Map<String, Object> updateAttrGroupInfo(AttrGroupPo attrGroupPo,
                                                        @RequestParam(value = "bigCategoryCodes[]" ,required = false) String[] bigCategoryCodes)
    {

        return this.attrGroupService.updateAttrGroupInfo(attrGroupPo,bigCategoryCodes);
    }

    @RequestMapping("/deleteAttrGroupInfoByGroupCodes.json")
    public Map<String, Object> deleteAttrGroupInfoByGroupCodes(@RequestParam("groupCodes[]") String[] groupCodes)
    {
        return this.attrGroupService.deleteAttrGroupInfoByGroupCodes(groupCodes);
    }

    @RequestMapping("/getAllAttrGroupByCatCode.json")
    public Map<String, Object> getAllAttrGroupByCatCode(@RequestParam("catCode") String catCode)
    {
        return this.attrGroupService.getAllAttrGroupByCatCode(catCode);
    }
}
