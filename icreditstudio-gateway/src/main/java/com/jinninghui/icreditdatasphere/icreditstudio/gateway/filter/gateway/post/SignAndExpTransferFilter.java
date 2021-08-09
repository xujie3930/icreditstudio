package com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.post;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.RespCodeMsgMappingService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.pre.auth.FinalAuthFilter;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.SignMsg;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：两个功能：
 * 1、如果响应需要签名，则签名
 * 2、翻译异常至错误码，屏蔽内部服务的异常
 */
@ConfigurationProperties(prefix = "apigw") //接收application.yml中的apigw下面的属性
@Component
@RefreshScope
public class SignAndExpTransferFilter extends ZuulFilter {
    private final static int SUCCESS = 200;
    
    static Logger log = LoggerFactory.getLogger(SignAndExpTransferFilter.class);
    @Autowired
    private UaaService uaaService;
    
    @Autowired
    RespCodeMsgMappingService respCodeMsgMappingService;
    
    
    private List<String> staticResources = new ArrayList<>();

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return FilterOrderConst.SIGN_AND_EXPTRANSFER_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        // 设置编码
        response.setCharacterEncoding("utf-8");
        ctx.setResponse(response);
        // 如果不进行路由的，则直接返回
        Object sendZuulResponse = ctx.get("sendZuulResponse");
        if (sendZuulResponse != null && Boolean.parseBoolean(sendZuulResponse.toString()) == false) {
            return null;
        }
        if (SUCCESS == ctx.getResponseStatusCode()) {
        	signResponseIfNeed(ctx, response);
            return null;
        }
        return null;
    }

	protected void signResponseIfNeed(RequestContext ctx, HttpServletResponse response) {
		HttpServletRequest request = ctx.getRequest();
		String requestUri = FinalAuthFilter.getUri(request);
		for (String staticResource : staticResources) {
			if (requestUri.endsWith(staticResource)) {
				return;
			}
		}
		
		
		if(ctx.getZuulRequestHeaders().containsKey(Constants.NEED_SIGN_RESPONSE))
		{
			InputStream responseDataStream = ctx.getResponseDataStream();
			try {
				String body = IOUtils.toString(responseDataStream , response.getCharacterEncoding());
				if (StringUtils.isBlank(body)) {
					return;
				}
				try {
					//拿到的是json字符串
                    ctx.setResponseBody(body);
					String sign = uaaService.sign(body);
					SignMsg signMsg = JSONObject.parseObject(sign , SignMsg.class);
					if (!signMsg.isSuccess()){
					    log.info(requestUri+"的响应签名异常，errorCode = " + signMsg.getReturnCode() + "，errorMsg = " + signMsg.getReturnMsg());
                    }else {
                        ctx.addZuulResponseHeader("x-dsp-sign", signMsg.getSign());
                        log.info(requestUri+"的响应签名结果:"+signMsg.getSign());
                        ctx.addZuulResponseHeader("x-dsp-sign-no", signMsg.getSignSn());
                    }
				} catch (Exception e) {
					log.error(requestUri+"的响应签名异常,",e);
					ctx.setResponseBody(body);
					return;
				}
			} catch (Exception e) {
				log.error("",e);
			}
		}
	}

    public List<String> getStaticResources() {
        return staticResources;
    }

    public void setStaticResources(List<String> staticResources) {
        this.staticResources = staticResources;
    }

}
