package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

@Data
public class DictColumnSaveRequest {

    private String id;
    private String dictId;
    private String columnKey;
    private String columnValue;
    private String remark;

}
