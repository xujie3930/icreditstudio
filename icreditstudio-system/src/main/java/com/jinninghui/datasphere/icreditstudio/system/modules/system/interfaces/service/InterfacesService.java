package com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.service.param.InterfacesDelParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.web.request.InterfacesEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.web.request.InterfacesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.web.result.InterfacesInfoExpert;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hzh
 */
public interface InterfacesService extends IService<InterfacesEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(InterfacesEntityPageRequest pageRequest);

    /**
     * 根据用户id 获取用户可访问的接口列表
     *
     * @param param
     * @return
     */
    List<InterfaceAuthResult> getUserAuthInterfaceIdList(InterfaceAuthParam param);


    BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, InterfacesEntity interfaces);

    BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<InterfacesInfoExpert> interfacesEntityClass);

    BusinessResult<Boolean> save(InterfacesEntitySaveParam param);

    BusinessResult<Boolean> update(InterfacesEntitySaveParam param);

    BusinessResult<Boolean> delete(InterfacesDelParam param);
}

