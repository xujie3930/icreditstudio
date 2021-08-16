package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzh
 */
@Data
public class UserAccountRequestParams {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 证书 密码
     */
    @ApiModelProperty(value = "旧密码")
    private String oldPassWord;

    /**
     * 密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPassWord;


}
