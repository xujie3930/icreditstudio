package com.jinninghui.datasphere.icreditstudio.common.log;

import com.jinninghui.datasphere.icreditstudio.modules.system.common.code.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.common.em.UaaCodeBean;
import com.hashtech.businessframework.exception.BaseException;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Created by PPai on 2021/6/2 10:56
 */
@Getter
public final class LogHandlerUtil {

    private Log.Type type;

    private Log.OperateType operateType;

    private String extend;

    private boolean flag = false;

    private LogHandlerUtil() {
    }

    public static LogHandlerUtil build(Method method) {
        if (Objects.isNull(method)) throw new NullPointerException();
        Log annotation = method.getAnnotation(Log.class);
        if (Objects.isNull(annotation)) throw new NullPointerException();
        LogHandlerUtil util = new LogHandlerUtil();
        util.type = annotation.type();
        util.operateType = annotation.operateType();
        util.extend = annotation.extend();
        return util;
    }

    public boolean filter(BiPredicate<Log.Type, Log.OperateType> predicate) {
        return this.flag = predicate.test(type, operateType);
    }

    public void consumer(BiConsumer<Log.Type, Log.OperateType> consumer) {
        if (flag) {
            consumer.accept(type, operateType);
        }
    }

    public <R> Optional<R> map(BiFunction<Log.Type, Log.OperateType, R> function) {
        Optional<R> r = Optional.empty();
        if (flag) {
            r = Optional.ofNullable(function.apply(type, operateType));
        }
        return r;
    }

    public String getExtend() {
        return this.extend;
    }

    public static String getAppExceptionErrorMsg(Throwable t) {
        String msg = "";
        if (t instanceof BaseException) {
            BaseException e = (BaseException) t;
            UaaCodeBean.UaaCode uaaCode = UaaCodeBean.UaaCode.find(e.getErrorCode());
            if (Objects.nonNull(uaaCode)) {
                msg = uaaCode.getMessage();
            } else {
                ResourceCodeBean.ResourceCode resourceCode = ResourceCodeBean.ResourceCode.find(e.getErrorCode());
                if (Objects.nonNull(resourceCode)) {
                    msg = resourceCode.getMessage();
                }
            }

        }
        return msg;
    }
}
