package com.jinninghui.datasphere.icreditstudio.datasource.web.controller;


import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceCatalogue;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.*;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.DataSourceBaseInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.DatasourceDetailResult;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.SourceTableInfo;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/datasource")
public class IcreditDatasourceController {

    @Autowired
    private IcreditDatasourceService datasourceService;


    /**
     * 判断数据源是否重复存在
     */
    @PostMapping("/hasExist")
    @Logable
    public BusinessResult<Boolean> info(@RequestBody DataSourceHasExistRequest request) {
        return datasourceService.hasExit(request);
    }


    /**
     * 新增数据源
     */
    @PostMapping("/save")
    @Logable
    public BusinessResult<Boolean> publish(@RequestHeader("x-userid") String userId, @RequestBody IcreditDatasourceSaveRequest request) {

        IcreditDatasourceSaveParam param = new IcreditDatasourceSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.saveDef(param);
    }

    /**
     * 更新数据源
     */
    @PostMapping("/update")
    @Logable
    public BusinessResult<Boolean> update(@RequestBody IcreditDatasourceUpdateRequest request) {

        IcreditDatasourceEntity entity = new IcreditDatasourceEntity();
        BeanCopyUtils.copyProperties(request, entity);
        return BusinessResult.success(datasourceService.updateById(entity));
    }

    /**
     * 删除数据源
     */
    @PostMapping("/delete")
    @Logable
    public BusinessResult<Boolean> deleteById(@RequestBody IcreditDatasourceDelRequest request) {
        IcreditDatasourceDelParam param = new IcreditDatasourceDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.deleteById(param);
    }

    /**
     * 根据主键id查询数据源信息
     */
    @GetMapping("/info/{id}")
    @Logable
    public BusinessResult<DatasourceDetailResult> info(@PathVariable("id") String id) {
        DatasourceDetailResult detail = datasourceService.getDetailById(id);
        return BusinessResult.success(detail);
    }

    /**
     * 分页查询数据源列表
     */
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@RequestHeader(value = "x-userid") String userId,
                                                       @RequestBody IcreditDatasourceEntityPageRequest pageRequest) {
        BusinessPageResult page = datasourceService.queryPage(pageRequest);
        return BusinessResult.success(page);
    }

    /**
     * 测试数据源连接
     */
    @PostMapping("/testConnect")
    @Logable
    public BusinessResult<String> testConnect(@RequestBody IcreditDatasourceTestConnectRequest request) {
        return datasourceService.testConn(request);
    }

    @GetMapping("/sync/{id}")
    @Logable
    public BusinessResult<String> sync(@PathVariable("id") String id) {
        return datasourceService.syncById(id);
    }

    @PostMapping("/datasourceSearch")
    public BusinessResult<List<DataSourceBaseInfo>> datasourceSearch(@RequestBody DataSyncQueryDataSourceSearchRequest request) {
        DataSyncQueryDataSourceSearchParam param = new DataSyncQueryDataSourceSearchParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.datasourceSearch(param);
    }

    /**
     * 数据源目录
     *
     * @return
     */
    @Logable
    @PostMapping("/getDatasourceCatalogue")
    public BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(@RequestBody DataSyncQueryDatasourceCatalogueRequest request) {
        DataSyncQueryDatasourceCatalogueParam param = new DataSyncQueryDatasourceCatalogueParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.getDatasourceCatalogue(param);
    }

    /**
     * 获取连接信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getConnectionInfo")
    public BusinessResult<ConnectionInfo> getConnectionInfo(@RequestBody ConnectionInfoRequest request) {
        ConnectionInfoParam param = new ConnectionInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.getConnectionInfo(param);
    }

    /**
     * 取得源表字段
     *
     * @param request
     * @return
     */
    @PostMapping("/getTableInfo")
    public BusinessResult<List<SourceTableInfo>> getTableInfo(@RequestBody DataSourceTableInfoRequest request) {
        DataSourceTableInfoParam param = new DataSourceTableInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.getTableInfo(param);
    }
}

