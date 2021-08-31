package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xujie
 */
@Data
public class LikeQueryUserRoleRequest {


    /**
     * 姓名，账号，或者手机号
     */
    @ApiModelProperty(value = "姓名")
    private String name;


}
