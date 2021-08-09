package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyanhui
 */
public class UserAccountGetsForm implements Serializable {
    private static final long serialVersionUID = -593521964803374709L;
    private List<String> ids;
    private String identifier;
    private String userId;

    public UserAccountGetsForm() {
    }

    public List<String> getIds() {
        return this.ids;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

