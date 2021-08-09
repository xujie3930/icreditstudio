package com.jinninghui.datasphere.icreditstudio.modules.system.setting.web.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统logo上传参数
 *
 * @author EDZ
 */
@Data
public class SystemLogoRequest {
  @ApiModelProperty(value = "系统设置id")
  private String id;

  @NotBlank(message = "10000000")
  private String userId;

  @NotBlank(message = "10000000")
  private String logo;
}
