package com.jinninghui.datasphere.icreditstudio.modules.system.user.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.hibernate.validator.constraints.Length;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hzh
 */
@Data
@TableName("ge_user")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String USER_NAME = "USER_NAME";
    public static final String TEL_PHONE = "TEL_PHONE";
    public static final Integer SORT_NUMBER = 0;
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    @ExcelProperty(value = "用户名称")
    private String userName;
    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
//    @ExcelProperty(value = "工号")
    private String userCode;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    //@ExcelProperty(value = "性别")
    private String userGender;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(strategy = FieldStrategy.IGNORED)
    //@ExcelProperty(value = "生日")
    private String userBirth;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
//    @ExcelProperty(value = "身份证号")
    private String idCard;
    /**
     * 入职时间
     */
    @ApiModelProperty(value = "入职时间")
//    @ExcelProperty(value = "入职时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    /**
     * 离职时间
     */
    @ApiModelProperty(value = "离职时间")
//    @ExcelProperty(value = "离职时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departureTime;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
//    @ExcelProperty(value = "邮箱")
    private String eMail;
    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
//    @ExcelProperty(value = "职位")
    private String userPosition;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    @ExcelProperty(value = "联系方式")
    private String telPhone;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "用户状态")
    //@ExcelProperty(value = "用户状态")
    private String deleteFlag;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
//    @ExcelProperty(value = "头像")
//    private String picturePath;
    private InputStream picturePath;

    @TableField(exist = false)
    private String photo = "";
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    @Length(max = 200, message = "用户备注超过最大长度")
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
    @TableField(exist = false)
    private String tempOrg;

    @ApiModelProperty(value = "用户字号")
    private String fontSize;

    @ApiModelProperty(value = "用户主题")
    private String cssId;

    @ApiModelProperty(value = "用户布局")
    private String layout;

    @ApiModelProperty(value = "是否启用自定义菜单")
    private String enableCustomMenu;
}
