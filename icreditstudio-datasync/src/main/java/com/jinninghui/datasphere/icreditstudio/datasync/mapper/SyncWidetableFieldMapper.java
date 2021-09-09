package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableFieldEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author peng
 * @Entity generator.domain.SyncWidetableFieldEntity
 */
@Mapper
public interface SyncWidetableFieldMapper extends BaseMapper<SyncWidetableFieldEntity> {
    /**
     * 物理删除记录
     *
     * @param ids
     * @return
     */
    boolean deleteByWideTableIds(Set<String> ids);
}




