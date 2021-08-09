package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result;

import com.hashtech.businessframework.result.CommonOuterResponse;

/**
 * @author liyanhui
 */
public class SignMsg extends CommonOuterResponse {
    private String signSn;
    private String sign;

    public SignMsg() {
    }

    public String getSignSn() {
        return this.signSn;
    }

    public void setSignSn(String signSn) {
        this.signSn = signSn;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
