package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.interfaces.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.interfaces.web.result.InterfacesInfoExpert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author hzh
 */
@Mapper
public interface InterfacesDao extends BaseMapper<InterfacesEntity> {

    List<InterfaceAuthResult> getUserAuthInterfaceIdList(InterfaceAuthParam param);


    List<InterfacesInfoExpert> exportExcel(InterfacesEntity interfaces);

}
