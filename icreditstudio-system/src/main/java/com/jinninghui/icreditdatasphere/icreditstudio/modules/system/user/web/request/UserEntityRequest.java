package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request;


import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hzh
 */
@Data
public class UserEntityRequest extends UserEntity {

    /**
     * 角色id集合
     */
    @ApiModelProperty(value = "角色id集合")
    private List<String> roleList;

    /**
     * 组织机构id集合
     */
    @ApiModelProperty(value = "组织机构id集合")
    private List<String> orgList;

    /**
     * 证书 密码
     */
    @ApiModelProperty(value = "密码")
    @NotNull(message = "50008014")
    private String accountCredential;
    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名")
    @NotNull(message = "50008012")
    private String accountIdentifier;

}
