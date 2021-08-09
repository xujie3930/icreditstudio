package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserElePerm implements Serializable {

    private List<String> canEdit;

    private List<String> canHidden;
}
