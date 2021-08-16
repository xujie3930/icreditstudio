package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity;

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
@TableName("ge_role")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class RoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ROLE_NAME = "ROLE_NAME";
    public static final String PARENT_ID = "PARENT_ID";
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @ExcelProperty(value = "角色名称")
    private String roleName;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    @ExcelProperty(value = "角色编码")
    private String roleCode;
    /**
     * 父角色id
     */
    @ApiModelProperty(value = "父角色id")
    @ExcelProperty(value = "父角色id")
    private String parentId;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    @ExcelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 角色备注
     */
    @ApiModelProperty(value = "角色备注")
    @ExcelProperty(value = "角色备注")
    private String roleRemark;

    /**
     * 删除标志 Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    @ExcelProperty(value = "删除标志 Y.已删除 N.未删除")
    private String deleteFlag;
    /**
     *
     */
    @ApiModelProperty(value = "创建者id")
    private Long createTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
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

}
