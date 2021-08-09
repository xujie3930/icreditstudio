package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.interfaces.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/11 18:04
 */
@Data
@Builder
public class InterfacesEntityConditionParam {

    private String id;

    private Set<String> ids;
}
