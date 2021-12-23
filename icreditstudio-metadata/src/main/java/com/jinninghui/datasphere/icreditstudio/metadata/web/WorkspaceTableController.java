package com.jinninghui.datasphere.icreditstudio.metadata.web;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;
import com.jinninghui.datasphere.icreditstudio.metadata.service.WorkspaceTableService;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.WorkspaceTableSavaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peng
 */
@Slf4j
@RestController
@RequestMapping("/metadata/workspace")
public class WorkspaceTableController {
    @Autowired
    private WorkspaceTableService workspaceTableService;

    @PostMapping("/test")
    public BusinessResult<Object> test(@RequestBody WorkspaceTableSavaRequest request) {
        WorkspaceTableEntity entity = BeanCopyUtils.copyProperties(request, WorkspaceTableEntity.class);
        workspaceTableService.save(entity);
        return BusinessResult.success(true);
    }
}
