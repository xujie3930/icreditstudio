package com.micro.cloud.modules.process.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程关联关系实体类〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
@TableName(value = "process_associated", autoResultMap = true)
public class ProcessAssociated extends BaseDO implements Serializable {

  private static final long serialVersionUID = -1093272860802997706L;

  @ApiModelProperty(value = "关联关系id")
  private String processAssociateId;

  @ApiModelProperty(value = "流程id", notes = "设定与某条流程关联")
  private String processInstanceId;

  @ApiModelProperty(value = "被关联流程实例id")
  private String associateProcessInstanceId;

  @ApiModelProperty(value = "查询条件")
  @TableField("associate_detail_condition")
  private String condition;

  public String getProcessAssociateId() {
    return processAssociateId;
  }

  public void setProcessAssociateId(String processAssociateId) {
    this.processAssociateId = processAssociateId;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getAssociateProcessInstanceId() {
    return associateProcessInstanceId;
  }

  public void setAssociateProcessInstanceId(String associateProcessInstanceId) {
    this.associateProcessInstanceId = associateProcessInstanceId;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  @Override
  public String toString() {
    return "ProcessAssociated{" +
        "processAssociateId='" + processAssociateId + '\'' +
        ", processInstanceId='" + processInstanceId + '\'' +
        ", associateProcessInstanceId='" + associateProcessInstanceId + '\'' +
        ", condition='" + condition + '\'' +
        '}';
  }
}
