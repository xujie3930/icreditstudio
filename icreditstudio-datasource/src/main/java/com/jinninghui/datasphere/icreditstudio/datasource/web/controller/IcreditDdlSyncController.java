package com.jinninghui.datasphere.icreditstudio.datasource.web.controller;


import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
@RestController
@RequestMapping("/ddlSync")
public class IcreditDdlSyncController {

    @Autowired
    private IcreditDdlSyncService ddlSyncService;


}

