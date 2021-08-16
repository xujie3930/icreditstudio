package com.jinninghui.datasphere.icreditstudio.gateway.service.impl;

import com.jinninghui.datasphere.icreditstudio.gateway.common.UaaFeignClient;
import com.jinninghui.datasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jidonglin
 * @Date: 2019/12/3 15:34
 */
@Service
public class UaaServiceImpl implements UaaService {

    @Autowired
    private UaaFeignClient uaaFeignClient;

    @Override
    public List<Interface> getAllServices() {
        return uaaFeignClient.getAllInterface();
    }

    @Override
    public BusinessToken tokenAuth(String interfaceUrl, String interfaceMethod,
                                   String token,
                                   String requestType) {
        return uaaFeignClient.tokenAuth(interfaceUrl, interfaceMethod, token, requestType);
    }


}
