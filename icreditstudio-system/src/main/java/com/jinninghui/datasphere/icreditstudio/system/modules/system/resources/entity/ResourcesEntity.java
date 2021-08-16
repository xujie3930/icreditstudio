package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.system.common.BaseEntity;
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
@TableName("ge_resources")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class ResourcesEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "NAME";
    public static final String PARENT_ID = "PARENT_ID";
    public static final String NEED_AUTH = "NEED_AUTH";
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @ExcelProperty(value = "菜单名称")
    private String name;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private String code;
    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id")
    @ExcelProperty(value = "父菜单id")
    private String parentId;
    /**
     * 路由路径(B 按钮类型URL为后端请求路径)
     */
    @ApiModelProperty(value = "路由路径(B 按钮类型URL为后端请求路径)")
    @ExcelProperty(value = "路由路径(B 按钮类型URL为后端请求路径)")
    private String url;
    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    @ExcelProperty(value = "权限标识")
    private String authIdentification;
    /**
     * 重定向路径
     */
    @ApiModelProperty(value = "重定向路径")
    @ExcelProperty(value = "重定向路径")
    private String redirectPath;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @ExcelProperty(value = "文件路径")
    private String filePath;
    /**
     * 是否在左侧菜单显示 Y 是  N 否
     */
    @ApiModelProperty(value = "是否在左侧菜单显示 Y 是  N 否")
    @ExcelProperty(value = "是否在左侧菜单显示 Y 是  N 否")
    private String isShow;
    /**
     * 是否缓存 Y...是 N...否
     */
    @ApiModelProperty(value = "是否缓存 Y...是 N...否")
    @ExcelProperty(value = "是否缓存 Y...是 N...否")
    private String isCache;
    /**
     * 是否活着 Y...是 N...否
     */
    @ApiModelProperty(value = "是否活着 Y...是 N...否")
    @ExcelProperty(value = "是否活着 Y...是 N...否")
    private String keepAlive;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    @ExcelProperty(value = "图标")
    private String iconPath;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    @ExcelProperty(value = "排序字段")
    private Integer sortNumber;
    /**
     * 内部或者外部
     */
    @ApiModelProperty(value = "内部或者外部")
    @ExcelProperty(value = "内部或者外部")
    private String internalOrExternal;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注信息")
    private String remark;
    /**
     * 菜单类型 M.菜单 D.顶部模块 B按钮
     */
    @ApiModelProperty(value = "菜单类型 M.菜单 D.顶部模块 B按钮")
    @ExcelProperty(value = "菜单类型 M.菜单 D.顶部模块 B按钮")
    private String type;
    /**
     * 删除标志 Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    @ExcelProperty(value = "删除标志 Y.已删除 N.未删除")
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
     * 更新者id
     */
    @ApiModelProperty(value = "按钮权限集合")
    @TableField(exist = false)
    private List<String> permissionList;

    private Integer needAuth;
}
