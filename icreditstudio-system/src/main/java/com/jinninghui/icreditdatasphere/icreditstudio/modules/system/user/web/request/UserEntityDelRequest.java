package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author hzh
 */
@Data
public class UserEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    @NotEmpty(message = "50009347")
    private Set<String> ids;

}
