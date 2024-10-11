package com.ruoyi.common.core.domain.http;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 渠道配置对象 channel
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@Data
public class Channel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 渠道名称 */
    @Excel(name = "渠道名称")
    private String channelName;

    /** 渠道签名 */
    @Excel(name = "渠道签名")
    private String channelSign;

    /** 渠道类型  1H5 2连登 3半流程 4全流程*/
    @Excel(name = "渠道类型")
    private String channelType;

    /** 扣量比 */
    @Excel(name = "扣量比")
    private Long score;

    /** 推广页名称 */
    @Excel(name = "推广页名称")
    private String htmlName;

    /** 推广页地址 */
    @Excel(name = "推广页地址")
    private String htmlLocation;

    /** 可访问IP */
    @Excel(name = "可访问IP")
    private String ips;

    /** 开启关闭时段 */
    @Excel(name = "开启关闭时段")
    private String period;


}
