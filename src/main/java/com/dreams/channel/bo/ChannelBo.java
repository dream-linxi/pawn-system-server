package com.dreams.channel.bo;

import com.dreams.channel.po.ChannelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author dreams-linxi
 * @date 2020/5/24 13:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChannelBo extends ChannelPo
{
    private String keyWord;
    private Integer page;
    private Integer limit;
}
