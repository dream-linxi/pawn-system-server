package com.dreams.product.bo;

import com.dreams.product.po.AttrConfPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/25 1:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttrConfBo extends AttrConfPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
}
