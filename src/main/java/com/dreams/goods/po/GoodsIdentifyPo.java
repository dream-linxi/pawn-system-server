package com.dreams.goods.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author dreams-linxi
 * @date 2020/5/28 22:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsIdentifyPo
{
    /* 鉴定编号 */
    private Integer idenityId;
    /* 商品编号 */
    private Integer goodsId;
    /* 流程实例Id */
    private String procInstId;
    /* 新旧程度 */
    private String goodsQuality;
    /* 备注 */
    private String identifyDesc;
    /* 鉴定结果 */
    private String identifyResult;
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
