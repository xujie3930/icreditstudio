package com.micro.cloud.sms.core.enums;

/**
 * 短信模板的审核状态枚举
 *
 * @author roy
 */

public enum SmsTemplateAuditStatusEnum {
    /**
     * 短信模版审批状态
     */
    CHECKING(1),
    SUCCESS(2),
    FAIL(3);

    SmsTemplateAuditStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    private final Integer status;

}
