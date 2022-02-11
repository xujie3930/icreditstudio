package com.micro.cloud.modules.system.resource.mapper;

import com.micro.cloud.modules.system.resource.dataobject.SysResource;
import com.micro.cloud.modules.system.resource.vo.SysResourceListReqVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Mapper
public interface SysResourceMapper extends BaseMapperX<SysResource> {

  /**
   * 资源列表
   *
   * @param reqVO
   * @return
   */
  default List<SysResource> selectList(SysResourceListReqVO reqVO) {
    return selectList(
        new QueryWrapperX<SysResource>()
            .likeIfPresent("name", reqVO.getName())
            .eqIfPresent("status", reqVO.getStatus()));
  }

  List<String> getAllUrls();
}
