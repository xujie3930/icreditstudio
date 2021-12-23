package com.jinninghui.datasphere.icreditstudio.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictColumnMapper extends BaseMapper<DictColumnEntity> {

    void delBatchByDictId(@Param("delFlag") Integer delFlag, @Param("dictId") String dictId);

    List<DictColumnResult> getColumnListByDictId(@Param("dictId") String dictId);

    void truthDelBatchByDictId(@Param("dictId") String dictId);
}
