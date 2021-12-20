package org.apache.dolphinscheduler.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * definition统计表
 * </p>
 *
 * @author xujie
 * @since 2021-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("statistics_definition")
public class StatisticsDefinitionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String workspaceId;

    private String userId;

    private String platformTaskId;

    private Date date;

    private String name;

    private Integer time;

    private Integer errorTime;

    private Double speedTime;

    private Integer scheduleType;

    public StatisticsDefinitionEntity() {
    }

    public StatisticsDefinitionEntity(String workspaceId, String userId, String platformTaskId, Date date, String name, Integer time, Integer errorTime, Double speedTime, Integer scheduleType) {
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.platformTaskId = platformTaskId;
        this.date = date;
        this.name = name;
        this.time = time;
        this.errorTime = errorTime;
        this.speedTime = speedTime;
        this.scheduleType = scheduleType;
    }
}
