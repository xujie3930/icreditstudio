package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result;

/**
 * @author EDZ
 */
public class PlatformUserAuthResponse {
    /**
     * token
     */
    private String accessToken;

    /**
     * 有效时间 单位秒
     */
    private Integer validTime;

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
