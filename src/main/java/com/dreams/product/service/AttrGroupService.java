package com.dreams.product.service;

import com.dreams.product.dao.AttrGroupDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:26
 */
@Service
public class AttrGroupService
{
    @Resource
    private AttrGroupDao attrGroupDao;
}
