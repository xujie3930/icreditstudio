package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pengpai on 2021/5/19 9:47
 */
@Data
public class RoleToUsersSaveRequest {
    /**
     * 用户id集合
     */
    @NotNull(message = "未选择用户")
    private List<String> userIds;
    /**
     * 角色id
     */
    @NotBlank(message = "未知角色")
    private String roleId;
}
