package com.dreams.channel.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author dreams-linxi
 * @date 2020/5/24 2:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChannelPo
{
    /* 渠道编号 */
    private String channelCode;
    /* 渠道名 */
    private String channelName;
    /* 手机 */
    private String mobile;
    /* 证件号码 */
    private String idNo;
    /* 账户名称 */
    private String accountName;
    /* 开户行 */
    private String bandName;
    /* 账号 */
    private String account;
    /* 会员数量 */
    private Integer memberCount;
    /* 累计充值 */
    private Double totalCharge;
    /* 累计消费 */
    private Double consume;
    /* 状态 */
    private Integer channelStat;
    /* 创建人 */
    private String createBy;
    /* 创建时间 */
    private Date createTime;
    /* 修改人 */
    private String modifyBy;
    /* 修改时间 */
    private Date modifyTime;
}
