package com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.pre.auth;

import com.hashtech.businessframework.utils.NetworkUtils;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.pre.FilterOrderConst;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 描述 ：拦截所有的访问地址，截取token，根据访问的uri，判断是否需要token校验
 */
@Component
public class TokenFilter extends ZuulFilter {

	static Logger log = LoggerFactory.getLogger(TokenFilter.class);

	@Autowired
	private UaaService uaaService;

	@Autowired
	private AuthExceptionHandlerService authExceptionHandler;

	@Autowired
	private HFPSServiceMgrService hfpsServiceMgrService;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterOrderConst.TOKEN_FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURI = FinalAuthFilter.getUri(request);
		String method = request.getMethod();
		if (ctx.getZuulRequestHeaders().containsKey(Constants.AUTH_PASS_KEY)) {
			log.info("不需要执行token鉴权,因为其他鉴权Filter已经鉴权通过, uri=" + requestURI + ",method=" + method);
			return false;
		}
		if (!hfpsServiceMgrService.interfaceNeedAuth(requestURI, method, Constants.TOKEN_AUTH_TYPE)) {
			log.info("不需要执行token鉴权,因为该接口未配置需要执行token鉴权, uri=" + requestURI + ",method=" + method);
			return false;
		}
		if (StringUtils.isBlank(getToken(request)) && request.getHeader(Constants.TOKEN_AUTH_TYPE_OUT)==null) {
			log.info("不需要执行token鉴权,因为请求的cookies中没有包含token, uri=" + requestURI + ",method=" + method);
			return false;
		}
		return true;
	}

	protected String getToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (Constants.TOKEN_AUTH_TYPE.equalsIgnoreCase(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		return token;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// 1 内部管理系统  2  外部登录用户
        String requestType = "2";

		String requestURI = FinalAuthFilter.getUri(request);
		String method = request.getMethod();
		// 外部登录用户  Access-Token 获取token鉴权
        String token = request.getHeader(Constants.TOKEN_AUTH_TYPE_OUT);
        // 内部管理系统 从Cookie 中获取 token
        if (token == null) {
            requestType = "1";
            token = getToken(request);
        }
		try {
			// token鉴权
			BusinessToken businessToken = uaaService.tokenAuth(requestURI, method, token,requestType);
			if(businessToken.isSuccess()) {
				// 鉴权通过
				addZuulReqHeader(ctx, request, requestURI, businessToken);
				log.info("token鉴权成功, uri=" + requestURI + ",method=" + method);
			} else {
				authExceptionHandler.businessExceptionHandler(ctx, businessToken.getReturnCode(), businessToken.getReturnMsg());
			}
			return null;
		} catch (Exception e) {//token鉴权失败会抛出异常
			authExceptionHandler.systemExceptionHandler(e, ctx);
			return null;
		}
	}

	/**
	 * 添加必要信息到请求头
	 */
	protected void addZuulReqHeader(RequestContext ctx, HttpServletRequest request, String requestURI,
			BusinessToken businessToken) throws IOException {
		ctx.addZuulRequestHeader(Constants.AUTH_PASS_KEY, Constants.SUCCESS);
		ctx.addZuulRequestHeader("x-token", businessToken.getToken());
		ctx.addZuulRequestHeader("x-Access-Token", businessToken.getToken());
		//用户ID
		ctx.addZuulRequestHeader("x-userid", businessToken.getUserId().toString());
		//客户编码
		ctx.addZuulRequestHeader("x-customer-code", businessToken.getCustomerCode());
		//用户角色
		ctx.addZuulRequestHeader("x-roleId", businessToken.getRoleId() != null ? businessToken.getRoleId().toString() : null);
		//客户类型请求头
		ctx.addZuulRequestHeader("x-customer-type", businessToken.getCustomerTypeCode());
		ctx.addZuulRequestHeader("x-business-type", businessToken.getBusinessType());
		ctx.addZuulRequestHeader("x-extra", businessToken.getExtra());
		ctx.addZuulRequestHeader("x-user-ip", NetworkUtils.getIpAddress(request));
	}

}
