package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.container.GenerateWideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.GenerateWideTableContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DataSyncDispatchTaskPageDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.MetadataFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.SchedulerFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.SystemFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.*;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.WarehouseInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncTaskMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableFieldService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncWidetableService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.mysql.HdfsWriterEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.mysql.MySqlReaderEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.mysql.MysqlReaderConfigParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.SyncTimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.TimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncGenerateWideTableRequest;
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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private Parser<String, SyncCondition> syncConditionParser;
    @Resource
    private MetadataFeign metadataFeign;
    @Resource
    private SyncTaskMapper syncTaskMapper;
    @Resource
    private SchedulerFeign schedulerFeign;
    @Resource
    private SystemFeign systemFeign;
    @Resource
    private DatasourceFeign datasourceFeign;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param) {
        String taskId = null;
        if (CallStepEnum.ONE == CallStepEnum.find(param.getCallStep())) {
            param.setTaskStatus(TaskStatusEnum.DRAFT.getCode());
            taskId = oneStepSave(param);
        }
        if (CallStepEnum.TWO == CallStepEnum.find(param.getCallStep())) {
            param.setTaskStatus(TaskStatusEnum.DRAFT.getCode());
            taskId = twoStepSave(param);
        }
        if (CallStepEnum.THREE == CallStepEnum.find(param.getCallStep())) {
            param.setTaskStatus(TaskStatusEnum.DRAFT.getCode());
            taskId = threeStepSave(param);
        }
        if (CallStepEnum.FOUR == CallStepEnum.find(param.getCallStep())) {
            param.setTaskStatus(TaskStatusEnum.find(EnableStatusEnum.find(param.getEnable())).getCode());
//            param.setExecStatus(ExecStatusEnum.SUCCESS.getCode());
            taskId = threeStepSave(param);
            if (StringUtils.isBlank(param.getTaskId())) {
                FeignCreatePlatformProcessDefinitionRequest build = FeignCreatePlatformProcessDefinitionRequest.builder()
                        .accessUser(new User())
                        .channelControl(new ChannelControlParam(param.getMaxThread(), param.isLimit(), param.getLimitRate()))
                        .schedulerParam(new SchedulerParam(param.getScheduleType(), param.getCron()))
                        .ordinaryParam(new PlatformTaskOrdinaryParam(param.getTaskName(), "icredit", taskId, buildTaskJson(taskId, param.getSql()), 0))
                        .build();
                String processDefinitionId = schedulerFeign.create(build);
                SyncTaskEntity updateEntity = new SyncTaskEntity();
                updateEntity.setId(taskId);
                updateEntity.setScheduleId(processDefinitionId);
                syncTaskMapper.updateById(updateEntity);
            } else {
                FeignUpdatePlatformProcessDefinitionRequest build = FeignUpdatePlatformProcessDefinitionRequest.builder()
                        .accessUser(new User())
                        .channelControl(new ChannelControlParam(param.getMaxThread(), param.isLimit(), param.getLimitRate()))
                        .schedulerParam(new SchedulerParam(param.getScheduleType(), param.getCron()))
                        .ordinaryParam(new PlatformTaskOrdinaryParam(param.getTaskName(), "icredit", taskId, "{}", 0))
                        .build();
                schedulerFeign.update(build);
            }

            CreateWideTableParam wideTableParam = BeanCopyUtils.copyProperties(param, CreateWideTableParam.class);
            if (Objects.nonNull(param.getSyncCondition())) {
                wideTableParam.setPartition(param.getSyncCondition().getPartition());
            }
            //创建宽表
            createWideTable(wideTableParam);
        }
        return BusinessResult.success(new ImmutablePair("taskId", taskId));
    }

    /**
     * 构建dataxjson
     *
     * @param taskId
     * @return
     */
    private String buildTaskJson(String taskId, String sql) {
        Map<String, String> transferColumnsByTaskId = findTransferColumnsByTaskId(taskId);
        List<DictInfo> dictInfos = null;
        if (MapUtil.isNotEmpty(transferColumnsByTaskId)) {
            Collection<String> values = transferColumnsByTaskId.values();
            dictInfos = findDictInfos(values);
        }

        MysqlReaderConfigParam readerConfigParam = findReaderConfigParam(taskId, sql);
        MySqlReaderEntity mySqlReaderEntity = new MySqlReaderEntity(transferColumnsByTaskId, dictInfos, readerConfigParam);

        HdfsWriterConfigParam hdfsWriterConfigParam = findHdfsWriterConfigParam(taskId);
        List<Column> wideTableColumns = getWideTableColumns(taskId);

        HdfsWriterEntity hdfsWriterEntity = new HdfsWriterEntity(wideTableColumns, hdfsWriterConfigParam);

        Map<String, Object> taskConfig = DataxJsonEntity.builder()
                .reader(mySqlReaderEntity)
                .writer(hdfsWriterEntity)
                .setting(getDataxSetting())
                .build().buildDataxJson();
        return JSONObject.toJSONString(taskConfig);
    }

    private String findDataSourceByDatabaseName(String database) {
        String dataSourceId = null;
        FeignDataSourcesRequest feignRequest = new FeignDataSourcesRequest();
        feignRequest.setDatabaseName(database);
        BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(feignRequest);
        if (dataSources.isSuccess() && CollectionUtils.isNotEmpty(dataSources.getData())) {
            dataSourceId = dataSources.getData().get(0).getId();
        }
        return dataSourceId;
    }

    private String parseDatabaseNameFromSql(String sql) {
        String from = StrUtil.subAfter(sql, "from", true);
        String databaseTable = StrUtil.subBefore(StrUtil.trim(from), " ", false);
        String database = StrUtil.subBefore(databaseTable, ".", false);
        return database;
    }

    private Map<String, Object> getDataxSetting() {
        Map<String, Object> result = Maps.newHashMap();

        Map<String, Object> speed = Maps.newHashMap();
        speed.put("channel", 1);
        result.put("speed", speed);
        return result;
    }

    private List<Column> getWideTableColumns(String taskId) {
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId, null);
        if (Objects.isNull(wideTableField)) {
            throw new AppException("60000030");
        }
        String wideTableId = wideTableField.getId();
        List<SyncWidetableFieldEntity> wideTableFields = syncWidetableFieldService.getWideTableFields(wideTableId);
        List<Column> results = null;
        results = Optional.ofNullable(wideTableFields).orElse(Lists.newArrayList())
                .stream()
                .filter(Objects::nonNull)
                .map(entity -> {
                    Column column = new Column();
                    column.setName(entity.getName());
                    column.setType(entity.getType());
                    return column;
                }).collect(Collectors.toList());
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private HdfsWriterConfigParam findHdfsWriterConfigParam(String taskId) {
        HdfsWriterConfigParam param = new HdfsWriterConfigParam();
        BusinessResult<WarehouseInfo> warehouseInfo = metadataFeign.getWarehouseInfo();
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId, null);
        if (Objects.isNull(wideTableField)) {
            throw new AppException("60000032");
        }
        if (warehouseInfo.isSuccess()) {
            WarehouseInfo data = warehouseInfo.getData();
            param.setDefaultFs(data.getDefaultFS());
            param.setFileName(wideTableField.getName());
            param.setPassWord(data.getPassWord());
            param.setUser(data.getUser());
            param.setThriftUrl(data.getThriftUrl());

            String syncCondition = wideTableField.getSyncCondition();

            String partition = null;
            if (StringUtils.isNotBlank(syncCondition)) {
                SyncCondition parse = syncConditionParser.parse(syncCondition);
                TimeInterval interval = new TimeInterval();
                SyncTimeInterval syncTimeInterval = interval.getSyncTimeInterval(parse, n -> true);
                param.setPath(getDataxSyncPath(data.getWarehouse(), wideTableField.getName(), wideTableField.getTargetSource(), syncTimeInterval.getTimeFormat()));
                partition = parse.getPartition();
            }
            param.setPartition(partition);
        } else {
            throw new AppException("60000031");
        }
        return param;
    }

    private String getDataxSyncPath(String basePath, String wideTableName, String database, String partitionDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(basePath);
        if (basePath.endsWith("/")) {
            sb.append(database);
        } else {
            sb.append("/");
            sb.append(database);
        }
        sb.append(".db/");
        sb.append(wideTableName);
        sb.append("/");
        sb.append(partitionDir);
        return sb.toString();
    }

    private MysqlReaderConfigParam findReaderConfigParam(String taskId, String sql) {
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId, null);
        if (Objects.isNull(wideTableField)) {
            throw new AppException("60000030");
        }
        String datasourceId = wideTableField.getDatasourceId();
        if (StringUtils.isBlank(datasourceId)) {
            String database = parseDatabaseNameFromSql(sql);
            if (StringUtils.isBlank(database)) {
                throw new AppException("60000033");
            }
            datasourceId = findDataSourceByDatabaseName(database);
        }
        if (StringUtils.isBlank(datasourceId)) {
            throw new AppException("60000033");
        }
        BusinessResult<MysqlReaderConfigParam> datasourceJdbcInfo = datasourceFeign.getDatasourceJdbcInfo(datasourceId);

        MysqlReaderConfigParam data = null;
        if (datasourceJdbcInfo.isSuccess()) {
            data = datasourceJdbcInfo.getData();
            data.setQuerySql(wideTableField.getSqlStr());
        }
        return Optional.ofNullable(data).orElse(new MysqlReaderConfigParam());
    }

    /**
     * 查询配置字典的列
     *
     * @param taskId
     * @return
     */
    private Map<String, String> findTransferColumnsByTaskId(String taskId) {
        Map<String, String> results = Maps.newConcurrentMap();
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId, null);
        if (Objects.nonNull(wideTableField)) {
            List<SyncWidetableFieldEntity> wideTableFields = syncWidetableFieldService.getWideTableFields(wideTableField.getId());
            if (CollectionUtils.isNotEmpty(wideTableFields)) {
                wideTableFields.parallelStream()
                        .filter(Objects::nonNull)
                        .filter(entity -> StringUtils.isNotBlank(entity.getDictKey()))
                        .forEach(entity -> {
                            results.put(entity.getName(), entity.getDictKey());
                        });
            }
        }
        return results;
    }

    /**
     * 获取字典信息
     *
     * @param keys
     * @return
     */
    private List<DictInfo> findDictInfos(Collection<String> keys) {
        BusinessResult<List<DictInfo>> dictInfoByTypes = systemFeign.getDictInfoByTypes(keys);
        if (dictInfoByTypes.isSuccess()) {
            return dictInfoByTypes.getData();
        } else {
            throw new AppException("60000029");
        }
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
        saveParam.setWideTableSql(param.getSql());
        saveParam.setSourceType(param.getSourceType());
        syncTaskBuildSave(saveParam);
        return taskId;
    }

    //第三步保存
    private String threeStepSave(DataSyncSaveParam param) {
        String taskId = twoStepSave(param);
        TaskParamSaveParam saveParam = BeanCopyUtils.copyProperties(param, TaskParamSaveParam.class);
        saveParam.setTaskId(taskId);
        saveParam.setSyncMode(SyncModeEnum.INC.getCode());
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
        saveOrUpdate(entity);
        return entity.getId();
    }

    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public void syncTaskBuildSave(DataSyncTaskBuildSaveParam param) {
        if (StringUtils.isBlank(param.getWideTableName())) {
            throw new AppException("60000002");
        }
        if (StringUtils.isBlank(param.getTargetSource())) {
            throw new AppException("60000001");
        }
        if (CollectionUtils.isEmpty(param.getFieldInfos())) {
            throw new AppException("60000014");
        }
        SyncWidetableEntity entity = new SyncWidetableEntity();
        entity.setSyncTaskId(param.getTaskId());
        entity.setName(param.getWideTableName());
        entity.setTargetSource(param.getTargetSource());
        entity.setSyncCondition(JSONObject.toJSONString(param.getSyncCondition()));
        entity.setSqlStr(param.getWideTableSql());
        entity.setViewJson(JSONObject.toJSONString(param.getView()));
        entity.setVersion(param.getVersion());
        entity.setSourceType(param.getSourceType());
        entity.setSourceTables(JSONObject.toJSONString(param.getSourceTables()));
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
            SyncWidetableEntity wideTable = syncWidetableService.getWideTableField(id, version);
            if (Objects.nonNull(wideTable)) {
                info = new TaskBuildInfo();
                info.setWideTableName(wideTable.getName());
                info.setSourceType(wideTable.getSourceType());
                info.setSyncCondition(syncConditionParser.parse(wideTable.getSyncCondition()));
                info.setTargetSource(wideTable.getTargetSource());
                info.setSql(wideTable.getSqlStr());

                List<TableInfo> tableInfos = JSONArray.parseArray(wideTable.getSourceTables(), TableInfo.class);
                if (CollectionUtils.size(tableInfos) == 1) {
                    TableInfo tableInfo = tableInfos.get(0);
                    AssociatedData data = new AssociatedData();
                    data.setLeftSourceDatabase(tableInfo.getDatabase());
                    data.setLeftSource(tableInfo.getTableName());
                    info.setView(Lists.newArrayList(data));
                } else {
                    info.setView(fileAssociatedParser.parse(wideTable.getViewJson()));
                }
                List<SyncWidetableFieldEntity> wideTableFields = syncWidetableFieldService.getWideTableFields(wideTable.getId());
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
        //根据参数确定源库类型
        GenerateWideTable generateWideTable = GenerateWideTableContainer.find(param);
        if (Objects.isNull(generateWideTable)) {
            throw new AppException("60000025");
        }
        //取得宽表sql
        String wideTableSql = generateWideTable.getWideTableSql(param);
        log.info("取得宽表的sql语句", wideTableSql);
        //校验sql语法
        generateWideTable.verifySql(wideTableSql, param);

        WideTable wideTable = new WideTable();
        if (CreateModeEnum.SQL == CreateModeEnum.find(param.getCreateMode()) && CollectionUtils.isEmpty(param.getSqlInfo().getDatabaseHost())) {
            List<DataSyncGenerateWideTableRequest.DatabaseInfo> databaseInfos = generateWideTable.checkDatabaseFromSql(wideTableSql);
            log.info("相同数据库信息", JSONObject.toJSONString(databaseInfos));
            //如何不同主机有相同数据库则返回给用户选择
            if (CollectionUtils.isNotEmpty(databaseInfos)) {
                wideTable.setSameNameDataBase(databaseInfos);
                wideTable.setSql(wideTableSql);
            } else {
                //取得数据源ID
                String dataSourceId = generateWideTable.getDataSourceId(wideTableSql, param);
                log.info("数据源ID", dataSourceId);
                //生成宽表数据列
                try {
                    wideTable = generateWideTable.generate(wideTableSql, dataSourceId);
                } catch (Exception e) {
                    throw new AppException("60000027");
                }
            }
        } else {
            //取得数据源ID
            String dataSourceId = generateWideTable.getDataSourceId(wideTableSql, param);
            log.info("数据源ID", dataSourceId);
            //生成宽表数据列
            wideTable = generateWideTable.generate(wideTableSql, dataSourceId);
        }
        return BusinessResult.success(wideTable);
    }

    private List<WideTableFieldResult> transferToWideTableFieldInfo(List<SyncWidetableFieldEntity> entities) {
        List<WideTableFieldResult> results = null;
        if (CollectionUtils.isNotEmpty(entities)) {
            results = entities.parallelStream()
                    .map(entity -> {
                        WideTableFieldResult info = new WideTableFieldResult();
                        BeanCopyUtils.copyProperties(entity, info);
                        info.setFieldChineseName(entity.getChinese());
                        info.setSourceTable(entity.getSource());
                        info.setAssociateDict(entity.getDictKey());
                        info.setFieldType(Arrays.asList(HiveMapJdbcTypeEnum.find(entity.getType()).getCategoryEnum().getCode(), entity.getType()));
                        info.setFieldName(entity.getName());
                        info.setRemark(entity.getRemark());
                        return info;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> stop(DataSyncExecParam param) {
        checkTaskId(param.getTaskId());
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        String processDefinitionId = getProcessDefinitionIdById(param.getTaskId());
        String result = schedulerFeign.stopSyncTask(processDefinitionId);
        if ("true".equals(result)) {
            entity.setTaskStatus(TaskStatusEnum.DISABLE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> remove(DataSyncExecParam param) {
        checkTaskId(param.getTaskId());
        String processDefinitionId = getProcessDefinitionIdById(param.getTaskId());
        String result = schedulerFeign.deleteSyncTask(processDefinitionId);
        if ("1".equals(result)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.message);
        }
        removeById(param.getTaskId());
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> enable(DataSyncExecParam param) {
        checkTaskId(param.getTaskId());
        String processDefinitionId = getProcessDefinitionIdById(param.getTaskId());
        String enableResult = schedulerFeign.enableSyncTask(processDefinitionId);
        if ("true".equals(enableResult)){
            SyncTaskEntity entity = new SyncTaskEntity();
            entity.setId(param.getTaskId());
            entity.setTaskStatus(TaskStatusEnum.ENABLE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> run(DataSyncExecParam param) {
        checkTaskId(param.getTaskId());
        if (0 != param.getExecType() && 1 != param.getExecType()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000028.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000028.message);
        }
        SyncTaskEntity syncTask = syncTaskMapper.selectById(param.getTaskId());
        if(ExecStatusEnum.EXEC.getCode() == syncTask.getExecStatus()){//“执行中” 状态
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.message);
        }
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setExecStatus(ExecStatusEnum.EXEC.getCode());
        updateById(entity);//执行中

        String processDefinitionId = getProcessDefinitionIdById(param.getTaskId());
        String result = schedulerFeign.execSyncTask(processDefinitionId, param.getExecType());
        if ("true".equals(result)) {//成功
            return BusinessResult.success(true);
        } else {//失败
            return BusinessResult.fail("", "执行失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> cease(DataSyncExecParam param) {
        checkTaskId(param.getTaskId());
        SyncTaskEntity syncTask = syncTaskMapper.selectById(param.getTaskId());
        if(ExecStatusEnum.EXEC.getCode() != syncTask.getExecStatus()){//不是 “执行中” 状态
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000034.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000034.message);
        }
        String processDefinitionId = getProcessDefinitionIdById(param.getTaskId());
        String ceaseResult = schedulerFeign.ceaseSyncTask(processDefinitionId);
        if("true".equals(ceaseResult)){
            SyncTaskEntity entity = new SyncTaskEntity();
            entity.setId(param.getTaskId());
            entity.setExecStatus(ExecStatusEnum.FAILURE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    private void checkTaskId(String taskId){
        if(StringUtils.isEmpty(taskId)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.message);
        }
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
        feignRequest.setDelimiter(DefaultDelimiterEnum.COMMA.getSymbol());

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

    @Override
    public BusinessResult<BusinessPageResult<DataSyncDispatchTaskPageResult>> dispatchPage(DataSyncDispatchTaskPageParam param) {
        DataSyncDispatchTaskPageDTO dispatchPageDTO = new DataSyncDispatchTaskPageDTO();
        BeanUtils.copyProperties(param, dispatchPageDTO);
        dispatchPageDTO.setPageNum((dispatchPageDTO.getPageNum() - 1) * dispatchPageDTO.getPageSize());
        long dispatchCount = syncTaskMapper.countDispatch(dispatchPageDTO);
        List<DataSyncDispatchTaskPageResult> dispatchList = syncTaskMapper.dispatchList(dispatchPageDTO);
        for (DataSyncDispatchTaskPageResult dataSyncDispatchTaskPageResult : dispatchList) {
            if (StringUtils.isNotEmpty(dataSyncDispatchTaskPageResult.getDispatchPeriod())) {
                JSONObject obj = (JSONObject) JSONObject.parse(dataSyncDispatchTaskPageResult.getDispatchPeriod());
                dataSyncDispatchTaskPageResult.setDispatchPeriod(obj.getString("cron"));//执行周期
            }
            if (StringUtils.isNotEmpty(dataSyncDispatchTaskPageResult.getDispatchType())) {//调度类型
                dataSyncDispatchTaskPageResult.setDispatchType(CollectModeEnum.find(Integer.valueOf(dataSyncDispatchTaskPageResult.getDispatchType())).getDesc());
            }
        }
        return BusinessResult.success(BusinessPageResult.build(dispatchList, param, dispatchCount));
    }

    @Override
    public String getProcessDefinitionIdById(String id) {
        SyncTaskEntity syncTask = syncTaskMapper.selectById(id);
        return syncTask == null ? null : syncTask.getScheduleId();
    }
}
