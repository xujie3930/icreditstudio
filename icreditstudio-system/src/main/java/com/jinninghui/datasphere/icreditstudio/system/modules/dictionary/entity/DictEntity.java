package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "icredit_dict")
public class DictEntity {

    public static final String CHINESE_NAME = "chinese_name";
    public static final String WORKSPACE_ID = "workspace_id";
    public static final String DEL_FLAG = "del_flag";
    private String id;
    private String workspaceId;
    private String englishName;
    private String chineseName;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String dictDesc;
    private Integer delFlag;//0 -- 未删除，1 -- 已删除

}
