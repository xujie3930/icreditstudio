package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @author peng
 */
public interface SyncTaskService extends IService<SyncTaskEntity> {
    /**
     * 检查重复任务名称
     *
     * @return
     */
    BusinessResult<ImmutablePair> checkRepeatTaskName(DataSyncSaveParam param);

    /**
     * 同步任务定义、同步任务构建、同步任务调度保存
     *
     * @param param
     * @return
     */
    BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param);

    /**
     * 同步任务定义保存
     *
     * @param param
     * @return
     */
    String syncTaskDefineSave(DataSyncTaskDefineSaveParam param);

    /**
     * 同步任务列表
     *
     * @param param
     * @return
     */
    BusinessResult<BusinessPageResult> syncTasks(DataSyncQueryParam param);

    /**
     * 同步任务定义信息
     *
     * @param param
     * @return
     */
    BusinessResult<TaskDefineInfo> taskDefineInfo(DataSyncDetailParam param);

    /**
     * 同步任务详情
     *
     * @param param
     * @return
     */
    BusinessResult<TaskDefineInfo> taskDetailInfo(DataSyncDetailParam param);

    /**
     * 同步任务构建信息
     *
     * @param param
     * @return
     */
    BusinessResult<TaskBuildInfo> taskBuildInfo(DataSyncDetailParam param);

    /**
     * 同步调度构建信息
     *
     * @param param
     * @return
     */
    BusinessResult<TaskScheduleInfo> taskScheduleInfo(DataSyncDetailParam param);

    /**
     * 数据源支持的关联类型
     *
     * @param param
     * @return
     */
    BusinessResult<Associated> dialectAssociatedSupport(DataSyncDialectSupportParam param);

    /**
     * 通过sql语句定位数据源
     * 如果创建方式是sql方式则需要前置调用
     *
     * @param param
     * @return
     */
    BusinessResult<PreSqlPositionDataSourceResult> preSqlPositionDataSource(PreSqlPositionDataSourceParam param);

    /**
     * 生成宽表
     *
     * @param param
     * @return
     */
    BusinessResult<WideTable> generateWideTable(DataSyncGenerateWideTableParam param);

    /**
     * 任务停用
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> stop(DataSyncExecParam param);

    /**
     * 任务删除
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> remove(DataSyncExecParam param);

    /**
     * 任务启用
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> enable(DataSyncExecParam param);

    /**
     * 立即运行
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> run(DataSyncExecParam param);

    /**
     * 停止
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> cease(DataSyncExecParam param);

    /**
     * 同步任务调度列表
     *
     * @param param
     * @return
     */
    BusinessResult<BusinessPageResult<DataSyncDispatchTaskPageResult>> dispatchPage(DataSyncDispatchTaskPageParam param);

    /**
     * 获取流程定义ID
     *
     * @param id
     * @return
     */
    String getProcessDefinitionIdById(String id);

    Boolean hasRunningTask(String datasourceId);

    String getDatasourceId(String taskId);

    Boolean updateExecStatusByScheduleId(String scheduleId);

    WideTableInfoResult getWideTableInfoByTaskId(String taskId);
}
