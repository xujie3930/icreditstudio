package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FormElementConditionParam {

    private String formId;

    private List<String> models;
}
