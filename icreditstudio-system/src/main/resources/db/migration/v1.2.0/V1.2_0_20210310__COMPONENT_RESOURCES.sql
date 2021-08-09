-- 接口访问权限配置 垂直越权问题
DELETE FROM  `ge_interfaces` WHERE interface_id in ('823616258510910000','823616258510910001');
INSERT INTO `ge_interfaces`(`interface_id`, `uri`, `method`, `module`, `name`, `remark`, `need_auth`, `support_auth_type`, `uri_type`) VALUES ('823616258510910000', '/uaa/auth/token', 'POST', 'IFrame', '用户登录获取token', '用户登录获取token', 0, '-', '0');
INSERT INTO `ge_interfaces`(`interface_id`, `uri`, `method`, `module`, `name`, `remark`, `need_auth`, `support_auth_type`, `uri_type`) VALUES ('823616258510910001', '/uaa/auth/token/refresh', 'POST', 'IFrame', '刷新token过期时间', '刷新token过期时间', 1, 'token', '0');
