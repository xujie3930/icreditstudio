package com.jinninghui.datasphere.icreaditstudio.workspace.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author 1
 */
@Data
public class IcreditWorkspaceEntityPageRequest extends BusinessBasePageForm {
    private String name;//工作空间名称
    private String createUser;//创建人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;//创建时间
    private String userId;//用户id
}
