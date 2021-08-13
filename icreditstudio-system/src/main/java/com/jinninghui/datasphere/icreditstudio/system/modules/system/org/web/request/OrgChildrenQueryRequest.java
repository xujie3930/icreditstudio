package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzh
 * @description
 * @date 2021/3/9 13:39
 */
@Data
public class OrgChildrenQueryRequest {

    /**
     * orgId
     */
    @ApiModelProperty(value = "orgId")
    private String orgId;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String orgCode;

    @ApiModelProperty(value = "删除标志位")
    private String deleteFlag;
}
