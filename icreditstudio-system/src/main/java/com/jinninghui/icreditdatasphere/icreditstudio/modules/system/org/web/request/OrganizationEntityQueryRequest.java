package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Pengpai on 2021/5/25 11:26
 */
@Data
public class OrganizationEntityQueryRequest {

    /**
     * 用户id
     */
//    @NotBlank(message = "10000000")
    private String userId;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 联系人名称
     */
    private String linkManName;

    private String deleteFlag;

    private boolean onlyReturnCurrAndSon;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String orgCode;

    private String accessUserId;
}
