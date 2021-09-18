package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.service.AbstractConnectionSourceParser;
import com.jinninghui.datasphere.icreditstudio.datasource.service.ConnectionSource;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 */
@Component
public class MysqlConnectionSourceParser extends AbstractConnectionSourceParser {
    @Override
    public String getDialect() {
        return DatasourceTypeEnum.MYSQL.getDesc();
    }

    @Override
    public String getDriverClass() {
        return DatasourceTypeEnum.MYSQL.getDriver();
    }

    @Override
    public ConnectionSource parse(String s) {
        return new MysqlConnectionSource(s);
    }

    public static class MysqlConnectionSource implements ConnectionSource {

        public static final String SEPARATOR = "|";
        private String url;

        public MysqlConnectionSource(String url) {
            this.url = url;
        }

        @Override
        public String getDriverClass() {
            return DatasourceTypeEnum.MYSQL.getDriver();
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
            return password;
        }
    }
}
