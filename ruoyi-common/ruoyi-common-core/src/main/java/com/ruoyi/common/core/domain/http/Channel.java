package com.ruoyi.common.core.domain.http;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 渠道配置对象 channel
 *
 * @author ruoyi
 * @date 2024-09-15
 */
public class Channel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

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

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getId()
    {
        return id;
    }
    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getChannelName()
    {
        return channelName;
    }
    public void setChannelSign(String channelSign)
    {
        this.channelSign = channelSign;
    }

    public String getChannelSign()
    {
        return channelSign;
    }
    public void setScore(Long score)
    {
        this.score = score;
    }

    public Long getScore()
    {
        return score;
    }
    public void setHtmlName(String htmlName)
    {
        this.htmlName = htmlName;
    }

    public String getHtmlName()
    {
        return htmlName;
    }
    public void setHtmlLocation(String htmlLocation)
    {
        this.htmlLocation = htmlLocation;
    }

    public String getHtmlLocation()
    {
        return htmlLocation;
    }
    public void setIps(String ips)
    {
        this.ips = ips;
    }

    public String getIps()
    {
        return ips;
    }
    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getPeriod()
    {
        return period;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("channelName", getChannelName())
                .append("channelSign", getChannelSign())
                .append("score", getScore())
                .append("htmlName", getHtmlName())
                .append("htmlLocation", getHtmlLocation())
                .append("ips", getIps())
                .append("period", getPeriod())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
