package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ElementPermVo implements Serializable {

    private String model;

    private UserElePerm permission;
}
