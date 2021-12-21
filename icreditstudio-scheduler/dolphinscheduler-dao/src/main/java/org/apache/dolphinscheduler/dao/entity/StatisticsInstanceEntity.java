package org.apache.dolphinscheduler.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * instance统计表
 * </p>
 *
 * @author xujie
 * @since 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("statistics_instance")
public class StatisticsInstanceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    private String workspaceId;

    private String userId;

    private Integer state;

    private Date date;

    private Integer count;

    private Long totalRecords;

    private Long totalByte;

    private Integer scheduleType;

    public StatisticsInstanceEntity() {
    }

    public StatisticsInstanceEntity(String workspaceId, String userId, Integer state, Date date, Integer count, Long totalRecords, Long totalByte, Integer scheduleType) {
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.state = state;
        this.date = date;
        this.count = count;
        this.totalRecords = totalRecords;
        this.totalByte = totalByte;
        this.scheduleType = scheduleType;
    }
}
