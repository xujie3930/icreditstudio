package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.resources.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/7 12:28
 */
@Data
@Builder
public class ResourcesEntityConditionParam {

    private Set<String> parentIds;

    private Set<String> ids;

    private Set<String> roleIds;

    private String name;

    private String deleteFlag;
}
