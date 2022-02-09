package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.common;

import com.jinninghui.datasphere.icreditstudio.framework.systemcode.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lmh
 */
@SystemCode
public class DictResourceCodeBean {

    public enum ResourceCode {

        RESOURCE_CODE_60000000("60000000", "工作空间ID为空"),
        RESOURCE_CODE_60000077("60000077", "字典列的key不能为空"),
        RESOURCE_CODE_60000078("60000078", "字典列的key不能重复"),
        RESOURCE_CODE_60000079("60000079", "导入字典表时文件不能为空"),
        RESOURCE_CODE_60000080("60000080", "字典表的ID不能为空"),
        RESOURCE_CODE_60000081("60000081", "字典表英文名只能包含 字母和下划线，并且长度在50以内"),
        RESOURCE_CODE_60000082("60000082", "字典表中文名只能包含 中文，并且长度在50以内"),
        RESOURCE_CODE_60000083("60000083", "字典表描述的长度在250以内"),
        RESOURCE_CODE_60000084("60000084", "字典表列中的key只能包含中文、字母、数字，并且长度在40以内"),
        RESOURCE_CODE_60000085("60000085", "字典表列中的value只能包含中文、字母、数字，并且长度在40以内"),
        RESOURCE_CODE_60000086("60000086", "字典表列中的备注的长度在200以内"),
        RESOURCE_CODE_60000087("600000867", "文件中没有实际的有效内容"),
        RESOURCE_CODE_60000091("60000091", "字典表中文名不能重复")
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
