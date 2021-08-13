package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.em;

import com.jinninghui.datasphere.icreditstudio.framework.utils.Constants;

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
