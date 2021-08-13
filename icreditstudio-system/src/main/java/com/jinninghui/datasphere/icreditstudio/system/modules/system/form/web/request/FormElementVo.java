package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormElementVo implements Serializable {

    private String id;

    private String model;

    private String desc;

    private String eleJson;
}
