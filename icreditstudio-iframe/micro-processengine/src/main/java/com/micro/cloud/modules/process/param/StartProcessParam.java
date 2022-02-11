package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;

/**
 * 〈启动流程引擎请求参数〉
 *
 * @author roy
 * @create 2021/12/9
 * @since 1.0.0
 */
@ApiModel(value = "启动流程实例参数")
public class StartProcessParam implements Serializable {

  private static final long serialVersionUID = -3535121940185030459L;

  @ApiModelProperty(value = "流程识别码", required = true, example = "process_key", notes = "")
  private String processKey;

  @ApiModelProperty(value = "流程表单数据", required = true, example = "ngr:xxx", notes = "")
  private Map<String, Object> formData;

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, Object> formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        return "StartProcessParam{" +
            "processKey='" + processKey + '\'' +
            ", formData=" + formData +
            '}';
    }
}
