package com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.post;

import com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.pre.auth.FinalAuthFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：消除缓存功能
 */
@ConfigurationProperties(prefix = "apigw")
@Component
@RefreshScope
public class NoCacheFilter extends ZuulFilter {

    private List<String> staticResources = new ArrayList<>();

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return FilterOrderConst.NO_CACHE_FILTER_ORDER;
    }

    @Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURI = FinalAuthFilter.getUri(request);
		if (requestURI.indexOf(".") != -1) {//所有不能Cache的URI中均不会包含.，一般有.的都是静态资源
			return false;
		} else {
			return true;
		}
	}

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        //指定页面不缓存
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        return null;
    }

    public List<String> getStaticResources() {
        return staticResources;
    }

    public void setStaticResources(List<String> staticResources) {
        this.staticResources = staticResources;
    }


}
