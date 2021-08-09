package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.setting.web.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** @author 1 */
@Data
public class SystemSettingsEntityDelRequest {

  /** id集合 */
  @ApiModelProperty(value = "id集合")
  private List<String> ids;
}
