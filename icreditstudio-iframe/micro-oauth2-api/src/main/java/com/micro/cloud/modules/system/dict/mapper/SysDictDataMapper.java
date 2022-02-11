package com.micro.cloud.modules.system.dict.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.dict.dataobject.SysDictData;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataExportReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataPageReqVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDictDataMapper extends BaseMapperX<SysDictData> {

  default SysDictData selectByDictTypeAndValue(String dictType, String value) {
    return selectOne(new QueryWrapper<SysDictData>().eq("dict_type", dictType).eq("value", value));
  }

  default int selectCountByDictType(String dictType) {
    return selectCount("dict_type", dictType);
  }

  default List<SysDictData> selectPage(SysDictDataPageReqVO reqVO) {
    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    QueryWrapper<SysDictData> queryWrapper =
        new QueryWrapperX<SysDictData>()
            .likeIfPresent("label", reqVO.getLabel())
            .likeIfPresent("dict_type", reqVO.getDictType())
            .eqIfPresent("status", reqVO.getStatus())
            .orderByAsc("dict_type", "sort");
    return selectList(queryWrapper);
  }

  default List<SysDictData> selectList(SysDictDataExportReqVO reqVO) {
    return selectList(
        new QueryWrapperX<SysDictData>()
            .likeIfPresent("label", reqVO.getLabel())
            .likeIfPresent("dict_type", reqVO.getDictType())
            .eqIfPresent("status", reqVO.getStatus()));
  }

  default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
    return selectOne(
            new QueryWrapper<SysDictData>()
                .select("id")
                .gt("update_time", maxUpdateTime)
                .last("LIMIT 1"))
        != null;
  }
}
