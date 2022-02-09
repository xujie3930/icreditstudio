package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class DictQueryRequest extends BusinessBasePageForm {

    private String userId;
    private String workspaceId;
    private String dictName;

}
