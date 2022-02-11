package com.micro.cloud.modules.system.dict.vo.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel("字典类型信息 Response VO")
public class SysDictTypeRespVO extends SysDictTypeBaseVO {

    @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
    private String id;

    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    private String type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysDictTypeRespVO{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", createTime=" + createTime +
            '}';
    }
}
