package com.dreams.goods.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author dreams-linxi
 * @date 2020/5/28 22:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsAppraisePo
{
    /* 评估编号 */
    private Integer appraiseId;
    /* 商品编号 */
    private Integer goodsId;
    /* 流程实例 Id */
    private String procInstId;
    /* 官方价 */
    private Double officialPrice;
    /* 评估价 */
    private Double valuationPrice;
    /* 典当价 */
    private Double pawnPrice;
    /* 收购价 */
    private Double rchasePrice;
    /* 售卖价 */
    private Double sellingPrice;
    /* 租价 */
    private Double rentalPrice;
    /* 备注 */
    private String appraiseDesc;
    //创建人
    private String createBy;
    //创建时间
    @JsonFormat(timezone = "Asia/Shanghai",pattern = "yyyy-MM-dd")
    private Date createTime;
    //修改人
    private String modifyBy;
    //修改时间
    @JsonFormat(timezone = "Asia/Shanghai",pattern = "yyyy-MM-dd")
    private Date modifyTime;
}
