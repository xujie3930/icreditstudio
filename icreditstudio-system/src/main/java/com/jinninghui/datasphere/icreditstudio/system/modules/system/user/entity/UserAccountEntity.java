package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity;

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
 * @author hzh
 */
@Data
@TableName("ge_user_account")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class UserAccountEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ACCOUNT_IDENTIFIER = "ACCOUNT_IDENTIFIER";
	public static final String USER_ID = "USER_ID";
	/**
	 * id
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "id")
    private String id;
	/**
	 * 账户是否已过期 Y.过期 N.未过期
	 */
	@ApiModelProperty(value = "账户是否已过期 Y.过期 N.未过期")
    @ExcelProperty(value = "账户是否已过期 Y.过期 N.未过期")
    private Long accountExpired;
	/**
	 * 是否锁定 Y.锁定 N.未锁定
	 */
	@ApiModelProperty(value = "是否锁定 Y.锁定 N.未锁定")
    @ExcelProperty(value = "是否锁定 Y.锁定 N.未锁定")
    private String accountLocked = "N";
	/**
	 * 证书 密码
	 */
	@ApiModelProperty(value = "密码")
    @ExcelProperty(value = "密码")
    private String accountCredential;
	/**
	 * 登录用户名
	 */
	@ApiModelProperty(value = "登录用户名")
    @ExcelProperty(value = "登录用户名")
    private String accountIdentifier;
	/**
	 * 证书过期时间
	 */
	@ApiModelProperty(value = "证书过期时间")
    @ExcelProperty(value = "证书过期时间")
    private Long credentialExpired;
	/**
	 * 标识类型  1:username,2:email,3:phone,4:wechat,5:qq
	 */
	@ApiModelProperty(value = "标识类型  1:username,2:email,3:phone,4:wechat,5:qq")
    @ExcelProperty(value = "标识类型  1:username,2:email,3:phone,4:wechat,5:qq")
    private String identityType = "1";
	/**
	 * 最近登陆ip
	 */
	@ApiModelProperty(value = "最近登陆ip")
    @ExcelProperty(value = "最近登陆ip")
    private String lastLoginIp;
	/**
	 * 最近登陆时间
	 */
	@ApiModelProperty(value = "最近登陆时间")
    @ExcelProperty(value = "最近登陆时间")
    private String lastLoginTime;
	/**
	 * 登陆模式
	 */
	@ApiModelProperty(value = "登陆模式")
    @ExcelProperty(value = "登陆模式")
    private Integer loginMode = 0;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
    @ExcelProperty(value = "用户id")
    private String userId;
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

}
