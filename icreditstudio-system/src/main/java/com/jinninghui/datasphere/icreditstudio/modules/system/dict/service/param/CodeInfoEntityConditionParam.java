package com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by Pengpai on 2021/6/7 23:24
 */
@Data
@Builder
public class CodeInfoEntityConditionParam {

    private Set<String> ids;
}
