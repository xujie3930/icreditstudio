package com.jinninghui.datasphere.icreditstudio.metadata.web;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.WarehouseInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.MetadataGenerateWideTableRequest;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.MetadataQueryTargetSourceRequest;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 生成宽表
     *
     * @param request
     * @return
     */
    @PostMapping("/generateWideTable")
    public BusinessResult<Boolean> generateWideTable(@RequestBody MetadataGenerateWideTableRequest request) {
        MetadataGenerateWideTableParam param = new MetadataGenerateWideTableParam();
        BeanCopyUtils.copyProperties(request, param);
        return metadataService.generateWideTable(param);
    }

    /**
     * 数据仓库配置信息
     *
     * @return
     */
    @GetMapping("/getWarehouseInfo")
    public BusinessResult<WarehouseInfo> getWarehouseInfo() {
        return metadataService.getWarehouseInfo();
    }
}
