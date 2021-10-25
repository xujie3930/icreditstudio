package org.apache.dolphinscheduler.api.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dolphinscheduler.api.dto.ScheduleParam;
import org.apache.dolphinscheduler.common.enums.FailureStrategy;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSchedulerParam {
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 项目编码
     */
    private String projectCode;
    /**
     * 流程定义ID
     */
    private String processDefineId;
    private ScheduleParam schedule;
    private WarningType warningType;
    private String warningGroupId;
    /**
     * 失败策略
     */
    private FailureStrategy failureStrategy;
    /**
     * 优先级
     */
    private Priority processInstancePriority;
    /**
     * 工作组
     */
    private String workerGroup;
}
