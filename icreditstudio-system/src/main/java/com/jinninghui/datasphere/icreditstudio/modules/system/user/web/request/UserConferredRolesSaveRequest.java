package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Pengpai on 2021/5/19 15:53
 */
@Data
public class UserConferredRolesSaveRequest {
    /**
     * 角色id集合
     */
    @NotNull(message = "roleIds不能为null")
    private List<String> roleIds;
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为null")
    private String userId;
}
