package com.jinninghui.datasphere.icreditstudio.gateway.filter;

import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 判断最终鉴权是否通过
 * 
 * @author Lida
 *
 */
@Component
public class FinalAuthFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(FinalAuthFilter.class);

	@Autowired
	private HFPSServiceMgrService hfpsServiceMgrService;

	@Autowired
	private AuthExceptionHandlerService authExceptionHandlerService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String uri = exchange.getRequest().getURI().getPath();
		String requestURI = FinalAuthFilter.getUri(uri);
		String method = exchange.getRequest().getMethod().name();
		if(exchange.getRequest().getHeaders().containsKey(Constants.AUTH_PASS_KEY)){  // 前面的过滤器已经鉴权通过，直接放行
			log.info("tokenAuth or certAuth passed: uri=" + requestURI + ",method=" + method);
			return chain.filter(exchange);
		}else {
			try {//这里不能调用interfaceNeedAuth，因为如果找不到对应的interface，也是要鉴权失败的
				if(hfpsServiceMgrService.matchOneExactlyInterface(requestURI, method))
				{
					return chain.filter(exchange);
				}
				if(hfpsServiceMgrService.matchOneWildInterface(requestURI, method))
				{
					return chain.filter(exchange);
				}
				return processAuthFailed(exchange, requestURI, method);
			}
			catch(Exception ex)
			{
				log.error("exception:",ex);
				return authExceptionHandlerService.handleException(exchange, Constants.ErrorCode.AUTH_FAIL.code, Constants.ErrorCode.AUTH_FAIL.comment);
			}
		}
	}

	@Override
	public int getOrder() {
		return 2;
	}

	protected Mono<Void> processAuthFailed(ServerWebExchange exchange, String requestURI, String method) {
		log.info("tokenAuth or certAuth not pass: uri=" + requestURI + ",method=" + method);
		return  authExceptionHandlerService.handleException (exchange, Constants.ErrorCode.AUTH_FAIL.code, Constants.ErrorCode.AUTH_FAIL.comment);
	}

	/**
	 * 如果uri中有两个//,去掉一个,以统一测试环境和生产环境的不同分隔符处理
	 * @param uri
	 * @return
	 */
	public static String getUri(String uri)
	{
		if(uri.startsWith("//")) {
			return uri.substring(1);
		} else {
			return uri;
		}
	}


}
