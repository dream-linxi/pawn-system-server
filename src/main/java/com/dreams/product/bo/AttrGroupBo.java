package com.dreams.product.bo;

import com.dreams.product.po.AttrGroupPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/24 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttrGroupBo extends AttrGroupPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
}
