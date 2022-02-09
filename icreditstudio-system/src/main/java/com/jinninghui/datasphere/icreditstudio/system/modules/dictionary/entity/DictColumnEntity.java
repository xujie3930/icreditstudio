package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "icredit_dict_column")
public class DictColumnEntity {

    public static final String COLUMN_KEY = "column_key";
    public static final String ID = "id";
    public static final String DICT_ID = "dict_id";
    private String id;
    private String dictId;
    private String columnKey;
    private String columnValue;
    private String remark;
    private Integer delFlag;//0 -- 未删除，1 -- 已删除

}
