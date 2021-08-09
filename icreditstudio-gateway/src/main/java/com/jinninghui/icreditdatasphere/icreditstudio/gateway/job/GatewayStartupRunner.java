package com.jinninghui.icreditdatasphere.icreditstudio.gateway.job;

import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GatewayStartupRunner implements CommandLineRunner{
	@Autowired
	private HFPSServiceMgrService hfpsServiceLoadInterface;

	Logger log = LoggerFactory.getLogger(GatewayStartupRunner.class);
	
	@Override
	public void run(String... arg0) throws Exception {
		//项目启动查询service表中的所有数据并且缓存到进程缓存
		try {
			hfpsServiceLoadInterface.loadInterfaces();
//			codeMsgMappingInterface.loadMappings();
		}
		catch(Exception e)
		{
			log.error("loadInterfaces或者loadMappings产生了异常,会导致API网关无法正常工作,异常信息:",e);
		}
		
		
	}

}
