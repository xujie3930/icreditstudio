package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * @author hzh
 */
@Data
@TableName("ge_user_org_map")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class UserOrgMapEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String ID = "ID";
	public static final String ORG_ID = "ORG_ID";
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
    private String userId;
	/**
	 * 组织机构id
	 */
	@ApiModelProperty(value = "组织机构id")
    private String orgId;
	/**
	 * 组织机构的上下级全路径
	 */
	@ApiModelProperty(value = "组织机构的上下级全路径")
    private String orgPath;
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
