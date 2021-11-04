package com.jinninghui.datasphere.icreditstudio.datasource.common;

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

        RESOURCE_CODE_70000000("70000000", "获取数据库连接失败"),
        RESOURCE_CODE_70000001("70000001", "表名称不能为空"),
        RESOURCE_CODE_70000002("70000002", "数据源ID不能为空"),
        RESOURCE_CODE_70000003("70000003", "数据库同步失败"),
        RESOURCE_CODE_70000004("70000004", "未找到合适的uri解析器"),
        RESOURCE_CODE_70000005("70000005", "工作空间ID不能为空"),
        RESOURCE_CODE_70000006("70000006", "数据源分类不能为空"),
        RESOURCE_CODE_70000007("70000007", "测试连接失败"),
        RESOURCE_CODE_70000008("70000008", "保存失败，请检查连接信息"),
        RESOURCE_CODE_70000009("70000009", "该数据源为启用状态,不能执行删除操作"),
        RESOURCE_CODE_70000010("70000010", "该数据源为禁用状态,不能执行同步操作"),
        RESOURCE_CODE_70000011("70000011", "该工作空间有启用的数据源,请停用后再进行此操作"),
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
