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
    private String groupCode;
    private String groupName;
    private String groupStat;
    private String sortNo;
}
