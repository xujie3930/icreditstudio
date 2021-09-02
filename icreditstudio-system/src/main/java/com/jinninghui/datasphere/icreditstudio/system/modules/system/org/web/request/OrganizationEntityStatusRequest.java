package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hzh
 */
@Data
public class OrganizationEntityStatusRequest {

    @NotBlank(message = "50000021")
    private String id;
    @NotBlank(message = "50009331")
    private String deleteFlag;

    private String accessUserId;
}
