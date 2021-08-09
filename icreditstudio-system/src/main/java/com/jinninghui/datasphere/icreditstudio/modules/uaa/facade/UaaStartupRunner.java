package com.jinninghui.datasphere.icreditstudio.modules.uaa.facade;

import com.jinninghui.datasphere.icreditstudio.modules.uaa.common.dto.Interface;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author jidonglin
 */
@Component
public class UaaStartupRunner implements CommandLineRunner{
	@Autowired
	private InterfaceService interfaceService;
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		//项目启动查询interface表中的所有数据并且缓存到进程缓存
		List<Interface> interfaceList = interfaceService.loadInterface();
		interfaceService.setCachedInterfaceList(interfaceList);
	}

}
