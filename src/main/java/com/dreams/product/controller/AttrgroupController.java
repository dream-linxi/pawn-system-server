package com.dreams.product.controller;

import com.dreams.product.service.AttrGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.dc.pr.PRError;

import javax.annotation.Resource;

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
}
