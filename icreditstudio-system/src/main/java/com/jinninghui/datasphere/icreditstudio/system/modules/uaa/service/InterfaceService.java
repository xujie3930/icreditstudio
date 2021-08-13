package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.dto.Interface;

import java.util.List;

/**
 * interface服务接口
 *
 * @author Administrator
 *
 */
public interface InterfaceService {

	List<Interface> loadInterface();

	/**
	 * 线程安全
	 *
	 * @param interfaceList
	 */
	void setCachedInterfaceList(List<Interface> interfaceList);

	/**
	 * 线程安全
	 *
	 * @return
	 */
	List<Interface> getCachedInterfaceList();
	/**
	 * 线程安全
	 *
	 * @return
	 */
	List<String> getCachedButtonUrlList();

}
