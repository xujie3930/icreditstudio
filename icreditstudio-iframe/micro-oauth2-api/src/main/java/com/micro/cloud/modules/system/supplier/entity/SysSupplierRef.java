package com.micro.cloud.modules.system.supplier.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description: 供应商部门关联实体类
 * @author: zlj
 * @create: 2021-12-21 2:18 下午
 */
@TableName("sys_supplier_hierarchy_ref")
public class SysSupplierRef {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("供应商层级关系id")
    @TableId("sys_supplier_hierarchy_ref_id")
    private String sysSupplierHierarchyRefId;

    @ApiModelProperty("供应商id")
    @TableField("sys_org_id")
    private String sysOrgId;

    @ApiModelProperty("供应商上级id")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建人id")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("更新人id")
    @TableField("update_id")
    private String updateId;

    public String getSysSupplierHierarchyRefId() {
        return sysSupplierHierarchyRefId;
    }

    public void setSysSupplierHierarchyRefId(String sysSupplierHierarchyRefId) {
        this.sysSupplierHierarchyRefId = sysSupplierHierarchyRefId;
    }

    public String getSysOrgId() {
        return sysOrgId;
    }

    public void setSysOrgId(String sysOrgId) {
        this.sysOrgId = sysOrgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    @Override
    public String toString() {
        return "SysSupplierRef{" +
                "sysSupplierHierarchyRefId='" + sysSupplierHierarchyRefId + '\'' +
                ", sysOrgId='" + sysOrgId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", createTime=" + createTime +
                ", creatorId='" + creatorId + '\'' +
                ", updateTime=" + updateTime +
                ", updateId='" + updateId + '\'' +
                '}';
    }
}
