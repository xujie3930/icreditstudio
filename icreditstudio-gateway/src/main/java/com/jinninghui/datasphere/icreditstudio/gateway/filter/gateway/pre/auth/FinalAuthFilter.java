package com.jinninghui.datasphere.icreditstudio.gateway.filter.gateway.pre.auth;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import com.jinninghui.datasphere.icreditstudio.gateway.filter.gateway.pre.FilterOrderConst;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 判断最终鉴权是否通过
 * 
 * @author Lida
 *
 */
@Component
public class FinalAuthFilter extends ZuulFilter {
	static Logger log = LoggerFactory.getLogger(FinalAuthFilter.class);
	
	@Autowired
	private HFPSServiceMgrService hfpsServiceMgrService;
	
	/**
	 * 如果uri中有两个//,去掉一个,以统一测试环境和生产环境的不同分隔符处理
	 * @param request
	 * @return
	 */
	public static String getUri(HttpServletRequest request)
	{
		String uri = request.getRequestURI();
		if(uri.startsWith("//")) {
			return uri.substring(1);
		} else {
			return uri;
		}
	}


	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * lida 添加注释
	 */
	@Override
	public Object run() {
		// 最终鉴权
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURI = FinalAuthFilter.getUri(request);
		String method = request.getMethod();
		if (ctx.getZuulRequestHeaders().containsKey(Constants.AUTH_PASS_KEY)) {
			log.info("tokenAuth or certAuth passed: uri=" + requestURI + ",method=" + method);
			return null;
		} else {//前面鉴权没有通过
			try {//这里不能调用interfaceNeedAuth，因为如果找不到对应的interface，也是要鉴权失败的
				if(hfpsServiceMgrService.matchOneExactlyInterface(requestURI, method))
				{
					return null;
				}
				if(hfpsServiceMgrService.matchOneWildInterface(requestURI, method))
				{
					return null;
				}
				processAuthFailed(ctx, requestURI, method);
			}
			catch(Exception ex)
			{
				log.error("exception:",ex);
				authFail(ctx, Constants.ErrorCode.AUTH_FAIL.code, Constants.ErrorCode.AUTH_FAIL.comment);
			}
			return null;
		}
	}


	protected void processAuthFailed(RequestContext ctx, String requestURI, String method) {
		Map<String, String> map = ctx.getZuulRequestHeaders();
		String code = map.get("code");//获得其他鉴权Filter设置的code和msg
		String msg = map.get("msg");
		log.info("tokenAuth or certAuth not pass: uri=" + requestURI + ",method=" + method + ", code =" + code
				+ ",msg =" + msg);
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(msg)) {
			authFail(ctx, code, msg);
		} else {
			authFail(ctx, Constants.ErrorCode.AUTH_FAIL.code, Constants.ErrorCode.AUTH_FAIL.comment);
		}
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterOrderConst.FINAL_AUTH_FILTER_ORDER;
	}

	/**
	 * 校验失败
	 *
	 * @param ctx
	 * @param code
	 * @param message
	 */
	protected void authFail(RequestContext ctx, String code, String message) {
		// 过滤该请求，不对其进行路由
		ctx.setSendZuulResponse(false);
		// 返回错误码
		ctx.setResponseStatusCode(200);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnCode", code);
		jsonObject.put("returnMsg", message);
		
		ctx.setResponseBody(jsonObject.toJSONString());
		ctx.getResponse().setContentType("application/json;charset=UTF-8");
	}

}
