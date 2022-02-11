package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author roy
 */
@ApiModel("角色精简信息 Response VO")
public class SysRoleSimpleRespVO implements Serializable {

    private static final long serialVersionUID = 4595179071520190851L;

    @ApiModelProperty(value = "角色编号", required = true, example = "1024")
    private String id;

    @ApiModelProperty(value = "角色名称", required = true, example = "admin")
    private String name;

    @ApiModelProperty("角色类型: 1->超管 2->数据管理员 3->外部系统用户默认角色 4 ->外部组织机构默认角色")
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysRoleSimpleRespVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
