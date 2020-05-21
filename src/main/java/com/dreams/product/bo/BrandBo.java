package com.dreams.product.bo;

import com.dreams.product.po.BrandPo;
import lombok.Data;

/**
 * @author dreams-linxi
 * @date 2020/5/21 21:36
 */
@Data
public class BrandBo extends BrandPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
}
