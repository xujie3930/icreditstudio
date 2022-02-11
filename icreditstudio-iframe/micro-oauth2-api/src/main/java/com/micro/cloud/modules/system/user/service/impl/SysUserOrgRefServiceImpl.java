package com.micro.cloud.modules.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefRespVO;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.modules.system.user.service.SysUserOrgRefService;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
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
public class SysUserOrgRefServiceImpl extends ServiceImpl<SysUserOrgRefMapper, SysUserOrgRef>
    implements SysUserOrgRefService {

    @Resource
    private SysUserOrgRefMapper sysUserOrgRefMapper;

    @Override
    public List<SysUserOrgRef> page(SysUserOrgRefPageReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize(), true);
        List<SysUserOrgRef> list = sysUserOrgRefMapper.selectSysUserRefList(reqVO);
        PageInfo<SysUserOrgRef> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }
}
