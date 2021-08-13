package com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @author hzh
 * @description
 * @date 2021/2/26 15:16
 */
public class CommonConstant {

    /**
     * 删除标志 Y.已删除 N.未删除
     */
    public static final String DELETE_FLAG_Y = "Y";
    public static final String DELETE_FLAG_N = "N";

    /**
     * 菜单类型 M.菜单 D.顶部模块 B 按钮
     */
    public static final String MENU_TYPE_M = "M";
    public static final String MENU_TYPE_D = "D";
    public static final String MENU_TYPE_B = "B";

    /**
     * 超管角色编码为1000，数据库初始设定的
     */
    public static final String SUPER_ADMIN_ROLE_CODE = "1000";


    /**
     * 菜单类型
     */
    public static final ImmutableMap<String, String> RESOURCES_TYPE_MAP = ImmutableMap.<String, String>builder()
            .put(MENU_TYPE_M, "菜单")
            .put(MENU_TYPE_D, "顶部模块")
            .put(MENU_TYPE_B, "按钮")

            .put("菜单", MENU_TYPE_M)
            .put("顶部模块", MENU_TYPE_D)
            .put("按钮", MENU_TYPE_B)

            .build();

    /**
     * 状态位
     */
    public static final ImmutableMap<String, String> RESOURCES_STATUS_MAP = ImmutableMap.<String, String>builder()
            .put("Y", "是")
            .put("N", "否")

            .put("是", "Y")
            .put("否", "N")
            .build();

}
