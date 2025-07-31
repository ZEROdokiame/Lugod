package com.ruoyi.hospital.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.hospital.domain.QueueInfo;

/**
 * 叫号队列信息Mapper接口
 *
 * @author lugod
 */
@Mapper
public interface QueueInfoMapper
{
    /**
     * 查询队列信息列表
     *
     * @param queueInfo 队列信息
     * @return 队列信息集合
     */
    public List<QueueInfo> selectQueueList(QueueInfo queueInfo);

    /**
     * 新增队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int insertQueueInfo(QueueInfo queueInfo);

    /**
     * 修改队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int updateQueueInfo(QueueInfo queueInfo);

    /**
     * 删除队列信息
     *
     * @param queueId 队列信息主键
     * @return 结果
     */
    public int deleteQueueInfoById(Long queueId);

    /**
     * 批量删除队列信息
     *
     * @param queueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQueueInfoByIds(Long[] queueIds);

    /**
     * 查询队列信息
     *
     * @param queueId 队列信息主键
     * @return 队列信息
     */
    public QueueInfo selectQueueById(Long queueId);
}
