package com.micro.cloud.mybatis.core.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体对象
 *
 * @author roy
 */
public class BaseDO implements Serializable {

  private static final long serialVersionUID = 9083582063662576011L;

  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
  /** 最后更新时间 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;

  @TableField(fill = FieldFill.INSERT)
  private String creatorId;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updaterId;

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public String getUpdaterId() {
    return updaterId;
  }

  public void setUpdaterId(String updaterId) {
    this.updaterId = updaterId;
  }

  @Override
  public String toString() {
    return "BaseDO{"
        + "createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", creatorId='"
        + creatorId
        + '\''
        + ", updaterId='"
        + updaterId
        + '\''
        + '}';
  }
}
