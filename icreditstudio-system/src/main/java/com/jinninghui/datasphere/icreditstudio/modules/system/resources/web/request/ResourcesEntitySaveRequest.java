package com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by PPai on 2021/6/7 11:15
 */
@Data
public class ResourcesEntitySaveRequest {

    private String id;

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 图标地址
     */
    private String iconPath;
    /**
     * 是否在菜单显示
     */
    private String isShow;
    /**
     * 是否保留
     */
    private String keepAlive;
    /**
     * 菜单名称
     */
    @NotBlank(message = "50009346")
    private String name;
    /**
     * 父菜单
     */
    private String parentId;
    /**
     * 重定向地址
     */
    private String redirectPath;
    /**
     * 备注
     */
    @Length(max = 200,  message = "50000011")
    private String remark;
    /**
     * 排序
     */
    private Integer sortNumber;
    /**
     * 菜单类型
     */
    @NotBlank(message = "50009345")
    private String type;
    /**
     * 路由路径
     */
    private String url;

    private String accessUserId;

    private String needAuth;

    @Length(max = 200,  message = "50000011")
    private String authIdentification;
}
