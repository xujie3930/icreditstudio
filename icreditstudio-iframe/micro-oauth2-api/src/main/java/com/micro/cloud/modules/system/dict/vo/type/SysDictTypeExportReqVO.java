package com.micro.cloud.modules.system.dict.vo.type;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("字典类型分页列表 Request VO")
public class SysDictTypeExportReqVO {

    @ApiModelProperty(value = "字典类型名称", example = "芋道", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "字典类型", example = "sys_common_sex", notes = "模糊匹配")
    private String type;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Override
    public String toString() {
        return "SysDictTypeExportReqVO{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", status=" + status +
            ", beginCreateTime=" + beginCreateTime +
            ", endCreateTime=" + endCreateTime +
            '}';
    }
}
