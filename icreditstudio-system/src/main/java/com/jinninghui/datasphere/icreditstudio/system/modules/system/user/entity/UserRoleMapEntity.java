package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity;

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
 * 
 *
 * @author hzh
 */
@Data
@TableName("ge_user_role_map")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class UserRoleMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	public static final String ROLE_ID = "ROLE_ID";
	public static final String USER_ID = "USER_ID";
	/**
	 * id
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "id")
    private String id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
    @ExcelProperty(value = "用户id")
    private String userId;
	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
    @ExcelProperty(value = "角色id")
    private String roleId;
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

}
