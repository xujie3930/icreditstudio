package com.micro.cloud.modules.system.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author roy
 */
@ApiModel("菜单精简信息 Response VO")
public class SysResourceSimpleRespVO {

    @ApiModelProperty(value = "菜单编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "菜单名称", required = true, example = "芋道")
    private String name;

    @ApiModelProperty(value = "父菜单 ID", required = true, example = "1024")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysResourceSimpleRespVO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", parentId=" + parentId +
            '}';
    }
}
