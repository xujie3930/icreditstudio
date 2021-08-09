package com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author hzh
 */
@Data
public class InterfacesDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    @NotNull(message = "50009338")
    private Set<String> ids;

}
