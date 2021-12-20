package org.apache.dolphinscheduler.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.dolphinscheduler.dao.entity.StatisticsDefinitionEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * definition统计表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-12-17
 */
public interface StatisticsDefinitionService extends IService<StatisticsDefinitionEntity> {

    StatisticsDefinitionEntity getRowData(String workspaceId, String userId, Date date, String platformTaskId);

    List<Map<String, Object>> runtimeTotalByDefinition(String workspaceId, String userId, Date startTime, Date endTime);

    List<Map<String, Object>> errorTimeTotalByDefinition(String workspaceId, String userId, Date startTime, Date endTime);
}
