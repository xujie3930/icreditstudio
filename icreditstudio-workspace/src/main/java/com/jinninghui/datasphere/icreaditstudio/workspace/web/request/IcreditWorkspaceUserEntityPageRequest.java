package com.jinninghui.datasphere.icreaditstudio.workspace.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author xujie
 * @since 2021-08-23
 */
@Data
public class IcreditWorkspaceUserEntityPageRequest extends BusinessBasePageForm {
    private String spaceId;//工作空间id
}
