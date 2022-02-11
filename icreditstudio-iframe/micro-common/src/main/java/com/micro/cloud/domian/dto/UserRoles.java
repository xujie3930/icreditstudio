package com.micro.cloud.domian.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈用户角色信息〉
 *
 * @author roy
 * @create 2021/12/1
 * @since 1.0.0
 */
public class UserRoles implements Serializable {

  private static final long serialVersionUID = 2064740709520300314L;

  @ApiModelProperty(value = "角色编号", required = true, example = "1024")
  private String id;

  @ApiModelProperty(value = "角色名称", required = true, example = "admin")
  private String name;

  @ApiModelProperty(value = "角色类型", required = true, example = "1")
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
        return "UserRoles{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", type=" + type +
            '}';
    }
}
