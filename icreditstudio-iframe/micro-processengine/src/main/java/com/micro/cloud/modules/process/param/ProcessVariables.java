package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈流程变量〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@ApiModel(value = "流程变量")
public class ProcessVariables implements Serializable {

  private static final long serialVersionUID = -1101871574026037378L;

  @ApiModelProperty(value = "变量名", required = true, example = "assign", notes = "流程所需变量")
  private String paramName;

  @ApiModelProperty(
      value = "流程变量值",
      required = true,
      example = "lihui,wangwu...",
      notes = "流程变量值，比如会签时会签人员可以多选")
  private List<String> paramValues;
}
