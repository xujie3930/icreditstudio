package com.micro.cloud.modules.system.dict.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel("字典数据信息 Response VO")
public class SysDictDataRespVO extends SysDictDataBaseVO {

    @ApiModelProperty(value = "字典数据编号", required = true, example = "1024")
    private String id;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
