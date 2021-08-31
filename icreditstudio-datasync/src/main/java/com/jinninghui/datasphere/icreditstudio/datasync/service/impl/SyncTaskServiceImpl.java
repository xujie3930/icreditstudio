package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncTaskMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableFieldService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
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
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author peng
 */
@Slf4j
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    @Resource
    private SyncWidetableService syncWidetableService;
    @Resource
    private SyncWidetableFieldService syncWidetableFieldService;
    @Resource
    private Parser<String, List<AssociatedData>> fileAssociatedParser;
    @Resource
    private Parser<String, TaskScheduleInfo> taskScheduleInfoParser;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param) {
        SyncTaskEntity entity = transferToSyncTaskEntity(param);
        this.saveOrUpdate(entity);
        param.setTaskId(entity.getId());
        SyncWidetableEntity wideTableEntity = transferToSyncWidetableEntity(param);
        syncWidetableService.saveOrUpdate(wideTableEntity);
        List<SyncWidetableFieldEntity> syncWideTableFieldEntities = transferToWideTableFields(wideTableEntity.getId(), param);
        syncWidetableFieldService.saveOrUpdateBatch(syncWideTableFieldEntities);
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
            SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(id, version);
            if (Objects.nonNull(wideTableField)) {
                info = new TaskBuildInfo();
                info.setWideTableName(wideTableField.getName());
                info.setSourceType(wideTableField.getSourceType());
                info.setPartition(wideTableField.getPartitionField());
                info.setTargetSource(wideTableField.getTargetUrl());
                info.setView(fileAssociatedParser.parse(wideTableField.getViewJson()));
                List<SyncWidetableFieldEntity> wideTableFields = syncWidetableFieldService.getWideTableFields(wideTableField.getId());
                info.setFieldInfos(transferToWideTableFieldInfo(wideTableFields));
            }
        }
        return BusinessResult.success(info);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<TaskScheduleInfo> taskScheduleInfo(DataSyncDetailParam param) {
        SyncTaskEntity byId = getById(param.getTaskId());
        TaskScheduleInfo info = null;
        if (Objects.nonNull(byId)) {
            String taskParamJson = byId.getTaskParamJson();
            info = taskScheduleInfoParser.parse(taskParamJson);
        }
        return BusinessResult.success(info);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<Associated> dialectAssociatedSupport(DataSyncDialectSupportParam param) {
        return BusinessResult.success(AssociatedUtil.find(param.getDialect()));
    }

    @Override
    public BusinessResult<WideTable> generateWideTable(DataSyncGenerateWideTableParam param) {
        AssociatedFormatterVo vo = new AssociatedFormatterVo();
        vo.setDialect(param.getDialect());
        vo.setSourceTables(param.getSourceTables());
        vo.setAssoc(param.getView());
        String sql = AssociatedUtil.wideTableSql(vo);
        WideTable wideTable = null;
        try {
            wideTable = new WideTable();
            wideTable.setTableName(RandomUtil.randomString(10) + DateUtil.now());
            ConnectionInfo info = getConnectionInfo(param.getWorkspaceId(), param.getDatasourceId());

            ResultSetMetaData metaData = AssociatedUtil.getResultSetMetaData(info, sql);
            int columnCount = metaData.getColumnCount();
            List<WideTableFieldInfo> fieldInfos = Lists.newArrayList();
            for (int i = 1; i <= columnCount; i++) {
                WideTableFieldInfo fieldInfo = new WideTableFieldInfo();
                fieldInfo.setSort(i);
                fieldInfo.setFieldName(metaData.getColumnName(i));
                fieldInfo.setFieldType(HiveMapJdbcTypeEnum.find(metaData.getColumnTypeName(i)).getHiveType());
                fieldInfo.setSourceTable(metaData.getTableName(i));
                fieldInfo.setFieldChineseName(null);
                fieldInfos.add(fieldInfo);
            }
            wideTable.setFields(fieldInfos);
        } catch (Exception e) {
            log.error("识别宽表失败", e);
            new AppException("识别宽表失败");
        }
        return BusinessResult.success(wideTable);
    }

    private ConnectionInfo getConnectionInfo(String workspaceId, String datasourceId) {
        ConnectionInfo info = new ConnectionInfo();
        info.setDriverClass("com.mysql.cj.jdbc.Driver");
        info.setUrl("jdbc:mysql://localhost:3306/datasync?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true");
        info.setUsername("root");
        info.setPassword("root@0000");
        return info;
    }

    private List<WideTableFieldInfo> transferToWideTableFieldInfo(List<SyncWidetableFieldEntity> entities) {
        List<WideTableFieldInfo> results = null;
        if (CollectionUtils.isNotEmpty(entities)) {
            results = entities.parallelStream()
                    .map(entity -> {
                        WideTableFieldInfo info = new WideTableFieldInfo();
                        BeanCopyUtils.copyProperties(entity, info);
                        return info;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private List<SyncWidetableFieldEntity> transferToWideTableFields(final String wideTableId, DataSyncSaveParam param) {
        List<SyncWidetableFieldEntity> results = null;
        List<WideTableFieldInfo> fieldInfos = param.getFieldInfos();
        if (CollectionUtils.isNotEmpty(fieldInfos)) {
            results = fieldInfos.parallelStream()
                    .map(field -> {
                        SyncWidetableFieldEntity entity = new SyncWidetableFieldEntity();
                        BeanCopyUtils.copyProperties(field, entity);
                        entity.setWideTableId(wideTableId);
                        entity.setSort(field.getSort());
                        entity.setName(field.getFieldName());
                        entity.setType(field.getFieldType());
                        entity.setChinese(field.getFieldChineseName());
                        entity.setDictKey(field.getAssociateDict());
                        entity.setSource(field.getSourceTable());
                        return entity;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
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
