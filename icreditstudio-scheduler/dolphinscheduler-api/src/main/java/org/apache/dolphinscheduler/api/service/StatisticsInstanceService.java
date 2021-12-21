package org.apache.dolphinscheduler.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.result.TaskCountResult;
import org.apache.dolphinscheduler.dao.entity.StatisticsInstanceEntity;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * instance统计表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-12-16
 */
public interface StatisticsInstanceService extends IService<StatisticsInstanceEntity> {

    StatisticsInstanceEntity getRowData(String workspaceId, String userId, Date date, Integer state);

    Long countByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime, int[] statusArray);

    Long totalRecordsByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime);

    Long totalBytesByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime);

    List<TaskCountResult> countByDay(String userId, SchedulerHomepageRequest request);
}
