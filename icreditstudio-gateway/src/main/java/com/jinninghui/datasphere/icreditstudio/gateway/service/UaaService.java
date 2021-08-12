package com.jinninghui.datasphere.icreditstudio.gateway.service;

import com.jinninghui.datasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.Interface;

import java.util.List;

public interface UaaService {

    List<Interface> getAllServices();

    BusinessToken tokenAuth(String interfaceUrl, String interfaceMethod, String token, String requestType);

}
