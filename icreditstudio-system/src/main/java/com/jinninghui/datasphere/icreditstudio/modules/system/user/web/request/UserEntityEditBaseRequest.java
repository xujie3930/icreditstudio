package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/3 17:30
 */
@Data
public class UserEntityEditBaseRequest {

    @NotBlank(message = "10000001")
    private String id;
    //    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "50009356")
    private String telPhone;

    private String userBirth;
    @Length(max = 20, message = "50009355")
    private String userCode;
    private String userGender;

    @NotBlank(message = "10000001")
    @Length(message = "50009354")
    private String userName;
    @Length(max = 200, message = "50003006")
    private String userRemark;
}
