package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/7 16:44
 */
@Data
@Builder
public class UserEntityConditionParam {

    private Set<String> ids;

    private String name;
}
