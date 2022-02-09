package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.dto;

import lombok.Data;

@Data
public class DictQueryDTO {

    private String dictName;
    private String userId;
    private String workspaceId;
    private int pageNum = 1;
    private int pageSize = 10;

}
