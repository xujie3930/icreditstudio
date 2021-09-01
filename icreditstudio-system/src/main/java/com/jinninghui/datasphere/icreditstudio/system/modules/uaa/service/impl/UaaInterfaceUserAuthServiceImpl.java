package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.InterfaceUserAuthService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param.InterfaceUserAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.InterfaceUserAuthResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.service.AllInterfacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/3/2 13:21
 */
@Service
public class UaaInterfaceUserAuthServiceImpl implements InterfaceUserAuthService {

    @Autowired
    private AllInterfacesService allInterfacesService;

    @Override
    public BusinessResult<List<InterfaceUserAuthResult>> getUserAuthInterfaceIdList(InterfaceUserAuthParam interfaceUserAuthParam) {

        InterfaceAuthParam interfaceAuthParam = BeanCopyUtils.copyProperties(interfaceUserAuthParam, InterfaceAuthParam.class);

        List<InterfaceAuthResult> userAuthInterfaceIdList = allInterfacesService.getUserAuthInterfaceIdList(interfaceAuthParam);

        List<InterfaceUserAuthResult> copy = BeanCopyUtils.copy(userAuthInterfaceIdList, InterfaceUserAuthResult.class);

        return BusinessResult.success(copy);
    }
}
