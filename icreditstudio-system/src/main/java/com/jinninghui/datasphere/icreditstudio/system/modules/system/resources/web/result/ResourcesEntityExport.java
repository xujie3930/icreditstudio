package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.result;

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

/**
 * @author hzh
 * @description
 * @date 2021/3/11 14:33
 */
@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 20)
@ColumnWidth(25)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class ResourcesEntityExport implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 模块类型 M.模块 D.顶部模块 B按钮
     */
    @ApiModelProperty(value = "模块类型 M.模块 D.顶部模块 B 按钮")
    @ExcelProperty(value = "模块类型")
    private String type;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    @ExcelProperty(value = "模块名称")
    private String name;

    /**
     * 路由路径(B 按钮类型URL为后端请求路径)
     */
    @ApiModelProperty(value = "路由路径(B 按钮类型URL为后端请求路径)")
    @ExcelProperty(value = "路径")
    private String url;

    /**
     * 父模块id
     */
    @ApiModelProperty(value = "上级模块")
    @ExcelProperty(value = "上级模块")
    private String parentName;


    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @ExcelProperty(value = "文件路径")
    private String filePath;

    /**
     * 重定向路径
     */
    @ApiModelProperty(value = "重定向路径")
    @ExcelProperty(value = "重定向地址")
    private String redirectPath;

    /**
     * 是否在左侧模块显示 Y 是  N 否
     */
    @ApiModelProperty(value = "是否在左侧模块显示 Y 是  N 否")
    @ExcelProperty(value = "是否在菜单显示")
    private String isShow;

    /**
     * 是否缓存 Y...是 N...否
     */
    @ApiModelProperty(value = "是否缓存 Y...是 N...否")
    @ExcelProperty(value = "是否缓存")
    private String isCache;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    @ExcelProperty(value = "排序")
    private Integer sortNumber;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "按钮权限标识")
    private String authIdentification;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 父模块id
     */
    @ApiModelProperty(value = "父模块id")
    private String parentId;

    /**
     * 是否活着 Y...是 N...否
     */
    @ApiModelProperty(value = "是否活着 Y...是 N...否")
    private String keepAlive;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String iconPath;

    /**
     * 内部或者外部
     */
    @ApiModelProperty(value = "内部或者外部")
    private String internalOrExternal;


    /**
     * 导入校验的错误信息
     */
    @ApiModelProperty(value = "导入校验的错误信息")
    private String errorMsg;


}
