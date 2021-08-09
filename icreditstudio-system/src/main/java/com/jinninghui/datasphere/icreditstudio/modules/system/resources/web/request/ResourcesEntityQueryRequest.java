package com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.request;


import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 *
 *
 * @author hzh
 */
@Data
public class ResourcesEntityQueryRequest extends ResourcesEntity {
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
//    @NotNull(message = "菜单名称不能为空")
    private String name;

    private Set<String> ids;

    private String deleteFlag;
    private String userId;

    private String accessUserId;
}
