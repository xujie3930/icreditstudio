package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hzh
 */
@Data
public class UserAccountEntityResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 账户是否已过期 Y.过期 N.未过期
     */
    @ApiModelProperty(value = "账户是否已过期 Y.过期 N.未过期")
    private Long accountExpired;
    /**
     * 是否锁定 Y.锁定 N.未锁定
     */
    @ApiModelProperty(value = "是否锁定 Y.锁定 N.未锁定")
    private Integer accountLocked;
    /**
     * 证书
     */
    @ApiModelProperty(value = "证书")
    private String accountCredential;
    /**
     * 账户标识
     */
    @ApiModelProperty(value = "账户标识")
    private String accountIdentifier;
    /**
     * 证书过期时间
     */
    @ApiModelProperty(value = "证书过期时间")
    private Long credentialExpired;
    /**
     * 标识类型  1:username,2:email,3:phone,4:wechat,5:qq
     */
    @ApiModelProperty(value = "标识类型  1:username,2:email,3:phone,4:wechat,5:qq")
    private String identityType;
    /**
     * 最近登陆ip
     */
    @ApiModelProperty(value = "最近登陆ip")
    private String lastLoginIp;
    /**
     * 最近登陆时间
     */
    @ApiModelProperty(value = "最近登陆时间")
    private String lastLoginTime;
    /**
     * 登陆模式
     */
    @ApiModelProperty(value = "登陆模式")
    private Integer loginMode;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    private String deleteFlag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
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
