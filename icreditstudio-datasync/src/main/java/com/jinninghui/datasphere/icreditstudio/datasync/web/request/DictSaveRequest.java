package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import java.util.List;

@Data
public class DictSaveRequest {

    private String id;
    private String englishName;
    private String chineseName;
    private String createUserId;
    private String dictDesc;
    private List<DictColumnSaveRequest> dictColumns;

}
