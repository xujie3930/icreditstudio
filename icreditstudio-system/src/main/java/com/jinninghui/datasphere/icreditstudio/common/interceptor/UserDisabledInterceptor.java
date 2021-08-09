package com.jinninghui.datasphere.icreditstudio.common.interceptor;

import com.jinninghui.datasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.SessionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Component
public class UserDisabledInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private OrganizationService organizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = getUserId(request);
        String token = getToken(request);
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            UserEntity byId = userService.getById(userId);
            if (Objects.nonNull(byId) && DeleteFlagEnum.Y.getCode().equals(byId.getDeleteFlag())) {
                sessionService.logout(token);
            }
            List<OrganizationEntity> organizations = organizationService.getOrganizationByUserId(userId);
            if (CollectionUtils.isNotEmpty(organizations)) {
                long count = organizations.parallelStream()
                        .filter(Objects::nonNull)
                        .filter(org -> DeleteFlagEnum.N.getCode().equals(org.getDeleteFlag()))
                        .count();
                if (count == 0) {
                    sessionService.logout(token);
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getUserId(HttpServletRequest request) {
        return request.getHeader("x-userid");
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("x-token");
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("x-Access-Token");
        }
        return token;
    }
}
