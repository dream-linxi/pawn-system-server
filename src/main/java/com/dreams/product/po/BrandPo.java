package com.dreams.product.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/21 21:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BrandPo
{
    /* 品牌编号 */
    private String brandCode;
    /* 品牌名称 */
    private String brandName;
    /* 品牌首字母 */
    private String fletter;
    /* 品牌简介 */
    private String brandDesc;
    /* 是否显示 */
    private String isShow;
    /* 排序 */
    private String sortNo;
}
