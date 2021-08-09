package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.param.InterfaceUserAuthParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.InterfaceUserAuthResult;
import com.hashtech.businessframework.result.BusinessResult;

import java.util.List;

/**
 * @author hzh
 */
public interface InterfaceUserAuthService {

    BusinessResult<List<InterfaceUserAuthResult>> getUserAuthInterfaceIdList(InterfaceUserAuthParam param);


}
