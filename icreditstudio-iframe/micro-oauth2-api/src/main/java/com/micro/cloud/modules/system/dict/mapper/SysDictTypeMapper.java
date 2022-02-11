package com.micro.cloud.modules.system.dict.mapper;

import com.github.pagehelper.PageHelper;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.dict.dataobject.SysDictType;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeExportReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypePageReqVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDictTypeMapper extends BaseMapperX<SysDictType> {

  default List<SysDictType> selectPage(SysDictTypePageReqVO reqVO) {
    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    QueryWrapperX<SysDictType> queryWrapperX =
        new QueryWrapperX<SysDictType>()
            .likeIfPresent("name", reqVO.getName())
            .likeIfPresent("`type`", reqVO.getType())
            .eqIfPresent("status", reqVO.getStatus())
            .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime());
    return selectList(queryWrapperX);
  }

  default List<SysDictType> selectList(SysDictTypeExportReqVO reqVO) {
    return selectList(
        new QueryWrapperX<SysDictType>()
            .likeIfPresent("name", reqVO.getName())
            .likeIfPresent("`type`", reqVO.getType())
            .eqIfPresent("status", reqVO.getStatus())
            .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime()));
  }

  default SysDictType selectByType(String type) {
    return selectOne(new QueryWrapperX<SysDictType>().eq("`type`", type));
  }

  default SysDictType selectByName(String name) {
    return selectOne(new QueryWrapperX<SysDictType>().eq("name", name));
  }
}
