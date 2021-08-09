package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.impl;

import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.InterfaceUserAuthService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.param.InterfaceUserAuthParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.InterfaceUserAuthResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.service.AllInterfacesService;
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
    private  AllInterfacesService allInterfacesService;

    @Override
    public BusinessResult<List<InterfaceUserAuthResult>> getUserAuthInterfaceIdList(InterfaceUserAuthParam interfaceUserAuthParam) {

        InterfaceAuthParam interfaceAuthParam = BeanCopyUtils.copyProperties(interfaceUserAuthParam, InterfaceAuthParam.class);

        List<InterfaceAuthResult> userAuthInterfaceIdList = allInterfacesService.getUserAuthInterfaceIdList(interfaceAuthParam);

        List<InterfaceUserAuthResult> copy = BeanCopyUtils.copy(userAuthInterfaceIdList, InterfaceUserAuthResult.class);

        return BusinessResult.success(copy);
    }
}
