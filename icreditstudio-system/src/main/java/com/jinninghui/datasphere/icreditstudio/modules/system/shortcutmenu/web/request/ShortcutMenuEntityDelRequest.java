package com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.web.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 快捷菜单
 *
 * @author 1
 */
@Data
public class ShortcutMenuEntityDelRequest {

  /** id集合 */
  @ApiModelProperty(value = "id集合")
  private List<String> ids;
}
