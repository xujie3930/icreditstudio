package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.impl;

import com.hashtech.businessframework.exception.interval.AppException;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import com.netflix.zuul.context.RequestContext;
import feign.codec.DecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthExceptionHandlerServiceImpl implements AuthExceptionHandlerService {

	private static final Logger log = LoggerFactory.getLogger(AuthExceptionHandlerServiceImpl.class);

	/**
	 * 本地异常处理（业务异常）
	 * 
	 * @param ctx
	 * @param code
	 * @param msg
	 */
	@Override
	public void businessExceptionHandler(RequestContext ctx, String code, String msg) {
		ctx.addZuulRequestHeader("code", code);
		ctx.addZuulRequestHeader("msg", msg);
	}

	/**
	 * 可同时处理本地异常以及远程异常 方法不会抛出异常
	 */
	@Override
	public void systemExceptionHandler(Exception e, RequestContext ctx) {
		try {
//			String subStr = StringUtils.substringBetween(e.getMessage(), "<span>", "</span>");
//			if (StringUtils.isNotBlank(subStr)) {
//				JSONObject jsonObject = JSONObject.parseObject(subStr.replaceAll("&quot;", "\""), JSONObject.class);
//				String code = (String) jsonObject.get("code");
//				String msg = respCodeMsgMappingService.getMsgByCode(code);
//				businessExceptionHandler(ctx, jsonObject.getString("code"), msg);
//			} else {
				log.info("错误信息中无法找到FeignException关键字,翻译为默认异常,e.getMessage:"+e.getMessage());
				if(e instanceof DecodeException) {
					DecodeException decodeException = (DecodeException) e;
					Throwable throwable = decodeException.getCause();
					if(throwable instanceof AppException) {
						AppException appException = (AppException) throwable;
						businessExceptionHandler(ctx, appException.getErrorCode(),
								appException.getErrorMsg());
					} else {
						businessExceptionHandler(ctx, Constants.ErrorCode.SYSTEM_FAIL.code,
								Constants.ErrorCode.SYSTEM_FAIL.comment);
					}
				} else {
					businessExceptionHandler(ctx, Constants.ErrorCode.SYSTEM_FAIL.code,
							Constants.ErrorCode.SYSTEM_FAIL.comment);
				}
//			}
		} catch (Exception ex) {
			log.error("翻译异常失败,异常msg:" + e.getMessage() + ",翻译为默认系统异常", ex);
			businessExceptionHandler(ctx, Constants.ErrorCode.SYSTEM_FAIL.code,
					Constants.ErrorCode.SYSTEM_FAIL.comment);
		}
	}

	@Override
	public void systemExceptionHandler(Exception e, RequestContext ctx, String code, String msg) {
		try {
			businessExceptionHandler(ctx, code, msg);
		} catch (Exception ex) {
			log.error("翻译异常失败,异常msg:" + e.getMessage() + ",翻译为默认系统异常", ex);
			businessExceptionHandler(ctx, Constants.ErrorCode.SYSTEM_FAIL.code,
					Constants.ErrorCode.SYSTEM_FAIL.comment);
		}
	}
}
