package com.micro.cloud.modules.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysUserRoleRefPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.mapper.SysUserRoleRefMapper;
import com.micro.cloud.modules.system.user.service.SysUserRoleRefService;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysUserRoleRefServiceImpl extends ServiceImpl<SysUserRoleRefMapper, SysUserRoleRef>
    implements SysUserRoleRefService {

    @Resource
    private SysUserRoleRefMapper sysUserRoleRefMapper;
    @Override
    public List<SysUserRoleRef> page(SysUserRoleRefPageReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize(), true);
        List<SysUserRoleRef> list=sysUserRoleRefMapper.selectSysUserRoleRefPageList(reqVO);
        PageInfo<SysUserRoleRef> pageInfo=new PageInfo<>(list);
        return pageInfo.getList();
    }
}
