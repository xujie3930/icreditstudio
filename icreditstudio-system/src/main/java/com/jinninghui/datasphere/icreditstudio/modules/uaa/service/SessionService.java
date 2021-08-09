package com.jinninghui.datasphere.icreditstudio.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.OperateLoginResponse;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.PlatformUserAuthResponse;
import com.hashtech.businessframework.result.BusinessResult;

/**
 * @author jidonglin
 */
public interface SessionService {

	void logout(String token);

    BusinessResult<OperateLoginResponse> backstageUserLogin(String username, String password, String application);

    BusinessResult<PlatformUserAuthResponse> getToken(String username, String password);

    BusinessResult<PlatformUserAuthResponse> refreshToken(String token);

    String getUserId();

    String getToken();


}
