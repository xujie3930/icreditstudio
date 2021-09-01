package com.jinninghui.datasphere.icreditstudio.system.common.config;

import com.jinninghui.datasphere.icreditstudio.system.common.utils.ObjectConvertUtils;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code.CommonConstant;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.SessionService;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * mybatis拦截器，
 * 自动注入主键,创建人、创建时间、修改人、修改时间
 *
 * @Author hzh
 * @Date 2021-02-22
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    @Lazy
    private SessionService sessionService;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        String sqlId = mappedStatement.getId();
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];

        if (parameter == null) {
            return invocation.proceed();
        }
        if (SqlCommandType.INSERT == sqlCommandType) {
            UserEntity sysUser = this.getLoginUser();
            Field[] fields = ObjectConvertUtils.getAllFields(parameter);
            for (Field field : fields) {
                log.debug("------field.name------" + field.getName());
                try {
                    // 主键 id
                    if ("id".equals(field.getName())) {
                        field.setAccessible(true);
                        Object localId = field.get(parameter);
                        field.setAccessible(false);
                        if (localId == null || "".equals(localId)) {
                            // 新增数据自动主键复制id
                            field.setAccessible(true);
                            field.set(parameter, sequenceService.nextValueString());
                            field.setAccessible(false);
                        }
                    }
                    // 创建人id
                    if ("createUserId".equals(field.getName())) {
                        field.setAccessible(true);
                        Object localCreateUserId = field.get(parameter);
                        field.setAccessible(false);
                        if (localCreateUserId == null || "".equals(localCreateUserId)) {
                            if (sysUser != null) {
                                // 登录人账号
                                field.setAccessible(true);
                                field.set(parameter, sysUser.getId());
                                field.setAccessible(false);
                            }
                        }
                    }
                    // 注入创建时间
                    if ("createTime".equals(field.getName())) {
                        field.setAccessible(true);
                        Object localCreateDate = field.get(parameter);
                        if (localCreateDate == null || "".equals(localCreateDate)) {
                            field.set(parameter, System.currentTimeMillis());
                        }
                        field.setAccessible(false);
                    }
                    // 注入删除标志位 默认未删除
                    if ("deleteFlag".equals(field.getName())) {
                        field.setAccessible(true);
                        Object deleteFlag = field.get(parameter);
                        if (deleteFlag == null || "".equals(deleteFlag)) {
                            field.set(parameter, CommonConstant.DELETE_FLAG_N);
                        }
                        field.setAccessible(false);
                    }
                } catch (Exception e) {
                }
            }
        }
        if (SqlCommandType.UPDATE == sqlCommandType) {
            UserEntity sysUser = this.getLoginUser();
            Field[] fields = null;
            if (parameter instanceof ParamMap) {
                ParamMap<?> p = (ParamMap<?>) parameter;
                if (p.containsKey("et")) {
                    parameter = p.get("et");
                } else {
                    parameter = p.get("param1");
                }
                if (parameter == null) {
                    return invocation.proceed();
                }

                fields = ObjectConvertUtils.getAllFields(parameter);
            } else {
                fields = ObjectConvertUtils.getAllFields(parameter);
            }

            for (Field field : fields) {
                log.debug("------field.name------" + field.getName());
                try {
                    // 修改用户id
                    if ("lastUpdateUserId".equals(field.getName())) {
                        //获取登录用户信息
                        if (sysUser != null) {
                            // 登录账号
                            field.setAccessible(true);
                            field.set(parameter, sysUser.getId());
                            field.setAccessible(false);
                        }
                    }
                    // 修改时间
                    if ("lastUpdateTime".equals(field.getName())) {
                        field.setAccessible(true);
                        field.set(parameter, System.currentTimeMillis());
                        field.setAccessible(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }

    private UserEntity getLoginUser() {
        UserEntity sysUser = new UserEntity();
        try {
            //获取登录人信息
            sysUser.setId(sessionService.getUserId());

        } catch (Exception e) {
            sysUser.setId("1");
        }
        return sysUser;
    }


}
