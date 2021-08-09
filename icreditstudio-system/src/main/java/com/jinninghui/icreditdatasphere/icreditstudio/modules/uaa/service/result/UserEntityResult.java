package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hzh
 */
@Data
public class UserEntityResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String userCode;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer userGender;
    /**
     * 所在部门id
     */
    @ApiModelProperty(value = "所在部门id")
    private String orgId;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private String userBirth;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String eMail;
    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
    private String userPosition;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String telPhone;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志Y.已删除 N.未删除")
    private String deleteFlag;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String picturePath;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String userRemark;
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
    /**
     * 扩展字段1
     */
    @ApiModelProperty(value = "扩展字段1")
    private String extendOne;
    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    private String extendTwo;
    /**
     * 扩展字段3
     */
    @ApiModelProperty(value = "扩展字段3")
    private String extendThree;
    /**
     * 扩展字段4
     */
    @ApiModelProperty(value = "扩展字段4")
    private String extendFour;

}
