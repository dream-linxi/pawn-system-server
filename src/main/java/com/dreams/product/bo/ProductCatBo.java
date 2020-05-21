package com.dreams.product.bo;

import com.dreams.product.po.ProductCatPo;
import lombok.Data;

/**
 * @author dreams-linxi
 * @date 2020/5/21 9:14
 */
@Data
public class ProductCatBo extends ProductCatPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
}
