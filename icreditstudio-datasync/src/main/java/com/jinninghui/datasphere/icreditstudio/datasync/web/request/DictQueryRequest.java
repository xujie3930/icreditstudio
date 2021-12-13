package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

@Data
public class DictQueryRequest extends BusinessBasePageForm {

    private String dictName;

}
