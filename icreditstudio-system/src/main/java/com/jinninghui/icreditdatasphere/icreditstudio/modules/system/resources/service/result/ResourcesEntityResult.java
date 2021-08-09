package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.resources.service.result;

import lombok.Data;

@Data
public class ResourcesEntityResult {
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 编号
     */
    private String code;
    /**
     * 父菜单id
     */
    private String parentId;
    /**
     * 父菜名称
     */
    private String parentName;
    /**
     * 路由路径(B 按钮类型URL为后端请求路径)
     */
    private String url;
    /**
     * 权限标识
     */
    private String authIdentification;
    /**
     * 重定向路径
     */
    private String redirectPath;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 是否在左侧菜单显示 Y 是  N 否
     */
    private String isShow;
    /**
     * 是否缓存 Y...是 N...否
     */
    private String isCache;
    /**
     * 是否活着 Y...是 N...否
     */
    private String keepAlive;
    /**
     * 图标
     */
    private String iconPath;
    /**
     * 排序字段
     */
    private Integer sortNumber;
    /**
     * 内部或者外部
     */
    private String internalOrExternal;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 菜单类型 M.菜单 D.顶部模块 B按钮
     */
    private String type;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    private String deleteFlag;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 创建者id
     */
    private String createUserId;
    /**
     * 更新时间
     */
    private Long lastUpdateTime;
    /**
     * 更新者id
     */
    private String lastUpdateUserId;

    private String operateFlag;

    private String needAuth;
}
