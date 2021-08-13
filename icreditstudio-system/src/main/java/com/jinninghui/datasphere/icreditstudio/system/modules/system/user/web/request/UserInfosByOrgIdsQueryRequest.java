package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UserInfosByOrgIdsQueryRequest {

    /**
     * 部门Id集合
     */
    @NotNull(message = "未选择部门")
    private Set<String> orgIds;

    /**
     * 删除标志Y.已删除 N.未删除 ALL.全部
     */
    private String deleteFlag;
}
