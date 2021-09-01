package com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.web.result;

import java.util.List;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户个性化设置返回结果
 *
 * @author EDZ
 */
@Data
public class ShortCutMenuResult {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户字号")
    private String fontSize;

    @ApiModelProperty(value = "用户主题")
    private String cssId;

    @ApiModelProperty(value = "用户布局")
    private String layoutId;

    @ApiModelProperty(value = "用户快捷菜单")
    private List<ResourcesEntity> menus;
}
