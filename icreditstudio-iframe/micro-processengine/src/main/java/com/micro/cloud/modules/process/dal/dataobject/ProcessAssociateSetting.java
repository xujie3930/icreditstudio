package com.micro.cloud.modules.process.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程关联设置实体类〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
@TableName(value = "process_associate_setting", autoResultMap = true)
public class ProcessAssociateSetting extends BaseDO implements Serializable {

  private static final long serialVersionUID = 6923597361634074036L;

  @ApiModelProperty(value = "流程关联设置id")
  private String processAssociateSettingId;

  @ApiModelProperty(value = "流程识别码")
  private String processKey;

  @ApiModelProperty(value = "被关联流程识别码")
  private String associatedProcessKey;

  @ApiModelProperty(value = "是否结束")
  private Boolean isFinish;

  public String getProcessAssociateSettingId() {
    return processAssociateSettingId;
  }

  public void setProcessAssociateSettingId(String processAssociateSettingId) {
    this.processAssociateSettingId = processAssociateSettingId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getAssociatedProcessKey() {
    return associatedProcessKey;
  }

  public void setAssociatedProcessKey(String associatedProcessKey) {
    this.associatedProcessKey = associatedProcessKey;
  }

  public Boolean getFinish() {
    return isFinish;
  }

  public void setFinish(Boolean finish) {
    isFinish = finish;
  }
}
