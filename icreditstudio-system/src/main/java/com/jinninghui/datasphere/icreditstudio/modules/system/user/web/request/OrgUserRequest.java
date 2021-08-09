package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzh
 */
@Data
public class OrgUserRequest {
    /**
     * 组织机构id
     */
    @ApiModelProperty(value = "组织机构id")
    private String orgId;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String orgCode;

    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "状态 Y 启用 N 禁用")
    private String deleteFlag;

}
