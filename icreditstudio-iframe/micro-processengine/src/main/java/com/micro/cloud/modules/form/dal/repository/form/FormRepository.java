package com.micro.cloud.modules.form.dal.repository.form;

/**
 * 〈表单自定义数据库操作〉
 *
 * @author roy
 * @create 2021/12/4
 * @since 1.0.0
 */
public interface FormRepository {

  /**
   * 执行创建数据库表语句
   *
   * @param sql 创建数据库表sql
   */
  void createTable(String sql);
}
