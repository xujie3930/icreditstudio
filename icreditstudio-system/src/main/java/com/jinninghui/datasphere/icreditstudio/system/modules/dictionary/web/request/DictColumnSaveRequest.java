package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request;

import lombok.Data;

@Data
public class DictColumnSaveRequest {

    private String id;
    private String dictId;
    private String columnKey;
    private String columnValue;
    private String remark;

}
