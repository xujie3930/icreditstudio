package com.jinninghui.datasphere.icreditstudio.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.common.HttpResponseUtils;
import com.jinninghui.datasphere.icreditstudio.gateway.common.HttpUtils;
import com.jinninghui.datasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.param.CertificateAuthRequest;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.AuthResponse;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.Interface;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: jidonglin
 * @Date: 2019/12/3 15:34
 */
@Service
public class UaaServiceImpl implements UaaService {

    @Value("${zuul.routes.hash.url}")
    private String url;

    @Override
    public void updateUaaInterface(List<Interface> list) {

    }

    @Override
    public List<Interface> getAllServices() {
        try {
            HttpResponse response = HttpUtils.doGet(url, "/interfaces", "GET", new HashMap<>(12), new HashMap<>(12));
            BusinessResult<String> jsonString = HttpResponseUtils.response(response);
            List<Interface> interfaces = JSONArray.parseArray(jsonString.getData(), Interface.class);
            return interfaces;
        } catch (Exception e) {
            throw new AppException(Constants.ErrorCode.FAIL_GET_AUTHINTERFACE.code);
        }
    }

    @Override
    public BusinessToken tokenAuth(String s, String s1, String s2, String requestType) {
        try {
            HashMap<String, String> querys = new HashMap<>();
            querys.put("interfaceUrl", s);
            querys.put("interfaceMethod", s1);
            querys.put("token", s2);
            querys.put("requestType", requestType);
            HttpResponse response = HttpUtils.doPost(url, "/tokenAuth", "POST", new HashMap<>(12), querys, new byte[]{});
            BusinessResult<String> jsonString = HttpResponseUtils.response(response);
            BusinessToken businessToken = JSON.parseObject(jsonString.getData(), BusinessToken.class);
            return businessToken;
        } catch (Exception e) {
            throw new AppException(Constants.ErrorCode.TOKEN_AUTH_FAIL.code);
        }
    }

    /**
     * idata暂时不用证书鉴权
     *
     * @param certificateAuthRequest
     * @return
     */
    @Override
    public AuthResponse certificateAuth(CertificateAuthRequest certificateAuthRequest) {
        return null;
    }

    @Override
    public String sign(String s) {
        try {
            HashMap<String, String> querys = new HashMap<>();
            querys.put("input", s);
            HttpResponse response = HttpUtils.doPost(url, "/sign", "POST", new HashMap<>(12), querys, new byte[]{});
            BusinessResult<String> jsonString = HttpResponseUtils.response(response);
            return JSONObject.toJSONString(jsonString.getData());
        } catch (Exception e) {
            throw new AppException(Constants.ErrorCode.TOKEN_AUTH_FAIL.code);
        }
    }
}
