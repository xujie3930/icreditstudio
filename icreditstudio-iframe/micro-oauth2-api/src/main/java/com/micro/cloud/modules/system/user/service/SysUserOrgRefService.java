package com.micro.cloud.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefRespVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;

import java.util.List;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysUserOrgRefService extends IService<SysUserOrgRef> {
    /**
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<SysUserOrgRef> page(SysUserOrgRefPageReqVO reqVO);

}
