package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 1
 */
@Data
public class AuditLogEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    private List<String> ids;

}
