package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import lombok.Data;

@Data
public class IcreditDatasourceUpdateStatusRequest {
    private String id;
    private Integer datasourceStatus;//0 -- 启用，1 -- 停用

}
