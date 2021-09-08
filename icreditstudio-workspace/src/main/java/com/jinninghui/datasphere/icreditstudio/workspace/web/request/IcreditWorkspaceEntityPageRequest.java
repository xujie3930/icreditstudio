package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author 1
 */
@Data
public class IcreditWorkspaceEntityPageRequest extends BusinessBasePageForm {
    private String name;//工作空间名称
    private String createUser;//创建人
    private String updateTime;//创建时间
    private String userId;//用户id
}
