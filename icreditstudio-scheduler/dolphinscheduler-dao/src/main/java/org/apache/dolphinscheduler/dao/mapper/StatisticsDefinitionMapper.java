package org.apache.dolphinscheduler.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.dolphinscheduler.dao.entity.StatisticsDefinitionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * definition统计表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-12-17
 */
public interface StatisticsDefinitionMapper extends BaseMapper<StatisticsDefinitionEntity> {

    StatisticsDefinitionEntity getRowData(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("date") Date date, @Param("platformTaskId") String platformTaskId);

    List<Map<String, Object>> runtimeTotalByDefinition(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<Map<String, Object>> errorTimeTotalByDefinition(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    void deleteByPlatformTaskId(@Param("platformTaskId") String platformTaskId);
}
