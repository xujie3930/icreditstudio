package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.container.DataSyncQuery;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.DataSyncQueryContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.QueryField;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.QueryStatementParseContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.QueryStatementParseHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside.OutsideGenerateWideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside.OutsideGenerateWideTableContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DataSyncDispatchTaskPageDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.*;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.MetadataFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.SchedulerFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.SystemFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.*;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.CreatePlatformTaskResult;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.WarehouseInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.SyncTaskMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.increment.IncrementUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.DataxJsonEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.DataxReader;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.DataxReaderContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer.hdfs.HdfsWriterConfigParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.writer.hdfs.HdfsWriterEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.SyncTimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.TimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.CronParam;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author peng
 */
@Slf4j
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    @Resource
    private SyncTaskHiService syncTaskHiService;
    @Resource
    private SyncWidetableService syncWidetableService;
    @Resource
    private SyncWidetableHiService syncWidetableHiService;
    @Resource
    private SyncWidetableFieldService syncWidetableFieldService;
    @Resource
    private SyncWidetableFieldHiService syncWidetableFieldHiService;
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
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private DictService dictService;
    @Resource
    private DictColumnService dictColumnService;

    @Override
    public BusinessResult<ImmutablePair> checkRepeatTaskName(DataSyncSaveParam param) {
        //同工作空间下任务名称不能重复
        QueryWrapper<SyncTaskEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(SyncTaskEntity.WORKSPACE_ID, param.getWorkspaceId());
        wrapper.eq(SyncTaskEntity.TASK_NAME, param.getTaskName());
        if (CollectionUtils.isNotEmpty(list(wrapper))) {
            return BusinessResult.success(new ImmutablePair<>("isRepeat", true));
        }
        return BusinessResult.success(new ImmutablePair<>("isRepeat", false));
    }

    /**
     * 同步任务  新增/编辑
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param) {
        String taskId = null;
        if (CallStepEnum.ONE == CallStepEnum.find(param.getCallStep())) {
            SyncStepOneParam oneParam = new SyncStepOneParam();
            BeanCopyUtils.copyProperties(param, oneParam);
            oneParam.setUserId(param.getUserId());
            oneParam.setTaskStatus(TaskStatusEnum.DRAFT.getCode());
            taskId = stepOneSave(oneParam);
        }
        if (CallStepEnum.TWO == CallStepEnum.find(param.getCallStep())) {
            SyncStepTwoParam twoParam = new SyncStepTwoParam();
            BeanCopyUtils.copyProperties(param, twoParam);
            taskId = stepTwoSave(twoParam);
        }
        if (CallStepEnum.THREE == CallStepEnum.find(param.getCallStep())) {
            SyncStepThreeParam threeParam = new SyncStepThreeParam();
            BeanCopyUtils.copyProperties(param, threeParam);
            taskId = stepThreeSave(threeParam);
        }
        if (CallStepEnum.FOUR == CallStepEnum.find(param.getCallStep())) {
            SyncStepThreeParam threeParam = new SyncStepThreeParam();
            BeanCopyUtils.copyProperties(param, threeParam);
            taskId = stepThreeSave(threeParam);
            publish(taskId, param.getUserId());
        }
        return BusinessResult.success(new ImmutablePair("taskId", taskId));
    }

    /**
     * 同步任务第一步保存(新增/更新)
     *
     * @param param
     * @return
     */
    private String stepOneSave(SyncStepOneParam param) {
        stepOnePreValid(param);

        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setTaskName(param.getTaskName());
        entity.setEnable(param.getEnable());
        entity.setCreateMode(param.getCreateMode());
        entity.setWorkspaceId(param.getWorkspaceId());
        entity.setTaskDescribe(param.getTaskDescribe());
        entity.setTaskStatus(param.getTaskStatus());
        if (StringUtils.isBlank(param.getTaskId())) {
            entity.setCreateUserId(param.getUserId());
            entity.setVersion(1);
        }
        entity.setLastUpdateUserId(param.getUserId());
        if (StringUtils.isNotBlank(param.getTaskId())) {
            SyncTaskEntity taskEntity = getSyncTaskEntityById(param.getTaskId());
            if (!TaskStatusEnum.DRAFT.getCode().equals(taskEntity.getTaskStatus())) {
                entity.setVersion(taskEntity.getVersion() + 1);
            }
        }
        saveOrUpdate(entity);
        return entity.getId();
    }

    private void stepOnePreValid(SyncStepOneParam param) {
        //工作空间ID不能为空
        if (StringUtils.isBlank(param.getWorkspaceId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000000.getCode());
        }
        //任务名称不能为空
        if (StringUtils.isBlank(param.getTaskName())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        if (StringUtils.isBlank(param.getTaskId())) {
            //同工作空间下任务名称不能重复
            QueryWrapper<SyncTaskEntity> wrapper = new QueryWrapper<>();
            wrapper.eq(SyncTaskEntity.WORKSPACE_ID, param.getWorkspaceId());
            wrapper.eq(SyncTaskEntity.TASK_NAME, param.getTaskName());
            if (CollectionUtils.isNotEmpty(list(wrapper))) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000058.getCode());
            }
        }
        //启用状态不能为空
        if (Objects.isNull(param.getEnable())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000010.getCode());
        }
        //创建方式不能为空
        if (Objects.isNull(param.getCreateMode())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000011.getCode());
        }
    }

    /**
     * 同步任务第二步保存(新增/更新)
     *
     * @param param
     * @return
     */
    private String stepTwoSave(SyncStepTwoParam param) {
        //同步任务新增/编辑前置参数校验
        stepTwoPreValid(param);
        SyncWidetableEntity widetableEntity = syncWidetableService.getWideTableByTaskId(param.getTaskId());
        boolean isNew = true;
        if (Objects.nonNull(widetableEntity)) {
            isNew = false;
        }
        SyncWidetableEntity entity = new SyncWidetableEntity();
        if (!isNew) {
            entity.setId(widetableEntity.getId());
        }
        entity.setSyncTaskId(param.getTaskId());

        List<QueryField> queryFields = transferQueryField(param.getFieldInfos());
        DataSyncQuery matching = DataSyncQueryContainer.matching(param.getSql());
        String querySql = matching.querySql(queryFields, param.getSql());

//        entity.setSqlStr(querySql);
        entity.setSqlStr(param.getSql());
        entity.setViewJson(JSONObject.toJSONString(param.getView()));
        //前置操作是识别宽表,dialect必然存在
        entity.setDialect(param.getDialect());
        //前置操作是识别宽表,datasourceId必然存在
        entity.setDatasourceId(param.getDatasourceId());
        entity.setTargetSource(param.getTargetSource());
        entity.setSourceType(param.getSourceType());
        entity.setSourceTables(JSONObject.toJSONString(param.getSourceTables()));

        SyncCondition syncCondition = param.getSyncCondition();
        syncCondition.setDialect(param.getDialect());
        entity.setSyncCondition(JSONObject.toJSONString(param.getSyncCondition()));
        entity.setName(param.getWideTableName());
        if (StringUtils.isBlank(entity.getId())) {
            entity.setCreateUserId(param.getUserId());
        }
        entity.setLastUpdateUserId(param.getUserId());

        SyncTaskEntity task = getSyncTaskEntityById(param.getTaskId());
        entity.setVersion(task.getVersion());
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
                        saveParam.setUserId(param.getUserId());
                        saveParam.setDatabaseName(info.getDatabaseName());
                        saveParam.setVersion(task.getVersion());
                        return saveParam;
                    }).collect(Collectors.toList());
            wideTableFieldSave(saveParams);
        }
        return param.getTaskId();
    }

    private List<QueryField> transferQueryField(List<WideTableFieldRequest> fieldInfos) {
        return Optional.ofNullable(fieldInfos).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(field -> {
                    QueryField queryField = new QueryField();
                    queryField.setFieldName(field.getFieldName());
                    queryField.setDatabaseName(field.getDatabaseName());
                    queryField.setSourceTable(field.getSourceTable());
                    return queryField;
                }).collect(Collectors.toList());
    }

    /**
     * 同步任务新增/编辑前置参数校验
     *
     * @param param
     */
    private void stepTwoPreValid(SyncStepTwoParam param) {
        if (StringUtils.isBlank(param.getTaskId())) {
            //任务ID为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.getCode());
        }
        if (StringUtils.isBlank(param.getWideTableName())) {
            //宽表名称为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000002.getCode());
        }
        if (StringUtils.isBlank(param.getTargetSource())) {
            //目标库名称为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000001.getCode());
        }
        if (CollectionUtils.isEmpty(param.getFieldInfos())) {
            //宽表字段为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000014.getCode());
        }
        if (StringUtils.isBlank(param.getSql())) {
            //生成宽表sql为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000024.getCode());
        }
        if (StringUtils.isBlank(param.getDialect())) {
            //数据源方言为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.getCode());
        }
        if (StringUtils.isBlank(param.getDatasourceId())) {
            //数据源ID为空
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000003.getCode());
        }
        if (Objects.isNull(param.getSourceType())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000062.getCode());
        }
    }

    /**
     * 同步任务第三步保存/更新
     *
     * @param param
     */
    private String stepThreeSave(SyncStepThreeParam param) {
        stepThreePreValid(param);

        SyncTaskEntity entity = new SyncTaskEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setId(param.getTaskId());
        entity.setCollectMode(param.getScheduleType());

        SyncWidetableEntity wideTableEntity = syncWidetableService.getWideTableByTaskId(param.getTaskId());
        String syncConditionJson = wideTableEntity.getSyncCondition();
        SyncCondition syncCondition = null;
        if (StringUtils.isNotBlank(syncConditionJson) && JSONUtil.isJson(syncConditionJson)) {
            syncCondition = JSONObject.parseObject(syncConditionJson).toJavaObject(SyncCondition.class);
        }
        if (Objects.nonNull(syncCondition)) {
            if (StringUtils.isNotBlank(param.getCronParam().getCron())) {
                syncCondition = IncrementUtil.getSyncCondition(syncCondition, param.getCronParam().getCron());
                wideTableEntity.setSyncCondition(JSONObject.toJSONString(syncCondition));
                syncWidetableService.saveOrUpdate(wideTableEntity);
            }
            if (StringUtils.isNotBlank(syncCondition.getIncrementalField())) {
                entity.setSyncMode(SyncModeEnum.INC.getCode());
            } else {
                entity.setSyncMode(SyncModeEnum.FULL.getCode());
            }
        }
        TaskScheduleInfo info = BeanCopyUtils.copyProperties(param, TaskScheduleInfo.class);
        entity.setTaskParamJson(JSONObject.toJSONString(info));
        entity.setCronParam(JSONObject.toJSONString(param.getCronParam()));
        saveOrUpdate(entity);
        return param.getTaskId();
    }

    /**
     * 同步任务调度 新增/编辑前置参数校验
     *
     * @param param
     */
    private void stepThreePreValid(SyncStepThreeParam param) {
        //任务ID为空
        if (StringUtils.isBlank(param.getTaskId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.getCode());
        }
        //最大并发数为空
        if (Objects.isNull(param.getMaxThread())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000070.getCode());
        }
        //限流类型不能为空
        if (Objects.isNull(param.getSyncRate())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000071.getCode());
        } else {
            if (SyncRateEnum.LIMIT.getCode().equals(param.getSyncRate()) && Objects.isNull(param.getLimitRate())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000074.getCode());
            }
        }
        //调度类型
        if (Objects.isNull(param.getScheduleType())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000072.getCode());
        } else {
            if (CollectModeEnum.CYCLE.getCode().equals(param.getScheduleType())) {
                if (Objects.isNull(param.getCronParam())
                        || StringUtils.isBlank(param.getCronParam().getType())
                        || CollectionUtils.isEmpty(param.getCronParam().getMoment())) {
                    throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000073.getCode());
                }
            }
        }
    }

    /**
     * 发布任务
     *
     * @param taskId
     */
    private void publish(String taskId, String userId) {
        if (StringUtils.isBlank(taskId)) {
            throw new AppException("60000016");
        }
        if (StringUtils.isBlank(userId)) {
            throw new AppException("60000046");
        }
        SyncTaskEntity taskEntity = getById(taskId);
        String taskParamJson = taskEntity.getTaskParamJson();

        //调度相关参数
        TaskScheduleInfo info = null;
        if (StringUtils.isNotBlank(taskParamJson) && JSONUtil.isJson(taskParamJson)) {
            info = JSONObject.parseObject(taskParamJson).toJavaObject(TaskScheduleInfo.class);
        }
        if (Objects.isNull(info)) {
            throw new AppException("60000054");
        }
        //任务宽表信息
        SyncWidetableEntity wideTableEntity = syncWidetableService.getWideTableByTaskId(taskId);
        if (Objects.isNull(wideTableEntity)) {
            throw new AppException("60000055");
        }
        String syncCondition = wideTableEntity.getSyncCondition();
        if (StringUtils.isBlank(syncCondition)) {
            throw new AppException("60000056");
        }
        //增量相关参数
        SyncCondition condition = JSONObject.parseObject(syncCondition).toJavaObject(SyncCondition.class);
        condition = IncrementUtil.getSyncCondition(condition, info.getCronParam().getCron());
        FeignSyncCondition feignSyncCondition = BeanCopyUtils.copyProperties(condition, FeignSyncCondition.class);
        feignSyncCondition.setFirstFull(info.getCronParam().getFirstFull());

        User user = getSystemUserByUserId(userId);

        List<SourceTable> sourceTableList = JSONArray.parseArray(wideTableEntity.getSourceTables(), SourceTable.class);
        StringJoiner sourceTables = new StringJoiner(",", "(", ")");
        for (SourceTable sourceTable : sourceTableList) {
            sourceTables.add(sourceTable.getTableName());
        }

        FeignPlatformProcessDefinitionRequest build = FeignPlatformProcessDefinitionRequest.builder()
                .processDefinitionId(taskEntity.getScheduleId())
                .accessUser(user)
                .channelControl(new ChannelControlParam(info.getMaxThread(), info.isLimit(), info.getLimitRate()))
                .partitionParam(feignSyncCondition)
                .schedulerParam(new SchedulerParam(info.getScheduleType(), info.getCronParam().getCron()))
                .ordinaryParam(new PlatformTaskOrdinaryParam(taskEntity.getVersion(), info.getScheduleType(), info.getCron(), wideTableEntity.getTargetSource(), String.valueOf(sourceTables), taskEntity.getWorkspaceId(), taskEntity.getEnable(), taskEntity.getTaskName(), "icredit", taskId, buildDataxJson(taskId), 0))
                .build();
        String scheduleId = taskEntity.getScheduleId();
        if (StringUtils.isBlank(scheduleId)) {
            //如果创建hive成功但接口异常可考虑删除该hive表
            //创建流程定义并回写到任务记录中
            String processDefinitionId = createDefinitionAndWriteBackId(taskId, build);
            taskEntity.setScheduleId(processDefinitionId);

            List<SyncWidetableFieldEntity> wideTableFields = syncWidetableFieldService.getWideTableFields(wideTableEntity.getId());
            List<WideTableFieldRequest> wideTableFieldRequests = wideTableFieldEntityTransferToRequest(wideTableFields);

            CreateWideTableParam wideTableParam = new CreateWideTableParam();
            wideTableParam.setWorkspaceId(taskEntity.getWorkspaceId());
            wideTableParam.setWideTableName(wideTableEntity.getName());
            wideTableParam.setTargetSource(wideTableEntity.getTargetSource());
            wideTableParam.setFieldInfos(wideTableFieldRequests);
            wideTableParam.setPartition(condition.getPartition());
            //创建宽表
            createWideTable(wideTableParam);
        } else {
            if (!TaskStatusEnum.DRAFT.getCode().equals(taskEntity.getTaskStatus())) {
                schedulerFeign.update(build);
            }
        }
        //判断是否是周期执行
        if (Objects.nonNull(taskEntity.getEnable())
                && Objects.nonNull(syncCondition)
                && EnableStatusEnum.ENABLE.getCode().equals(taskEntity.getEnable())
                && StringUtils.isNotBlank(condition.getIncrementalField())) {
            cycleRun(taskEntity);
        }
        //更新状态为启用
        SyncTaskEntity entity = new SyncTaskEntity();
        entity.setId(taskId);
        if (TaskStatusEnum.ENABLE.getCode().equals(taskEntity.getEnable())) {
            entity.setTaskStatus(TaskStatusEnum.ENABLE.getCode());
        } else {
            entity.setTaskStatus(TaskStatusEnum.DISABLE.getCode());
        }
        updateById(entity);
        //同步历史数据
        syncHiRecord(taskId);
    }

    /**
     * 同步历史记录
     *
     * @param taskId
     */
    private void syncHiRecord(String taskId) {
        //任务记录
        SyncTaskEntity taskEntity = getSyncTaskEntityById(taskId);
        CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> {
            SyncTaskHiEntity hiEntity = new SyncTaskHiEntity();
            BeanCopyUtils.copyProperties(taskEntity, hiEntity);
            hiEntity.setId(null);
            hiEntity.setTaskId(taskEntity.getId());
            syncTaskHiService.save(hiEntity);
        }, executor);
        //任务宽表记录
        QueryWrapper<SyncWidetableEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(SyncWidetableEntity.SYNC_TASK_ID, taskId);
        List<SyncWidetableEntity> widetableEntities = syncWidetableService.list(wrapper);
        if (CollectionUtils.isNotEmpty(widetableEntities)) {
            SyncWidetableEntity widetableEntity = widetableEntities.get(0);
            CompletableFuture<Void> widetableFuture = CompletableFuture.runAsync(() -> {
                SyncWidetableHiEntity widetableHiEntity = new SyncWidetableHiEntity();
                BeanCopyUtils.copyProperties(widetableEntity, widetableHiEntity);
                widetableHiEntity.setId(null);
                widetableHiEntity.setWidetableId(widetableEntity.getId());
                syncWidetableHiService.save(widetableHiEntity);
            }, executor);
            //宽表字段记录
            QueryWrapper<SyncWidetableFieldEntity> fieldWrapper = new QueryWrapper<>();
            fieldWrapper.eq(SyncWidetableFieldEntity.WIDE_TABLE_ID, widetableEntity.getId());
            List<SyncWidetableFieldEntity> fieldEntities = syncWidetableFieldService.list(fieldWrapper);
            CompletableFuture<Void> fieldFuture = CompletableFuture.runAsync(() -> {
                List<SyncWidetableFieldHiEntity> fieldHiEntities = fieldEntities.stream()
                        .filter(Objects::nonNull)
                        .map(fieldEntity -> {
                            SyncWidetableFieldHiEntity fieldHiEntity = new SyncWidetableFieldHiEntity();
                            BeanCopyUtils.copyProperties(fieldEntity, fieldHiEntity);
                            fieldHiEntity.setId(null);
                            fieldHiEntity.setWideTableId(fieldEntity.getWideTableId());
                            fieldHiEntity.setWidetableFieldId(fieldEntity.getId());
                            return fieldHiEntity;
                        }).collect(Collectors.toList());
                syncWidetableFieldHiService.saveBatch(fieldHiEntities);
            }, executor);
            CompletableFuture.allOf(taskFuture, widetableFuture, fieldFuture).join();
        }
    }

    /**
     * SyncWidetableFieldEntity  转化为  WideTableFieldRequest
     *
     * @param wideTableFields
     * @return
     */
    private List<WideTableFieldRequest> wideTableFieldEntityTransferToRequest(List<SyncWidetableFieldEntity> wideTableFields) {
        return Optional.ofNullable(wideTableFields).orElse(Lists.newArrayList())
                .stream()
                .filter(Objects::nonNull)
                .map(entity -> {
                    WideTableFieldRequest request = new WideTableFieldRequest();
                    request.setFieldType(entity.getType());
                    request.setDatabaseName(entity.getDatabaseName());
                    request.setFieldName(entity.getName());
                    request.setFieldChineseName(entity.getChinese());
                    request.setSort(entity.getSort());
                    request.setSourceTable(entity.getSource());
                    request.setAssociateDict(entity.getDictKey());
                    request.setRemark(entity.getRemark());
                    return request;
                }).collect(Collectors.toList());
    }

    /**
     * 创建流程定义并回写定义ID到task中
     */
    private String createDefinitionAndWriteBackId(String taskId, FeignPlatformProcessDefinitionRequest request) {
        BusinessResult<CreatePlatformTaskResult> businessResult = schedulerFeign.create(request);
        String processDefinitionId = null;
        if (businessResult.isSuccess() && businessResult.getData() != null) {
            CreatePlatformTaskResult data = businessResult.getData();
            SyncTaskEntity updateEntity = new SyncTaskEntity();
            updateEntity.setId(taskId);
            updateEntity.setScheduleId(data.getProcessDefinitionId());
            syncTaskMapper.updateById(updateEntity);
            processDefinitionId = data.getProcessDefinitionId();
        } else {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000037.getCode());
        }
        if (StringUtils.isBlank(processDefinitionId)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000076.getCode());
        }
        return processDefinitionId;
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    private User getSystemUserByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            log.error("查询系统用户,传递参数userId为空");
            throw new AppException("60000046");
        }
        User user = null;
        BusinessResult<User> userAccountInfo = systemFeign.getUserAccountInfo(userId);
        if (userAccountInfo.isSuccess() && Objects.nonNull(userAccountInfo.getData())) {
            user = userAccountInfo.getData();
        }
        if (Objects.isNull(user)) {
            throw new AppException("60000038");
        }
        return user;
    }

    private SyncTaskEntity getSyncTaskEntityById(String taskId) {
        if (StringUtils.isBlank(taskId)) {
            log.error("根据同步任务ID查询信息，参数任务ID为空");
            throw new AppException("60000016");
        }
        SyncTaskEntity byId = getById(taskId);
        if (Objects.isNull(byId)) {
            log.error("根据同步任务ID查询信息失败，taskId:" + taskId);
            throw new AppException("60000039");
        }
        return byId;
    }

    /**
     * 执行周期任务
     *
     * @param entity
     */
    public void cycleRun(SyncTaskEntity entity) {
        if (Objects.isNull(entity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000057.code);
        }
        if (!TaskStatusEnum.ENABLE.getCode().equals(entity.getEnable())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000044.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000044.message);
        }
        //“执行中” 状态
        if (ExecStatusEnum.EXEC.getCode().equals(entity.getExecStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.message);
        }
        String processDefinitionId = entity.getScheduleId();

        BusinessResult<Boolean> result = schedulerFeign.execCycle(processDefinitionId);
        if (!result.isSuccess()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000052.code);
        }
    }

    /**
     * 构建datax执行json
     *
     * @param taskId
     * @return
     */
    private String buildDataxJson(String taskId) {
        //查找配置了字典的列
        Map<String, String> transferColumnsByTaskId = findTransferColumnsByTaskId(taskId);
        List<DictInfo> dictInfos = null;
        if (MapUtil.isNotEmpty(transferColumnsByTaskId)) {
            Collection<String> values = transferColumnsByTaskId.values();
            //根据字典KEY查找字典信息
            dictInfos = findDictInfos(values);
        }
        SyncTaskEntity taskEntity = getById(taskId);
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId);
        String taskParamJson = taskEntity.getTaskParamJson();
        TaskScheduleInfo parse = taskScheduleInfoParser.parse(taskParamJson);
        //根据dialect取得reader插件处理器
        DataxReader dataxReader = DataxReaderContainer.get(wideTableField.getDialect());
        dataxReader.setNeedTransferColumns(transferColumnsByTaskId);
        dataxReader.setTransferDict(dictInfos);
        dataxReader.preSetConfigParam(wideTableField);

        HdfsWriterConfigParam hdfsWriterConfigParam = findHdfsWriterConfigParam(taskId);
        List<Column> wideTableColumns = getWideTableColumns(taskId);
        //hdfs写插件处理器
        HdfsWriterEntity hdfsWriterEntity = new HdfsWriterEntity(wideTableColumns, hdfsWriterConfigParam);
        Map<String, Object> taskConfig = DataxJsonEntity.builder()
                .reader(dataxReader)
                .writer(hdfsWriterEntity)
                .setting(getDataxSetting(parse.getMaxThread()))
                .core(getDataxCore(parse.getMaxThread(), parse.getLimitRate()))
                .build().buildDataxJson();
        return JSONObject.toJSONString(taskConfig);
    }

    private Map<String, Object> getDataxCore(Integer ch, Integer record) {
        Map<String, Object> core = new HashMap<>(1);
        Map<String, Object> transport = new HashMap<>(1);
        Map<String, Object> channel = new HashMap<>(1);
        Map<String, Integer> speed = new HashMap<>(2);

        if (Objects.nonNull(ch) && Objects.nonNull(record)) {
            speed.put("channel", ch);
            speed.put("record", record);
        } else {
            speed.put("channel", 1);
            speed.put("record", 100000);
        }
        channel.put("speed", speed);
        transport.put("channel", channel);
        core.put("transport", transport);
        return core;
    }

    private Map<String, Object> getDataxSetting(Integer channel) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> speed = Maps.newHashMap();
        if (Objects.nonNull(channel)) {
            speed.put("channel", channel);
        } else {
            speed.put("channel", 1);
        }
        result.put("speed", speed);
        return result;
    }

    private List<Column> getWideTableColumns(String taskId) {
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId);
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
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId);
        if (Objects.isNull(wideTableField)) {
            log.error("未找到宽表信息" + taskId);
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
                partition = parse.getPartition();

                String partitionDir = "";
                if (StringUtils.isNotBlank(partition)) {
                    partitionDir = syncTimeInterval.getTimeFormat();
                }
                param.setPath(getDataxSyncPath(data.getWarehouse(), wideTableField.getName(), wideTableField.getTargetSource(), partitionDir));

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
        if (StringUtils.isNotBlank(partitionDir)) {
            sb.append("/");
            sb.append(partitionDir);
        }
        return sb.toString();
    }

    /**
     * 查询配置字典的列
     *
     * @param taskId
     * @return
     */
    private Map<String, String> findTransferColumnsByTaskId(String taskId) {
        Map<String, String> results = Maps.newConcurrentMap();
        SyncWidetableEntity wideTableField = syncWidetableService.getWideTableField(taskId);
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
        List<DictColumnEntity> dictInfoByKeys = dictColumnService.getDictInfoByIds(keys);
        List<DictInfo> result = null;
        if (CollectionUtils.isNotEmpty(dictInfoByKeys)) {
            result = dictInfoByKeys.stream()
                    .filter(Objects::nonNull)
                    .map(dictColumnEntity -> {
                        DictInfo info = new DictInfo();
                        info.setName(dictColumnEntity.getColumnValue());
                        info.setKey(dictColumnEntity.getDictId());
                        info.setValue(dictColumnEntity.getColumnKey());
                        return info;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(result).orElse(Lists.newArrayList());
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public String syncTaskDefineSave(DataSyncTaskDefineSaveParam param) {
        SyncTaskEntity entity = new SyncTaskEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setId(param.getTaskId());
        entity.setCreateUserId(param.getUserId());
        saveOrUpdate(entity);
        return entity.getId();
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
                        entity.setCreateUserId(param.getUserId());
                        entity.setLastUpdateUserId(param.getUserId());
                        return entity;
                    }).collect(Collectors.toList());
            Set<String> wideTableIds = params.parallelStream().filter(Objects::nonNull).map(WideTableFieldSaveParam::getWideTableId).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(wideTableIds)) {
                syncWidetableFieldService.deleteByWideTableIds(wideTableIds);
            }
            syncWidetableFieldService.saveOrUpdateBatch(collect);
        }
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
            info.setEnable(byId.getEnable());
            info.setTaskId(byId.getId());
            info.setTaskDescribe(byId.getTaskDescribe());
            info.setCreateMode(byId.getCreateMode());
        }
        return BusinessResult.success(info);
    }

    @Override
    public BusinessResult<TaskDefineInfo> taskDetailInfo(DataSyncDetailParam param) {
        SyncTaskEntity byId = getById(param.getTaskId());
        TaskDefineInfo info = null;
        if (Objects.nonNull(byId)) {
            info = new TaskDefineInfo();
            BeanCopyUtils.copyProperties(byId, info);
            info.setEnable(byId.getTaskStatus());
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
            SyncWidetableEntity wideTable = syncWidetableService.getWideTableField(id);
            if (Objects.nonNull(wideTable)) {
                info = new TaskBuildInfo();
                info.setDatasourceId(wideTable.getDatasourceId());
                info.setWideTableName(wideTable.getName());
                info.setSourceType(wideTable.getSourceType());
                info.setSyncCondition(syncConditionParser.parse(wideTable.getSyncCondition()));
                info.setTargetSource(wideTable.getTargetSource());
                info.setSql(wideTable.getSqlStr());

                info.setDialect(wideTable.getDialect());

                List<TableInfo> tableInfos = JSONArray.parseArray(wideTable.getSourceTables(), TableInfo.class);
                if (CollectionUtils.size(tableInfos) == 1) {
                    TableInfo tableInfo = tableInfos.get(0);
                    AssociatedData data = new AssociatedData();
                    data.setLeftSourceDatabase(tableInfo.getDatabase());
                    data.setLeftSource(tableInfo.getTableName());
                    info.setView(Lists.newArrayList(data));
                    info.setDatasourceId(tableInfo.getDatasourceId());
                } else {
                    info.setView(fileAssociatedParser.parse(wideTable.getViewJson()));
                }
                if (CollectionUtils.isNotEmpty(tableInfos)) {
                    TableInfo tableInfo = tableInfos.get(0);
                    info.setDatasourceId(tableInfo.getDatasourceId());
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
            String cronParam = byId.getCronParam();
            if (StringUtils.isNotBlank(cronParam) && JSONUtil.isJson(cronParam)) {
                CronParam cronParams = JSONObject.parseObject(cronParam).toJavaObject(CronParam.class);
                info.setCronParam(cronParams);
            }
        }
        return BusinessResult.success(info);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<Associated> dialectAssociatedSupport(DataSyncDialectSupportParam param) {
        return BusinessResult.success(AssociatedUtil.find(param.getDialect()));
    }

    /**
     * sql创建方式前置调用接口
     * 用于根据查询语句定位数据源
     *
     * @param param
     * @return
     */
    @Override
    public BusinessResult<PreSqlPositionDataSourceResult> preSqlPositionDataSource(PreSqlPositionDataSourceParam param) {
        if (Objects.isNull(param.getSourceType())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000062.getCode());
        }
        if (StringUtils.isBlank(param.getSql())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000063.getCode());
        }
        QueryStatementParseHandler handler = QueryStatementParseContainer.get(param.getSql());
        List<PreSqlPositionDataSourceResult.DatabaseInfo> dataSourceInfo = handler.getDataSourceInfo(param.getSourceType(), param.getSql());
        if (CollectionUtils.isEmpty(dataSourceInfo)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000064.getCode());
        }
        preSqlPositionDataSourceValid(dataSourceInfo);
        PreSqlPositionDataSourceResult result = new PreSqlPositionDataSourceResult();
        if (dataSourceInfo.size() > 1) {
            result.setShowWindow(true);
        }
        result.setSameNameDataBase(dataSourceInfo);
        return BusinessResult.success(result);
    }

    /**
     * 校验sql匹配的数据源信息
     *
     * @param dataSourceInfo
     */
    private void preSqlPositionDataSourceValid(List<PreSqlPositionDataSourceResult.DatabaseInfo> dataSourceInfo) {
        for (PreSqlPositionDataSourceResult.DatabaseInfo info : dataSourceInfo) {
            if (StringUtils.isBlank(info.getDialect())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000065.getCode());
            }
            if (StringUtils.isBlank(info.getDatabaseName())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000066.getCode());
            }
            if (StringUtils.isBlank(info.getHost())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000067.getCode());
            }
            if (StringUtils.isBlank(info.getDatasourceId())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000068.getCode());
            }
        }
    }

    /**
     * 识别宽表
     *
     * @param param
     * @return
     */
    @Override
    @BusinessParamsValidate
    public BusinessResult<WideTable> generateWideTable(DataSyncGenerateWideTableParam param) {
        //参数前置校验
        generateWideTablePreValid(param);

        WideTable wideTable = null;
        log.info("生成宽表请求参数:" + JSONObject.toJSONString(param));
        if (SourceTypeEnum.EXTERNAL_DATABASE.getCode().equals(param.getSourceType())) {
            OutsideSourceWideTableParam tableParam = new OutsideSourceWideTableParam();
            BeanCopyUtils.copyProperties(param, tableParam);
            wideTable = outsideSourceGenerateWideTable(tableParam);
        }
        if (SourceTypeEnum.LOCAL_FILE.getCode().equals(param.getSourceType())) {
            //TODO
        }
        if (SourceTypeEnum.BLOCK_CHAIN.getCode().equals(param.getSourceType())) {
            //TODO
        }
        if (Objects.nonNull(wideTable)) {
            wideTable.setSourceDialect(param.getDialect());
            wideTable.setDatasourceId(param.getDatasourceId());
        }
        return BusinessResult.success(wideTable);
    }

    /**
     * 外部数据源识别宽表
     *
     * @param param
     * @return
     */
    private WideTable outsideSourceGenerateWideTable(OutsideSourceWideTableParam param) {
        //根据参数确定源库类型
        OutsideGenerateWideTable outsideGenerateWideTable = OutsideGenerateWideTableContainer.find(param);
        //未匹配到合适的类型处理器
        if (Objects.isNull(outsideGenerateWideTable)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000025.getCode());
        }
        //取得宽表sql
        String wideTableSql = outsideGenerateWideTable.getWideTableSql(param);
        log.info("取得宽表的sql语句" + wideTableSql);
        //取得数据源ID
        String dataSourceId = outsideGenerateWideTable.getDataSourceId(wideTableSql, param);
        log.info("数据源ID" + dataSourceId);
        //生成宽表数据列
        WideTable wideTable = outsideGenerateWideTable.generate(wideTableSql, dataSourceId);
        return wideTable;
    }

    /**
     * 本地数据源识别宽表
     *
     * @param param
     * @return
     */
    private WideTable localSourceGenerateWideTable(LocalSourceWideTableParam param) {
        // TODO
        return null;
    }

    /**
     * 区块链数据源识别宽表
     *
     * @param param
     * @return
     */
    private WideTable blockchainSourceGenerateWideTable(BlockchainSourceWideTableParam param) {
        //TODO
        return null;
    }

    /**
     * 识别宽表前置校验
     *
     * @param param
     */
    private void generateWideTablePreValid(DataSyncGenerateWideTableParam param) {
        if (Objects.isNull(param.getSourceType())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000062.getCode());
        }
        //创建方式不能为空
        if (Objects.isNull(param.getCreateMode())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000011.getCode());
        }

        if (CreateModeEnum.SQL.getCode().equals(param.getCreateMode())) {
            //sql创建方式，校验sql信息
            if (StringUtils.isBlank(param.getSql())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000024.getCode());
            }
        } else {//可视化传参方式
            //可视化表信息列表为空
            if (CollectionUtils.isEmpty(param.getSourceTables())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000059.getCode());
            }
            //大于一张表可视化关系列表不能为空
            if (CollectionUtils.size(param.getSourceTables()) > 1 && CollectionUtils.isEmpty(param.getView())) {
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000060.getCode());
            }
        }
        //数据源ID不能为空
        if (StringUtils.isBlank(param.getDatasourceId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000003.getCode());
        }
        //数据库方言不能为空
        if (StringUtils.isBlank(param.getDialect())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000004.getCode());
        }
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
        SyncTaskEntity entity = checkTaskId(param.getTaskId());
        if (!TaskStatusEnum.ENABLE.getCode().equals(entity.getTaskStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000041.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000041.message);
        }
        String processDefinitionId = entity.getScheduleId();
        entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        String result = schedulerFeign.stopSyncTask(processDefinitionId);
        if ("true".equals(result)) {
            entity.setTaskStatus(TaskStatusEnum.DISABLE.getCode());
            entity.setEnable(EnableStatusEnum.DISABLE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> remove(DataSyncExecParam param) {
        SyncTaskEntity entity = checkTaskId(param.getTaskId());
        if (TaskStatusEnum.ENABLE.getCode().equals(entity.getTaskStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000042.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000042.message);
        }
        if (ExecStatusEnum.EXEC.getCode().equals(entity.getExecStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.message);
        }
        String processDefinitionId = entity.getScheduleId();
        schedulerFeign.deleteSyncTask(processDefinitionId);
        removeById(param.getTaskId());
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> enable(DataSyncExecParam param) {
        //根据taskId查找数据源id
        String datasourceId = getDatasourceId(param.getTaskId());
        BusinessResult<DatasourceDetailResult> info = datasourceFeign.info(datasourceId);
        if (info.isSuccess() && null != info.getData() && EnableStatusEnum.DISABLE.getCode().equals(info.getData().getStatus())) {
            throw new AppException("60000053");
        }
        SyncTaskEntity entity = checkTaskId(param.getTaskId());
        if (!TaskStatusEnum.DISABLE.getCode().equals(entity.getTaskStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000043.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000043.message);
        }
        String processDefinitionId = entity.getScheduleId();
        String enableResult = schedulerFeign.enableSyncTask(processDefinitionId);
        if ("true".equals(enableResult)) {
            entity = new SyncTaskEntity();
            entity.setId(param.getTaskId());
            entity.setTaskStatus(TaskStatusEnum.ENABLE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> run(DataSyncExecParam param) {
        SyncTaskEntity entity = checkTaskId(param.getTaskId());
        if (!TaskStatusEnum.ENABLE.getCode().equals(entity.getTaskStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000044.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000044.message);
        }
        if (ExecStatusEnum.EXEC.getCode().equals(entity.getExecStatus())) {//“执行中” 状态
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.message);
        }
        String processDefinitionId = entity.getScheduleId();
        entity = new SyncTaskEntity();
        entity.setId(param.getTaskId());
        entity.setExecStatus(ExecStatusEnum.EXEC.getCode());
        updateById(entity);//执行中
        String result = schedulerFeign.execSyncTask(processDefinitionId);
        if ("true".equals(result)) {//成功
            return BusinessResult.success(true);
        } else {//失败
            return BusinessResult.fail("", "执行失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> cease(DataSyncExecParam param) {
        SyncTaskEntity entity = checkTaskId(param.getTaskId());
        if (!TaskStatusEnum.ENABLE.getCode().equals(entity.getTaskStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000045.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000045.message);
        }
        if (!ExecStatusEnum.EXEC.getCode().equals(entity.getExecStatus())) {//不是 “执行中” 状态
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000034.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000034.message);
        }
        String processDefinitionId = entity.getScheduleId();
        String ceaseResult = schedulerFeign.ceaseSyncTask(processDefinitionId);
        if ("true".equals(ceaseResult)) {
            entity = new SyncTaskEntity();
            entity.setId(param.getTaskId());
            entity.setExecStatus(ExecStatusEnum.FAILURE.getCode());
            updateById(entity);
        }
        return BusinessResult.success(true);
    }

    private SyncTaskEntity checkTaskId(String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000016.message);
        }
        SyncTaskEntity entity = syncTaskMapper.selectById(taskId);
        if (null == entity) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000075.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000075.message);
        }
        return entity;
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
        feignRequest.setWorkspaceId(param.getWorkspaceId());
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
        wrapper.orderByDesc(SyncTaskEntity.CREATE_TIME);
        return wrapper;
    }

    @Override
    public BusinessResult<BusinessPageResult<DataSyncDispatchTaskPageResult>> dispatchPage(DataSyncDispatchTaskPageParam param) {
        DataSyncDispatchTaskPageDTO dispatchPageDTO = new DataSyncDispatchTaskPageDTO();
        BeanUtils.copyProperties(param, dispatchPageDTO);
        if (!"0".equals(dispatchPageDTO.getWorkspaceId())) {
            dispatchPageDTO.setCurrLoginUserId(null);
        }
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

    @Override
    public Boolean hasRunningTask(String datasourceId) {
        return BooleanUtils.isTrue(syncTaskMapper.hasRunningTask(datasourceId));
    }

    @Override
    public String getDatasourceId(String taskId) {
        return syncTaskMapper.getDatasourceId(taskId);
    }

    @Override
    public Boolean updateExecStatusByScheduleId(String scheduleId) {
        syncTaskMapper.updateExecStatusByScheduleId(scheduleId, ExecStatusEnum.EXEC.getCode());
        return true;
    }
}
