package com.jinninghui.datasphere.icreditstudio.datasource.service;

import com.jinninghui.datasphere.icreditstudio.datasource.service.impl.DataSourceUrlParseContainer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Peng
 */
@Slf4j
public abstract class AbstractConnectionSourceParser implements ConnectionSourceParser, DialectKeyRegister {
    public AbstractConnectionSourceParser() {
        try {
            Class.forName(getDriverClass());
            register();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 驱动类名称
     *
     * @return
     */
    public abstract String getDriverClass();

    @Override
    public void register() {
        DataSourceUrlParseContainer.getInstance().put(getDialect(), this);
    }
}
