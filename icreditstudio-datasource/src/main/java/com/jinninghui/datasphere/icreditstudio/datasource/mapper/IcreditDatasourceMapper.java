package com.jinninghui.datasphere.icreditstudio.datasource.mapper;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.DataSourceHasExistRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
public interface IcreditDatasourceMapper extends BaseMapper<IcreditDatasourceEntity> {

    void updateStatusById(@Param("id") String id);

    IcreditDatasourceEntity selectById(@Param("id") String id);

    Boolean hasExit(@Param("request") DataSourceHasExistRequest request);

    List<IcreditDatasourceEntity> selectAll(@Param("spaceId")String spaceId, @Param("status") Integer status);

    Boolean selectByWorkspaceIdHasExit(@Param("spaceId")String spaceId);
}
