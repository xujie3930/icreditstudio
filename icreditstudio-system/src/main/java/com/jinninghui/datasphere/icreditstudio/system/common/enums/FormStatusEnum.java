package com.jinninghui.datasphere.icreditstudio.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum FormStatusEnum {
    D("D", "草稿箱"),
    P("P", "已发布"),
    C("C", "已删除"),
    T("T", "已停用");
    private String code;
    private String desc;

    public static FormStatusEnum find(String code) {
        FormStatusEnum result = null;
        if (StringUtils.isNotBlank(code)) {
            for (FormStatusEnum value : FormStatusEnum.values()) {
                if (value.code.equals(code)) {
                    result = value;
                    break;
                }
            }
        }
        return result;
    }
}
