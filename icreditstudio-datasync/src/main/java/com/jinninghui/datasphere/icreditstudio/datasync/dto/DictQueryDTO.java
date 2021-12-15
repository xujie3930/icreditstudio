package com.jinninghui.datasphere.icreditstudio.datasync.dto;

import lombok.Data;

@Data
public class DictQueryDTO {

    private String dictName;
    private String userId;
    private String spaceId;
    private int pageNum = 1;
    private int pageSize = 10;

}
