package com.dreams.product.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/25 1:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttrConfPo
{
    private String attrCode;
    private String groupCode;
    private String attrName;
    private String attrType;
    private String options;
    private String sortNo;
}
