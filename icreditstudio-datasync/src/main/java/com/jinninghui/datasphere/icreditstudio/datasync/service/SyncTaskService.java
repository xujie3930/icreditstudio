package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncDetailParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncTaskInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskBuildInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskDefineInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncDetailRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
     * @param param
     * @return
     */
    BusinessResult<TaskBuildInfo> taskBuildInfo(DataSyncDetailParam param);
}
