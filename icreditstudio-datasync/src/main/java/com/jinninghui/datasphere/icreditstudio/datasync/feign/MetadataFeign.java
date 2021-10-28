package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignMetadataGenerateWideTableRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.WarehouseInfo;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Peng
 */
@FeignClient(value = "metadata")
public interface MetadataFeign {

    /**
     * 创建宽表
     *
     * @param request
     * @return
     */
    @PostMapping("/metadata/generateWideTable")
    BusinessResult<Boolean> generateWideTable(@RequestBody FeignMetadataGenerateWideTableRequest request);

    /**
     * 数据存放路径
     *
     * @return
     */
    @GetMapping("/metadata/getWarehouseInfo")
    BusinessResult<WarehouseInfo> getWarehouseInfo();
}
