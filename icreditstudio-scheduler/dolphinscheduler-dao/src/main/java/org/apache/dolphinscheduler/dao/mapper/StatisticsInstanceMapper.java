package org.apache.dolphinscheduler.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.dolphinscheduler.dao.entity.StatisticsInstanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * instance统计表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-12-16
 */
public interface StatisticsInstanceMapper extends BaseMapper<StatisticsInstanceEntity> {

    StatisticsInstanceEntity getRowData(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("date") Date date, @Param("state") Integer state);

    Long countByWorkspaceIdAndTime(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("states") int[] statusArray);

    Long totalRecordsByWorkspaceIdAndTime(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Long totalBytesByWorkspaceIdAndTime(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Map<String, Object>> countByDay(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("scheduleType") Integer scheduleType, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("states") int[] statusArray);
}
