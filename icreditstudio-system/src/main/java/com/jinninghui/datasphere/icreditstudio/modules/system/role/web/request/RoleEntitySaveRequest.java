package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author hzh
 */
@Data
public class RoleEntitySaveRequest {

    private String id;

    private String accessUserId;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "50009001")
    @Length(max = 20, message = "50009350")
    private String roleName;
    /**
     * 启用标识
     */
    @NotBlank(message = "50009331")
    private String deleteFlag;
    /**
     * 上级角色id
     */
    @NotBlank(message = "50009016")
    private String parentId;
    /**
     * 角色描述
     */
    @Length(max = 200, message = "50000011")
    private String roleRemark;
}
