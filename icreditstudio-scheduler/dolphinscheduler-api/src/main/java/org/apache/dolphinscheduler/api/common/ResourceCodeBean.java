package org.apache.dolphinscheduler.api.common;

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

        RESOURCE_CODE_60000000("60000000", "工作流定义不存在"),
        RESOURCE_CODE_60000001("60000001", "工作流定义不是上线状态"),
        RESOURCE_CODE_60000002("60000002", "开始时间不能和结束时间一样"),
        RESOURCE_CODE_60000003("60000003", "请求参数无效"),
        RESOURCE_CODE_60000004("60000004", "任务ID不能为空"),
        RESOURCE_CODE_60000005("60000005", "任务执行类型必须为【0：重跑，1：终止】"),
        RESOURCE_CODE_60000006("60000006", "该任务下没有实例"),
        RESOURCE_CODE_60000007("60000007", "无法找到对应的流程定义，任务执行失败"),
        RESOURCE_CODE_60000008("60000008", "该任务不在【执行中】，无法终止"),
        RESOURCE_CODE_60000009("60000009", "该任务已在 【执行中】，不能重跑，请等待执行完再重跑"),
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
