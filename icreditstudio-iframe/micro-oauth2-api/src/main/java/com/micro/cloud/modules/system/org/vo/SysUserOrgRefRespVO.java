package com.micro.cloud.modules.system.org.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel("用户机构关系 Response VO")
@TableName("sys_user_org_ref")
public class SysUserOrgRefRespVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true, example = "1024")
    private String sysUserOrgId;
    @ApiModelProperty(value = "用户id", required = true, example = "1024")
    private String sysUserId;
    @ApiModelProperty(value = "机构id", required = true, example = "1024")
    private String sysOrgId;
    @ApiModelProperty(value = "创建人id", required = true, example = "1024")
    private String creatorId;
    @ApiModelProperty(value = "创建时间", required = true, example = "1024")
   private String createTime;
    @ApiModelProperty(value = "更新人id", required = true, example = "1024")
   private String updaterId;
    @ApiModelProperty(value = "更新时间", required = true, example = "1024")
  private String updateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSysUserOrgId() {
        return sysUserOrgId;
    }

    public void setSysUserOrgId(String sysUserOrgId) {
        this.sysUserOrgId = sysUserOrgId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysOrgId() {
        return sysOrgId;
    }

    public void setSysOrgId(String sysOrgId) {
        this.sysOrgId = sysOrgId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
