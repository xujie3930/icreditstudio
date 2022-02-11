package com.micro.cloud.sms.core.property;

import com.micro.cloud.sms.core.enums.SmsChannelEnum;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 短信渠道配置类
 *
 * @author zzf
 * @date 2021/1/25 17:01
 */
@Validated
public class SmsChannelProperties {

    /**
     * 渠道编号
     */
    @NotNull(message = "短信渠道 ID 不能为空")
    private Long id;
    /**
     * 短信签名
     */
    @NotEmpty(message = "短信签名不能为空")
    private String signature;
    /**
     * 渠道编码
     *
     * 枚举 {@link SmsChannelEnum}
     */
    @NotEmpty(message = "渠道编码不能为空")
    private String code;
    /**
     * 短信 API 的账号
     */
    @NotEmpty(message = "短信 API 的账号不能为空")
    private String apiKey;
    /**
     * 短信 API 的秘钥
     */
    @NotEmpty(message = "短信 API 的秘钥不能为空")
    private String apiSecret;
    /**
     * 短信发送回调 URL
     */
    private String callbackUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public String toString() {
        return "SmsChannelProperties{" +
            "id=" + id +
            ", signature='" + signature + '\'' +
            ", code='" + code + '\'' +
            ", apiKey='" + apiKey + '\'' +
            ", apiSecret='" + apiSecret + '\'' +
            ", callbackUrl='" + callbackUrl + '\'' +
            '}';
    }
}
