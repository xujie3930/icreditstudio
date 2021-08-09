package com.jinninghui.datasphere.icreditstudio.gateway.common;

import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessResult;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: jidonglin
 * @Date: 2020/1/16 11:42
 */
public class HttpResponseUtils {

    protected final static Logger logger = LoggerFactory.getLogger(HttpResponseUtils.class);

    public static BusinessResult<String> response(HttpResponse response) throws Exception{
        try {
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();

            if (state == HttpStatus.SC_OK) {
                String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
                return BusinessResult.success(jsonString);
            }else{
                throw new AppException(Constants.ErrorCode.ERROR_HTTP.code);
            }
        } catch (IOException e) {
            throw e;
        }
    }

}
