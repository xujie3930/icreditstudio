package com.micro.cloud.modules.system.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.modules.system.app.dataobject.SysApplication;
import com.micro.cloud.modules.system.app.mapper.SysApplicationMapper;
import com.micro.cloud.modules.system.app.service.SysApplicationService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysApplicationServiceImpl extends ServiceImpl<SysApplicationMapper, SysApplication>
    implements SysApplicationService {}
