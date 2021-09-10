package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author 1
 */
@Data
public class IcreditWorkspaceEntityPageRequest extends BusinessBasePageForm {
    private String spaceId;//工作空间id
    private String name;//工作空间名称
    private String updateUser;//更新人
    private String updateTime;//创建时间
    private String userId;//用户id
}
