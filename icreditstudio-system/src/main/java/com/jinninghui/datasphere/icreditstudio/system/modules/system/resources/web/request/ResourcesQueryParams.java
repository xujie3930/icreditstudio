package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hzh
 */
@Data
public class ResourcesQueryParams {

    @NotNull()
    @ApiModelProperty(value = "角色id集合")
    private List<String> roleIdList;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    private String deleteFlag;

    /**
     * 菜单类型 M.菜单 D.顶部模块 B按钮
     */
    @ApiModelProperty(value = "菜单类型 M.菜单 D.顶部模块 B按钮")
    private String type;

}
