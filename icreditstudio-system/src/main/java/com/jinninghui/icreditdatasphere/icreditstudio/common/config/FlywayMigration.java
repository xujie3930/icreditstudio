package com.jinninghui.icreditdatasphere.icreditstudio.common.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

/**
 * @author hzh
 * @description Flyway
 * @date 2021/3/12 10:48
 */
@Component
public class FlywayMigration implements FlywayMigrationStrategy {
    @Override
    public void migrate(Flyway flyway) {
        // 如果第一次初始化，则以当前数据库版本为基准
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
    }
}
