package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author hzh
 */
@Data
public class RoleEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    @NotEmpty(message = "50009332")
    private Set<String> ids;

    private String accessUserId;
}
