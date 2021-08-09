package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result;

/**
 * @author liyanhui
 */
public class UserInfoResult {
    private static final long serialVersionUID = 6088779545622789413L;
    private String companyId;
    private String organizationId;
    private Long platformUserId;
    private String nodeId;
    private Integer sex;
    private String birthday;
    private String faceUrl;
    private String name;
    private String email;
    private String telPhone;
    private String position;
    private Short userType;
    private Integer userLevel;
    private String remark;
    private String userCode;
    private Long platPersonId;

    public UserInfoResult() {
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public Long getPlatformUserId() {
        return this.platformUserId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public Integer getSex() {
        return this.sex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getFaceUrl() {
        return this.faceUrl;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelPhone() {
        return this.telPhone;
    }

    public String getPosition() {
        return this.position;
    }

    public Short getUserType() {
        return this.userType;
    }

    public Integer getUserLevel() {
        return this.userLevel;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getPlatPersonId() {
        return this.platPersonId;
    }

    public void setPlatPersonId(Long platPersonId) {
        this.platPersonId = platPersonId;
    }
}
