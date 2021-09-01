package com.jinninghui.datasphere.icreditstudio.datasource.mapper;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
public interface IcreditDdlSyncMapper extends BaseMapper<IcreditDdlSyncEntity> {

    IcreditDdlSyncEntity selectMaxVersionByDatasourceId(@Param("id") String id);
}
