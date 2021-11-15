package com.jinninghui.datasphere.icreditstudio.workspace.common.code;

import com.jinninghui.datasphere.icreditstudio.framework.systemcode.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        RESOURCE_CODE_80000001("80000001", "工作空间启用中，不能删除"),
        RESOURCE_CODE_80000002("80000002", "默认工作空间不支持删除"),
        RESOURCE_CODE_80000003("80000003", "默认工作空间不支持查询"),
        RESOURCE_CODE_80000004("80000004", "默认工作空间不支持修改"),
        ;

        public final String code;
        public final String message;

        ResourceCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static ResourceCode find(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ResourceCode value : ResourceCode.values()) {
                    if (code.equals(value.getCode())) {
                        return value;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
