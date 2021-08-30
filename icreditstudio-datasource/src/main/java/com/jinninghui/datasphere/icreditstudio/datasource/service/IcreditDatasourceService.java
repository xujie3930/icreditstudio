package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * <p>
 *  服务类
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
}
