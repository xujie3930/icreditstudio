package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author 1
 */
@Data
public class CodeInfoEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    @NotNull(message = "10000000")
    private Set<String> ids;

}
