package com.micro.cloud.modules.system.user.vo;

import com.micro.cloud.domian.dto.UserRoles;
import com.micro.cloud.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * @author roy
 * @ApiModel(value = "用户信息 Response VO", description = "相比个人用户基本信息来说，会多角色、所属组织")
 */
@ApiModel(value = "用户个人信息")
public class CommonUserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", example = "234543212345643")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "用户名", example = "wangwei")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "手机号码", example = "15601691300")
    @Length(min = 11, max = 11, message = "手机号长度必须 11 位")
    @Mobile
    private String phone;

    @ApiModelProperty(value = "用户邮箱", example = "roy@xxxx.cn")
    @Email(message = "邮箱格式不正确")
    @Length(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @ApiModelProperty(value = "真实姓名", example = "18552443033")
    private String realName;

    @ApiModelProperty(name = "身份证号")
    private String certificateNum;

    @ApiModelProperty(value = "用户职业", notes = "取字典表value")
    private String profession;

    @ApiModelProperty(value = "用户行业", example = "取字典表value")
    private String industry;

    @ApiModelProperty(
            value = "用户类型",
            example = "取字典表value",
            notes = "用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户")
    private String type;

    @ApiModelProperty(value = "用户角色", example = "角色信息数组")
    private List<UserRoles> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "CommonUserInfoVo{"
                + "userId='"
                + userId
                + '\''
                + ", username='"
                + username
                + '\''
                + ", phone='"
                + phone
                + '\''
                + ", email='"
                + email
                + '\''
                + ", realName='"
                + realName
                + '\''
                + ", certificateNum='"
                + certificateNum
                + '\''
                + ", profession='"
                + profession
                + '\''
                + ", industry='"
                + industry
                + '\''
                + ", type='"
                + type
                + '\''
                + ", roles='"
                + roles
                + '\''
                + '}';
    }
}
