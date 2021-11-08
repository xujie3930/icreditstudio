package com.jinninghui.datasphere.icreditstudio.datasource.web.controller;


import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ColumnListResult;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
@RestController
@RequestMapping("/datasource")
public class IcreditDdlSyncController {

    @Autowired
    private IcreditDdlSyncService ddlSyncService;

    @Logable
    @GetMapping("/datasourceStructure/{id}")
    public BusinessResult<List<ColumnListResult>> getDatasourceStructure(@PathVariable("id") String id) {
        return ddlSyncService.getDatasourceStructure(id);
    }
}

