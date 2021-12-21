package org.apache.dolphinscheduler.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dolphinscheduler.api.service.StatisticsDefinitionService;
import org.apache.dolphinscheduler.dao.entity.StatisticsDefinitionEntity;
import org.apache.dolphinscheduler.dao.mapper.StatisticsDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * definition统计表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-12-17
 */
@Service
public class StatisticsDefinitionServiceImpl extends ServiceImpl<StatisticsDefinitionMapper, StatisticsDefinitionEntity> implements StatisticsDefinitionService {

    @Autowired
    private StatisticsDefinitionMapper statisticsDefinitionMapper;

    @Override
    public StatisticsDefinitionEntity getRowData(String workspaceId, String userId, Date date, String platformTaskId) {
        return statisticsDefinitionMapper.getRowData(workspaceId, userId, date, platformTaskId);
    }

    @Override
    public List<Map<String, Object>> runtimeTotalByDefinition(String workspaceId, String userId, Date startTime, Date endTime) {
        return statisticsDefinitionMapper.runtimeTotalByDefinition(workspaceId, userId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> errorTimeTotalByDefinition(String workspaceId, String userId, Date startTime, Date endTime) {
        return statisticsDefinitionMapper.errorTimeTotalByDefinition(workspaceId, userId, startTime, endTime);
    }

}
