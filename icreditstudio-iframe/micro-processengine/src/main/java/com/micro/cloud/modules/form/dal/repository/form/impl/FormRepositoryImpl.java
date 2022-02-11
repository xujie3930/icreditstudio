package com.micro.cloud.modules.form.dal.repository.form.impl;

import com.micro.cloud.modules.form.dal.repository.form.FormRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/12/4
 * @since 1.0.0
 */
@Repository
public class FormRepositoryImpl implements FormRepository {

  private final JdbcTemplate jdbcTemplate;

  public FormRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * 执行创建数据库表语句
   *
   * @param sql 创建数据库表sql
   */
  @Override
  public void createTable(String sql) {
    jdbcTemplate.execute(sql);
  }
}
