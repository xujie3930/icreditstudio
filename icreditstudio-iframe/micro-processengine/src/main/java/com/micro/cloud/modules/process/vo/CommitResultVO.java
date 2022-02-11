package com.micro.cloud.modules.process.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈提交返回结果〉
 *
 * @author roy
 * @create 2021/12/11
 * @since 1.0.0
 */
public class CommitResultVO implements Serializable {

  @ApiModelProperty(value = "流程实例id",required = true, example = "2871d6dc-5b16-11ec-bc2b-2e28761e4d5c")
  private String processInstanceId;

  @ApiModelProperty(value = "签字意见")
  private String comment;
}
