package com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.entity;

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

/**
 * 快捷菜单
 *
 * @author 1
 */
@Data
@TableName("ge_shortcut_menu")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(
    horizontalAlignment = HorizontalAlignment.CENTER,
    borderTop = BorderStyle.THIN,
    borderBottom = BorderStyle.THIN,
    borderLeft = BorderStyle.THIN,
    borderRight = BorderStyle.THIN)
public class ShortcutMenuEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** */
  @TableId(type = IdType.INPUT)
  @ApiModelProperty(value = "ID")
  private String id;
  /** */
  @ApiModelProperty(value = "用户id")
  @ExcelProperty(value = "用户id")
  private String userId;
  /** */
  @ApiModelProperty(value = "菜单id")
  @ExcelProperty(value = "菜单id")
  private String resourceId;
  /** */
  @ApiModelProperty(value = "创建时间")
  private Long createTime;
  /** */
  @ApiModelProperty(value = "创建人")
  private String createUserId;
  /** */
  @ApiModelProperty(value = "最新更改时间")
  private Long lastUpdateTime;
  /** */
  @ApiModelProperty(value = "最新修改人")
  private String lastUpdateUserId;
}
