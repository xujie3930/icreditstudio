package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hzh
 */
@Data
public class UserChangeStatusRequest {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "50000021")
    private String userId;

    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "用户状态 Y 启用 N 禁用")
    @NotBlank(message = "50009331")
    private String deleteFlag;

}
