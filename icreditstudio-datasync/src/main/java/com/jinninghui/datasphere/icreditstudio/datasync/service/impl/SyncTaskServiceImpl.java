package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.MetadataFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignConnectionInfoRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignMetadataGenerateWideTableRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.StatementField;
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
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
    @Resource
    private DatasourceFeign datasourceFeign;
    @Resource
    private MetadataFeign metadataFeign;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param) {
        String taskId = null;
        if (CallStepEnum.ONE == CallStepEnum.find(param.getCallStep())) {
            taskId = oneStepSave(param);
        }
        if (CallStepEnum.TWO == CallStepEnum.find(param.getCallStep())) {
            taskId = twoStepSave(param);
        }
        if (CallStepEnum.THREE == CallStepEnum.find(param.getCallStep())) {
            taskId = threeStepSave(param);
        }
        if (CallStepEnum.FOUR == CallStepEnum.find(param.getCallStep())) {
            CreateWideTableParam wideTableParam = BeanCopyUtils.copyProperties(param, CreateWideTableParam.class);
            //创建宽表
            createWideTable(wideTableParam);
            taskId = threeStepSave(param);
        }
        return BusinessResult.success(new ImmutablePair("taskId", taskId));
    }

    //第一步保存
    private String oneStepSave(DataSyncSaveParam param) {
        DataSyncTaskDefineSaveParam defineSaveParam = BeanCopyUtils.copyProperties(param, DataSyncTaskDefineSaveParam.class);
        return syncTaskDefineSave(defineSaveParam);
    }

    //第二部保存
    private String twoStepSave(DataSyncSaveParam param) {
        String taskId = oneStepSave(param);
        DataSyncTaskBuildSaveParam saveParam = BeanCopyUtils.copyProperties(param, DataSyncTaskBuildSaveParam.class);
        saveParam.setTaskId(taskId);
        syncTaskBuildSave(saveParam);
        return taskId;
    }

    //第三步保存
    private String threeStepSave(DataSyncSaveParam param) {
        String taskId = twoStepSave(param);
        TaskParamSaveParam saveParam = BeanCopyUtils.copyProperties(param, TaskParamSaveParam.class);
        saveParam.setTaskId(taskId);
        taskParamSave(saveParam);
        return taskId;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public String syncTaskDefineSave(DataSyncTaskDefineSaveParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setId(param.getTaskId());
        entity.setTaskStatus(EnableStatusEnum.find(param.getEnable()).getTaskStatus().getCode());
        saveOrUpdate(entity);
        return entity.getId();
    }

    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public void syncTaskBuildSave(DataSyncTaskBuildSaveParam param) {
        SyncWidetableEntity entity = new SyncWidetableEntity();
        entity.setSyncTaskId(param.getTaskId());
        entity.setName(param.getWideTableName());
        entity.setTargetSource(param.getTargetSource());
        entity.setPartitionField(param.getPartition());
        entity.setSqlStr(param.getWideTableSql());
        entity.setViewJson(JSONObject.toJSONString(param.getView()));
        entity.setVersion(param.getVersion());
        if (StringUtils.isNotBlank(param.getTaskId())) {
            Map<String, Object> columnMap = Maps.newHashMap();
            columnMap.put(SyncWidetableEntity.SYNC_TASK_ID, param.getTaskId());
            List<SyncWidetableEntity> entities = (List<SyncWidetableEntity>) syncWidetableService.listByMap(columnMap);
            if (CollectionUtils.isNotEmpty(entities)) {
                SyncWidetableEntity entity1 = entities.get(0);
                entity.setId(entity1.getId());
            }
        }
        syncWidetableService.saveOrUpdate(entity);

        List<WideTableFieldRequest> fieldInfos = param.getFieldInfos();
        if (CollectionUtils.isNotEmpty(fieldInfos)) {
            List<WideTableFieldSaveParam> saveParams = fieldInfos.parallelStream()
                    .filter(Objects::nonNull)
                    .map(info -> {
                        WideTableFieldSaveParam saveParam = new WideTableFieldSaveParam();
                        BeanCopyUtils.copyProperties(info, saveParam);
                        saveParam.setChineseName(info.getFieldChineseName());
                        saveParam.setWideTableId(entity.getId());
                        saveParam.setName(info.getFieldName());
                        saveParam.setDictKey(info.getAssociateDict());
                        saveParam.setType(info.getFieldType());
                        return saveParam;
                    }).collect(Collectors.toList());
            wideTableFieldSave(saveParams);
        }
    }

    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public void wideTableFieldSave(List<WideTableFieldSaveParam> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            List<SyncWidetableFieldEntity> collect = params.parallelStream()
                    .filter(Objects::nonNull)
                    .map(param -> {
                        SyncWidetableFieldEntity entity = new SyncWidetableFieldEntity();
                        BeanCopyUtils.copyProperties(param, entity);
                        entity.setSource(param.getSourceTable());
                        entity.setChinese(param.getChineseName());
                        return entity;
                    }).collect(Collectors.toList());
            Set<String> wideTableIds = params.parallelStream().filter(Objects::nonNull).map(WideTableFieldSaveParam::getWideTableId).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(wideTableIds)) {
                syncWidetableFieldService.deleteByWideTableIds(wideTableIds);
            }
            syncWidetableFieldService.saveOrUpdateBatch(collect);
        }
    }

    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public void taskParamSave(TaskParamSaveParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setId(param.getTaskId());
        entity.setCollectMode(param.getScheduleType());
        entity.setTaskStatus(TaskStatusEnum.DRAFT.getCode());
        TaskScheduleInfo info = BeanCopyUtils.copyProperties(param, TaskScheduleInfo.class);
        entity.setTaskParamJson(JSONObject.toJSONString(info));

        saveOrUpdate(entity);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult> syncTasks(DataSyncQueryParam param) {
        //查询条件
        SyncTaskConditionParam build = SyncTaskConditionParam.builder()
                .workspaceId(param.getWorkspaceId())
                .taskName(param.getTaskName())
                .taskStatus(TaskStatusEnum.find(param.getTaskStatus()))
                .execStatus(ExecStatusEnum.find(param.getExecStatus()))
                .build();
        //查询结果
        IPage<SyncTaskEntity> page = this.page(
                new Query<SyncTaskEntity>().getPage(param),
                queryWrapper(build)
        );
        //结果分页列表数据
        List<SyncTaskEntity> records = page.getRecords();

        IPage<SyncTaskInfo> resultPage = new Page<>();
        if (CollectionUtils.isNotEmpty(records)) {
            BeanCopyUtils.copyProperties(page, resultPage);
            //处理数据
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
            info.setEnable(TaskStatusEnum.find(byId.getTaskStatus()).getStatusEnum().getCode());
            info.setTaskId(byId.getId());
            info.setTaskDescribe(byId.getTaskDescribe());
            info.setCreateMode(byId.getCreateMode());
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
    @BusinessParamsValidate
    public BusinessResult<WideTable> generateWideTable(DataSyncGenerateWideTableParam param) {
        String sql;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            AssociatedFormatterVo vo = new AssociatedFormatterVo();
            vo.setDialect(param.getDialect());
            vo.setSourceTables(BeanCopyUtils.copy(param.getSourceTables(), TableInfo.class));
            vo.setAssoc(param.getView());
            sql = AssociatedUtil.wideTableSql(vo);
        } else {
            sql = param.getSql();
        }
        WideTable wideTable;
        try {
            wideTable = new WideTable();
//            wideTable.setTableName(RandomUtil.randomString(10) + DateUtil.now());
            ConnectionInfo info = getConnectionInfo(param.getDatasourceId());
            Connection connection = AssociatedUtil.getConnection(info);
            ResultSetMetaData metaData = AssociatedUtil.getResultSetMetaData(connection, sql);
            DatabaseMetaData databaseMetaData = AssociatedUtil.getDatabaseMetaData(connection);

            List<TableInfo> sourceTables = param.getSourceTables();
            List<ImmutablePair<String, String>> collect = Optional.ofNullable(sourceTables).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(ti -> {
                        ImmutablePair<String, String> pair = new ImmutablePair(ti.getDatabase(), ti.getTableName());
                        return pair;
                    })
                    .collect(Collectors.toList());

            Map<String, String> tableFieldRemark = getTableFieldRemark(databaseMetaData, collect);

            int columnCount = metaData.getColumnCount();
            List<WideTableFieldInfo> fieldInfos = Lists.newArrayList();
            for (int i = 1; i <= columnCount; i++) {
                WideTableFieldResult fieldInfo = new WideTableFieldResult();
                fieldInfo.setSort(i);
                fieldInfo.setFieldName(metaData.getColumnName(i));
                HiveMapJdbcTypeEnum typeEnum = HiveMapJdbcTypeEnum.find(metaData.getColumnTypeName(i));
                fieldInfo.setFieldType(Lists.newArrayList(typeEnum.getCategoryEnum().getCode(), typeEnum.getHiveType()));
                fieldInfo.setSourceTable(metaData.getTableName(i));
                String key = new StringJoiner(".").add(metaData.getSchemaName(i)).add(metaData.getTableName(i)).add(metaData.getColumnName(i)).toString();
                fieldInfo.setFieldChineseName(tableFieldRemark.get(key));
                fieldInfos.add(fieldInfo);
            }
            wideTable.setFields(fieldInfos);
        } catch (Exception e) {
            log.error("识别宽表失败", e);
            throw new AppException("60000020");
        } finally {

        }
        wideTable.setSql(sql);
        wideTable.setPartitions(Arrays.stream(PartitionTypeEnum.values()).map(e -> new WideTable.Select(e.getName(), e.getName())).collect(Collectors.toList()));
        return BusinessResult.success(wideTable);
    }

    private ConnectionInfo getConnectionInfo(String datasourceId) {
        FeignConnectionInfoRequest build = FeignConnectionInfoRequest.builder()
                .datasourceId(datasourceId)
                .build();
        BusinessResult<ConnectionInfo> connectionInfo = datasourceFeign.getConnectionInfo(build);
        if (connectionInfo.isSuccess() && Objects.nonNull(connectionInfo.getData())) {
            return connectionInfo.getData();
        }
        throw new AppException("60000006");
    }

    private List<WideTableFieldInfo> transferToWideTableFieldInfo(List<SyncWidetableFieldEntity> entities) {
        List<WideTableFieldInfo> results = null;
        if (CollectionUtils.isNotEmpty(entities)) {
            results = entities.parallelStream()
                    .map(entity -> {
                        WideTableFieldInfo info = new WideTableFieldInfo();
                        BeanCopyUtils.copyProperties(entity, info);
                        info.setFieldChineseName(entity.getChinese());
                        info.setSourceTable(entity.getSource());
                        info.setAssociateDict(entity.getDictKey());
                        info.setFieldName(entity.getName());
                        info.setRemark(entity.getRemark());
                        return info;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @Override
    public BusinessResult<Boolean> stop(DataSyncExecParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setTaskStatus(TaskStatusEnum.DISABLE.getCode());
        updateById(entity);
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<Boolean> remove(DataSyncExecParam param) {
        removeById(param.getTaskId());
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<Boolean> enable(DataSyncExecParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setTaskStatus(TaskStatusEnum.ENABLE.getCode());
        updateById(entity);
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<Boolean> run(DataSyncExecParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setExecStatus(ExecStatusEnum.EXEC.getCode());
        updateById(entity);
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<Boolean> cease(DataSyncExecParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setExecStatus(ExecStatusEnum.FAILURE.getCode());
        updateById(entity);
        return BusinessResult.success(true);
    }

    private Map<String, String> getTableFieldRemark(DatabaseMetaData metaData, List<ImmutablePair<String, String>> pairs) throws Exception {
        Map<String, String> results = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(pairs)) {
            for (ImmutablePair<String, String> pair : pairs) {
                ResultSet columnResultSet = metaData.getColumns(null, "%", pair.getRight(), "%");
                while (columnResultSet.next()) {
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    if (StringUtils.isNotBlank(columnName)) {
                        String key = new StringJoiner(".").add(pair.getLeft()).add(pair.getRight()).add(columnName).toString();
                        // 描述
                        String remarks = columnResultSet.getString("REMARKS");
                        results.put(key, remarks);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 创建宽表
     *
     * @param param
     */
    private void createWideTable(CreateWideTableParam param) {
        String wideTableName = param.getWideTableName();
        List<WideTableFieldRequest> fieldInfos = param.getFieldInfos();
        String targetSource = param.getTargetSource();
        if (StringUtils.isBlank(wideTableName)) {
            throw new AppException("60000002");
        }
        if (StringUtils.isBlank(targetSource)) {
            throw new AppException("60000001");
        }
        if (CollectionUtils.isEmpty(fieldInfos)) {
            throw new AppException("60000014");
        }
        FeignMetadataGenerateWideTableRequest feignRequest = new FeignMetadataGenerateWideTableRequest();
        feignRequest.setWideTableName(wideTableName);
        feignRequest.setPartition(param.getPartition());
        feignRequest.setDatabaseName(targetSource);
        feignRequest.setDelimiter(DefaultDelimiterEnum.TABS.getSymbol());

        Set<String> checkDuplicate = new HashSet<>();
        List<StatementField> statementFields = fieldInfos.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(WideTableFieldRequest::getSort))
                .map(info -> {
                    StatementField field = new StatementField();
                    if (checkDuplicate.contains(info.getFieldName())) {
                        field.setFieldName(new StringJoiner("_").add(info.getSourceTable()).add(info.getFieldName()).toString());
                        checkDuplicate.add(info.getFieldName());
                    } else {
                        field.setFieldName(info.getFieldName());
                        checkDuplicate.add(info.getFieldName());
                    }
                    field.setFieldType(info.getFieldType());
                    return field;
                }).collect(Collectors.toList());
        feignRequest.setFieldList(statementFields);
        metadataFeign.generateWideTable(feignRequest);
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
        if (Objects.nonNull(param.getTaskStatus()) && !TaskStatusEnum.ALL.equals(param.getTaskStatus())) {
            wrapper.eq(SyncTaskEntity.TASK_STATUS, param.getTaskStatus().getCode());
        }
        if (Objects.nonNull(param.getExecStatus()) && !ExecStatusEnum.ALL.equals(param.getExecStatus())) {
            wrapper.eq(SyncTaskEntity.EXEC_STATUS, param.getExecStatus().getCode());
        }
        wrapper.orderByAsc(SyncTaskEntity.TASK_STATUS);
        wrapper.orderByDesc(SyncTaskEntity.LAST_SCHEDULING_TIME);
        return wrapper;
    }
}
