package com.dreams.base.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopPo
{
    private String shopCode;    //商品号码
    private String shopName;    //商品名称
    private String contact;     //联系人
    private String phoneNo;     //电话号码
    private String address;     //地址;
    private String shopStat;    //商品状态

    // 关联门店
    private String wareHouses;
}
