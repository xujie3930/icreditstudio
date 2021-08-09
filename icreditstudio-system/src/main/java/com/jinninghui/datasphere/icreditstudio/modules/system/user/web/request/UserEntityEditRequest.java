package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;


import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result.UserOrgListResult;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author hzh
 */
@Data
public class UserEntityEditRequest {

    @NotBlank(message = "10000001")
    private String userName;

    private Integer sortNumber;

    private String userGender;

    private String userCode;

    private String userBirth;

    private String telPhone;

    private String deleteFlag;

    private String userRemark;

    /**
     * 角色id集合
     */
    private List<String> roleList;

    /**
     * 组织机构id集合
     */
    private List<UserOrgListResult> orgList;

    /**
     * 证书 密码
     */
    private String accountCredential;
    /**
     * 登录用户名
     */
    private String accountIdentifier;

}
