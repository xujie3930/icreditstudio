package com.micro.cloud.modules.system.common.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import java.util.Date;

/**
 * 手机验证码 数据库实体
 *
 * <p>idx_mobile 索引：基于 {@link #mobile} 字段
 *
 * @author roy
 */
@TableName("sys_sms_code")
public class SysSmsCode extends BaseDO {

  /** 编号 */
  private String id;
  /** 手机号 */
  private String mobile;
  /** 验证码 */
  private String code;
  /** 发送场景 */
  private Integer scene;
  /** 创建 IP */
  private String createIp;
  /** 今日发送的第几条 */
  private Integer todayIndex;
  /** 是否使用 0 -> 未使用 1-> 已使用 */
  private Boolean used;
  /** 使用时间 */
  private Date usedTime;
  /** 使用 IP */
  private String usedIp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getScene() {
    return scene;
  }

  public void setScene(Integer scene) {
    this.scene = scene;
  }

  public String getCreateIp() {
    return createIp;
  }

  public void setCreateIp(String createIp) {
    this.createIp = createIp;
  }

  public Integer getTodayIndex() {
    return todayIndex;
  }

  public void setTodayIndex(Integer todayIndex) {
    this.todayIndex = todayIndex;
  }

  public Boolean getUsed() {
    return used;
  }

  public void setUsed(Boolean used) {
    this.used = used;
  }

  public Date getUsedTime() {
    return usedTime;
  }

  public void setUsedTime(Date usedTime) {
    this.usedTime = usedTime;
  }

  public String getUsedIp() {
    return usedIp;
  }

  public void setUsedIp(String usedIp) {
    this.usedIp = usedIp;
  }

  @Override
  public String toString() {
    return "SysSmsCode{"
        + "id="
        + id
        + ", mobile='"
        + mobile
        + '\''
        + ", code='"
        + code
        + '\''
        + ", scene="
        + scene
        + ", createIp='"
        + createIp
        + '\''
        + ", todayIndex="
        + todayIndex
        + ", used="
        + used
        + ", usedTime="
        + usedTime
        + ", usedIp='"
        + usedIp
        + '\''
        + '}';
  }
}
