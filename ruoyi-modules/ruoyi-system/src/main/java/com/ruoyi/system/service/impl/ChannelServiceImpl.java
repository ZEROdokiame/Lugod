package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.mapper.ChannelMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.Channel;
import com.ruoyi.system.service.IChannelService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 渠道配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class ChannelServiceImpl implements IChannelService 
{
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 查询渠道配置
     * 
     * @param id 渠道配置主键
     * @return 渠道配置
     */
    @Override
    public Channel selectChannelById(Long id)
    {
        return channelMapper.selectChannelById(id);
    }

    /**
     * 查询渠道配置列表
     * 
     * @param channel 渠道配置
     * @return 渠道配置
     */
    @Override
    public List<Channel> selectChannelList(Channel channel)
    {
        return channelMapper.selectChannelList(channel);
    }

    /**
     * 新增渠道配置
     * 
     * @param channel 渠道配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertChannel(Channel channel)
    {
        if (StringUtils.isEmpty(channel.getChannelSign())) {
            channel.setChannelSign(RandomStringUtils.random(16, true, false));
        }
        channel.setCreateTime(DateUtils.getNowDate());
        Long i = channelMapper.insertChannel(channel);
        //新增插入缓存
        Channel channelById = channelMapper.selectChannelById(i);
        redisService.setCacheObject(CacheConstants.CHANNEL_ID+i,channelById);
        redisService.setCacheObject(CacheConstants.CHANNEL_SIGN+channel.getChannelSign(),channelById);
        return i;
    }

    /**
     * 修改渠道配置
     * 
     * @param channel 渠道配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateChannel(Channel channel)
    {
        channel.setUpdateTime(DateUtils.getNowDate());
        int i = channelMapper.updateChannel(channel);

        Channel channelById = channelMapper.selectChannelById(channel.getId());
        redisService.deleteObject(CacheConstants.CHANNEL_ID+channel.getId());
        redisService.deleteObject(CacheConstants.CHANNEL_SIGN+channel.getChannelSign());
        redisService.setCacheObject(CacheConstants.CHANNEL_ID+channel.getId(),channelById);
        redisService.setCacheObject(CacheConstants.CHANNEL_SIGN+channel.getChannelSign(),channelById);
        return i;
    }

    /**
     * 批量删除渠道配置
     * 
     * @param ids 需要删除的渠道配置主键
     * @return 结果
     */
    @Override
    public int deleteChannelByIds(Long[] ids)
    {
        return channelMapper.deleteChannelByIds(ids);
    }

    /**
     * 删除渠道配置信息
     * 
     * @param id 渠道配置主键
     * @return 结果
     */
    @Override
    public int deleteChannelById(Long id)
    {
        return channelMapper.deleteChannelById(id);
    }

    /**
     * 获取所有的渠道列表
     * @return
     */
    @Cacheable(value = "channel",key = "'channel:all'")
    public List<Channel> findAllChannelList(){
        return channelMapper.findAllChannelList();
    }
}
