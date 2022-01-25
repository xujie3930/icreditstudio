package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DataSyncDispatchTaskPageDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.model.TaskCallBackModel;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DataSyncDispatchTaskPageResult;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author peng
 * @Entity generator.domain.SyncTaskEntity
 */
@Mapper
public interface SyncTaskMapper extends BaseMapper<SyncTaskEntity> {

    Long countDispatch(DataSyncDispatchTaskPageDTO dispatchPageDTO);

    List<DataSyncDispatchTaskPageResult> dispatchList(DataSyncDispatchTaskPageDTO dispatchPageDTO);

    Boolean hasRunningTask(@Param("datasourceId") String datasourceId);

    String getDatasourceId(@Param("taskId") String taskId);

    void updateExecStatusByScheduleId(@Param("scheduleId") String scheduleId, @Param("execState") int execState);

    void taskWriteBack(TaskCallBackModel model);

}
