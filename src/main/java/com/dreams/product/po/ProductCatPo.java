package com.dreams.product.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品分类实体类
 * @author dreams-linxi
 * @date 2020/5/21 9:14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCatPo
{
    /* 分类编号 */
    private String catCode;
    /* 分类名称 */
    private String catName;
    /* 上级分类 */
    private String pCatCode;
    /* 分类级次 */
    private Integer catLvl;
    /* 分类路径 */
    private String catRoute;
    /* 数量单位 */
    private String unit;
    /* 分类描述 */
    private String catDesc;
    /* 鉴定图定义 */
    private String evalPicDef;
    /* 是否显示 */
    private String isShow;
    /* 排序 */
    private String sortNo;
}
