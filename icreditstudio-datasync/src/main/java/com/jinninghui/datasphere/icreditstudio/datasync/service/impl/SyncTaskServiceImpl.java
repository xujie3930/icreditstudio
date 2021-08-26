package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncTaskMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncDetailParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.SyncTaskConditionParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncTaskInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskBuildInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskDefineInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskScheduleInfo;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author peng
 */
@Slf4j
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    @Resource
    private SyncWidetableService syncWidetableService;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param) {
        SyncTaskEntity entity = transferToSyncTaskEntity(param);
        saveOrUpdate(entity);
        param.setTaskId(entity.getId());
        SyncWidetableEntity entity1 = transferToSyncWidetableEntity(param);
        syncWidetableService.saveOrUpdate(entity1);
        return BusinessResult.success(new ImmutablePair("taskId", entity.getId()));
    }

    @Override
    public BusinessResult<BusinessPageResult> syncTasks(DataSyncQueryParam param) {
        SyncTaskConditionParam build = SyncTaskConditionParam.builder()
                .workspaceId(param.getWorkspaceId())
                .taskName(param.getTaskName())
                .taskStatus(TaskStatusEnum.find(param.getTaskStatus()))
                .execStatus(ExecStatusEnum.find(param.getExecStatus()))
                .build();

        IPage<SyncTaskEntity> page = this.page(
                new Query<SyncTaskEntity>().getPage(param),
                queryWrapper(build)
        );

        List<SyncTaskEntity> records = page.getRecords();

        IPage<SyncTaskInfo> resultPage = new Page<>();
        if (CollectionUtils.isNotEmpty(records)) {
            BeanCopyUtils.copyProperties(page, resultPage);
            List<SyncTaskInfo> collect = records.stream()
                    .map(entity -> {
                        SyncTaskInfo info = new SyncTaskInfo();
                        BeanCopyUtils.copyProperties(entity, info);
                        info.setTaskId(entity.getId());
                        info.setTaskBuildMode(entity.getCreateMode());
                        info.setExecMode(entity.getCollectMode());
                        info.setCreateTime(entity.getCreateTime().getTime());
                        info.setLastScheduleTime(Objects.nonNull(entity.getLastSchedulingTime()) ? entity.getLastSchedulingTime().getTime() : null);
                        return info;
                    }).collect(Collectors.toList());
            resultPage.setRecords(collect);
        }
        return BusinessResult.success(BusinessPageResult.build(resultPage, param));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<TaskDefineInfo> taskDefineInfo(DataSyncDetailParam param) {
        SyncTaskEntity byId = getById(param.getTaskId());
        TaskDefineInfo info = null;
        if (Objects.nonNull(byId)) {
            info = new TaskDefineInfo();
            BeanCopyUtils.copyProperties(byId, info);
            info.setEnable(byId.getTaskStatus());
            info.setTaskId(byId.getId());
            info.setTaskDescription(byId.getTaskDescribe());
            info.setBuildMode(byId.getCreateMode());
        }
        return BusinessResult.success(info);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<TaskBuildInfo> taskBuildInfo(DataSyncDetailParam param) {
        SyncTaskEntity byId = getById(param.getTaskId());
        TaskBuildInfo info = null;
        if (Objects.nonNull(byId)) {
            String id = byId.getId();
            Integer version = byId.getVersion();
            SyncWidetableEntity wideTableFields = syncWidetableService.getWideTableFields(id, version);
            info = new TaskBuildInfo();

        }
        return BusinessResult.success(info);
    }

    /**
     * 转换同步任务表数据
     *
     * @param param
     * @return
     */
    private SyncTaskEntity transferToSyncTaskEntity(DataSyncSaveParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setTaskStatus(TaskStatusEnum.find(param.getEnable()).getCode());
        entity.setCreateMode(CreateModeEnum.find(param.getCreateMode()).getCode());
        entity.setSyncMode(SyncModeEnum.FULL.getCode());
        if (StringUtils.isNotBlank(param.getPartition())) {
            entity.setSyncMode(SyncModeEnum.INC.getCode());
        }
        entity.setCollectMode(CollectModeEnum.find(param.getScheduleType()).getCode());
        TaskScheduleInfo scheduleInfo = findScheduleInfo(param);
        entity.setTaskParamJson(JSONObject.toJSONString(scheduleInfo));
        return entity;
    }

    private SyncWidetableEntity transferToSyncWidetableEntity(DataSyncSaveParam param) {
        SyncWidetableEntity entity = new SyncWidetableEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setSyncTaskId(param.getTaskId());
        entity.setPartitionField(param.getPartition());
        entity.setTargetUrl(param.getTargetSource());
        entity.setName(param.getWideTableName());
        entity.setSourceType(SourceTypeEnum.find(param.getSourceType()).getCode());
        return entity;
    }

    private String transferToWideTableSql(DataSyncSaveParam param) {
        return "";
    }

    private TaskDefineInfo findTaskDefineInfo(DataSyncSaveParam param) {
        TaskDefineInfo info = new TaskDefineInfo();
        BeanCopyUtils.copyProperties(param, info);
        return info;
    }

    private TaskBuildInfo findTaskBuildInfo(DataSyncSaveParam param) {
        TaskBuildInfo info = new TaskBuildInfo();
        BeanCopyUtils.copyProperties(param, info);
        return info;
    }

    private TaskScheduleInfo findScheduleInfo(DataSyncSaveParam param) {
        TaskScheduleInfo info = new TaskScheduleInfo();
        BeanCopyUtils.copyProperties(param, info);
        info.setMaxConcurrent(param.getMaxThread());
        return info;
    }

    private QueryWrapper<SyncTaskEntity> queryWrapper(SyncTaskConditionParam param) {
        QueryWrapper<SyncTaskEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getTaskId())) {
            wrapper.eq(SyncTaskEntity.ID, param.getTaskId());
        }
        if (StringUtils.isNotBlank(param.getWorkspaceId())) {
            wrapper.eq(SyncTaskEntity.WORKSPACE_ID, param.getWorkspaceId());
        }
        if (StringUtils.isNotBlank(param.getTaskName())) {
            wrapper.like(SyncTaskEntity.TASK_NAME, param.getTaskName());
        }
        if (Objects.nonNull(param.getTaskStatus())) {
            wrapper.eq(SyncTaskEntity.TASK_STATUS, param.getTaskStatus().getCode());
        }
        if (Objects.nonNull(param.getExecStatus())) {
            wrapper.eq(SyncTaskEntity.EXEC_STATUS, param.getExecStatus().getCode());
        }
        wrapper.orderByAsc(SyncTaskEntity.TASK_STATUS);
        return wrapper;
    }
}
