package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result;

/**
 * @author liyanhui
 */
public class RoleInfoResult {
    private static final long serialVersionUID = -3314964769044843165L;
    private String applicationId;
    private String custId;
    private String code;
    private String name;
    private Short status;
    private String remark;

    public RoleInfoResult() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustId() {
        return this.custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

