package com.jinninghui.datasphere.icreditstudio.framework.result.base;

import lombok.Data;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.common
 * ClassName: BaseEntity
 * Description:  BaseEntity类
 * Date: 2021/5/26 2:23 下午
 *
 * @author liyanhui
 */
@Data
public class BaseEntity extends BaseObject {

    //********************常量字段 start************************//

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String CREATE_USER_ID = "CREATE_USER_ID";
    public static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
    public static final String LAST_UPDATE_USER_ID = "LAST_UPDATE_USER_ID";

    //********************常量字段 endt************************//

    //********************转换方法 start***********************//

    public <T extends BaseResult> T  toResult(Class<T> targetClazz){
        return super.createAndCopyProperties(targetClazz);
    }

    //********************转换方法 end***********************//

}
