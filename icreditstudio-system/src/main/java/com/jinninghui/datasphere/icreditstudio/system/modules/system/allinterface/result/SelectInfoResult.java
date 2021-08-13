package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result;

import lombok.Data;

/**
 * @author hzh
 * @description
 * @date 2021/3/3 10:00
 */
@Data
public class SelectInfoResult {
    private String id;
    private String name;
    private String sonNum;
    private String roleName;
    private String parentId;
    private String roleRemark;
    private Long createTime;
    private String deleteFlag;
    private String operateFlag;
    private boolean currOrg;
    private SelectInfoResult parent;
}
