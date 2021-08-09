package com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author hzh
 */
@Data
public class OrganizationEntitySaveRequest {
    private String id;
    @NotBlank(message = "10000000")
    @Length(max = 20, message = "50009339")
    private String orgName;
    @NotBlank(message = "10000000")
    @Length(min = 0,max = 20,message = "50009339")
    private String orgCode;
    @NotBlank(message = "10000000")
    private String orgAddress;
    @NotBlank(message = "10000000")
    private String linkManName;
    @NotBlank(message = "10000000")
    private String linkManTel;
    @NotBlank(message = "10000000")
    private String parentId;
    @Length(max = 200, message = "50000011")
    private String remark;

    private Integer sortNumber;

    private String accessUserId;
}
