package com.micro.cloud.enums;


/**
 * 性别的枚举值
 *
 * @author roy
 */
public enum SysSexEnum {

    /** 男 */
    MALE(1),
    /** 女 */
    FEMALE(2),
    /* 未知 */
    UNKNOWN(3);

    SysSexEnum(Integer sex) {
        this.sex = sex;
    }

    /**
     * 性别
     */
    private final Integer sex;

    public Integer getSex() {
        return sex;
    }
}
