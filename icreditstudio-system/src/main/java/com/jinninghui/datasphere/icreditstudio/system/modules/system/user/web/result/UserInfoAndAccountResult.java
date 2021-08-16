package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result;

import lombok.Data;

import java.util.Date;

/**
 * Created by Pengpai on 2021/6/4 12:17
 */
@Data
public class UserInfoAndAccountResult {

    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 工号
     */
    private String userCode;
    /**
     * 性别
     */
    private Integer userGender;

    /**
     * 生日
     */
    private String userBirth;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 入职时间
     */
    private Date entryTime;
    /**
     * 离职时间
     */
    private Date departureTime;
    /**
     * 邮箱
     */
    private String eMail;
    /**
     * 职位
     */
    private String userPosition;
    /**
     * 联系方式
     */
    private String telPhone;
    /**
     * 排序字段
     */
    private Integer sortNumber;
    /**
     * 删除标志Y.已删除 N.未删除
     */
    private String userDeleteFlag;

    private String photo = "";
    /**
     * 备注
     */
    private String userRemark;
    /**
     * 创建时间
     */
    private Long userCreateTime;
    /**
     * 扩展字段1
     */
    private String extendOne;
    /**
     * 扩展字段2
     */
    private String extendTwo;
    /**
     * 扩展字段3
     */
    private String extendThree;
    /**
     * 扩展字段4
     */
    private String extendFour;

    private String accountId;
    /**
     * 账户是否已过期 Y.过期 N.未过期
     */
    private Long accountExpired;
    /**
     * 是否锁定 Y.锁定 N.未锁定
     */
    private String accountLocked = "N";
    /**
     * 证书 密码
     */
    private String accountCredential;
    /**
     * 登录用户名
     */
    private String accountIdentifier;
    /**
     * 证书过期时间
     */
    private Long credentialExpired;
    /**
     * 标识类型  1:username,2:email,3:phone,4:wechat,5:qq
     */
    private String identityType = "1";
    /**
     * 最近登陆ip
     */
    private String lastLoginIp;
    /**
     * 最近登陆时间
     */
    private String lastLoginTime;
    /**
     * 登陆模式
     */
    private Integer loginMode = 0;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    private String accountDeleteFlag;
    /**
     * 创建时间
     */
    private Long accountCreateTime;
}
