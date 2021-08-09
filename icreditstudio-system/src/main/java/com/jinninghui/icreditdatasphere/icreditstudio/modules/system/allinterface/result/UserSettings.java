package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result;

import lombok.Builder;
import lombok.Data;

/**
 * Created by PPai on 2021/6/29 11:28
 */
@Data
@Builder
public class UserSettings {

    private String fontSize;

    private String cssId;

    private String logo;

    private String copyRight;

    private String appName;

    private String enableCustomMenu;
}
