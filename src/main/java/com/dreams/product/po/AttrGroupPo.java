package com.dreams.product.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttrGroupPo
{
    /* 分组编号 */
    private String groupCode;
    /* 分组名称 */
    private String groupName;
    /* 分组桩体 */
    private String groupStat;
    /* 排序 */
    private String sortNo;

    /* 管理的大类信息 */
    private String bigCatName;

    /* 属性个数 */
    private Integer countAttr;
}
