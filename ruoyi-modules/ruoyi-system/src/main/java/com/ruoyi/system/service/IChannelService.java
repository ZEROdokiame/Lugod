package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Channel;

/**
 * 渠道配置Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface IChannelService 
{
    /**
     * 查询渠道配置
     * 
     * @param id 渠道配置主键
     * @return 渠道配置
     */
    public Channel selectChannelById(Long id);

    /**
     * 查询渠道配置列表
     * 
     * @param channel 渠道配置
     * @return 渠道配置集合
     */
    public List<Channel> selectChannelList(Channel channel);

    /**
     * 新增渠道配置
     * 
     * @param channel 渠道配置
     * @return 结果
     */
    public Long insertChannel(Channel channel);

    /**
     * 修改渠道配置
     * 
     * @param channel 渠道配置
     * @return 结果
     */
    public int updateChannel(Channel channel);

    /**
     * 批量删除渠道配置
     * 
     * @param ids 需要删除的渠道配置主键集合
     * @return 结果
     */
    public int deleteChannelByIds(Long[] ids);

    /**
     * 删除渠道配置信息
     * 
     * @param id 渠道配置主键
     * @return 结果
     */
    public int deleteChannelById(Long id);

    /**
     * 获取所有的渠道列表
     * @return
     */
    public List<Channel> findAllChannelList();
}
