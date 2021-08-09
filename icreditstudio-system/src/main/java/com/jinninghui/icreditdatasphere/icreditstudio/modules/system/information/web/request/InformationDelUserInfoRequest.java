package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.web.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Created by PPai on 2021/6/18 11:28
 */
@Data
public class InformationDelUserInfoRequest {
    /**
     * 消息ID
     */
    @NotEmpty(message = "50009338")
    private Set<String> ids;
    /**
     * 用户ID
     */
    @NotBlank(message = "50009365")
    private String userId;
}
