package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserEntityInfoResult {
    private String id;
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
    private String userGender;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    private String deleteFlag;

    private String photo = "";
    /**
     * 备注
     */
    private String userRemark;
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

    private Set<String> orgNames;

    private Set<String> orgIds;

    private String functionalAuthority;//功能权限

    private String dataAuthority;//数据权限

    private String roleId;

    private String roleName;
}
