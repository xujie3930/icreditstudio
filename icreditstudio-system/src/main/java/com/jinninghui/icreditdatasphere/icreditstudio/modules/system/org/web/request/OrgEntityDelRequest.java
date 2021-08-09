package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author hzh
 */
@Data
public class OrgEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    private Set<String> ids;

    private String accessUserId;

}
