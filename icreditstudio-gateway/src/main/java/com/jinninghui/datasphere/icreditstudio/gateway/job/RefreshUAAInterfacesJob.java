
package com.jinninghui.datasphere.icreditstudio.gateway.job;

import com.jinninghui.datasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时刷新UAA的interface表，和刷新错误码-错误信息映射，并且更新进程缓存
 * @author lidab
 *
 */
@Component
public class RefreshUAAInterfacesJob {
	@Autowired
	private HFPSServiceMgrService hfpsServerMgrService;
	Logger log = LoggerFactory.getLogger(RefreshUAAInterfacesJob.class);

	@Scheduled(cron = "0/60 * * * * ?")
	public void execute() {
		try
		{
			log.info("从UAA定时刷新接口列表。");
			hfpsServerMgrService.loadInterfaces();
//			respCodeMsgMappingService.loadMappings();
		}
		catch(Exception ex)
		{
			log.error("定时刷新UAA的Interface/错误码-错误消息映射失败,异常:",ex);
		}
	}

}
