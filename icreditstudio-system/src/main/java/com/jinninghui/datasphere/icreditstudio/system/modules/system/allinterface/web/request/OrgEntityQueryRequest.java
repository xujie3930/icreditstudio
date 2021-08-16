package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzh
 * @description
 * @date 2021/3/9 13:39
 */
@Data
public class OrgEntityQueryRequest {

    @ApiModelProperty(value = "用户编码")
    private String userId;

    @ApiModelProperty(value = "删除标志位")
    private String deleteFlag;
}
