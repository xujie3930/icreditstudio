package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/7 16:48
 */
@Data
@Builder
public class UserAccountEntityConditionParam {

    private String userAccount;

    private Set<String> userIds;

    private String deleteFlag;
}
