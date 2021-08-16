package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity;

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
 * @author hzh
 */
@Data
@TableName("ge_organization")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class OrganizationEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String ORG_NAME = "ORG_NAME";
    public static final String LINK_MAN_NAME = "LINK_MAN_NAME";
    public static final String ORG_CODE = "ORG_CODE";
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private String orgCode;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String orgName;
    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id")
    @ExcelProperty(value = "父部门id")
    private String parentId;
    /**
     * 部门地址
     */
    @ApiModelProperty(value = "部门地址")
    @ExcelProperty(value = "部门地址")
    private String orgAddress;
    /**
     * 图标地址
     */
    @ApiModelProperty(value = "图标地址")
    @ExcelProperty(value = "图标地址")
    private String iconPath;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    @ExcelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 联系人名称
     */
    @ApiModelProperty(value = "联系人名称")
    @ExcelProperty(value = "联系人名称")
    private String linkManName;
    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    @ExcelProperty(value = "联系人电话")
    private String linkManTel;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String orgRemark;
    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志Y.已删除 N.未删除")
    @ExcelProperty(value = "删除标志Y.已删除 N.未删除")
    private String deleteFlag;
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
    @ExcelProperty(value = "扩展字段1")
    private String extendOne;
    /**
     * 扩展字段2
     */
    @ApiModelProperty(value = "扩展字段2")
    @ExcelProperty(value = "扩展字段2")
    private String extendTwo;
    /**
     * 扩展字段3
     */
    @ApiModelProperty(value = "扩展字段3")
    @ExcelProperty(value = "扩展字段3")
    private String extendFour;
    /**
     * 扩展字段4
     */
    @ApiModelProperty(value = "扩展字段4")
    @ExcelProperty(value = "扩展字段4")
    private String extendThree;

}
