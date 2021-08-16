package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author hzh
 */
@Data
public class ResEntityDelRequest {


    /**
     * 资源ids
     */
    @ApiModelProperty(value = "资源ids")
    @NotEmpty(message = "50009347")
    private Set<String> ids;

}
