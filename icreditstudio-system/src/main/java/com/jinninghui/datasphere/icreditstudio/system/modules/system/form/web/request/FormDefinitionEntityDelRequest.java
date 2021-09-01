package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表单定义模板表
 *
 * @author 1
 */
@Data
public class FormDefinitionEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    private List<String> ids;

}
