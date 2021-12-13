package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.service.AbstractConnectionSourceParser;
import com.jinninghui.datasphere.icreditstudio.datasource.service.ConnectionSource;
import com.jinninghui.datasphere.icreditstudio.framework.utils.sm4.SM4Utils;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 */
@Component
public class OracleConnectionSourceParser extends AbstractConnectionSourceParser {
    @Override
    public String getDriverClass() {
        return DatasourceTypeEnum.ORACLE.getDriver();
    }

    @Override
    public String getDialect() {
        return DatasourceTypeEnum.ORACLE.getDesc();
    }

    @Override
    public ConnectionSource parse(String s) {
        return new OracleConnectionSource(s);
    }

    public static class OracleConnectionSource implements ConnectionSource {

        public static final String SEPARATOR = "|";
        private String url;

        public OracleConnectionSource(String url) {
            this.url = url;
        }

        @Override
        public String getDriverClass() {
            return DatasourceTypeEnum.ORACLE.getDriver();
        }

        @Override
        public String getUrl() {
            return StrUtil.subBefore(this.url, SEPARATOR, false);
        }

        @Override
        public String getUsername() {
            //根据uri获取username
            String temp = this.url.substring(this.url.indexOf("username=") + "username=".length());
            String username = temp.substring(0, temp.indexOf(SEPARATOR));
            return username;
        }

        @Override
        public String getPassword() {
            //根据uri获取password
            String temp = this.url.substring(this.url.indexOf("password=") + "password=".length());
            String password;
            if (!temp.endsWith(SEPARATOR)) {
                password = temp;
            } else {
                password = temp.substring(0, temp.indexOf(SEPARATOR));
            }
            SM4Utils sm4 = new SM4Utils();
            return sm4.decryptData_ECB(password);
        }
    }
}
