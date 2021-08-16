package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表单定义模板表
 *
 * @author 1
 */
@Mapper
public interface FormDefinitionMapper extends BaseMapper<FormDefinitionEntity> {

    void updateStatusByIds(@Param("ids") List<String> ids, @Param("status") String status);
}
