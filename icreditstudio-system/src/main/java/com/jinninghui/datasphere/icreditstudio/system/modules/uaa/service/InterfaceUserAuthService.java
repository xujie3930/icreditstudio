package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param.InterfaceUserAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.InterfaceUserAuthResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

import java.util.List;

/**
 * @author hzh
 */
public interface InterfaceUserAuthService {

    BusinessResult<List<InterfaceUserAuthResult>> getUserAuthInterfaceIdList(InterfaceUserAuthParam param);


}
