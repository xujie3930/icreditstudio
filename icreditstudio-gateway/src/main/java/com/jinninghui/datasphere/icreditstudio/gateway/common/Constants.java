package com.jinninghui.datasphere.icreditstudio.gateway.common;

public interface Constants {
	final String AUTH_PASS_KEY="isauth";
	final String NEED_SIGN_RESPONSE="needsignresp";
	final String TOKEN_AUTH_TYPE = "token";
	final String TOKEN_AUTH_TYPE_OUT = "Access-Token";
	final String CERT_AUTH_TYPE = "cert";

    enum ErrorCode {
        SYSTEM_FAIL("API01", "系统处理错误，请联系管理员"),
        FAIL("API02", "cookie获取失败"),
        COOKIE_NOT_EXIST("API03", "cookie获取失败"),
        COOKIE_VALIDATE_FAIL("API04", "cookie校验失败"),
        INTERFACE_AUTH_FAIL("API05", "接口鉴权失败"),
        CERTIFICATE_AUTH_FAIL("API06", "证书鉴权失败"),
    	AUTH_FAIL("API07" , "鉴权不通过"),
    	POST_CONTENTNULL("API08" , "post请求body中的内容为空"),
    	CERTIFICATE_AUTH_SIGNSNNULL("API09", "证书鉴权signSN为空"),
    	CERTIFICATE_AUTH_CUSTOMERCODENULL("API10", "证书鉴权客户编码为空"),
    	TOKEN_AUTH_FAIL("API11", "token鉴权失败"),
    	UNSUPPORT_METHOD("API12", "不支持的请求方式"),
    	FAIL_GET_AUTHINTERFACE("API13", "获取接口鉴权失败"),
    	ERROR_HTTP("API14", "HTTP请求异常"),
		;
        public final String code;
        public final String comment;

        ErrorCode(String code, String comment) {
            this.code = code;
            this.comment = comment;
        }
    }

    /**
	 * 跟common的Constants常量一致
	 */
	public final static String SUCCESS = ReturnCode.SUCCESS.code;
	public final static String FAIL = ReturnCode.FAIL.code;
	enum ReturnCode {
		SUCCESS ("0",   "处理成功"),
		FAIL ("1",   "系统内部错误");
		public final String code;
		public final String comment;
		ReturnCode(String code, String comment) {
			this.code = code;
			this.comment = comment;
		}
	}

	/**
	 * 是否需要鉴权
	 */
	enum NeedAuth {
		NOT_NEED_AUTH (0,   "不需要鉴权"),
		NEED_AUTH (1,   "需要鉴权");
		public final Integer code;
		public final String comment;
		NeedAuth(Integer code, String comment) {
			this.code = code;
			this.comment = comment;
		}
	}

	/**
	 * 是否需要鉴权
	 */
	enum UriType {
		/**
		 * 接口地址
		 */
		INTERFACE ("0",   "接口地址"),
		/**
		 * 通配符
		 */
		WILDCARD ("1",   "通配符");
		public final String code;
		public final String comment;
		UriType(String code, String comment) {
			this.code = code;
			this.comment = comment;
		}
	}

	enum UserType {
		OPERATOR_USER ("1",   "运营管理用户"),
		MERCHANT_USER ("2",   "商户门户用户");
		public final String code;
		public final String comment;
		UserType(String code, String comment) {
			this.code = code;
			this.comment = comment;
		}
	}

}
