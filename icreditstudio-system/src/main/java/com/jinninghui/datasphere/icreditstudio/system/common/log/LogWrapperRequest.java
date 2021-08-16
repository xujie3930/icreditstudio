package com.jinninghui.datasphere.icreditstudio.system.common.log;

import com.jinninghui.datasphere.icreditstudio.framework.utils.NetworkUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LogWrapperRequest extends HttpServletRequestWrapper {

    private Cookie[] coo;
    private String requestUri;
    private String remoAddr;
//    private byte[] requestBody;
    private Map<String, String> headers = new HashMap<>();

    public LogWrapperRequest(HttpServletRequest request) {
        super(request);
        this.requestUri = request.getRequestURI();
        this.coo = ArrayUtils.clone(request.getCookies());
        getHeaders(request);
//        this.remoAddr = request.getRemoteAddr();
        try {
            this.remoAddr = NetworkUtils.getIpAddress(request);
//            this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String header = request.getHeader(key);
            headers.put(key, header);
        }
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    /*@Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return bis.read();
            }
        };
    }*/

    @Override
    public String getRequestURI() {
        return this.requestUri;
    }

    @Override
    public Cookie[] getCookies() {
        return this.coo;
    }

    @Override
    public String getRemoteAddr() {
        return this.remoAddr;
    }
}
