package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.resources.web.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hzh
 */
@Data
public class ResChangeStatusRequest {


    /**
     * 资源ids
     */
    @ApiModelProperty(value = "资源ids")
    private List<String> resourcesIdList;

    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "资源状态 Y 启用 N 禁用")
    private String deleteFlag;

}
