package org.apache.dolphinscheduler.service.commom;

import com.hashtech.businessframework.system.code.SystemCode;
import org.apache.commons.lang3.StringUtils;

@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        RESOURCE_CODE_100("100", "该任务流程定义分区信息有误"),
        RESOURCE_CODE_101("101", "分区信息中的数据库方言dialect为空")
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
