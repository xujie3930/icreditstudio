package com.micro.cloud.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysUserRoleRefPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;

import java.util.List;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysUserRoleRefService extends IService<SysUserRoleRef> {

    /**
     * 分页查询用户角色对应表
     *
     * @param reqVO 筛选条件请求 VO
     * @return
     */
    List<SysUserRoleRef> page(SysUserRoleRefPageReqVO reqVO);

}
