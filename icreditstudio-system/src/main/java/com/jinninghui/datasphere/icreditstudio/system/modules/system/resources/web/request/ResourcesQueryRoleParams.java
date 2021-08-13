package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hzh
 */
@Data
public class ResourcesQueryRoleParams {

    @NotNull(message = "资源编码不能为空！")
    @ApiModelProperty(value = "资源id集合")
    private List<String> resourcesIdList;

}
