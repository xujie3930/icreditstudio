package com.jinninghui.icreditdatasphere.icreditstudio.common.interceptor;

import com.jinninghui.icreditdatasphere.icreditstudio.common.enums.NumberEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.RoleService;
import com.hashtech.businessframework.exception.interval.AppException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * Created by PPai on 2021/6/11 19:14
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String userId = getUserId(request);
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //判断如果请求的类是swagger的控制器，直接通行。
        if(handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController")){
            return true;
        }

        List<ResourcesEntity> resources = roleService.findResourcesByUserId(userId);
        if (!containsUrl(requestURI, resources)) {
            throw new AppException("50009115");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getUserId(HttpServletRequest request) {
        return request.getHeader("x-userid");
    }

    private boolean containsUrl(String url, List<ResourcesEntity> resourcesEntities) {
        boolean flag = false;
        if (CollectionUtils.isNotEmpty(resourcesEntities) && StringUtils.isNotBlank(url)) {
            long count = resourcesEntities.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(resourcesEntity -> url.equals(resourcesEntity.getUrl()) || NumberEnum.ZERO.getNum().equals(resourcesEntity.getNeedAuth()))
                    .count();
            flag = count > 0;
        }
        return flag;
    }
}
