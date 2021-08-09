package com.jinninghui.datasphere.icreditstudio.gateway;

import com.alibaba.fastjson.JSONObject;
import com.hashtech.businessframework.result.BusinessResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author liyanhui
 */
@Component
@Order(0)
public class ZuulErrorAttribute extends DefaultErrorAttributes {

    @Value("${system.errorCode}")
    private String systemErrorCode;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        if(!result.get("status").equals(HttpStatus.TOO_MANY_REQUESTS.value())){
            return result;
        }

        webRequest.setAttribute("javax.servlet.error.status_code",HttpStatus.OK.value(), RequestAttributes.SCOPE_REQUEST);
        return JSONObject.parseObject(JSONObject.toJSONString(BusinessResult.fail(systemErrorCode, "频繁请求，请稍后再试")), Map.class);
    }
}
