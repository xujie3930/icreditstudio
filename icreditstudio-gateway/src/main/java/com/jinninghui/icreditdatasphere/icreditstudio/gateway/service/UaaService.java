package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service;

import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.AuthResponse;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.param.CertificateAuthRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.Interface;

import java.util.List;

public interface UaaService {

    void updateUaaInterface(List<Interface> interfaces);

    List<Interface> getAllServices();

    BusinessToken tokenAuth(String interfaceUrl, String interfaceMethod, String token, String requestType);

    AuthResponse certificateAuth(CertificateAuthRequest certificateAuthRequest);

    String sign(String input);
}
