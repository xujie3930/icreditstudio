package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncDetailParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncDialectSupportParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DialectAssociated;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskBuildInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskDefineInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskScheduleInfo;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @author peng
 */
public interface SyncTaskService extends IService<SyncTaskEntity> {
    /**
     * 同步任务定义、同步任务构建、同步任务调度保存
     *
     * @param param
     * @return
     */
    BusinessResult<ImmutablePair<String, String>> save(DataSyncSaveParam param);

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
    BusinessResult<DialectAssociated> dialectAssociatedSupport(DataSyncDialectSupportParam param);
}
