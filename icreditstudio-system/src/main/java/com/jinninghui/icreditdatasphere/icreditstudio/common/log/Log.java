package com.jinninghui.icreditdatasphere.icreditstudio.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    Type type();

    OperateType operateType();

    String extend() default "";

    enum Type {
        LOGIN("登录"),
        AUDIT("审计");

        private String desc;

        Type(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }


    }

    enum OperateType {
        ADD("C", "新增"),
        DEL("R", "删除"),
        UPDATE("U", "更新"),
        SELECT("D", "查询"),
        STATUS("S", "更新状态"),
        LOGIN("LOGIN", "登录"),
        LOGOUT("LOGOUT", "退出"),
        ;
        private String code;
        private String desc;

        OperateType(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }
    }

    enum Extend {
        USER("用户"),
        ROLE("角色"),
        DICT("字典"),
        RESOURCES("模块"),
        INTERFACE("接口"),
        ORGANIZATION("部门"),
        ACCOUNT("账户");

        Extend(String desc) {
            this.desc = desc;
        }

        private String desc;

        public String getDesc() {
            return desc;
        }
    }
}
