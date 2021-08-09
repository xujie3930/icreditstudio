package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity;

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

import java.io.Serializable;

/**
 * 表单定义模板表
 *
 * @author 1
 */
@Data
@TableName("ge_form_definition")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class FormDefinitionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID = "ID";
    public static final String FORM_NAME = "FORM_NAME";
    public static final String FORM_STATUS = "FORM_STATUS";
    public static final String FORM_VERSION = "FORM_VERSION";
    public static final String FORM_PUBLISH_TIME = "FORM_PUBLISH_TIME";
    public static final String CREATE_TIME = "CREATE_TIME";
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
    private String formName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private Long formPublishTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private Long formUpdateTime;
    /**
     * (0 未发布1已发布)
     */
    @ApiModelProperty(value = "(0 未发布1已发布)")
    @ExcelProperty(value = "(0 未发布1已发布)")
    private String formStatus;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String formVersion;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String formDesc;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userId;
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
