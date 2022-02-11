package com.micro.cloud.modules.system.supplier.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 设置下级部门入参
 * @author: zlj
 * @create: 2021-12-21 4:13 下午
 */
public class SysSupLowerVo implements Serializable {

    @ApiModelProperty(value = "部门id", example = "1313223",required = true)
    @NotNull(message = "部门id不能为空")
    private String orgId;

    @ApiModelProperty(value = "上级部门id", example = "12233",required = true)
    @NotNull(message = "上级id不能为空")
    private String parentId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysSupLowerVo{" +
                "orgId='" + orgId + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
