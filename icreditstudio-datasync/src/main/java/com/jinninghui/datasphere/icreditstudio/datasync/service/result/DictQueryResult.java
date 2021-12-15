package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DictQueryResult {

    private String id;
    private String englishName;
    private String chineseName;
    private String createUserName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private String dictDesc;

}
