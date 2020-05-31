package com.dreams.goods.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dreams-linxi
 * @date 2020/5/28 9:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodAndAttrPo
{
    private String attrCode;
    private Integer goodsId;
    private String attrValue;
}
