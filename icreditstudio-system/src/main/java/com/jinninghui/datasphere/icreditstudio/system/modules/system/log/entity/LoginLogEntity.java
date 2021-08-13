package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;

import java.io.Serializable;

import com.jinninghui.datasphere.icreditstudio.system.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author 1
 */
@Data
@TableName("ge_login_log")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class LoginLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    public static final String USER_ACCOUNT = "USER_ACCOUNT";
    public static final String USER_NAME = "USER_NAME";
    public static final String LOGIN_TIME = "LOGIN_TIME";
    public static final String USER_TOKEN = "USER_TOKEN";
    /**
     *
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String id;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userAccount;

    private String errorInfo;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String loginTime;
    /**
     * S....SUCCESS
     * F....FAILURE
     */
    @ApiModelProperty(value = "S....SUCCESS F....FAILURE")
    @ExcelProperty(value = "S....SUCCESS F....FAILURE")
    private String loginStatus;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userIp;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userToken;
    /**
     * Y.....已删除
     * N.....未删除
     */
    @ApiModelProperty(value = "Y.....已删除 N.....未删除")
    @ExcelProperty(value = "Y.....已删除 N.....未删除")
    private String logoutTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createUserId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long lastUpdateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String lastUpdateUserId;

}
