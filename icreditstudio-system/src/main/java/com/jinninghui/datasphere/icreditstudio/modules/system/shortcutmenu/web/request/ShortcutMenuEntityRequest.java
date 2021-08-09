package com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.web.request;

import java.util.List;

import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.entity.ShortcutMenuEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 快捷菜单
 *
 * @author 1
 */
@Data
public class ShortcutMenuEntityRequest extends ShortcutMenuEntity {

  @ApiModelProperty(value = "用户字号")
  private String fontSize;

  @ApiModelProperty(value = "用户布局")
  private String layout;

  @ApiModelProperty(value = "用户主题")
  private String cssId;

  @ApiModelProperty(value = "用户快捷菜单id")
  List<String> resourceIds;

  @ApiModelProperty(value = "用户快捷菜单")
  private List<ResourcesEntity> menus;
}
