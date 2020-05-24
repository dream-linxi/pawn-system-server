package com.dreams.channel.controller;

import com.dreams.channel.bo.ChannelBo;
import com.dreams.channel.po.ChannelPo;
import com.dreams.channel.service.ChannelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/24 13:20
 */
@RestController
@RequestMapping("/channel/channel")
public class ChannelController
{
    @Resource
    private ChannelService channelService;

    /**
     * 获取所有渠道信息
     * @param channelBo 渠道带条件实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/getAllChannelInfo.json")
    public Map<String, Object> getAllChannelInfo(ChannelBo channelBo)
    {
        return this.channelService.getAllChannelInfo(channelBo);
    }

    /**
     * 检查当前账号信息是否存在于数据库
     * @param channelCode 渠道编号
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/checkChannelCode.json")
    public Map<String, Object> checkChannelCode(@RequestParam("channelCode") String channelCode)
    {
        return this.channelService.checkChannelCode(channelCode);
    }

    /**
     * 添加渠道信息
     * @param channelPo 渠道信息实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/addChannelInfo.json")
    public Map<String, Object> addChannelInfo(ChannelPo channelPo)
    {
        return this.channelService.addChannelInfo(channelPo);
    }

    /**
     * 更新渠道信息
     * @param channelPo 渠道信息实体类
     * @return Map 集合,封装了对应的数据
     */
    @RequestMapping("/updateChannelInfo.json")
    public Map<String, Object> updateChannelInfo(ChannelPo channelPo)
    {
        return this.channelService.updateChannelInfo(channelPo);
    }

    /**
     * 根据渠道编号删除渠道信息
     * @param channelCodes 渠道编号数组
     * @return  Map 集合,封装了对应的数据
     */
    @RequestMapping("/deleteChannelInfoByChannelCodes.json")
    public Map<String, Object> deleteChannelInfoByChannelCodes(@RequestParam("channelCodes[]") String[] channelCodes)
    {
        return this.channelService.deleteChannelInfoByChannelCodes(channelCodes);
    }

}
