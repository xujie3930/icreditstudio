package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request;


import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result.UserOrgListResult;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hzh
 */
@Data
public class UserEntitySaveRequest {

    private String id;
    /**
     * 主键
     */
    private String userId;
    /**
     * 用户名字
     */
    @NotBlank(message = "50004001")
    @Length(max = 20, message = "50008003")
    private String userName;
    /**
     * 工号
     */
    @Length(max = 20, message = "50009355")
    private String userCode;
    /**
     * 性别
     */
    private String userGender;
    /**
     * 电话
     */
    private String telPhone;
    /**
     * 排序字段
     */
    private Integer sortNumber;
    /**
     * 状态
     */
    private String deleteFlag;
    /**
     * 生日
     */
    private String userBirth;
    /**
     * 备注
     */
    @Length(max = 200, message = "50003006")
    private String userRemark;
    /**
     * 组织机构id集合
     */
    @NotNull(message = "50003016")
    private List<UserOrgListResult> orgList;
    /**
     * 登录用户名
     */
    @NotBlank(message = "50008012")
    private String accountIdentifier;

    private String tempOrg;
}
