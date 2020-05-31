package com.dreams.goods.bo;

import com.dreams.goods.po.GoodsPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author dreams-linxi
 * @date 2020/5/25 9:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsBo extends GoodsPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
    private String searchStarteTime;
    private String searchEndTime;

}
