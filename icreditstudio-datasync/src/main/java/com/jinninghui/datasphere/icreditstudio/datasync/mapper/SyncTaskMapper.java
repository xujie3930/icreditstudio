package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DataSyncDispatchTaskPageDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DataSyncDispatchTaskPageResult;
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

}
