package com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.entity;

import java.io.InputStream;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.hibernate.validator.constraints.Length;

/**
 * @author 1
 */
@Data
@TableName("ge_system_settings")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(
        horizontalAlignment = HorizontalAlignment.CENTER,
        borderTop = BorderStyle.THIN,
        borderBottom = BorderStyle.THIN,
        borderLeft = BorderStyle.THIN,
        borderRight = BorderStyle.THIN)
public class SystemSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     *
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String id;
    /**
     *
     */
    @ApiModelProperty(value = "系统logo")
    @ExcelProperty(value = "系统logo")
    private InputStream logoPath;
    /**
     *
     */
    @ApiModelProperty(value = "系统名称")
    @ExcelProperty(value = "系统名称")
    @Length(max = 40, message = "50009366")
    private String appName;
    /**
     *
     */
    @ApiModelProperty(value = "版权信息")
    @ExcelProperty(value = "版权信息")
    @Length(max = 100, message = "50009365")
    private String copyRight;
    /**
     *
     */
    @ApiModelProperty(value = "默认字号")
    @ExcelProperty(value = "默认字号")
    private String defaultFontSize;
    /**
     *
     */
    @ApiModelProperty(value = "默认主题")
    @ExcelProperty(value = "默认主题")
    private String defaultCssId;

    @ApiModelProperty(value = "默认布局")
    @ExcelProperty(value = "默认布局")
    private String defaultLayout;

    @ApiModelProperty(value = "应用参数1")
    @ExcelProperty(value = "应用参数1")
    private String paramOne;
    /**
     *
     */
    @ApiModelProperty(value = "应用参数2")
    @ExcelProperty(value = "应用参数2")
    private String paramTwo;
    /**
     * Y.....已删除 N.....未删除
     */
    @ApiModelProperty(value = "应用参数3")
    @ExcelProperty(value = "应用参数3")
    private String paramThree;
    /**
     *
     */
    @ApiModelProperty(value = "应用参数4")
    @ExcelProperty(value = "应用参数4")
    private String paramFour;
    /**
     *
     */
    @ApiModelProperty(value = "应用参数5")
    @ExcelProperty(value = "应用参数5")
    private String paramFive;
    /**
     *
     */
    @ApiModelProperty(value = "应用参数6")
    @ExcelProperty(value = "应用参数6")
    private String paramSix;
    /**
     *
     */
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    /**
     *
     */
    @ApiModelProperty(value = "创建人id")
    private String createUserId;
    /**
     *
     */
    @ApiModelProperty(value = "最新更新时间")
    private Long lastUpdateTime;
    /**
     *
     */
    @ApiModelProperty(value = "最新更新用户id")
    private String lastUpdateUserId;
}
