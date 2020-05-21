package com.dreams.base.bo;

import com.dreams.base.po.ShopPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/19 10:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopBo extends ShopPo
{
    /* 当前页 */
    private Integer page;
    /* 每页显示行数 */
    private Integer limit;
    /* 关键字 */
    private String keyWord;
}
