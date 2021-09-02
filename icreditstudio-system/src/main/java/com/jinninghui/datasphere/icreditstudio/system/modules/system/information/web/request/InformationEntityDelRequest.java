package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 1
 */
@Data
public class InformationEntityDelRequest {

    /**
     * id集合
     */
    @ApiModelProperty(value = "id集合")
    private List<String> ids;

}
