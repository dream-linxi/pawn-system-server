package com.dreams.base.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WareHousePo
{
    private String whCode;
    private String whName;
    private String contact;
    private String phoneNo;
    private String address;
    private String whStat;

    private String shops;
}
