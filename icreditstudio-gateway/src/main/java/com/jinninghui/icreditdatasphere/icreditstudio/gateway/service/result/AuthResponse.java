package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result;

import com.hashtech.businessframework.result.CommonOuterResponse;

/**
 * 鉴权返回对象
 *
 * @author lidab
 * @author LIYANHUI
 */
public class AuthResponse extends CommonOuterResponse {
    private String customerCode;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
