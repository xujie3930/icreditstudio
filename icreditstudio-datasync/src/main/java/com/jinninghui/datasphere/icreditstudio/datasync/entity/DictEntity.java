package com.jinninghui.datasphere.icreditstudio.datasync.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "icredit_dict")
public class DictEntity {

    private String id;
    private String englishName;
    private String chineseName;
    private String createUserId;
    private Date createTime;
    private String dictDesc;
    private Integer delFlag;//0 -- 未删除，1 -- 已删除

}
