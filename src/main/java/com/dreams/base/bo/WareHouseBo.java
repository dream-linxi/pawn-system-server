package com.dreams.base.bo;

import com.dreams.base.po.WareHousePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WareHouseBo extends WareHousePo
{
    /* 当前页 */
    private Integer page;
    /* 每页显示行数 */
    private Integer limit;
    /* 关键字 */
    private String keyWord;
}
