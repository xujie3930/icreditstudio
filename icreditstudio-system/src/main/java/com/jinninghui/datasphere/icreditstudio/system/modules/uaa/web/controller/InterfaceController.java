package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.dto.Interface;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.InterfaceService;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 接口控制器
 *
 * @author jidonglin
 */
@RestController
public class InterfaceController {
    @Autowired
    private InterfaceService interfaceService;

    @Logable(businessTag = "getAllInterface")
    @GetMapping(value = "/interfaces")
    public List<Interface> getAllInterface() {
        return interfaceService.loadInterface();

    }

}

