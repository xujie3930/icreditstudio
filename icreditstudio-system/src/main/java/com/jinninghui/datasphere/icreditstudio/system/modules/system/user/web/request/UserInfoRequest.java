package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author hzh
 */
@Data
@Builder
public class UserInfoRequest {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "用户状态 Y 启用 N 禁用")
    private String deleteFlag;

}
