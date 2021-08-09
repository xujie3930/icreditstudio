package com.jinninghui.datasphere.icreditstudio.modules.system.general.result;

import lombok.Data;

@Data
public class UserLoginInfo {
    private String userId;

    private String token;
    /**
     * 账户
     */
    private String accountIdentifier;
}
