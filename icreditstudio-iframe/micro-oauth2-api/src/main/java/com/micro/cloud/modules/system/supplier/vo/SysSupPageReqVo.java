package com.micro.cloud.modules.system.supplier.vo;

import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description: 下级部门列表分页
 * @author: zlj
 * @create: 2021-12-21 4:00 下午
 */
@ApiModel("下级部门列表 Request VO")
public class SysSupPageReqVo extends PageParam implements Serializable {

    @ApiModelProperty(value = "上级部门id", example = "工程建设中心", notes = "没有则为空")
    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysSupPageReqVo{" +
                "parentId='" + parentId + '\'' +
                '}';
    }
}
