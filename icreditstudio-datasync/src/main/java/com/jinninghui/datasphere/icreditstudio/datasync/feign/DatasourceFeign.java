package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignConnectionInfoRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignDataSourcesRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DatasourceDetailResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DatasourceInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.ReaderConfigParam;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Peng
 */
@FeignClient(value = "datasource")
public interface DatasourceFeign {
    /**
     * 获取连接信息
     *
     * @param request
     * @return
     */
    @PostMapping("/datasource/getConnectionInfo")
    BusinessResult<ConnectionInfo> getConnectionInfo(@RequestBody FeignConnectionInfoRequest request);

    /**
     * 根据数据库名称获取数据源信息
     *
     * @param request
     * @return
     */
    @PostMapping("/datasource/getDataSources")
    BusinessResult<List<DatasourceInfo>> getDataSources(@RequestBody FeignDataSourcesRequest request);

    /**
     * 获取数据源jdbc信息
     *
     * @param id
     * @return
     */
    @GetMapping("/datasource/getDatasourceJdbcInfo")
    BusinessResult<ReaderConfigParam> getDatasourceJdbcInfo(@RequestParam("id") String id);

    /**
     * 判断数据源状态
     *
     * @param id
     * @return
     */
    @GetMapping("/datasource/info/{id}")
    BusinessResult<DatasourceDetailResult> info(@PathVariable("id") String id);
}
