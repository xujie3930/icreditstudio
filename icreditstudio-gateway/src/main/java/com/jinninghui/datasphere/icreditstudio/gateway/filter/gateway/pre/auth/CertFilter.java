package com.jinninghui.datasphere.icreditstudio.gateway.filter.gateway.pre.auth;

import com.jinninghui.datasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.filter.gateway.pre.FilterOrderConst;
import com.jinninghui.datasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.param.CertificateAuthRequest;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.AuthResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 证书鉴权
 * 
 *
 */
@Component
public class CertFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(CertFilter.class);

	@Autowired
	private UaaService uaaService;
	@Autowired
	private HFPSServiceMgrService hfpsServiceMgrService;
	@Autowired
	private AuthExceptionHandlerService authExceptionHandler;
	

	

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String uri = FinalAuthFilter.getUri(request);
		String method = request.getMethod();

		if (ctx.getZuulRequestHeaders().containsKey(Constants.AUTH_PASS_KEY)) {
			log.info("不需要执行证书鉴权,因为其他鉴权Filter已经鉴权通过, uri=" + uri + ",method=" + method);
			return false;
		}
		if (!hfpsServiceMgrService.interfaceNeedAuth(uri, method, Constants.CERT_AUTH_TYPE)) {
			log.info("不需要执行证书鉴权,因为该接口未配置需要执行证书鉴权, uri=" + uri + ",method=" + method);
			return false;
		}

		String signSN = request.getHeader("x-dsp-sign-no");
		String sign = request.getHeader("x-dsp-sign");
		if (StringUtils.isBlank(signSN) || StringUtils.isBlank(sign)) {
			log.info("不需要执行证书鉴权,因为请求头中没有sign-no和sign头, uri=" + uri + ",method=" + method);
			return false;
		}
		return true;
	}

	@Override
	public Object run() {
		// 证书鉴权处理逻辑
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String signOrigStr = null;
		String uri = FinalAuthFilter.getUri(request);
		String method = request.getMethod();
		if ("GET".equals(method)) {
			// 如果请求方式是get，那么对查询字符串进行验签
			signOrigStr = request.getQueryString();
		} else if ("POST".equals(method)) {
			// 如果是post，那么就对消息体的内容进行验签，
			try {
				signOrigStr = getBody(request.getInputStream(), request);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				authExceptionHandler.businessExceptionHandler(ctx, Constants.ErrorCode.SYSTEM_FAIL.code,
						Constants.ErrorCode.SYSTEM_FAIL.comment);
				return null;
			}
		} else {
			log.info("证书鉴权失败，仅支持post和get, uri=" + uri + ",method=" + method);
			authExceptionHandler.businessExceptionHandler(ctx, Constants.ErrorCode.UNSUPPORT_METHOD.code,
					Constants.ErrorCode.UNSUPPORT_METHOD.comment);
			return null;
		}
		return callUaaCertAuth(ctx, request, signOrigStr, uri, method);

	}

	protected Object callUaaCertAuth(RequestContext ctx, HttpServletRequest request, String queryString, String uri,
                                     String method) {
		String sign = request.getHeader("x-dsp-sign");
		String signSN = request.getHeader("x-dsp-sign-no");
		try {
			CertificateAuthRequest certRequest = new CertificateAuthRequest(queryString, uri, method, sign, signSN);
			AuthResponse response = uaaService.certificateAuth(certRequest);
			if(response.isSuccess()) {
				log.info("证书鉴权通过, uri=" + uri + ",method=" + method + ",客户编码:" + response.getCustomerCode());
				ctx.addZuulRequestHeader(Constants.AUTH_PASS_KEY, Constants.SUCCESS);
				ctx.addZuulRequestHeader(Constants.NEED_SIGN_RESPONSE, "true");
				ctx.addZuulRequestHeader("x-customer-code", response.getCustomerCode());
			} else {
				log.info("证书鉴权未通过, uri=" + uri + ",method=" + method);
				authExceptionHandler.businessExceptionHandler(ctx, response.getReturnCode(), response.getReturnMsg());
			}
		} catch (Exception e) {
			log.info("证书鉴权未通过, uri=" + uri + ",method=" + method);
			authExceptionHandler.systemExceptionHandler(e, ctx);
		}
		return null;
	}

	@Override
	public String filterType() {
		// 前置filter
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterOrderConst.CERT_FILTER_ORDER;
	}

	public UaaService getTokenService() {
		return uaaService;
	}

	public void setTokenService(UaaService tokenService) {
		this.uaaService = tokenService;
	}

	/**
	 * 获取body的内容，拿到body内容后会重新构造request放到ctx中
	 * @param inputStream
	 * @param request
	 * @return
	 * @throws IOException
	 */
	protected String getBody(InputStream inputStream, HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, request.getCharacterEncoding()));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		RequestContext ctx = RequestContext.getCurrentContext();
		final byte[] reqBodyBytes = sb.toString().getBytes();
		//拿到body中的数据之后重新写会request中
		ctx.setRequest(new HttpServletRequestWrapper(request) {
			@Override
			public ServletInputStream getInputStream() throws IOException {
				return new ServletInputStreamWrapper(reqBodyBytes);
			}
			@Override
			public int getContentLength() {
				return reqBodyBytes.length;
			}
			@Override
			public long getContentLengthLong() {
				return reqBodyBytes.length;
			}
		});
		return sb.toString();
	}

}
