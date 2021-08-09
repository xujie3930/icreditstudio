package com.jinninghui.datasphere.icreditstudio.modules.system.form.web.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormModelVo implements Serializable {

    private String config;

    private List<FormElementVo> list;

    private List<ElementPermVo> authList;
}
