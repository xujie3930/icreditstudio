package com.jinninghui.datasphere.icreditstudio.modules.uaa.facade;

import com.jinninghui.datasphere.icreditstudio.modules.uaa.common.dto.Interface;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.InterfaceService;
import com.hashtech.businessframework.log.Logable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 定时器查新interface表，并且更新进程缓存
 * @author Administrator
 *
 */
@Component
public class LoadInterfacesJob {
	@Autowired
	private InterfaceService interfaceService;
	
	@Logable(businessTag = "loadInterfacesJob", isEntrance=true)
	/**
	 * 	每一分钟执行一次
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void execute() {
		// TODO Auto-generated method stub
		//加写锁
		List<Interface> interfaceList = interfaceService.loadInterface();
		interfaceService.setCachedInterfaceList(interfaceList);
	}
	
}
