package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.dto.DictQueryDTO;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.entity.DictEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictResult;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<DictEntity> {

    DictResult getInfoById(@Param("id") String id);

    long countDict(DictQueryDTO dictQueryDTO);

    List<DictQueryResult> pageList(DictQueryDTO dictQueryDTO);

    boolean delById(@Param("delFlag") Integer delFlag, @Param("id") String id);

    DictEntity findByName(@Param("chineseName") String chineseName, @Param("workspaceId") String workspaceId, @Param("createUserId") String createUserId);
}
