package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzh
 */
@Data
public class LikeQueryUserInfoRequest {


    /**
     * 姓名，账号，或者手机号
     */
    @ApiModelProperty(value = "姓名，账号，或者手机号")
    private String name;


}
