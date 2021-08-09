package com.jinninghui.datasphere.icreditstudio.gateway.service.param;

import com.alibaba.fastjson.JSON;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CertificateAuthRequest {

	String queryString;//签名原始串
	String uri;
	String method;
	String sign;//签名串
	String signSN;//证书序列号

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
}
