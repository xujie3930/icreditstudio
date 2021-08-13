package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * 表单定义模板表
 *
 * @author 1
 */
@Data
public class FormDefinitionPageRequest extends BusinessBasePageForm {

    private String formName;

    private String formStatus;

    private String formVersion;
}
