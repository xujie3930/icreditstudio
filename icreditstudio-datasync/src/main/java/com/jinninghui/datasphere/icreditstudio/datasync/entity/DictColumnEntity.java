package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "icredit_dict_column")
public class DictColumnEntity {

    private String id;
    private String dictId;
    private String columnKey;
    private String columnValue;
    private String remark;
    private Integer delFlag;//0 -- 未删除，1 -- 已删除

}
