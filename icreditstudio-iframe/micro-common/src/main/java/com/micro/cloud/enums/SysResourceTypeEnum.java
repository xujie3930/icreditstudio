package com.micro.cloud.enums;

/**
 * 菜单类型枚举类
 *
 * @author roy
 */

public enum SysResourceTypeEnum {

    DIR(1), // 目录
    MENU(2), // 菜单
    BUTTON(3) // 按钮
    ;

    SysResourceTypeEnum(Integer type) {
        this.type = type;
    }

    /**
     * 类型
     */
    private final Integer type;

    public Integer getType() {
        return type;
    }
}
