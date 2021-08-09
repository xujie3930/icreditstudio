package com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeInfoResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String label;
    private String value;

}
