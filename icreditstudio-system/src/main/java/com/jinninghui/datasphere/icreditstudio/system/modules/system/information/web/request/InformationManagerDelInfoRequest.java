package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.web.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Created by PPai on 2021/6/18 17:53
 */
@Data
public class InformationManagerDelInfoRequest {

    @NotEmpty(message = "50009372")
    private Set<String> ids;
}
