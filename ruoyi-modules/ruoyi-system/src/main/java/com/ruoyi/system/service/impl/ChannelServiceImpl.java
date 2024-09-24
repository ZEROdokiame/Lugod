package com.ruoyi.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.mapper.ChannelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.http.Channel;
import com.ruoyi.system.service.IChannelService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 渠道配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
@Slf4j
public class ChannelServiceImpl implements IChannelService 
{
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void init(){
        log.info("初始化渠道数据开始");
        List<Channel> channels = channelMapper.selectList(new LambdaQueryWrapper<Channel>());
        for (Channel channel:channels) {
            redisService.setCacheObject(CacheConstants.CHANNEL_ID+channel.getId(),channel);
            redisService.setCacheObject(CacheConstants.CHANNEL_SIGN+channel.getChannelSign(),channel);
        }
    }

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
    public long insertChannel(Channel channel)
    {
        if (StringUtils.isEmpty(channel.getChannelSign())) {
            channel.setChannelSign(RandomStringUtils.random(16, true, false));
        }
        channel.setCreateTime(DateUtils.getNowDate());
        channel.setUpdateTime(DateUtils.getNowDate());
        long i = channelMapper.insertChannel(channel);
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
    //@Cacheable(value = "channel",key = "'channel:all'")
    public List<Channel> findAllChannelList(){
        return channelMapper.findAllChannelList();
    }

    /**
     * 根据渠道标识获取渠道
     * @param sign
     * @return
     */
    @Override
    public AjaxResult getChannelBySign(String sign) {
        Channel channel = channelMapper.selectOne(new LambdaQueryWrapper<Channel>().eq(Channel::getChannelSign, sign));
        if (channel==null||channel.getChannelSign()==null){
            return AjaxResult.error("渠道异常");
        }
        return AjaxResult.success("获取成功",channel.getChannelType());
    }
}
