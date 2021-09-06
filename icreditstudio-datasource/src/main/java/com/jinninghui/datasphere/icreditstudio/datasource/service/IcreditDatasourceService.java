package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.ConnectionInfoParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.DataSyncQueryDatasourceCatalogueParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceCatalogue;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.DataSourceHasExistRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.DatasourceDetailResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
public interface IcreditDatasourceService extends IService<IcreditDatasourceEntity> {

    BusinessResult<Boolean> saveDef(IcreditDatasourceSaveParam param);

    BusinessResult<Boolean> deleteById(IcreditDatasourceDelParam param);

    BusinessPageResult queryPage(IcreditDatasourceEntityPageRequest pageRequest);

    BusinessResult<String> testConn(IcreditDatasourceTestConnectRequest request);

    BusinessResult<String> syncById(String id);

    /**
     * 数据源目录列表
     *
     * @param param
     * @return
     */
    BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(DataSyncQueryDatasourceCatalogueParam param);

    /**
     * 获取连接信息
     *
     * @param param
     * @return
     */
    BusinessResult<ConnectionInfo> getConnectionInfo(ConnectionInfoParam param);

    BusinessResult<Boolean> hasExit(DataSourceHasExistRequest request);

    DatasourceDetailResult getDetailById(String id);
}