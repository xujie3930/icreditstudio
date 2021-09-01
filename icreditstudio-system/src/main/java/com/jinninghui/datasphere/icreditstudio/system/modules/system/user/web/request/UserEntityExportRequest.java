package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hzh
 */
@Data
public class UserEntityExportRequest {
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String telPhone;

    /**
     * 删除标志Y.已删除 N.未删除
     */
    @ApiModelProperty(value = "用户状态")
    @ExcelProperty(value = "用户状态")
    private String deleteFlag;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    @ExcelProperty(value = "角色id")
    private String roleId;

    /**
     * 当前登录人id
     */
    @ApiModelProperty(value = "当前登录人id")
    private String userId;

    /**
     * 登录账号
     */
    private String accountIdentifier;

    private List<String> orgIds;
}
