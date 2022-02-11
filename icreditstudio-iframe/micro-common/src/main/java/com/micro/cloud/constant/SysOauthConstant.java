package com.micro.cloud.constant;

/** Redis常量 Created by xulei on 2021/11/3 */
public interface SysOauthConstant {

  String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";
  /** token有效期 */
  int ACCESS_TOKEN_VALIDITY_SECONDS = 3600 * 2;
  /** refresh_token 有效期 */
  int REFRESH_TOKEN_VALIDITY_SECONDS = 86400;

  String JWT_JKS_PASS = "1qaz2WSX";

  String JNH_CLIENT_ID = "jnh-app";
  /** client_secret */
  String JNH_CLIENT_SECRET = "7cf25c075d5a0f8c";
}
