package com.micro.cloud.constant;

/**
 * 权限相关常量定义
 *
 * @author roy
 */
public interface AuthConstant {

  /** JWT存储权限前缀 */
  String AUTHORITY_PREFIX = "ROLE_";

  /** JWT存储权限属性 */
  String AUTHORITY_CLAIM_NAME = "authorities";

  /** 后台管理接口路径匹配 */
  String ADMIN_URL_PATTERN = "api/**";

  /** 认证信息Http请求头 */
  String JWT_TOKEN_HEADER = "Authorization";

  String ACCESS_TOKEN = "access:access:";

  /** JWT令牌前缀 */
  String JWT_TOKEN_PREFIX = "Bearer ";

  /** 用户信息Http请求头 */
  String USER_ID_HEADER = "userId";

  /** JWT载体key */
  String JWT_PAYLOAD_KEY = "payload";

  /** JWT ID 唯一标识 */
  String JWT_JTI = "jti";

  /** JWT ID 唯一标识 */
  String JWT_EXP = "exp";

  /** 黑名单token前缀 */
  String TOKEN_BLACKLIST_PREFIX = "AUTH:BLACKLIST:";
}
