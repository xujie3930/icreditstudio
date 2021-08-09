package com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InterfacesEntitySaveRequest {
    private String interfaceId;

    @NotBlank(message = "50009357")
    private String method;
    @NotBlank(message = "50009360")
    private String module;
    @NotBlank(message = "50009358")
    private String name;
    @NotNull(message = "50009359")
    private Integer needAuth;
    @Length(max = 200, message = "50002006")
    private String remark;
    @NotBlank(message = "50009361")
    private String supportAuthType;
    @NotBlank(message = "50009362")
    private String uri;
    @NotBlank(message = "50009363")
    private String uriType;
}
