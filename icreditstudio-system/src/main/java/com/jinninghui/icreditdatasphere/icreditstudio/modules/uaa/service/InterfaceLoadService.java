package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.common.dto.Interface;

import java.util.List;

/**
 * interface服务接口
 *
 * @author Administrator
 *
 */
public interface InterfaceLoadService {

	List<Interface> loadInterface();

	List<String> loadButtonMenuUrlList();

}
