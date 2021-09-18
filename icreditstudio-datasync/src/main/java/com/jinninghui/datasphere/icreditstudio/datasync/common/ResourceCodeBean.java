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
        RESOURCE_CODE_60000007("60000007", "任务状态只能是0、1、2、3"),
        RESOURCE_CODE_60000008("60000008", "执行状态只能是0、1、2、3"),
        RESOURCE_CODE_60000009("60000009", "任务名称为空"),
        RESOURCE_CODE_60000010("60000010", "任务启用状态为空"),
        RESOURCE_CODE_60000011("60000011", "任务创建方式为空"),
        RESOURCE_CODE_60000012("60000012", "不能大于255个字符"),
        RESOURCE_CODE_60000013("60000013", "数据源分类为空"),
        RESOURCE_CODE_60000014("60000014", "宽表字段为空"),
        RESOURCE_CODE_60000015("60000015", "启用停用状态只能是0、1"),
        RESOURCE_CODE_60000016("60000016", "任务ID为空"),
        RESOURCE_CODE_60000017("60000017", "数据源分类只能是0、1、2"),
        RESOURCE_CODE_60000018("60000018", "连接表为空"),
        RESOURCE_CODE_60000019("60000019", "宽表ID为空"),
        RESOURCE_CODE_60000020("60000020", "识别宽表失败"),
        RESOURCE_CODE_60000021("60000021", "调用步骤不能为空"),
        RESOURCE_CODE_60000022("60000022", "任务名称不大于15个字符"),
        RESOURCE_CODE_60000023("60000023", "任务描述不大于255个字符"),
        RESOURCE_CODE_60000024("60000024", "生成宽表sql为空"),
        RESOURCE_CODE_60000025("60000025", "未匹配到合适数据源"),
        RESOURCE_CODE_60000026("60000026", "未找到合适的格式化器"),
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
