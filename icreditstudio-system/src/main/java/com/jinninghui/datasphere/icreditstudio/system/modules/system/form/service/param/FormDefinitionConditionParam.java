package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.param;

import com.jinninghui.datasphere.icreditstudio.system.common.enums.FormStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormDefinitionConditionParam {

    private String id;

    private String formName;

    private FormStatusEnum formStatus;

    private String formVersion;
}
