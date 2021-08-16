package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.system.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * 
 *
 * @author 1
 */
@Data
@TableName("ge_code_info")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class CodeInfoEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CODE_NAME = "CODE_NAME";
	public static final String CODE_SORT = "CODE_SORT";
	/**
	 * 主键
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
    private String id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String codeName;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
    @ExcelProperty(value = "类型")
    private String codeType;
	/**
	 * 值
	 */
	@ApiModelProperty(value = "值")
    @ExcelProperty(value = "值")
    private String codeValue;
	/**
	 * 说明
	 */
	@ApiModelProperty(value = "说明")
    @ExcelProperty(value = "说明")
    private String codeRemark;
	/**
	 * 排序字段
	 */
	@ApiModelProperty(value = "排序字段")
    @ExcelProperty(value = "排序字段")
    private String codeSort;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
    private Long createTime;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
    private String createUserId;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
    private Long lastUpdateTime;
	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
    private String lastUpdateUserId;
	/**
	 * Y.....已删除
            N.....未删除
	 */
	@ApiModelProperty(value = "Y已删除 N未删除")
    @ExcelProperty(value = "Y已删除 N未删除")
    private String deleteFlag;

}
