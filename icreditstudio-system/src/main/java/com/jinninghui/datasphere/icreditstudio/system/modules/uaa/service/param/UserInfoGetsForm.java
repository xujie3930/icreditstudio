package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyanhui
 */
public class UserInfoGetsForm implements Serializable {
    private static final long serialVersionUID = 9139478959873075081L;
    private List<String> ids;
    private Long platformUserId;

    public UserInfoGetsForm() {
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Long getPlatformUserId() {
        return this.platformUserId;
    }

    public void setPlatformUserId(Long platformUserId) {
        this.platformUserId = platformUserId;
    }
}
