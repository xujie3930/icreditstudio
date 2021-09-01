package com.jinninghui.datasphere.icreditstudio.datasync.common;

import com.jinninghui.datasphere.icreditstudio.framework.systemcode.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),

        RESOURCE_CODE_60000000("60000000", "工作空间ID为空"),
        RESOURCE_CODE_60000001("60000001", "目标库名称为空"),
        RESOURCE_CODE_60000002("60000002", "宽表名称为空"),
        RESOURCE_CODE_60000003("60000003", "数据源ID为空"),
        RESOURCE_CODE_60000004("60000004", "数据源方言为空"),
        RESOURCE_CODE_60000005("60000005", "数据源表为空"),
        RESOURCE_CODE_60000006("60000006", "未获取数据源连接"),
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
