package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.dto.Interface;

import java.util.List;

/**
 * interface服务接口
 *
 * @author Administrator
 */
public interface InterfaceLoadService {

    List<Interface> loadInterface();

    List<String> loadButtonMenuUrlList();

}
