package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.common.dto;

/**
 * 	接口授权
 *	 每个Interface指一个对外接口
 * 	@author jidonglin
 */
public class Interface {
	/**
	 * 数据库主键
	 */
	private Long interfaceId;
	/**
	 * 接口URI
	 */
	private String uri;
	/**
	 * GET POST PUT DELETE
	 */
	private String method;
	/**
	 * 接口归属系统模块
	 */
	private String module;
	/**
	 * 接口权限代码
	 */
	private String code;
	/**
	 * 中文名称
	 */
	private String name;
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 是否需要鉴权，0：不需要鉴权，1：需要鉴权
	 */
	private Integer needAuth;
	/**
	 * 支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token
	 */
	private String supportAuthType;
	/**
	 * 链接的类型，0：接口地址，1：通配符
	 */
	private String uriType;
	public Long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getNeedAuth() {
		return needAuth;
	}
	public void setNeedAuth(Integer needAuth) {
		this.needAuth = needAuth;
	}
	public String getSupportAuthType() {
		return supportAuthType;
	}
	public void setSupportAuthType(String supportAuthType) {
		this.supportAuthType = supportAuthType;
	}
	public String getUriType() {
		return uriType;
	}
	public void setUriType(String uriType) {
		this.uriType = uriType;
	}
}
