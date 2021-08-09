package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author hzh
 */
@Data
public class UserAccountResetParams {


    /**
     * 用户ids
     */
    @ApiModelProperty(value = "用户ids")
    private Set<String> userIdList;


}
