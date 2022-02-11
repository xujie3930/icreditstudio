package com.micro.cloud.modules.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.modules.system.role.dataobject.SysRoleResourceRef;
import com.micro.cloud.modules.system.role.mapper.SysRoleResourceRefMapper;
import com.micro.cloud.modules.system.role.service.SysRoleResourceRefService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysRoleResourceRefServiceImpl
    extends ServiceImpl<SysRoleResourceRefMapper, SysRoleResourceRef>
    implements SysRoleResourceRefService {}
