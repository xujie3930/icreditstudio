package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hzh
 */
@Data
public class RoleEntityResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    /**
     * 父角色id
     */
    @ApiModelProperty(value = "父角色id")
    private String parentId;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 角色备注
     */
    @ApiModelProperty(value = "角色备注")
    private String roleRemark;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    private String deleteFlag;
    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
    private Long createTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createUserId;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Long lastUpdateTime;
    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private String lastUpdateUserId;

}
