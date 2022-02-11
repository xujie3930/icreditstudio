package com.micro.cloud.modules.system.user.vo.internal;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈系统用户简要信息〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
public class InternalUserSimpleVO implements Serializable {

    @ApiModelProperty(value = "用户id", required = true, example = "342424242")
    private String id;

    @ApiModelProperty(value = "账号名称",required = true, example = "admin")
    private String userName;

    @ApiModelProperty(value = "用户真实姓名",required = true, example = "张三")
    private String realName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "InternalUserSimpleVO{" +
            "id='" + id + '\'' +
            ", userName='" + userName + '\'' +
            ", realName='" + realName + '\'' +
            '}';
    }
}