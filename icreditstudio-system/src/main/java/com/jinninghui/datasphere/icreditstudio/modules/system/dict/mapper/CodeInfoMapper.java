package com.jinninghui.datasphere.icreditstudio.modules.system.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.result.CodeInfoResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 * @author 1
 */
@Mapper
public interface CodeInfoMapper extends BaseMapper<CodeInfoEntity> {

    List<CodeInfoResult> getInfoByKey(@Param("key") String key);
}
