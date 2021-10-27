package com.jinninghui.datasphere.icreditstudio.datasource.schedule;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xujie
 * @description 周期扫描数据源，删掉没有的库
 * @create 2021-10-27 10:04
 **/
@Component
@Lazy(false)
//TODO：这里暂且用扫描全表方式，后续再变更为分布式的调度框架
public class DatasourceClearTask {

    @Autowired
    private IcreditDatasourceService datasourceService;


    @Scheduled(cron = "0 0 */1 * * ?")
    public void cleanDatasource() throws InterruptedException {
        List<IcreditDatasourceEntity> allDatasoure = datasourceService.findAllDatasoure();
        for (IcreditDatasourceEntity datasourceEntity : allDatasoure) {
            try {
                IcreditDatasourceTestConnectRequest request = new IcreditDatasourceTestConnectRequest(
                        datasourceEntity.getType(), datasourceEntity.getUri());
                String datasourceId = datasourceEntity.getId();
                //不成功则跳过
                if (!datasourceService.testConn(request).isSuccess()){
                    continue;
                }
                datasourceService.syncById(datasourceId);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
