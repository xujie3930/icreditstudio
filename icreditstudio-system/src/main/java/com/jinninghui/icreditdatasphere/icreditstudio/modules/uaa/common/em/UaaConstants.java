package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.common.em;

import com.hashtech.businessframework.utils.Constants;

/**
 * 	UAA枚举
 * @author jidonglin
 *
 */
public interface UaaConstants extends Constants {


    enum UaaSeqName
	{
		/**
		 * BusinessToken序列
		 */
		BUSINESS_TOKEN_ID("UAA:BusinessToken:ID");
		public final String name;
		UaaSeqName(String name){
			this.name = name;
		}
	}
}
