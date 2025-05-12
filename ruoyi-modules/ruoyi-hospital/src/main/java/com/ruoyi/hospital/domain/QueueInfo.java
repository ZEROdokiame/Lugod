package com.ruoyi.hospital.domain;

import lombok.Data;
import java.util.Date;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 叫号队列信息对象 hospital_queue_info
 * 
 * @author lugod
 */
@Data
public class QueueInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 队列ID */
    private Long queueId;

    /** 队列名称 */
    @Excel(name = "队列名称")
    private String queueName;

    /** 科室ID */
    private Long deptId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String deptName;

    /** 当前叫号 */
    @Excel(name = "当前叫号")
    private String currentNumber;

    /** 最大号码 */
    @Excel(name = "最大号码")
    private String maxNumber;

    /** 队列状态（0正常 1暂停 2关闭） */
    @Excel(name = "队列状态", readConverterExp = "0=正常,1=暂停,2=关闭")
    private String status;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    /** 备注 */
    private String remark;
}
