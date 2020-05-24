package com.dreams.channel.service;

import com.dreams.channel.bo.ChannelBo;
import com.dreams.channel.dao.ChannelDao;
import com.dreams.channel.po.ChannelPo;
import com.dreams.product.po.BrandPo;
import com.dreams.sys.bo.UserBo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/24 13:20
 */
@Service
public class ChannelService
{
    @Resource
    private ChannelDao channelDao;

    /**
     * 获取所有渠道信息
     * @param channelBo 渠道带条件实体类
     * @return Map 集合,封装了对应的数据
     *  - msg: 消息
     *  - data: 对应数据
     *  - code: 响应码
     *  - count: 总记录数
     */
    public Map<String, Object> getAllChannelInfo(ChannelBo channelBo)
    {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(channelBo.getPage(), channelBo.getLimit());
        List<ChannelPo> channelPos = this.channelDao.getAllChannelInfo(channelBo);
        PageInfo<ChannelPo> channelPoPageInfo = new PageInfo<ChannelPo>(channelPos);

        // 响应数据
        result.put("msg","");
        result.put("data",channelPos);
        result.put("code",0);
        result.put("count",channelPoPageInfo.getTotal());

        return result;
    }

    /**
     * 检查当前账号信息是否存在于数据库
     * @param channelCode 渠道编号
     * @return Map 集合,封装了对应的数据
     */
    public Map<String, Object> checkChannelCode(String channelCode)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.channelDao.checkChannelCode(channelCode);

        result.put("row", row);
        return result;
    }

    /**
     * 添加渠道信息
     * @param channelPo 渠道信息实体类
     * @return Map 集合,封装了对应的数据
     *  - row : 受影响行数
     */
    public Map<String, Object> addChannelInfo(ChannelPo channelPo)
    {
        Map<String, Object> result = new HashMap<>();

        // 获取当前登录用户
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();

        channelPo.setCreateBy(userBo.getUserId());
        channelPo.setCreateTime(new Date());

        Integer row = this.channelDao.addChannelInfo(channelPo);

        result.put("row", row);
        return result;
    }

    /**
     * 更新渠道信息
     * @param channelPo 渠道信息实体类
     * @return Map 集合,封装了对应的数据
     *  - row : 受影响行数
     */
    public Map<String, Object> updateChannelInfo(ChannelPo channelPo)
    {
        Map<String, Object> result = new HashMap<>();

        // 获取当前登录用户
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication contextAuthentication = context.getAuthentication();
        UserBo userBo = (UserBo)contextAuthentication.getPrincipal();
        channelPo.setModifyBy(userBo.getUserId());
        channelPo.setModifyTime(new Date());

        Integer row = this.channelDao.updateChannelInfo(channelPo);

        result.put("row", row);
        return result;
    }

    /**
     * 根据渠道编号删除渠道信息
     * @param channelCodes 渠道编号数组
     * @return  Map 集合,封装了对应的数据\
     * - row : 受影响行数
     */
    public Map<String, Object> deleteChannelInfoByChannelCodes(String[] channelCodes)
    {
        Map<String, Object> result = new HashMap<>();

        Integer row = this.channelDao.deleteChannelInfoByChannelCodes(channelCodes);

        result.put("row", row);
        return result;
    }
}
