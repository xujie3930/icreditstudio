package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;
import java.util.List;


/**
 * @author hzh
 */
@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 20)
@ColumnWidth(25)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class OrganizationEntityExpert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String orgName;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "部门编码")
    private String orgCode;

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id")
    private String parentId;

    /**
     * 部门地址
     */
    @ApiModelProperty(value = "部门地址")
    @ExcelProperty(value = "部门地址")
    private String orgAddress;

    /**
     * 联系人名称
     */
    @ApiModelProperty(value = "联系人名称")
    @ExcelProperty(value = "联系人")
    private String linkManName;
    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    @ExcelProperty(value = "联系方式")
    private String linkManTel;

    /**
     * 上级部门
     */
    @ApiModelProperty(value = "上级部门")
    @ExcelProperty(value = "上级部门")
    private String parentName;

    /**
     * 导入校验的错误信息
     */
    @ApiModelProperty(value = "导入校验的错误信息")
    private String errorMsg;

    private String id;

}
