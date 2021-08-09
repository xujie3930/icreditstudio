/**
 * 
 */
/**
 * @author Lida
 * 
 * 设计原则：
 * 所有鉴权类Filter构成一个链：
 * 任何一个鉴权通过，则设置ctx.addZuulRequestHeader(Constants.AUTH_PASS_KEY, Constants.SUCCESS);
 * 任何一个的shouldFilter均检查该头部参数是否已设置值，如果已设置，说明已经鉴权通过，则不再需要执行本鉴权
 * AuthFilter作为链中的最后一个Filter
 * 
 *
 */
package com.jinninghui.icreditdatasphere.icreditstudio.gateway.filter.gateway.pre;
