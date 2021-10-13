package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DataSyncDispatchTaskPageDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncTaskEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.vo.DataSyncDispatchTaskPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author peng
 * @Entity generator.domain.SyncTaskEntity
 */
@Mapper
public interface SyncTaskMapper extends BaseMapper<SyncTaskEntity> {
    Long countDispatch(DataSyncDispatchTaskPageDTO dispatchPageDTO);

    List<DataSyncDispatchTaskPageVO> dispatchList(DataSyncDispatchTaskPageDTO dispatchPageDTO);
}
