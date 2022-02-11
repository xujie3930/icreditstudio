package com.micro.cloud.constant;

import com.micro.cloud.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * <p>system 系统，使用 1-002-000-000 段
 *
 * @author roy
 */
public interface SysErrorCodeConstants {

  ErrorCode SUCCESS = new ErrorCode(0, "成功");
  // ========== 验证码登录 模块 1002000000 ==========
  ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，账号密码不正确");
  ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，账号被禁用");
  // 登录失败的兜底，未知原因
  ErrorCode AUTH_LOGIN_FAIL_UNKNOWN = new ErrorCode(1002000002, "登录失败");
  ErrorCode AUTH_LOGIN_CAPTCHA_NOT_FOUND = new ErrorCode(1002000003, "图片验证码不存在");
  ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000004, "验证码不正确");
  ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1002000005, "未绑定账号，需要进行绑定");
  ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1002000006, "Token 已经过期");
  ErrorCode USER_HAS_NOT_LOGIN = new ErrorCode(1002000007, "用户尚未登录");

  // ========== SMS CODE 模块 1005001000 ==========
  ErrorCode USER_SMS_CODE_NOT_FOUND = new ErrorCode(1005001000, "短信验证码不存在");
  ErrorCode USER_SMS_CODE_EXPIRED = new ErrorCode(1005001001, "短信验证码已过期");
  ErrorCode USER_SMS_CODE_USED = new ErrorCode(1005001002, "短信验证码已使用");
  ErrorCode USER_SMS_CODE_NOT_CORRECT = new ErrorCode(1005001003, "短信验证码不正确");
  ErrorCode USER_SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY =
      new ErrorCode(1005001004, "超过每日短信发送数量");
  ErrorCode USER_SMS_CODE_SEND_TOO_FAST = new ErrorCode(1005001005, "短信发送过于频率");

  // ========== 组织架构模块 1002005000 ==========
  ErrorCode ORG_NAME_DUPLICATE = new ErrorCode(1002004001, "该组织机构已存在");
  ErrorCode ORG_PARENT_NOT_EXITS = new ErrorCode(1002004002, "父级部门不存在");
  ErrorCode ORG_NOT_FOUND = new ErrorCode(1002004003, "当前部门不存在");
  ErrorCode ORG_EXITS_CHILDREN = new ErrorCode(1002004004, "存在子部门，无法删除");
  ErrorCode ORG_PARENT_ERROR = new ErrorCode(1002004005, "不能设置自己为父部门");
  ErrorCode ORG_EXISTS_USER = new ErrorCode(1002004006, "部门中存在员工，无法删除");
  ErrorCode ORG_NOT_ENABLE = new ErrorCode(1002004007, "所属单位未启用");
  ErrorCode ORG_PARENT_IS_CHILD = new ErrorCode(1002004008, "不能设置自己的子部门为父部门");
  ErrorCode ORG_HAS_USER = new ErrorCode(1002004009, "当前机构/部门存在用户，无法删除");
  ErrorCode ORG_EQ_PARENT = new ErrorCode(1002004011, "无法选定本部门为子部门");
  ErrorCode ORG_REJECTION_ERROR = new ErrorCode(1002004013, "无法选定上级部门为子级部门");
  ErrorCode ORG_HAS_EXITS = new ErrorCode(1002004013, "该部门已经是你的下级部门");
  ErrorCode ORG_CREDIT_EXITS = new ErrorCode(1002004014, "组织机构信用代码重复");
  ErrorCode ORG_MOBILE_EXISTS = new ErrorCode(1002004015, "该手机号已注册");
  ErrorCode ORG_NAME_EXITS = new ErrorCode(1002004016, "组织机构名称重复");



  // ========== 角色模块 1002003000 ==========
  ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1002003000, "角色不存在");
  ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1002003001, "已经存在名为【{}】的角色");
  ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1002003002, "已经存在编码为【{}】的角色");
  ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1002003004, "不能操作类型为系统内置的角色");
  ErrorCode ROLE_IS_DISABLE = new ErrorCode(1002003005, "角色不可用");

  // ========== 用户模块 1002004000 ==========
  ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1002004000, "用户名已经存在");
  ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1002004001, "手机号已经存在");
  ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1002004002, "邮箱已经存在");
  ErrorCode USER_NOT_EXISTS = new ErrorCode(1002004003, "用户不存在");
  ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1002004004, "导入用户数据不能为空！");
  ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1002004005, "账号或密码错误");
  ErrorCode USER_DISABLE = new ErrorCode(1002004006, "用户已被停用");
  ErrorCode USER_ACCOUNT_DISABLE = new ErrorCode(1002004007, "用户账号暂不可用");
  ErrorCode USER_ACCOUNT_LOCKED = new ErrorCode(1002004008, "用户账号已被锁定");
  ErrorCode PASSWORD_NOT_CONFIRM = new ErrorCode(1002004009, "前后两次密码不一致，请重新输入");
  ErrorCode PASSWORD_EQUAL_ORIGIN = new ErrorCode(1002004010, "新密码与原密码一致，请更换后重试");

  // ========== 字典类型 1002006000 ==========
  ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1002006001, "当前字典类型不存在");
  ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1002006002, "字典类型不处于开启状态，不允许选择");
  ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1002006003, "已经存在该名字的字典类型");
  ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1002006004, "已经存在该类型的字典类型");
  ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1002006004, "无法删除，该字典类型还有字典数据");

  // ========== 字典类型 1002006000 ==========
  ErrorCode BUSINESS_ID_NOT_EXITS = new ErrorCode(1002006005, "表单数据主键id不存在，请检查");

  // ========== 流程引擎 1002007000 ==========
  ErrorCode PROCESS_APPEND_OPERATOR_HAS_TASK = new ErrorCode(1002007001, "当前流程实例该用户已存在任务，请勿重复加签: ");

  // 支付模块
  ErrorCode NO_STATUS_ARGS = new ErrorCode(1002007002, "参数不全");

  // ========== 流程引擎附件 1002008000 ==========
  ErrorCode PROCESS_ATTACHMENT_NOT_EXIST = new ErrorCode(1002008001, "流程附件不存在，请检查后重试");

  // ========== 表单引擎 1002009000 ==========
  ErrorCode FIELD_VALIDATE_ERROR = new ErrorCode(1002009001, "表单参数校验异常，请检查后提交");
}
