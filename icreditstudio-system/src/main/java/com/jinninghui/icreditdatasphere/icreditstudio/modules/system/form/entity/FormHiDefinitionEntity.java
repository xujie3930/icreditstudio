package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 
 *
 * @author 1
 */
@Data
@TableName("ge_form_hi_definition")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class FormHiDefinitionEntity implements Serializable {
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
    private String formId;
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
	 * 
	 */
	@ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String formStatus;
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
