package com.jinninghui.datasphere.icreditstudio.modules.system.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.entity.LoginLogEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.mapper.LoginLogMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.LoginLogService;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.param.LoginLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.web.request.LoginLogEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service("loginLogService")
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogEntity> implements LoginLogService {

    @Override
    public BusinessPageResult queryPage(LoginLogEntityPageRequest pageRequest) {
        QueryWrapper<LoginLogEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageRequest.getUserAccount())) {
            wrapper.like(LoginLogEntity.USER_ACCOUNT, pageRequest.getUserAccount());
        }
        if (StringUtils.isNotBlank(pageRequest.getUserName())) {
            wrapper.like(LoginLogEntity.USER_NAME, pageRequest.getUserName());
        }
        if (Objects.nonNull(pageRequest.getStartTime())) {
            wrapper.ge(LoginLogEntity.LOGIN_TIME, pageRequest.getStartTime());
        }
        if (Objects.nonNull(pageRequest.getEndTime())) {
            wrapper.le(LoginLogEntity.LOGIN_TIME, pageRequest.getEndTime());
        }
        wrapper.orderByDesc(LoginLogEntity.LOGIN_TIME);
        IPage<LoginLogEntity> page = this.page(
                new Query<LoginLogEntity>().getPage(pageRequest),
                wrapper

        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public LoginLogEntity log(LoginLogEntitySaveParam param) {

        LoginLogEntity entity = new LoginLogEntity();
        BeanCopyUtils.copyProperties(param, entity);
        saveOrUpdate(entity);
        return entity;
    }

    @Override
    public List<LoginLogEntity> getLogByToken(String token) {
        QueryWrapper<LoginLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(LoginLogEntity.USER_TOKEN, token);
        return list(wrapper);
    }
}
