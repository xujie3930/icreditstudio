package com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/2/25 15:41
 */
@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 20)
@ColumnWidth(25)
@ContentStyle(
    horizontalAlignment = HorizontalAlignment.CENTER,
    borderTop = BorderStyle.THIN,
    borderBottom = BorderStyle.THIN,
    borderLeft = BorderStyle.THIN,
    borderRight = BorderStyle.THIN)
public class UserEntityResult implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  /** 用户名称 */
  @ApiModelProperty(value = "用户姓名")
  @ExcelProperty(value = "用户姓名")
  private String userName;

  /** 组织名 */
  @ApiModelProperty(value = "部门")
  @ExcelProperty(value = "部门")
  private String orgName;

  /** 登录用户名 */
  @ApiModelProperty(value = "登录账号")
  @ExcelProperty(value = "登录账号")
  private String accountIdentifier;

  /** 联系方式 */
  @ApiModelProperty(value = "手机号")
  @ExcelProperty(value = "手机号")
  private String telPhone;

  /** 工号 */
  private String userCode;

  /** 性别 */
  private String userGender;

  /** 生日 */
  private String userBirth;

  /** 身份证号 */
  private String idCard;

  /** 入职时间 */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date entryTime;

  /** 离职时间 */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date departureTime;

  /** 邮箱 */
  private String eMail;

  /** 职位 */
  private String userPosition;


  /** 排序字段 */
  private Integer sortNumber;

  /** 删除标志Y.已删除 N.未删除 */
  private String deleteFlag;

  /** 头像 */
  private InputStream picturePath;

  @TableField(exist = false)
  private String photo = "";

  /** 备注 */
  @ApiModelProperty(value = "备注")
  private String userRemark;

  /** 创建时间 */
  private Long createTime;

  /** 创建者id */
  private String createUserId;

  /** 更新时间 */
  private Long lastUpdateTime;

  /** 更新者id */
  private String lastUpdateUserId;

  /** 扩展字段1 */
  private String extendOne;

  /** 扩展字段2 */
  private String extendTwo;

  /** 扩展字段3 */
  private String extendThree;

  /** 扩展字段4 */
  private String extendFour;

  /** 角色id集合 */
  private List<String> roleList;

  /** 组织机构id集合 */
  private List<UserOrgListResult> orgList;

  /** 角色名 */
  @ApiModelProperty(value = "角色名")
  private String roleName;


}
