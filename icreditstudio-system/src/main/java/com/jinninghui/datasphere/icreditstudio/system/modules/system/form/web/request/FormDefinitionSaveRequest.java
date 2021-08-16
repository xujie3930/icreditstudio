package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 表单定义模板表
 *
 * @author 1
 */
@Data
public class FormDefinitionSaveRequest {

    private String id;

    private String formStatus;
    @Length(max = 50, message = "50009382")
    private String formName;
    @Length(max = 200, message = "50009383")
    private String formDesc;

    private String userId;

    private boolean change;

    private FormModelVo modelEditorJson;
}
