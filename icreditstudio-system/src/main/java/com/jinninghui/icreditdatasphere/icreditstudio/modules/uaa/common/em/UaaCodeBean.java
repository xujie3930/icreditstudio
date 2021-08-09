package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.common.em;

import com.hashtech.businessframework.system.code.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * UAA错误码
 *
 * @author jidonglin
 */
@SystemCode
public class UaaCodeBean {

    public enum UaaCode {
        /**
         * UAA错误码
         */
        FAIL("UAA01", "系统内部错误"),
        NOT_EXSIT_TOKEN("UAA23", "令牌不存在"),
        NOT_EXIST_USER("UAA27", "无此账户"),
        PWD_OR_ACCOUNT_ERROR("UAA29","用户名或密码错误"),
        TOO_FREQUENTLY_REQUEST("UAA43", "验证码请求过于频繁"),
        INVALID_INTERFACE_ROLE("UAA47", "接口的角色鉴权不通过"),
        INVALID_AUTHCODE("UAA48", "验证码非法"),
        ERROR_LOGINPWD("UAA49", "密码错误"),


        INVALID_LOGIN_USER_STATE("UAA50", "当前用户已被禁用"),
        INVALID_PHONE_OR_EMAIL("UAA55", "手机号码或邮箱无效"),
        FAIL_GENERATE_GRAPHICODE("UAA56", "验证码生成失败"),
        INVALID_LOGIN_USER_ROLE_STATE("UAA57", "用户角色状态异常"),
        INVALID_LOGIN_USER_ORG_STATE("UAA58", "用户部门状态异常"),

        /**
         * 参数必传
         */
        MUSH_AUTHCODE("UAA61", "验证码必须"),
        NOT_NULL_SUBJECT_OR_CONTENT("UAA69", "发送邮件的主题和内容不能为空"),
        NOT_NULL_INTERFACECODE("UAA6A", "短信业务类型不能为空"),

        ;

        public final String code;
        public final String message;

        UaaCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static UaaCode find(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (UaaCode value : UaaCode.values()) {
                    if (code.equals(value.getCode())){
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
