package com.jinninghui.datasphere.icreditstudio.metadata.web;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.MetadataQueryTargetSourceRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Peng
 */
@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @Resource
    private MetadataService metadataService;

    /**
     * 获取目标库列表
     *
     * @param request
     * @return
     */
    @PostMapping("/targetSources")
    public BusinessResult<List<TargetSourceInfo>> targetSources(@RequestBody MetadataQueryTargetSourceRequest request) {
        MetadataQueryTargetSourceParam param = new MetadataQueryTargetSourceParam();
        BeanCopyUtils.copyProperties(request, param);
        return metadataService.targetSources(param);
    }
}
