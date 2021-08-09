package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormHiDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 * @author 1
 */
@Mapper
public interface FormHiDefintionMapper extends BaseMapper<FormHiDefinitionEntity> {

    void updateStatusByFormDefiIds(@Param("formIds") List<String> formIds, @Param("status") String status);

    void updateHiFormStatusById(@Param("id") String id, @Param("status") String status);
}
