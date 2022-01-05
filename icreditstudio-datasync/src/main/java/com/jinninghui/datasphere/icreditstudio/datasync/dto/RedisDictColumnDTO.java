package com.jinninghui.datasphere.icreditstudio.datasync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedisDictColumnDTO {

    private String dictId;
    private String columnKey;
    private String columnValue;

}
