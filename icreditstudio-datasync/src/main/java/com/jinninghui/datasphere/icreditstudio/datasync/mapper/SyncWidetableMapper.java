package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

/**
 * @Entity generator.domain.SyncWidetableEntity
 * @author peng
 */
@Mapper
public interface SyncWidetableMapper extends BaseMapper<SyncWidetableEntity> {

    String getWideTableInfoByTaskId(@Param("taskId") String taskId);
}




