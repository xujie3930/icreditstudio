package com.jinninghui.datasphere.icreditstudio.datasource.schedule;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceStatusEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
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


    @Scheduled(cron = "0 */10 * * * ?")
    public void cleanDatasource() throws InterruptedException {
        System.out.println("开始执行更新数据源任务");
        long startTime = System.currentTimeMillis();
        List<IcreditDatasourceEntity> allDatasoure = datasourceService.findAllDatasoure();
        for (IcreditDatasourceEntity datasourceEntity : allDatasoure) {
            try {
                IcreditDatasourceTestConnectRequest request = new IcreditDatasourceTestConnectRequest(
                        datasourceEntity.getType(), datasourceEntity.getUri());
                String datasourceId = datasourceEntity.getId();
                //不成功则停用
                if (!datasourceService.testConn(request).isSuccess()){
                    datasourceEntity.setStatus(DatasourceStatusEnum.DISABLE.getCode());
                    datasourceService.updateById(datasourceEntity);
                }
                datasourceService.syncById(datasourceId);
            } catch (Exception e) {
                continue;
            }
        }
        long endTime = System.currentTimeMillis();
        long spendTime = (endTime - startTime) / 1000;
        System.out.println("定时更新数据源任务总耗时:" + spendTime);
    }
}
