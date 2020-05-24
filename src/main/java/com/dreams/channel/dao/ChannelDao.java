package com.dreams.channel.dao;

import com.dreams.channel.bo.ChannelBo;
import com.dreams.channel.po.ChannelPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/24 13:19
 */
@Repository
public interface ChannelDao
{

    List<ChannelPo> getAllChannelInfo(ChannelBo channelBo);

    Integer checkChannelCode(@Value("channelCode") String channelCode);

    Integer addChannelInfo(ChannelPo channelPo);

    Integer updateChannelInfo(ChannelPo channelPo);

    Integer deleteChannelInfoByChannelCodes(String[] channelCodes);
}
