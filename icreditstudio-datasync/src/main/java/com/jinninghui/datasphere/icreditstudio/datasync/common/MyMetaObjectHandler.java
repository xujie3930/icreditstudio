package com.jinninghui.datasphere.icreditstudio.datasync.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Peng
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", new Date(), metaObject);
        //todo  获取用户ID
        setFieldValByName("createUserId", "", metaObject);
        setFieldValByName("lastUpdateTime", new Date(), metaObject);
        //todo  获取用户ID
        setFieldValByName("lastUpdateUserId", "", metaObject);
        setFieldValByName("deleteFlag", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("lastUpdateTime", new Date(), metaObject);
        //todo  获取用户ID
        setFieldValByName("lastUpdateUserId", "", metaObject);
    }
}
