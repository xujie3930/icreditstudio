package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author 1
 */
@Data
@TableName("ge_form_hi_element")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class FormHiElementEntity implements Serializable {
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
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String formHiId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private Integer formVersion;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String elementId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String elementLable;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String paramKey;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String defJson;
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
