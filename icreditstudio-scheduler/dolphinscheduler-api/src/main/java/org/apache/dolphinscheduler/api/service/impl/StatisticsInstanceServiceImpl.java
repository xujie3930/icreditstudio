package org.apache.dolphinscheduler.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.StatisticsInstanceService;
import org.apache.dolphinscheduler.api.service.result.TaskCountResult;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.dao.entity.StatisticsInstanceEntity;
import org.apache.dolphinscheduler.dao.mapper.StatisticsInstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * instance统计表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-12-16
 */
@Service
public class StatisticsInstanceServiceImpl extends ServiceImpl<StatisticsInstanceMapper, StatisticsInstanceEntity> implements StatisticsInstanceService {

    @Autowired
    private StatisticsInstanceMapper statisticsInstanceMapper;

    @Override
    public StatisticsInstanceEntity getRowData(String workspaceId, String userId, Date date, Integer state) {
        return statisticsInstanceMapper.getRowData(workspaceId, userId, date, state);
    }

    @Override
    public Long countByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime, int[] statusArray) {
        return statisticsInstanceMapper.countByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime, statusArray);
    }

    @Override
    public Long totalRecordsByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime) {
        return statisticsInstanceMapper.totalRecordsByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime);
    }

    @Override
    public Long totalBytesByWorkspaceIdAndTime(String workspaceId, String userId, Date startTime, Date endTime) {
        return statisticsInstanceMapper.totalBytesByWorkspaceIdAndTime(workspaceId, userId, startTime, endTime);
    }

    @Override
    public List<TaskCountResult> countByDay(String userId, SchedulerHomepageRequest request) {
        Date startTime;
        Date endTime;
        //默认统计前七天的数据
        if (Objects.isNull(request.getSchedulerStartTime()) && Objects.isNull(request.getSchedulerEndTime())) {
            startTime = DateUtils.getStartOfDay(DateUtils.getSomeDay(new Date(), -7));
            endTime = DateUtils.getEndOfDay(DateUtils.getSomeDay(new Date(), -1));
        } else {
            startTime = DateUtils.getStartOfDay(new Date(request.getSchedulerStartTime()));
            endTime = DateUtils.getEndOfDay(new Date(request.getSchedulerEndTime()));
        }

        List<Map<String, Object>> countByDay = statisticsInstanceMapper.countByDay(request.getWorkspaceId(), userId, request.getScheduleType(), startTime, endTime, new int[]{});
        List<TaskCountResult> list = getDaysCount(startTime, endTime, countByDay);
        return list;
    }

    /**
     * 补全数据库未null的数据为0，且做好排序
     *
     * @param startTime
     * @param endTime
     * @param countByDay
     * @return
     */
    private List<TaskCountResult> getDaysCount(Date startTime, Date endTime, List<Map<String, Object>> countByDay) {
        List<TaskCountResult> list = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        Map<String, Object> tempMap = new HashMap<>();
        for (Map<String, Object> m : countByDay) {
            tempMap.put((String) m.get("date"), m.get("count"));
        }
        while (tempStart.before(tempEnd)) {
            String date = dateFormat.format(tempStart.getTime());
            if (!tempMap.containsKey(date)) {
                tempMap.put(date, 0L);
            }
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        for (Map.Entry<String, Object> m : tempMap.entrySet()) {
            list.add(new TaskCountResult(m.getKey(), (long) m.getValue()));
        }
        list.sort(Comparator.comparing(TaskCountResult::getDate));
        return list;
    }
}
