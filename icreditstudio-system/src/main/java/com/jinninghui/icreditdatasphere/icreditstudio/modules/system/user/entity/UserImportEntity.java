package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jinninghui.icreditdatasphere.icreditstudio.common.utils.GenderConverter;
import com.jinninghui.icreditdatasphere.icreditstudio.common.utils.UserStateConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *
 *
 * @author hzh
 */
@Data
//@TableName("ge_user")
@ExcelIgnoreUnannotated
public class UserImportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "用户姓名")
    private String userName;

    @ExcelProperty(value = "工号")
    private String userCode;

    @ExcelProperty(value = "账号")
    private String loginUsername;

  @ExcelProperty(value = "生日")
  @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}", message = "50009364")
  private String userBirth;

    @ExcelProperty(value = "排序")
    private Integer sortNumber;

    @ExcelProperty(value = "手机号")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "50009352")
    private String telPhone;

    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private String userGender;

   /* @ExcelProperty(value = "状态")
    private String status;
*/
    @ExcelProperty(value = "部门")
    private String orgName;

    @ExcelProperty(value = "状态", converter = UserStateConverter.class)
    private String deleteFlag;

    //@ExcelProperty(value = "角色名")
	private String roleName;

    @ExcelProperty(value = "备注")
    private String userRemark;
    /**
     * 导入校验的错误信息
     */
    @ApiModelProperty(value = "导入校验的错误信息")
    private String errorMsg;
}
