/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.210
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.0.210:3306
 Source Schema         : iframe

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 22/11/2021 14:00:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_application
-- ----------------------------
DROP TABLE IF EXISTS `sys_application`;
CREATE TABLE `sys_application` (
  `sys_application_id` varchar(32) NOT NULL COMMENT '系统应用id',
  `application_code` varchar(32) DEFAULT NULL COMMENT '系统应用编码',
  `application_name` varchar(64) DEFAULT NULL COMMENT '系统名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否启用: 0->否 1->是',
  PRIMARY KEY (`sys_application_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_application
-- ----------------------------
BEGIN;
INSERT INTO `sys_application` VALUES ('911846681287327334', 'OPEN_DATA_PLATFORM', '南京江北新区数据开放平台', '2021-11-21 06:45:39', NULL, '2021-11-21 06:45:43', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `sys_dict_data_id` varchar(32) NOT NULL COMMENT '字典编码数据id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` tinyint(1) DEFAULT NULL COMMENT '字典类型',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用: 0->否 1->是',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sys_dict_data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `sys_dict_type_id` varchar(32) NOT NULL COMMENT '字典类型主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用: 0->否 1->是',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sys_dict_type_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `sys_login_log_id` varchar(32) NOT NULL COMMENT '访问ID',
  `log_type` bigint(20) DEFAULT NULL COMMENT '日志类型',
  `trace_id` varchar(64) DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint(20) DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint(4) DEFAULT '0' COMMENT '用户类型',
  `username` varchar(50) DEFAULT '' COMMENT '用户账号',
  `result` tinyint(4) DEFAULT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) DEFAULT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '变更人',
  PRIMARY KEY (`sys_login_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_login_log` VALUES ('911867090065948672', 100, '', 911741368311742464, 0, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 22:34:16', '', '2021-11-20 22:34:16', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911874314465181696', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:02:58', '', '2021-11-20 23:02:58', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911874770931286016', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:04:47', '', '2021-11-20 23:04:47', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911877305347866624', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:14:51', '', '2021-11-20 23:14:51', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911877432846319616', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:15:22', '', '2021-11-20 23:15:22', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911877721464766464', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:16:31', '', '2021-11-20 23:16:31', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911880991742623744', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-20 23:29:30', '', '2021-11-20 23:29:30', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911881457041932288', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-20 23:31:21', '', '2021-11-20 23:31:21', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911882115795124224', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-20 23:33:58', '', '2021-11-20 23:33:58', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911882451490439168', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-20 23:35:18', '', '2021-11-20 23:35:18', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911884521987637248', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-20 23:43:32', '', '2021-11-20 23:43:32', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911885036679069696', 100, '', 911718581618606080, 1, 'Voluna', 1, '127.0.0.1', NULL, '', '2021-11-20 23:45:35', '', '2021-11-20 23:45:35', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911886508972048384', 100, '', 911718581618606080, 1, 'Voluna', 1, '127.0.0.1', NULL, '', '2021-11-20 23:51:26', '', '2021-11-20 23:51:26', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911904723739607040', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-21 01:03:49', '', '2021-11-21 01:03:49', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911907151402762240', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 01:13:27', '', '2021-11-21 01:13:27', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911908219402584064', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 01:17:42', '', '2021-11-21 01:17:42', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911916338270699520', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.85', NULL, '', '2021-11-21 01:49:58', '', '2021-11-21 01:49:58', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911916544743702528', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 01:50:47', '', '2021-11-21 01:50:47', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911917153584676864', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 01:53:12', '', '2021-11-21 01:53:12', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911917628753182720', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-21 01:55:05', '', '2021-11-21 01:55:05', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911921856469008384', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 02:11:53', '', '2021-11-21 02:11:53', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911922088476934144', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-21 02:12:49', '', '2021-11-21 02:12:49', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911922852888838144', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-21 02:15:51', '', '2021-11-21 02:15:51', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911923081050587136', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-21 02:16:45', '', '2021-11-21 02:16:45', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911924398083014656', 100, '', 911924331506827264, 1, 'xq123346', 1, '192.18.1.26', NULL, '', '2021-11-21 02:21:59', '', '2021-11-21 02:21:59', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('911997220293705728', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.122', NULL, '', '2021-11-21 07:11:21', '', '2021-11-21 07:11:21', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912000264410497024', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.122', NULL, '', '2021-11-21 07:23:27', '', '2021-11-21 07:23:27', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912001672320909312', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.15', NULL, '', '2021-11-21 07:29:03', '', '2021-11-21 07:29:03', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912005102267531264', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.15', NULL, '', '2021-11-21 07:42:41', '', '2021-11-21 07:42:41', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912005238997647360', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.122', NULL, '', '2021-11-21 07:43:13', '', '2021-11-21 07:43:13', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912015627261050880', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-21 08:24:30', '', '2021-11-21 08:24:30', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912016546459549696', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-21 08:28:09', '', '2021-11-21 08:28:09', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912016806275710976', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-21 08:29:11', '', '2021-11-21 08:29:11', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912017663490785280', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-21 08:32:35', '', '2021-11-21 08:32:35', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912028037413863424', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.125', NULL, '', '2021-11-21 09:13:49', '', '2021-11-21 09:13:49', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912028664202264576', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-21 09:16:18', '', '2021-11-21 09:16:18', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912281517386891264', 100, '', 912281420393611264, 1, 'zss1234', 1, '192.18.1.210', NULL, '', '2021-11-22 02:01:03', '', '2021-11-22 02:01:03', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912281728490405888', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.26', NULL, '', '2021-11-22 02:01:53', '', '2021-11-22 02:01:53', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912282401659420672', 100, '', 912281420393611264, 1, 'zss1234', 1, '192.18.1.210', NULL, '', '2021-11-22 02:04:34', '', '2021-11-22 02:04:34', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912283300922392576', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.210', NULL, '', '2021-11-22 02:08:08', '', '2021-11-22 02:08:08', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912284501273477120', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-22 02:12:55', '', '2021-11-22 02:12:55', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912298468700061696', 100, '', 912298373707464704, 1, 'SystemAdmin', 1, '192.168.0.114', NULL, '', '2021-11-22 03:08:25', '', '2021-11-22 03:08:25', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912302879463702528', 100, '', 911741368311742464, 1, 'xq12334', 1, '192.18.1.26', NULL, '', '2021-11-22 03:25:56', '', '2021-11-22 03:25:56', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912303956531609600', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.114', NULL, '', '2021-11-22 03:30:13', '', '2021-11-22 03:30:13', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912307611980791808', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.114', NULL, '', '2021-11-22 03:44:45', '', '2021-11-22 03:44:45', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912332018958729216', 100, '', 912298373707464704, 1, 'SystemAdmin', 1, '192.168.0.114', NULL, '', '2021-11-22 05:21:44', '', '2021-11-22 05:21:44', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912332578600517632', 100, '', 910626036754939904, 3, 'admin', 1, '192.168.0.114', NULL, '', '2021-11-22 05:23:57', '', '2021-11-22 05:23:57', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912340301736050688', 100, '', 910626036754939904, 3, 'admin', 1, '127.0.0.1', NULL, '', '2021-11-22 05:54:38', '', '2021-11-22 05:54:38', NULL, NULL);
INSERT INTO `sys_login_log` VALUES ('912341693754245120', 100, '', 910626036754939904, 3, 'admin', 1, '192.18.1.210', NULL, '', '2021-11-22 06:00:10', '', '2021-11-22 06:00:10', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `sys_org_id` varchar(32) NOT NULL COMMENT '组织机构id',
  `org_name` varchar(64) NOT NULL COMMENT '组织机构名称',
  `org_credit_code` varchar(64) DEFAULT NULL COMMENT '组织机构信用代码',
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否为叶子节点: 0->否 1->是',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级部门',
  `direct_id` varchar(32) DEFAULT NULL COMMENT '行政区域id',
  `type` int(11) DEFAULT NULL COMMENT '组织机构类型：1->外部法人机构  2 ->外部非法人机构 3 ->系统组织机构',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '是否启用: 0->否 1->是',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `creator_depart_id` varchar(32) DEFAULT NULL COMMENT '创建人部门id',
  `creator_depart_name` varchar(256) DEFAULT NULL COMMENT '创建人部门名称',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人部门id',
  `updater_depart_name` varchar(256) DEFAULT NULL COMMENT '更新人部门名称',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '组织机构简称',
  `application_id` varchar(32) DEFAULT NULL COMMENT '所属应用id',
  `org_code` varchar(32) DEFAULT NULL COMMENT '部门编码',
  `order_by` int(11) NOT NULL DEFAULT '0' COMMENT '排序下标',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `remark` varchar(256) DEFAULT NULL COMMENT '描述信息',
  `contact` varchar(32) DEFAULT NULL COMMENT '联系人',
  `short_name` varchar(64) DEFAULT NULL COMMENT '机构简称',
  PRIMARY KEY (`sys_org_id`) USING BTREE,
  UNIQUE KEY `index_org_id` (`sys_org_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES ('909819630892089344', '南京金宁汇科技有限公司', '2020219827656789', 1, NULL, '22222', 3, '15601691000', 1, '2021-11-20 21:25:32', NULL, NULL, NULL, NULL, '2021-11-20 13:25:32', NULL, NULL, NULL, NULL, NULL, NULL, '0212', 2, '南京市江北新区孵鹰大厦B座22-01', '金宁汇科技是一家xxxx', '王七七', '王七七');
INSERT INTO `sys_org` VALUES ('911682601415081984', '测试组织机构一', '1', 0, NULL, '区划', 3, '18855055753', 1, '2021-11-20 18:21:56', NULL, NULL, NULL, NULL, '2021-11-20 10:21:57', NULL, NULL, NULL, NULL, NULL, NULL, '1', 0, '地址', '描述', '联系人', '简称');
INSERT INTO `sys_org` VALUES ('911682794453729280', '测试组织机构一的部门1', NULL, 1, '911682601415081984', NULL, 3, '18855055753', 1, '2021-11-20 18:28:12', NULL, NULL, NULL, NULL, '2021-11-20 10:28:12', NULL, NULL, NULL, NULL, NULL, NULL, '1', 2, '部门地址', '部门描述', '部门联系人', '部门简介');
INSERT INTO `sys_org` VALUES ('911728398009630720', '江北大数据管理中心', '12320100MB05816936', 1, NULL, '浦口区', 3, '18552453044', 1, '2021-11-20 13:23:09', NULL, NULL, NULL, NULL, '2021-11-20 13:23:09', NULL, NULL, NULL, NULL, NULL, NULL, '0001', 0, '南京江北新区大数据管理中心', '南京江北新区大数据管理中心', '李辉', '大数据管理中心');
INSERT INTO `sys_org` VALUES ('912299331711664128', '机构名称', '代码', NULL, NULL, NULL, 2, NULL, NULL, '2021-11-22 11:12:01', '', NULL, '', NULL, '2021-11-22 03:12:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `sys_resource_id` varchar(32) NOT NULL COMMENT '系统资源id',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `is_leaf` tinyint(1) NOT NULL COMMENT '是否为叶子结点',
  `code` varchar(32) DEFAULT NULL COMMENT '资源识别码',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级资源id',
  `status` tinyint(1) NOT NULL COMMENT '资源状态:0->停用 1->启用',
  `url` varchar(256) NOT NULL DEFAULT '' COMMENT '资源URL',
  `type` tinyint(1) NOT NULL COMMENT '菜单类型（1目录 2菜单 3按钮）',
  `layout` int(1) DEFAULT NULL COMMENT '前端布局:\n1->top 2->bottom 3->left 4->right\n',
  `order_by` int(11) DEFAULT NULL COMMENT '排序标识',
  `remark` varchar(256) DEFAULT NULL COMMENT '资源描述',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人姓名',
  `creator_depart_id` varchar(32) DEFAULT NULL COMMENT '创建人部门id',
  `creator_depart_name` varchar(256) DEFAULT NULL COMMENT '创建人部门id',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人员id',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人员姓名',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人员部门id',
  `updater_depart_name` varchar(256) DEFAULT NULL COMMENT '更新人员部门名称',
  `need_auth` tinyint(1) DEFAULT NULL COMMENT '是否需要鉴权',
  PRIMARY KEY (`sys_resource_id`) USING BTREE,
  UNIQUE KEY `index_sys_resource_id` (`sys_resource_id`) USING BTREE COMMENT '主键唯一索引',
  UNIQUE KEY `index_code` (`code`) USING BTREE COMMENT '资源标识符'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('910458825671180288', '系统管理', 0, 'L-001', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:23:58', NULL, NULL, NULL, NULL, '2021-11-21 08:23:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910462683696857088', '组织机构管理', 0, 'L-002', '910458825671180288', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:08', NULL, NULL, NULL, NULL, '2021-11-21 08:22:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910463396514627584', '创建', 1, 'L-003', '910462683696857088', 1, '/api/sys/org/create', 1, 3, NULL, NULL, '2021-11-21 08:22:08', NULL, NULL, NULL, NULL, '2021-11-21 08:22:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910464289360314368', '编辑', 1, 'L-004', '910462683696857088', 1, '/api/sys/org/update', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910464899388276736', '删除', 1, 'L-005', '910462683696857088', 1, '/api/sys/org/delete', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910465124161028096', '调整状态', 1, 'L-006', '910462683696857088', 1, '/api/sys/org/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910465301781413888', '查看', 1, 'L-007', '910462683696857088', 1, '/api/sys/org/get/*', 1, 3, NULL, NULL, '2021-11-22 13:33:46', NULL, NULL, NULL, NULL, '2021-11-22 13:33:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910465428852047872', '树形查看', 1, 'L-008', '910462683696857088', 1, '/api/sys/org/tree-sync', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910466624694255616', '用户管理', 0, 'L-009', '910458825671180288', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910468067878764544', '个人用户', 0, 'L-010', '910466624694255616', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910468436977516544', '创建', 1, 'L-011', '910468067878764544', 1, '/api/sys/user/individual/create', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910468663608344576', '删除', 1, 'L-012', '910468067878764544', 1, '/api/sys/user/individual/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:33:56', NULL, NULL, NULL, NULL, '2021-11-22 13:33:56', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910468767874547712', '编辑', 1, 'L-013', '910468067878764544', 1, '/api/sys/user/individual/update', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910468881213030400', '批量删除', 1, 'L-014', '910468067878764544', 1, '/api/sys/user/individual/delete/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910469023659982848', '查看', 1, 'L-015', '910468067878764544', 1, '/api/sys/user/individual/info/*', 1, 3, NULL, NULL, '2021-11-22 13:34:03', NULL, NULL, NULL, NULL, '2021-11-22 13:34:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910469669666684928', '批量调整状态', 1, 'L-017', '910468067878764544', 1, '/api/sys/user/individual/update-status/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, '2021-11-21 08:22:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910470026811670528', '分页查询', 1, 'L-018', '910468067878764544', 1, '/api/sys/user/individual/page', 1, 3, NULL, NULL, '2021-11-21 08:22:11', NULL, NULL, NULL, NULL, '2021-11-21 08:22:11', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910470298816479232', '机构用户', 0, 'L-019', '910466624694255616', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910470728174796800', '分页查询', 1, 'L-020', '910470298816479232', 1, '/api/sys/user/org/page', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910470792024686592', '创建', 1, 'L-021', '910470298816479232', 1, '/api/sys/user/org/create', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910470876141453312', '查看', 1, 'L-022', '910470298816479232', 1, '/api/sys/user/org/info/*', 1, 3, NULL, NULL, '2021-11-22 13:34:07', NULL, NULL, NULL, NULL, '2021-11-22 13:34:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471098246627328', '编辑', 1, 'L-023', '910470298816479232', 1, '/api/sys/user/org/update', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471342820687872', '删除', 1, 'L-024', '910470298816479232', 1, '/api/sys/user/org/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:34:11', NULL, NULL, NULL, NULL, '2021-11-22 13:34:11', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471399162773504', '批量删除', 1, 'L-025', '910470298816479232', 1, '/api/sys/user/org/delete/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471598677426176', '调整状态', 1, 'L-026', '910470298816479232', 1, '/api/sys/user/org/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471671528292352', '批量调整状态', 1, 'L-027', '910470298816479232', 1, '/api/sys/user/org/update-status/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910471840462274560', '系统用户', 0, 'L-028', '910466624694255616', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910472223045713920', '分页查询', 1, 'L-029', '910471840462274560', 1, '/api/sys/user/internal/page', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910472333729202176', '创建', 1, 'L-030', '910471840462274560', 1, '/api/sys/user/internal/create', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910473050309263360', '调整状态', 1, 'L-031', '910468067878764544', 1, '/api/sys/user/individual/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, '2021-11-21 08:22:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910473421639385088', '查看', 1, 'L-032', '910471840462274560', 1, '/api/sys/user/internal/info/*', 1, 3, NULL, NULL, '2021-11-22 13:34:18', NULL, NULL, NULL, NULL, '2021-11-22 13:34:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910473676262998016', '编辑', 1, 'L-033', '910471840462274560', 1, '/api/sys/user/internal/update', 1, 3, NULL, NULL, '2021-11-21 08:22:13', NULL, NULL, NULL, NULL, '2021-11-21 08:22:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910634765835567104', '申请', 1, 'J-034', '911896056462049280', 1, '/*/backend/apply/source/directory/applySource', 1, NULL, NULL, NULL, '2021-11-22 13:55:52', NULL, NULL, NULL, NULL, '2021-11-22 13:55:52', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910634890096017408', '江北链系统', 1, 'J-035', '911896056462049280', 1, '/*/backend/apply/source/directory/detail', 1, NULL, NULL, NULL, '2021-11-22 13:56:02', NULL, NULL, NULL, NULL, '2021-11-22 13:56:02', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910634964389724160', '江北链系统', 1, 'J-036', '911896056462049280', 1, '/*/backend/apply/source/directory/list', 1, NULL, NULL, NULL, '2021-11-22 13:56:06', NULL, NULL, NULL, NULL, '2021-11-22 13:56:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635019125391360', '江北链系统', 1, 'J-037', '911896056462049280', 1, '/*/backend/source/directory/favorite', 1, NULL, NULL, NULL, '2021-11-22 13:56:12', NULL, NULL, NULL, NULL, '2021-11-22 13:56:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635079074578432', '江北链系统', 1, 'J-038', '911896056462049280', 1, '/*/backend/source/directory/favorite/checkFavorite', 1, NULL, NULL, NULL, '2021-11-22 13:56:15', NULL, NULL, NULL, NULL, '2021-11-22 13:56:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635134728798208', '江北链系统', 1, 'J-039', '911896056462049280', 1, '/*/backend/source/directory/favorite/listFavorite', 1, NULL, NULL, NULL, '2021-11-22 13:56:19', NULL, NULL, NULL, NULL, '2021-11-22 13:56:19', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635186905939968', '江北链系统', 1, 'J-040', '911896056462049280', 1, '/*/backend/source/directory/correct/correctSubmit', 1, NULL, NULL, NULL, '2021-11-22 13:56:23', NULL, NULL, NULL, NULL, '2021-11-22 13:56:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635239120830464', '江北链系统', 1, 'J-041', '911896056462049280', 1, '/*/backend/source/directory/correct/detailCorrect', 1, NULL, NULL, NULL, '2021-11-22 13:56:34', NULL, NULL, NULL, NULL, '2021-11-22 13:56:34', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635303901855744', '江北链系统', 1, 'J-042', '911896056462049280', 1, '/*/backend/source/directory/correct/listCorrect', 1, NULL, NULL, NULL, '2021-11-22 13:56:38', NULL, NULL, NULL, NULL, '2021-11-22 13:56:38', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635384881283072', '江北链系统', 1, 'J-043', '911896056462049280', 1, '/*/backend/source/directory/interface/sourceDirectoryInterfaceInfo', 1, NULL, NULL, NULL, '2021-11-22 13:56:42', NULL, NULL, NULL, NULL, '2021-11-22 13:56:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635437737902080', '江北链系统', 1, 'J-044', '911896056462049280', 1, '/*/backend/source/directory/file', 1, NULL, NULL, NULL, '2021-11-22 13:56:46', NULL, NULL, NULL, NULL, '2021-11-22 13:56:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635485179674624', '江北链系统', 1, 'J-045', '911896056462049280', 1, '/*/backend/source/directory/file/deleteSourceFile', 1, NULL, NULL, NULL, '2021-11-22 13:56:50', NULL, NULL, NULL, NULL, '2021-11-22 13:56:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635543463723008', '江北链系统', 1, 'J-046', '911896056462049280', 1, '/*/backend/source/directory/file/download', 1, NULL, NULL, NULL, '2021-11-22 13:56:55', NULL, NULL, NULL, NULL, '2021-11-22 13:56:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635599415738368', '江北链系统', 1, 'J-047', '911896056462049280', 1, '/*/backend/source/directory/file/manageQueryDirectoryFile', 1, NULL, NULL, NULL, '2021-11-22 13:56:58', NULL, NULL, NULL, NULL, '2021-11-22 13:56:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635658588979200', '江北链系统', 1, 'J-048', '911896056462049280', 1, '/*/backend/source/directory/file/sourceFileDataPreview', 1, NULL, NULL, NULL, '2021-11-22 13:57:03', NULL, NULL, NULL, NULL, '2021-11-22 13:57:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635708312453120', '江北链系统', 1, 'J-049', '911896056462049280', 1, '/*/backend/source/directory/file/uploadSourceFile', 1, NULL, NULL, NULL, '2021-11-22 13:57:06', NULL, NULL, NULL, NULL, '2021-11-22 13:57:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635761492033536', '江北链系统', 1, 'J-050', '911896056462049280', 1, '/*/backend/source/directory/add', 1, NULL, NULL, NULL, '2021-11-22 13:57:10', NULL, NULL, NULL, NULL, '2021-11-22 13:57:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635818270326784', '江北链系统', 1, 'J-051', '911896056462049280', 1, '/*/backend/source/directory/deleteSourceDirectory', 1, NULL, NULL, NULL, '2021-11-22 13:57:18', NULL, NULL, NULL, NULL, '2021-11-22 13:57:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635872024526848', '江北链系统', 1, 'J-052', '911896056462049280', 1, '/*/backend/source/directory/downloadSourceDirectory', 1, NULL, NULL, NULL, '2021-11-22 13:57:22', NULL, NULL, NULL, NULL, '2021-11-22 13:57:22', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635947958206464', '江北链系统', 1, 'J-053', '911896056462049280', 1, '/*/backend/source/directory/edit', 1, NULL, NULL, NULL, '2021-11-22 13:57:26', NULL, NULL, NULL, NULL, '2021-11-22 13:57:26', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910636014916075520', '江北链系统', 1, 'J-054', '911896056462049280', 1, '/*/backend/source/directory/managerSourceList', 1, NULL, NULL, NULL, '2021-11-22 13:57:29', NULL, NULL, NULL, NULL, '2021-11-22 13:57:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911220088584011776', '删除', 1, 'L-034', '910471840462274560', 1, '/api/sys/user/internal/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:34:36', NULL, NULL, NULL, NULL, '2021-11-22 13:34:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911220506210861056', '批量删除', 1, 'L-035', '910471840462274560', 1, '/api/sys/user/internal/delete/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911264547786981376', '调整状态', 1, 'L-036', '910471840462274560', 1, '/api/sys/user/internal/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911265318825885696', '批量调整状态', 1, 'L-037', '910471840462274560', 1, '/api/sys/user/internal/update-status/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911266821833752576', '角色管理', 0, 'L-038', '910458825671180288', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911266991778562048', '创建', 1, 'L-039', '911266821833752576', 1, '/api/sys/role/create', 1, 3, NULL, NULL, '2021-11-22 13:37:43', NULL, NULL, NULL, NULL, '2021-11-22 13:37:43', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911272927503056896', '删除', 1, 'L-040', '911266821833752576', 1, '/api/sys/role/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:34:40', NULL, NULL, NULL, NULL, '2021-11-22 13:34:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273050970783744', '查看', 1, 'L-041', '911266821833752576', 1, '/api/sys/role/get/*', 1, 3, NULL, NULL, '2021-11-22 13:34:44', NULL, NULL, NULL, NULL, '2021-11-22 13:34:44', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273192671150080', '精简信息列表', 1, 'L-042', '911266821833752576', 1, '/api/sys/role/list-all-simple', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273277576445952', '分页查询', 1, 'L-043', '911266821833752576', 1, '/api/sys/role/page', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273420799344640', '更新', 1, 'L-044', '911266821833752576', 1, '/api/sys/role/update', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273539665920000', '调整状态', 1, 'L-045', '911266821833752576', 1, '/api/sys/role/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273753030164480', '相关用户查询', 1, 'L-046', '911266821833752576', 1, '/api/sys/role/user/page', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273861541003264', '添加用户', 1, 'L-047', '911266821833752576', 1, '/api/sys/role/allocate/user', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273965257752576', '授权', 1, 'L-048', '911266821833752576', 1, '/api/sys/role/auth', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911274190005338112', '移除人员', 1, 'L-049', '911266821833752576', 1, '/api/sys/role/remove/user', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911279075891347456', '开放目录', 0, 'L-050', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:24:22', NULL, NULL, NULL, NULL, '2021-11-21 08:24:22', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911279148515721216', '申请管理', 0, 'L-051', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:24:34', NULL, NULL, NULL, NULL, '2021-11-21 08:24:34', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911742977389363200', '账号信息', 0, 'L-052', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:45:18', NULL, NULL, NULL, NULL, '2021-11-21 08:45:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911743107379232768', '个人资料', 1, 'L-053', '911742977389363200', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911743189088468992', '密码修改', 1, 'L-054', '911742977389363200', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857187565862912', '数据申请', 1, 'L-055', '911899907676176384', 1, '/backend/apply/source/directory/list', 1, 3, NULL, NULL, '2021-11-21 08:45:23', NULL, NULL, NULL, NULL, '2021-11-21 08:45:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857289659416576', '互动管理', 0, 'L-056', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:45:35', NULL, NULL, NULL, NULL, '2021-11-21 08:45:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857398677766144', '纠错管理', 1, 'L-057', '911857289659416576', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857452847202304', '建议跟踪', 1, 'L-058', '911857289659416576', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911896056462049280', '子应用', 0, 'J-001', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:31:49', NULL, NULL, NULL, NULL, '2021-11-21 08:31:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911899907676176384', '个人中心', 0, 'L-059', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:47:40', NULL, NULL, NULL, NULL, '2021-11-21 08:47:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911900316755034112', '数据申请', 1, 'L-060', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:51:03', NULL, NULL, NULL, NULL, '2021-11-21 08:51:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911901584017850368', '收藏管理', 0, 'L-061', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:52:46', NULL, NULL, NULL, NULL, '2021-11-21 08:52:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902013506191360', '收藏', 1, 'L-062', '911901584017850368', 1, '/backend/source/directory/favorite', 1, 3, NULL, NULL, '2021-11-21 00:53:02', NULL, NULL, NULL, NULL, '2021-11-21 00:53:02', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902247925841920', '检查是否收藏', 1, 'L-063', '911901584017850368', 1, '/backend/source/directory/favorite/checkFavorite', 1, 3, NULL, NULL, '2021-11-21 00:53:58', NULL, NULL, NULL, NULL, '2021-11-21 00:53:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902588771762176', '收藏列表', 1, 'L-064', '911901584017850368', 1, '/backend/source/directory/favorite/listFavorite', 1, 3, NULL, NULL, '2021-11-21 00:55:19', NULL, NULL, NULL, NULL, '2021-11-21 00:55:19', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902914602074112', '纠错', 1, 'L-065', '911857398677766144', 1, '/backend/source/directory/correct/correctSubmit', 1, 3, NULL, NULL, '2021-11-21 00:56:37', NULL, NULL, NULL, NULL, '2021-11-21 00:56:37', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903017899393024', '详情', 1, 'L-066', '911857398677766144', 1, '/backend/source/directory/correct/detailCorrect', 1, 3, NULL, NULL, '2021-11-21 00:57:02', NULL, NULL, NULL, NULL, '2021-11-21 00:57:02', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903187856785408', '纠错列表', 1, 'L-067', '911857398677766144', 1, '/backend/source/directory/correct/listCorrect', 1, 3, NULL, NULL, '2021-11-21 00:57:42', NULL, NULL, NULL, NULL, '2021-11-21 00:57:42', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903886820769792', '资源管理', 0, 'L-068', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 01:00:29', NULL, NULL, NULL, NULL, '2021-11-21 01:00:29', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `sys_role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(256) DEFAULT NULL COMMENT '角色信息描述',
  `count` int(11) DEFAULT NULL COMMENT '角色数目',
  `status` tinyint(1) NOT NULL COMMENT '是否启用: 0->停用 1-> 启用',
  `type` int(1) DEFAULT NULL COMMENT '角色类型: 1->超管 2->数据管理员...',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `creator_depart_id` varchar(32) DEFAULT NULL COMMENT '创建人部门id',
  `creator_depart_name` varchar(256) DEFAULT NULL COMMENT '创建人部门名称',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人部门id',
  `updater_depart_name` varchar(256) DEFAULT NULL COMMENT '更新人部门名称',
  `parent_role_id` varchar(32) DEFAULT NULL COMMENT '上级角色id',
  `category` int(1) DEFAULT NULL COMMENT '角色类别',
  PRIMARY KEY (`sys_role_id`) USING BTREE,
  UNIQUE KEY `index_sys_role_id` (`sys_role_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('910473676262998016', 'EX_USER_ROLE', 'ex-001', NULL, NULL, 1, 1, '2021-11-17 10:53:05', NULL, NULL, NULL, NULL, '2021-11-17 10:53:09', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('910473676262998019', 'ADMIN', 'in-001', NULL, NULL, 1, NULL, '2021-11-17 20:26:06', NULL, NULL, NULL, NULL, '2021-11-17 20:26:10', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('910947921455742976', '角色名称1', NULL, '123', NULL, 1, 2, '2021-11-18 09:41:49', '', NULL, NULL, NULL, '2021-11-19 05:13:09', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('910985087594528768', '角色名称2', NULL, '111', NULL, 1, 2, '2021-11-18 12:09:30', '', NULL, NULL, NULL, '2021-11-18 12:09:30', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911204344873877504', '测试角色001', NULL, '123', NULL, 1, 2, '2021-11-19 02:40:45', '', NULL, NULL, NULL, '2021-11-19 02:40:45', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911205320863252480', '测试角色002', NULL, '123', NULL, 1, 2, '2021-11-19 02:44:38', '', NULL, NULL, NULL, '2021-11-19 02:49:17', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911250186393616384', '角色名称12345', NULL, '角色描述123456', NULL, 1, 2, '2021-11-19 05:42:55', '', NULL, NULL, NULL, '2021-11-19 05:44:15', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911293891448143872', '角色名称123', NULL, '角色描述', NULL, 0, 2, '2021-11-19 08:36:35', '', NULL, NULL, NULL, '2021-11-19 08:43:39', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911620513858584576', '测试角色名称字段', NULL, '描述', NULL, 1, 2, '2021-11-20 06:14:28', '', NULL, NULL, NULL, '2021-11-20 06:14:28', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('911648948626653184', '角色名称1234566', NULL, 'miaoshu编辑184', NULL, 1, 2, '2021-11-20 08:07:27', '', NULL, NULL, NULL, '2021-11-22 06:00:34', '', NULL, NULL, NULL, NULL, 2);
INSERT INTO `sys_role` VALUES ('911649014343008256', '角色名称123454', NULL, 'miaoshu4', NULL, 1, 2, '2021-11-20 08:07:43', '', NULL, NULL, NULL, '2021-11-20 08:07:43', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('912283935352815616', '新增角色123', NULL, '角色描述', NULL, 1, 1, '2021-11-22 02:10:40', '', NULL, NULL, NULL, '2021-11-22 02:10:40', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_role` VALUES ('912298425515507712', '新增角色1122', NULL, '编辑', NULL, 1, 1, '2021-11-22 03:08:14', '', NULL, NULL, NULL, '2021-11-22 03:24:25', '', NULL, NULL, NULL, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_resource_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource_ref`;
CREATE TABLE `sys_role_resource_ref` (
  `sys_role_resource_ref_id` varchar(32) NOT NULL COMMENT '角色权限关系id',
  `sys_role_id` varchar(32) NOT NULL COMMENT '角色id',
  `sys_resource_id` varchar(32) NOT NULL COMMENT '资源id',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sys_role_resource_ref_id`) USING BTREE,
  UNIQUE KEY `index_role_auth_ref_id` (`sys_role_resource_ref_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource_ref
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_resource_ref` VALUES ('911736272786030592', '910947921455742976', '910470792024686592', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272786030593', '910947921455742976', '910465428852047872', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224896', '910947921455742976', '910470876141453312', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224897', '910947921455742976', '910469669666684928', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224898', '910947921455742976', '910470298816479232', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224899', '910947921455742976', '910465301781413888', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224900', '910947921455742976', '910469023659982848', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224901', '910947921455742976', '910466624694255616', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224902', '910947921455742976', '910464289360314368', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224903', '910947921455742976', '910462683696857088', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224904', '910947921455742976', '910463396514627584', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224905', '910947921455742976', '910468436977516544', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224906', '910947921455742976', '910468767874547712', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224907', '910947921455742976', '910470728174796800', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224908', '910947921455742976', '910468067878764544', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224909', '910947921455742976', '910468881213030400', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224910', '910947921455742976', '910458825671180288', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224911', '910947921455742976', '910470026811670528', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224912', '910947921455742976', '910464899388276736', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224913', '910947921455742976', '910465124161028096', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224914', '910947921455742976', '910468663608344576', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911736272790224915', '910947921455742976', '910473050309263360', NULL, '2021-11-20 13:54:27', NULL, '2021-11-20 13:54:27');
INSERT INTO `sys_role_resource_ref` VALUES ('911785222247284749', '911293891448143872', '910458825671180288', NULL, '2021-11-20 17:08:57', NULL, '2021-11-20 17:08:57');
INSERT INTO `sys_role_resource_ref` VALUES ('911785222247284756', '911293891448143872', '911279148515721216', NULL, '2021-11-20 17:08:57', NULL, '2021-11-20 17:08:57');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052416', '911293891448143872', '910471098246627328', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052417', '911293891448143872', '910471598677426176', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052418', '911293891448143872', '910470792024686592', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052419', '911293891448143872', '910470728174796800', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052420', '911293891448143872', '910471671528292352', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052422', '911293891448143872', '911279075891347456', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052423', '911293891448143872', '910470298816479232', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052424', '911293891448143872', '910471399162773504', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850862245052425', '911293891448143872', '910471342820687872', NULL, '2021-11-20 21:29:47', NULL, '2021-11-20 21:29:47');
INSERT INTO `sys_role_resource_ref` VALUES ('911850951952826368', '911293891448143872', '910473676262998016', NULL, '2021-11-20 21:30:08', NULL, '2021-11-20 21:30:08');
INSERT INTO `sys_role_resource_ref` VALUES ('911850951957020672', '911293891448143872', '910471840462274560', NULL, '2021-11-20 21:30:08', NULL, '2021-11-20 21:30:08');
INSERT INTO `sys_role_resource_ref` VALUES ('911850951957020673', '911293891448143872', '911220506210861056', NULL, '2021-11-20 21:30:08', NULL, '2021-11-20 21:30:08');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239744', '911205320863252480', '910471098246627328', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239745', '911205320863252480', '910470792024686592', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239746', '911205320863252480', '910469669666684928', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239747', '911205320863252480', '910470298816479232', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239748', '911205320863252480', '911220088584011776', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239749', '911205320863252480', '910468436977516544', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239750', '911205320863252480', '910468767874547712', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239751', '911205320863252480', '910472223045713920', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239752', '911205320863252480', '911265318825885696', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239753', '911205320863252480', '910471598677426176', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239754', '911205320863252480', '910470728174796800', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239755', '911205320863252480', '910468067878764544', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239756', '911205320863252480', '910468881213030400', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239757', '911205320863252480', '910471671528292352', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239758', '911205320863252480', '910470026811670528', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239759', '911205320863252480', '911279075891347456', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239760', '911205320863252480', '910471342820687872', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239761', '911205320863252480', '910472333729202176', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239762', '911205320863252480', '910470876141453312', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239763', '911205320863252480', '910469023659982848', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239764', '911205320863252480', '910471399162773504', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239765', '911205320863252480', '910466624694255616', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239766', '911205320863252480', '911264547786981376', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239767', '911205320863252480', '910473676262998016', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239768', '911205320863252480', '910458825671180288', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239769', '911205320863252480', '910471840462274560', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239770', '911205320863252480', '911220506210861056', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239771', '911205320863252480', '910473421639385088', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239772', '911205320863252480', '911279148515721216', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239773', '911205320863252480', '910468663608344576', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852395502239774', '911205320863252480', '910473050309263360', NULL, '2021-11-20 21:35:52', NULL, '2021-11-20 21:35:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504896', '911293891448143872', '910472333729202176', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504897', '911293891448143872', '910470876141453312', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504898', '911293891448143872', '910469669666684928', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504899', '911293891448143872', '910469023659982848', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504900', '911293891448143872', '911220088584011776', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504901', '911293891448143872', '910466624694255616', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504902', '911293891448143872', '911264547786981376', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504903', '911293891448143872', '910468436977516544', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504904', '911293891448143872', '910468767874547712', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504905', '911293891448143872', '910472223045713920', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504906', '911293891448143872', '911265318825885696', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504907', '911293891448143872', '910468067878764544', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504908', '911293891448143872', '910468881213030400', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504909', '911293891448143872', '910470026811670528', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504910', '911293891448143872', '910473421639385088', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504911', '911293891448143872', '910468663608344576', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911852499575504912', '911293891448143872', '910473050309263360', NULL, '2021-11-20 21:36:17', NULL, '2021-11-20 21:36:17');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604736', '911250186393616384', '910464289360314368', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604737', '911250186393616384', '910462683696857088', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604738', '911250186393616384', '910463396514627584', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604739', '911250186393616384', '910465428852047872', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604740', '911250186393616384', '910458825671180288', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604741', '911250186393616384', '911279075891347456', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604742', '911250186393616384', '910464899388276736', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604743', '911250186393616384', '910465124161028096', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604744', '911250186393616384', '910465301781413888', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911853649695604745', '911250186393616384', '911279148515721216', NULL, '2021-11-20 21:40:52', NULL, '2021-11-20 21:40:52');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174784', '911293891448143872', '911273050970783744', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174785', '911293891448143872', '911273539665920000', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174786', '911293891448143872', '911273965257752576', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174787', '911293891448143872', '911273277576445952', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174788', '911293891448143872', '911273420799344640', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174789', '911293891448143872', '911274190005338112', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174790', '911293891448143872', '911273861541003264', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174791', '911293891448143872', '911273192671150080', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174792', '911293891448143872', '911266821833752576', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911856555245174793', '911293891448143872', '911273753030164480', NULL, '2021-11-20 21:52:24', NULL, '2021-11-20 21:52:24');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121728', '911620513858584576', '910471098246627328', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121729', '911620513858584576', '910470792024686592', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121730', '911620513858584576', '910465428852047872', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121731', '911620513858584576', '910470876141453312', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121732', '911620513858584576', '910470298816479232', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559042121733', '911620513858584576', '910465301781413888', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316032', '911620513858584576', '910471399162773504', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316033', '911620513858584576', '910466624694255616', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316034', '911620513858584576', '910464289360314368', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316035', '911620513858584576', '910462683696857088', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316036', '911620513858584576', '910463396514627584', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316037', '911620513858584576', '910471598677426176', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316038', '911620513858584576', '910470728174796800', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316039', '911620513858584576', '910471671528292352', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316040', '911620513858584576', '910458825671180288', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316041', '911620513858584576', '911279075891347456', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316042', '911620513858584576', '910464899388276736', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316043', '911620513858584576', '910465124161028096', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316044', '911620513858584576', '911279148515721216', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911859559046316045', '911620513858584576', '910471342820687872', NULL, '2021-11-20 22:04:20', NULL, '2021-11-20 22:04:20');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000320', '910473676262998019', '910471098246627328', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000321', '910473676262998019', '911272927503056896', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000322', '910473676262998019', '910469669666684928', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000323', '910473676262998019', '910468436977516544', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000324', '910473676262998019', '910468767874547712', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000325', '910473676262998019', '910471598677426176', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000326', '910473676262998019', '910470728174796800', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000327', '910473676262998019', '910471671528292352', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000328', '910473676262998019', '911279075891347456', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000329', '910473676262998019', '911274190005338112', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000330', '910473676262998019', '910465124161028096', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000331', '910473676262998019', '910471342820687872', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000332', '910473676262998019', '910465428852047872', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000333', '910473676262998019', '911266991778562048', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000334', '910473676262998019', '910471399162773504', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000335', '910473676262998019', '910466624694255616', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000336', '910473676262998019', '911266821833752576', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000337', '910473676262998019', '911264547786981376', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000338', '910473676262998019', '910473676262998016', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000339', '910473676262998019', '910458825671180288', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000340', '910473676262998019', '911903886820769792', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000341', '910473676262998019', '911220506210861056', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000342', '910473676262998019', '910468663608344576', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000343', '910473676262998019', '911273050970783744', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000344', '910473676262998019', '911273539665920000', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000345', '910473676262998019', '910470792024686592', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000346', '910473676262998019', '910470298816479232', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000347', '910473676262998019', '910465301781413888', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000348', '910473676262998019', '911220088584011776', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000349', '910473676262998019', '910464289360314368', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000350', '910473676262998019', '910462683696857088', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000351', '910473676262998019', '910463396514627584', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000352', '910473676262998019', '910472223045713920', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000353', '910473676262998019', '911265318825885696', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000354', '910473676262998019', '910468067878764544', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000355', '910473676262998019', '910468881213030400', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000356', '910473676262998019', '910470026811670528', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000357', '910473676262998019', '911273861541003264', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000359', '910473676262998019', '911896056462049280', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000360', '910473676262998019', '910472333729202176', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000361', '910473676262998019', '911273965257752576', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000362', '910473676262998019', '910470876141453312', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000363', '910473676262998019', '910469023659982848', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000364', '910473676262998019', '911273277576445952', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000365', '910473676262998019', '911273420799344640', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000366', '910473676262998019', '910464899388276736', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000367', '910473676262998019', '910471840462274560', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000368', '910473676262998019', '910473421639385088', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000369', '910473676262998019', '911273192671150080', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000370', '910473676262998019', '911279148515721216', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000371', '910473676262998019', '910473050309263360', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911912147427000372', '910473676262998019', '911273753030164480', NULL, '2021-11-21 01:33:18', NULL, '2021-11-21 01:33:18');
INSERT INTO `sys_role_resource_ref` VALUES ('911917502198448139', '910473676262998016', '911899907676176384', NULL, '2021-11-21 01:54:35', NULL, '2021-11-21 01:54:35');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378560', '910473676262998016', '911272927503056896', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378561', '910473676262998016', '910471098246627328', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378562', '910473676262998016', '911273050970783744', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378563', '910473676262998016', '911273539665920000', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378564', '910473676262998016', '910470792024686592', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378565', '910473676262998016', '910469669666684928', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378566', '910473676262998016', '910470298816479232', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378567', '910473676262998016', '910465301781413888', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378568', '910473676262998016', '911220088584011776', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378569', '910473676262998016', '910464289360314368', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378570', '910473676262998016', '910462683696857088', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378571', '910473676262998016', '910468436977516544', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378572', '910473676262998016', '910468767874547712', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378573', '910473676262998016', '910463396514627584', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378574', '910473676262998016', '910472223045713920', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378575', '910473676262998016', '911265318825885696', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378576', '910473676262998016', '910471598677426176', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378577', '910473676262998016', '910470728174796800', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378578', '910473676262998016', '910468067878764544', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378579', '910473676262998016', '910471671528292352', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378580', '910473676262998016', '910468881213030400', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378581', '910473676262998016', '910470026811670528', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378582', '910473676262998016', '911274190005338112', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378583', '910473676262998016', '910465124161028096', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378584', '910473676262998016', '911273861541003264', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378585', '910473676262998016', '910471342820687872', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378586', '910473676262998016', '910472333729202176', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378587', '910473676262998016', '911273965257752576', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378588', '910473676262998016', '910465428852047872', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378589', '910473676262998016', '911266991778562048', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378590', '910473676262998016', '910470876141453312', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378591', '910473676262998016', '910469023659982848', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378592', '910473676262998016', '910471399162773504', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378593', '910473676262998016', '911266821833752576', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378594', '910473676262998016', '910466624694255616', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378595', '910473676262998016', '911264547786981376', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378596', '910473676262998016', '910473676262998016', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378597', '910473676262998016', '911273277576445952', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378598', '910473676262998016', '911273420799344640', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378599', '910473676262998016', '910458825671180288', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378600', '910473676262998016', '911220506210861056', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378601', '910473676262998016', '910464899388276736', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378602', '910473676262998016', '910471840462274560', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378603', '910473676262998016', '910473421639385088', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378604', '910473676262998016', '911273192671150080', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378605', '910473676262998016', '910468663608344576', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378606', '910473676262998016', '911273753030164480', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
INSERT INTO `sys_role_resource_ref` VALUES ('912282001405378607', '910473676262998016', '910473050309263360', NULL, '2021-11-22 02:02:59', NULL, '2021-11-22 02:02:59');
COMMIT;

-- ----------------------------
-- Table structure for sys_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_code`;
CREATE TABLE `sys_sms_code` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `code` varchar(6) DEFAULT NULL COMMENT '验证码',
  `create_ip` varchar(15) DEFAULT NULL COMMENT '创建 IP',
  `scene` tinyint(4) DEFAULT NULL COMMENT '发送场景',
  `today_index` tinyint(4) DEFAULT NULL COMMENT '今日发送的第几条',
  `used` tinyint(4) DEFAULT NULL COMMENT '是否使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `used_ip` varchar(255) DEFAULT NULL COMMENT '使用 IP',
  `creator_id` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_mobile` (`mobile`) USING BTREE COMMENT '手机号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='手机验证码';

-- ----------------------------
-- Records of sys_sms_code
-- ----------------------------
BEGIN;
INSERT INTO `sys_sms_code` VALUES ('907712684881870848', '13141520468', '9999', '127.0.0.1', 1, 1, 0, NULL, NULL, NULL, '2021-11-09 11:26:09', NULL, '2021-11-09 11:26:09');
INSERT INTO `sys_sms_code` VALUES ('909010319353839616', '15601691234', '9999', '127.0.0.1', 1, 1, 0, NULL, NULL, NULL, '2021-11-13 01:22:29', NULL, '2021-11-13 01:22:29');
INSERT INTO `sys_sms_code` VALUES ('909021535107284992', '15601691300', '9999', '127.0.0.1', 1, 1, 0, NULL, NULL, NULL, '2021-11-13 02:07:03', NULL, '2021-11-13 02:07:03');
INSERT INTO `sys_sms_code` VALUES ('909108659936559104', '15643456789', '9999', '192.168.88.103', 1, 1, 0, NULL, NULL, NULL, '2021-11-13 07:53:15', NULL, '2021-11-13 07:53:15');
INSERT INTO `sys_sms_code` VALUES ('909108760587272192', '15643456', '9999', '192.168.88.103', 1, 1, 0, NULL, NULL, NULL, '2021-11-13 07:53:39', NULL, '2021-11-13 07:53:39');
INSERT INTO `sys_sms_code` VALUES ('909114845868064768', '', '9999', '192.168.88.103', 1, 1, 0, NULL, NULL, NULL, '2021-11-13 08:17:50', NULL, '2021-11-13 08:17:50');
INSERT INTO `sys_sms_code` VALUES ('909115286655860736', '', '9999', '192.168.88.103', 1, 2, 0, NULL, NULL, NULL, '2021-11-13 08:19:35', NULL, '2021-11-13 08:19:35');
INSERT INTO `sys_sms_code` VALUES ('909115879331987456', '', '9999', '192.168.88.103', 1, 3, 0, NULL, NULL, NULL, '2021-11-13 08:21:56', NULL, '2021-11-13 08:21:56');
INSERT INTO `sys_sms_code` VALUES ('909116174199947264', '', '9999', '192.168.88.103', 1, 4, 0, NULL, NULL, NULL, '2021-11-13 08:23:06', NULL, '2021-11-13 08:23:06');
INSERT INTO `sys_sms_code` VALUES ('909116461149061120', '', '9999', '192.168.88.103', 1, 5, 0, NULL, NULL, NULL, '2021-11-13 08:24:15', NULL, '2021-11-13 08:24:15');
INSERT INTO `sys_sms_code` VALUES ('909116774954303488', '', '9999', '192.168.88.103', 1, 6, 0, NULL, NULL, NULL, '2021-11-13 08:25:30', NULL, '2021-11-13 08:25:30');
INSERT INTO `sys_sms_code` VALUES ('909117052785000448', '', '9999', '192.168.88.103', 1, 7, 0, NULL, NULL, NULL, '2021-11-13 08:26:36', NULL, '2021-11-13 08:26:36');
INSERT INTO `sys_sms_code` VALUES ('909760049205739520', '', '9999', '192.168.88.133', 1, 8, 0, NULL, NULL, NULL, '2021-11-15 03:01:38', NULL, '2021-11-15 03:01:38');
INSERT INTO `sys_sms_code` VALUES ('909795878351405056', '16765456789', '9999', '192.168.88.133', 1, 1, 1, '2021-11-15 05:24:10', '192.168.88.133', NULL, '2021-11-15 05:24:01', NULL, '2021-11-15 05:24:10');
INSERT INTO `sys_sms_code` VALUES ('909818399587041280', '', '9999', '192.18.1.26', 1, 9, 0, NULL, NULL, NULL, '2021-11-15 06:53:30', NULL, '2021-11-15 06:53:30');
INSERT INTO `sys_sms_code` VALUES ('909818757654773760', '', '9999', '192.18.1.26', 1, 10, 0, NULL, NULL, NULL, '2021-11-15 06:54:55', NULL, '2021-11-15 06:54:55');
INSERT INTO `sys_sms_code` VALUES ('909818811203452928', '17890875456', '9999', '192.18.1.26', 1, 1, 0, NULL, NULL, NULL, '2021-11-15 06:55:08', NULL, '2021-11-15 06:55:08');
INSERT INTO `sys_sms_code` VALUES ('910247458645213184', '16765456789', '9999', '192.18.1.26', 1, 2, 0, NULL, NULL, NULL, '2021-11-16 11:18:26', NULL, '2021-11-16 11:18:26');
INSERT INTO `sys_sms_code` VALUES ('910248399318548480', '16765456789', '9999', '192.18.1.26', 1, 3, 0, NULL, NULL, NULL, '2021-11-16 11:22:10', NULL, '2021-11-16 11:22:10');
INSERT INTO `sys_sms_code` VALUES ('910248679422558208', '16765456789', '9999', '192.18.1.26', 1, 4, 0, NULL, NULL, NULL, '2021-11-16 11:23:17', NULL, '2021-11-16 11:23:17');
INSERT INTO `sys_sms_code` VALUES ('910249432753111040', '16765456789', '9999', '192.18.1.26', 1, 5, 0, NULL, NULL, NULL, '2021-11-16 11:26:16', NULL, '2021-11-16 11:26:16');
INSERT INTO `sys_sms_code` VALUES ('910250564992892928', '16765456789', '9999', '192.18.1.26', 1, 6, 0, NULL, NULL, NULL, '2021-11-16 11:30:46', NULL, '2021-11-16 11:30:46');
INSERT INTO `sys_sms_code` VALUES ('911183526974455808', '13192727301', '9999', '192.168.0.122', 1, 1, 0, NULL, NULL, NULL, '2021-11-19 01:18:02', NULL, '2021-11-19 01:18:02');
INSERT INTO `sys_sms_code` VALUES ('911183795875479552', '13192727301', '9999', '192.168.0.122', 1, 2, 0, NULL, NULL, NULL, '2021-11-19 01:19:06', NULL, '2021-11-19 01:19:06');
INSERT INTO `sys_sms_code` VALUES ('911203556797710336', '13192727301', '9999', '192.168.0.122', 1, 3, 0, NULL, NULL, NULL, '2021-11-19 02:37:37', NULL, '2021-11-19 02:37:37');
INSERT INTO `sys_sms_code` VALUES ('911243654880821248', '13600000000', '9999', '192.168.0.122', 1, 1, 1, '2021-11-19 05:17:25', '192.168.0.122', NULL, '2021-11-19 05:16:57', NULL, '2021-11-19 05:17:25');
INSERT INTO `sys_sms_code` VALUES ('911244104959000576', '13630303333', '9999', '192.168.0.122', 1, 1, 1, '2021-11-19 05:19:03', '192.168.0.122', NULL, '2021-11-19 05:18:45', NULL, '2021-11-19 05:19:03');
INSERT INTO `sys_sms_code` VALUES ('911285292378357760', '15601691300', '9999', '127.0.0.1', 1, 2, 0, NULL, NULL, NULL, '2021-11-19 08:02:25', NULL, '2021-11-19 08:02:25');
INSERT INTO `sys_sms_code` VALUES ('911628644567220224', '15601691300', '9999', '127.0.0.1', 1, 3, 0, NULL, NULL, NULL, '2021-11-20 06:46:46', NULL, '2021-11-20 06:46:46');
INSERT INTO `sys_sms_code` VALUES ('911718452392099840', '18243548809', '9999', '192.18.1.85', 1, 1, 1, '2021-11-20 12:44:09', '192.18.1.85', NULL, '2021-11-20 12:43:38', NULL, '2021-11-20 12:44:09');
INSERT INTO `sys_sms_code` VALUES ('911741246664343552', '18569548320', '9999', '192.18.1.26', 1, 1, 1, '2021-11-20 14:14:41', '192.18.1.26', NULL, '2021-11-20 14:14:13', NULL, '2021-11-20 14:14:41');
INSERT INTO `sys_sms_code` VALUES ('911924022302736384', '18569548320', '9999', '192.18.1.26', 1, 2, 0, NULL, NULL, NULL, '2021-11-21 02:20:30', NULL, '2021-11-21 02:20:30');
INSERT INTO `sys_sms_code` VALUES ('911924284870361088', '18569548321', '9999', '192.18.1.26', 1, 1, 1, '2021-11-21 02:21:43', '192.18.1.26', NULL, '2021-11-21 02:21:32', NULL, '2021-11-21 02:21:43');
INSERT INTO `sys_sms_code` VALUES ('912030404737761280', '13192492555', '9999', '127.0.0.1', 1, 1, 1, '2021-11-21 09:23:35', '127.0.0.1', NULL, '2021-11-21 09:23:13', NULL, '2021-11-21 09:23:35');
INSERT INTO `sys_sms_code` VALUES ('912281145641533440', '18855055753', '9999', '192.18.1.26', 1, 1, 1, '2021-11-22 02:00:40', '192.18.1.26', NULL, '2021-11-22 01:59:34', NULL, '2021-11-22 02:00:40');
INSERT INTO `sys_sms_code` VALUES ('912298280602304512', '13192727301', '9999', '192.168.0.114', 1, 4, 1, '2021-11-22 03:08:02', '192.168.0.114', NULL, '2021-11-22 03:07:40', NULL, '2021-11-22 03:08:02');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `sys_tenant_id` varchar(32) NOT NULL COMMENT '租户id',
  `name` varchar(100) DEFAULT NULL COMMENT '租户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `begin_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `status` int(11) DEFAULT NULL COMMENT '状态 1正常 0冻结',
  `code` varchar(32) DEFAULT NULL COMMENT '租户编码',
  PRIMARY KEY (`sys_tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='多租户信息表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` VALUES ('911846681287327722', 'IPlatform后台管理系统', '2021-11-21 06:20:57', 'admin', '2021-11-21 06:22:25', '2099-11-21 06:22:28', 1, 'IPlatform');
INSERT INTO `sys_tenant` VALUES ('911846681287327732', '江北新区数据开放平台', '2021-11-21 06:20:57', 'admin', NULL, NULL, 1, 'Open Data Platfoem');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `sys_user_id` varchar(32) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `work_code` varchar(64) DEFAULT NULL COMMENT '工号',
  `certificate_num` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(16) NOT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NOT NULL COMMENT '用户是否启用: 0->否 1->是',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `type` tinyint(1) NOT NULL COMMENT '用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户',
  `is_auth` tinyint(1) DEFAULT '0' COMMENT '是否已实名认证: 0->否 1->是',
  `data_source` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据来源: 1-> 系统默认 2->第三方系统',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `creator_depart_id` varchar(32) DEFAULT NULL COMMENT '创建人部门id',
  `creator_depart_name` varchar(256) DEFAULT NULL COMMENT '创建人部门名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人部门id',
  `updater_depart_name` varchar(256) DEFAULT NULL COMMENT '更新人部门名称',
  `real_name` varchar(64) DEFAULT NULL COMMENT '用户真实姓名',
  `sys_tenant_id` varchar(32) DEFAULT NULL COMMENT '系统租户id',
  `remark` varchar(256) DEFAULT '' COMMENT '人员描述信息',
  `application_id` varchar(32) DEFAULT NULL COMMENT '所属系统',
  PRIMARY KEY (`sys_user_id`) USING BTREE,
  UNIQUE KEY `index_user_id` (`sys_user_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('910626036754939904', 'admin', NULL, NULL, '18855051258', '2928501731@qq.com', 1, NULL, 3, 0, 1, '2021-11-17 12:22:46', '', NULL, '', NULL, '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911682961068261376', 'A121212', NULL, NULL, '18856055753', '2928501731@qq.com', 1, NULL, 3, 0, 1, '2021-11-20 10:22:36', '', NULL, '', NULL, '2021-11-20 10:22:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911687846199296000', 'test001', NULL, NULL, '18552459876', 'rouy@163.com', 1, NULL, 3, 0, 1, '2021-11-20 10:42:01', '', NULL, '', NULL, '2021-11-20 10:42:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911692737051361280', 'test003', NULL, NULL, '18552453032', 'roy@test.com', 1, NULL, 3, 0, 1, '2021-11-20 11:01:27', '', NULL, '', NULL, '2021-11-20 11:01:27', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911692957415899136', 'A12121125', NULL, NULL, '18855015755', '2928501735@qq.com', 1, NULL, 3, 0, 1, '2021-11-20 11:02:19', '', NULL, '', NULL, '2021-11-20 11:40:44', '', NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911702598027837440', 'A1124', NULL, NULL, '18855057753', '2928501731@qq.com', 1, NULL, 3, 0, 1, '2021-11-20 11:40:38', '', NULL, '', NULL, '2021-11-22 03:12:20', '', NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334');
INSERT INTO `sys_user` VALUES ('911718581618606080', 'Voluna', NULL, NULL, '18243548809', NULL, 1, NULL, 1, 0, 1, '2021-11-20 12:44:09', NULL, NULL, NULL, NULL, '2021-11-20 12:44:09', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('911741368311742464', 'xq12334', NULL, NULL, '18569548320', NULL, 1, NULL, 1, 0, 1, '2021-11-20 14:14:42', NULL, NULL, NULL, NULL, '2021-11-20 14:14:42', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('911924331506827264', 'xq123346', NULL, NULL, '18569548321', NULL, 1, NULL, 1, 0, 1, '2021-11-21 02:21:43', NULL, NULL, NULL, NULL, '2021-11-21 02:21:43', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('912030495166955520', 'admin1', NULL, NULL, '13192492555', NULL, 1, NULL, 1, 0, 1, '2021-11-21 09:23:35', NULL, NULL, NULL, NULL, '2021-11-21 09:23:35', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('912281420393611264', 'zss1234', NULL, NULL, '18855055753', NULL, 1, NULL, 1, 0, 1, '2021-11-22 02:00:40', NULL, NULL, NULL, NULL, '2021-11-22 02:00:40', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('912298373707464704', 'SystemAdmin', NULL, NULL, '13192727301', NULL, 1, NULL, 1, 0, 1, '2021-11-22 03:08:02', NULL, NULL, NULL, NULL, '2021-11-22 03:08:02', NULL, NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('912299125570011136', 'A11221124', NULL, NULL, '18855015753', '2928501731@qq.com', 1, NULL, 1, 0, 1, '2021-11-22 03:11:01', '', NULL, '', NULL, '2021-11-22 03:11:09', '', NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
INSERT INTO `sys_user` VALUES ('912299332034625536', 'A1214', NULL, NULL, '13855015753', '2928501731@qq.com', 1, NULL, 2, 0, 1, '2021-11-22 03:11:50', '', NULL, '', NULL, '2021-11-22 03:12:01', '', NULL, NULL, NULL, NULL, NULL, '', '911846681287327334');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_account`;
CREATE TABLE `sys_user_account` (
  `sys_user_account_id` varchar(32) NOT NULL COMMENT '个人用户账号id',
  `account_expired` tinyint(1) NOT NULL COMMENT '账户是否过期: 0->否 1->是',
  `account_locked` tinyint(1) NOT NULL COMMENT '账户是否锁定:0 ->否 1 ->是',
  `credential` varchar(512) DEFAULT NULL COMMENT '账户凭据',
  `credential_expired` tinyint(1) DEFAULT NULL COMMENT '账户凭据是否过期: 0 ->否 1->是',
  `identifier` tinyint(1) DEFAULT NULL COMMENT '账户标识符:\r\n1:username,2:email,3:phone,4:wechat,5:qq',
  `login_mode` tinyint(1) DEFAULT NULL COMMENT '登录模式',
  `status` tinyint(1) NOT NULL COMMENT '账户状态: 0->停用 1-> 启用 2->待激活',
  `sys_user_id` varchar(32) NOT NULL COMMENT '个人用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `creator_depart_id` varchar(32) DEFAULT NULL COMMENT '创建人部门id',
  `creator_depart_name` varchar(256) DEFAULT NULL COMMENT '创建人部门名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人部门id',
  `updater_depart_name` varchar(256) DEFAULT NULL COMMENT '更新人部门名称',
  `last_login_ip` varchar(32) DEFAULT NULL COMMENT '上次登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_modify_pwd_time` datetime DEFAULT NULL COMMENT '上次更改密码时间',
  PRIMARY KEY (`sys_user_account_id`) USING BTREE,
  UNIQUE KEY `index_sys_user_account_id` (`sys_user_account_id`) COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_account
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_account` VALUES ('1', 0, 0, '$2a$10$fepd1SVs633S4MkMO5oHoOC9FmsIx0vtXsrbvU7KvxNvs/3SEYGEq', 0, 1, NULL, 1, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('909023911516045312', 0, 0, '$2a$10$EWDmzVDUf/Dvne3U8iR3G.0GW3h93dQy7DJXhzCuGD1BG085D8MrW', 0, 1, NULL, 1, '909023911117586432', '2021-11-13 02:16:29', NULL, NULL, NULL, NULL, '2021-11-13 02:16:29', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('909795919346532352', 0, 0, '$2a$10$oGjdITh9sZYvNEoUAh9M3OQ.M2ZSHPxYnnm5b2bZ7a.RVnWaR4Mi.', 0, 1, NULL, 0, '909795919136817152', '2021-11-15 05:24:10', NULL, NULL, NULL, NULL, '2021-11-15 05:24:10', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910483654973587456', 0, 0, '$2a$10$82RtZGyP4pLBDFziFElXC.wld69aXxUxjPpUEDlD8lwL3biNImEbi', 0, 1, NULL, 0, '910483654457688064', '2021-11-17 02:56:59', NULL, NULL, NULL, NULL, '2021-11-17 02:56:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910484543914377216', 0, 0, '$2a$10$93rXs8B3.4UebR4mAAicMOR/cC6s.jZ4Gmf8UFpvxytbCB/crLP9i', 0, 1, NULL, 0, '910484543536889856', '2021-11-17 03:00:31', NULL, NULL, NULL, NULL, '2021-11-17 03:00:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910490648354750464', 0, 0, '$2a$10$2CWjYS3oWOO29X6gawNriuWdIL1aFXrz3xrVLO/RvzIs6LC90Rmpm', 0, 1, NULL, 0, '910490648031789056', '2021-11-17 03:24:47', NULL, NULL, NULL, NULL, '2021-11-17 03:24:47', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910490731871731712', 0, 0, '$2a$10$zpbnah1Xdea1Xbqh6pEpgeVG7.J.zsB58vDU/ZCgKOjKy9LwnRPCa', 0, 1, NULL, 0, '910490731544576000', '2021-11-17 03:25:07', NULL, NULL, NULL, NULL, '2021-11-17 03:25:07', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910492849852645376', 0, 0, '$2a$10$WhLhWhsm7paxAL4T8KxGjuPoQKn7NriabnngtqJ2/7VWDH6Bcpq4e', 0, 1, NULL, 0, '910492849500323840', '2021-11-17 03:33:32', NULL, NULL, NULL, NULL, '2021-11-17 03:33:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910493231039381504', 0, 0, '$2a$10$bwF9rwbf1I/PIlmHjtKvmuJmrCXcf9D7HjhWtN200QRxEhVP.sOua', 0, 1, NULL, 0, '910493230691254272', '2021-11-17 03:35:03', NULL, NULL, NULL, NULL, '2021-11-17 03:35:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910517708859113472', 0, 0, '$2a$10$vK0TvgymNjFqF4dPftqsnuEXForEWrRwmqOM0pIWe89ElywpGdXUG', 0, 1, NULL, 0, '910517708531957760', '2021-11-17 05:12:18', NULL, NULL, NULL, NULL, '2021-11-17 05:12:18', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910518140641738752', 0, 0, '$2a$10$CHpwS8Uc1DnPfuQzv2sEkuIXbC6ZLLv9BaBxSgRdmVo.MsBg1mqwi', 0, 1, NULL, 0, '910518140318777344', '2021-11-17 05:14:01', NULL, NULL, NULL, NULL, '2021-11-17 05:14:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910524502243278848', 0, 0, '$2a$10$Pc8HU.3em1f7TNmBUSKFkOZQb5SaJZutZ9rfcfcTEzT.2td0M7DdO', 0, 1, NULL, 0, '910524501609938944', '2021-11-17 05:39:18', NULL, NULL, NULL, NULL, '2021-11-17 05:39:18', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910525498872823808', 0, 0, '$2a$10$eT1GaPnHFbkvIhuXLE8RLO1/LPPwYs.utTDRnUuOVWF.7SvjeGhzq', 0, 1, NULL, 0, '910525498264649728', '2021-11-17 05:43:16', NULL, NULL, NULL, NULL, '2021-11-17 05:43:16', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910525880583847936', 0, 0, '$2a$10$9Pinsu.IhxJLMWgRoOf9l.iBK/wr1QrXv/xOLbjv/69/q6u/9.7c.', 0, 1, NULL, 0, '910525879841456128', '2021-11-17 05:44:47', NULL, NULL, NULL, NULL, '2021-11-17 05:44:47', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910526122876207104', 0, 0, '$2a$10$PJNE.0fO48ty13/I.dul.O/x4HjHcRwtvpC65SOSRpbmvikRfBSP.', 0, 1, NULL, 0, '910526122284810240', '2021-11-17 05:45:45', NULL, NULL, NULL, NULL, '2021-11-17 05:45:45', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910527164359639040', 0, 0, '$2a$10$DZl1aMrc6A9D3HNujNFndes818V8iS2YUMCTRXkbVMDAzi3gqaCZ6', 0, 1, NULL, 0, '910527163759853568', '2021-11-17 05:49:53', NULL, NULL, NULL, NULL, '2021-11-17 05:49:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910532467788611584', 0, 0, '$2a$10$7oHReMpV14FBLC3cg1uPEuXcAydb2vZv8mcopB7lhjyKULlz8CAd.', 0, 1, NULL, 0, '910532466819727360', '2021-11-17 06:10:57', NULL, NULL, NULL, NULL, '2021-11-17 06:10:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910532588496486400', 0, 0, '$2a$10$fqmG6LEtNTwOjJ0lUjJ9VurZB3JYOYXG59nUkXQOOe6SoNtDQsm1W', 0, 1, NULL, 0, '910532587858952192', '2021-11-17 06:11:26', NULL, NULL, NULL, NULL, '2021-11-17 06:11:26', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910535990660038656', 0, 0, '$2a$10$qtVJVSkML1oY.ECbMRWgd..K8xy7b3Y6VRA7vBsCwfYuwbXaOCE4u', 0, 1, NULL, 0, '910535990047670272', '2021-11-17 06:24:57', NULL, NULL, NULL, NULL, '2021-11-17 06:24:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910566433425981440', 0, 0, '$2a$10$3COvOd.QDkesthFSlsCLH.WAopy6L1iV4ojdxLERR.exCqKqTc2d2', 0, 1, NULL, 0, '910566432863944704', '2021-11-17 08:25:55', NULL, NULL, NULL, NULL, '2021-11-17 08:25:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910626037899984896', 0, 0, '$2a$10$x3bjuD0IS5QH7beyJipH5OTaOlmnEpKqCMey1A/G/KVWLXvCrJx2O', 0, 1, NULL, 0, '910626036754939904', '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910626700180586496', 0, 0, '$2a$10$P3q8hj9m3x5lAAAt/IvvTuoD7i9GeETd5V8rWNYfRf6wVK3AZBTK2', 0, 1, NULL, 0, '910626699564023808', '2021-11-17 12:25:24', NULL, NULL, NULL, NULL, '2021-11-17 12:25:24', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910629389631225856', 0, 0, '$2a$10$PKLJ9H7XSU0g2MYQlaWk8.HJF6SjcdIR3TJ1Cjlsay6fbquRPSju6', 0, 1, NULL, 0, '910629389031440384', '2021-11-17 12:36:05', NULL, NULL, NULL, NULL, '2021-11-17 12:36:05', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910631485805953024', 0, 0, '$2a$10$6Rt8.pkTUcMfotJ.6phfB.OU.PvRpTbZ6jOxZdWNFEzTSCqXQRZBe', 0, 1, NULL, 0, '910631485214556160', '2021-11-17 12:44:25', NULL, NULL, NULL, NULL, '2021-11-17 12:44:25', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910631727565635584', 0, 0, '$2a$10$aZk06XoSTl8.Ly2bQBD/WO1jlCbUJyFISj0h025nyqe0YqcMV75DW', 0, 1, NULL, 0, '910631726965850112', '2021-11-17 12:45:23', NULL, NULL, NULL, NULL, '2021-11-17 12:45:23', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910839432846770176', 0, 0, '$2a$10$cnSWOV57UkiutBTeIo6OLeu/tc8BpwgcD3Wq4XPs5AUPPsCdEjnt2', 0, 1, NULL, 0, '910839431965966336', '2021-11-18 02:30:44', NULL, NULL, NULL, NULL, '2021-11-18 02:30:44', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910843462687916032', 0, 0, '$2a$10$6AykfsBl4Zf44pp5jvL/9OM1ySUiHoju9poIr8J9qt5wJyTNbA7/S', 0, 1, NULL, 0, '910843462050381824', '2021-11-18 02:46:44', NULL, NULL, NULL, NULL, '2021-11-18 02:46:44', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910849444721721344', 0, 0, '$2a$10$DdbCCtX6mMA3k6pLkGjGV.jZLVk3OUDhI2/qQt/2NzbDL8QD1CwBG', 0, 1, NULL, 0, '910849444671389696', '2021-11-18 03:10:30', NULL, NULL, NULL, NULL, '2021-11-18 03:10:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910906173757063168', 0, 0, '$2a$10$qGHTVykLhP7tOKVKQuVgceERdMJRboq2C5.bmTqhn03JXqrinaDTu', 0, 1, NULL, 0, '910906172893036544', '2021-11-18 06:55:56', NULL, NULL, NULL, NULL, '2021-11-18 06:55:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910907066187513856', 0, 0, '$2a$10$aoDdWvi8QcTcw3EGt9r1ke6KWPDkUWPdIWf4zannZuwVJKY5gsZR6', 0, 1, NULL, 0, '910907065520619520', '2021-11-18 06:59:29', NULL, NULL, NULL, NULL, '2021-11-18 06:59:29', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910907827101368320', 0, 0, '$2a$10$9bVFoQaXmziyMopW.Fm6Mu48DEtxenGNgRD7skTXEQRm2vOrjAVr.', 0, 1, NULL, 0, '910907826447056896', '2021-11-18 07:02:30', NULL, NULL, NULL, NULL, '2021-11-18 07:02:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('910936213471363072', 0, 0, '$2a$10$rpSwlAQx6aB4pMQVtbMGfeCaYNkBlmAipk1tKQvzxr1C6VTQ7YOnG', 0, 1, NULL, 0, '910936212213071872', '2021-11-18 08:55:18', NULL, NULL, NULL, NULL, '2021-11-18 08:55:18', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911243771365031936', 0, 0, '$2a$10$Zvq1UR4zR2swfIsi35V3cOhoc/TypM6DnKHgfTxKbvQDXLPokbZTy', 0, 1, NULL, 0, '911243771314700288', '2021-11-19 05:17:25', NULL, NULL, NULL, NULL, '2021-11-19 05:17:25', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911244182582984704', 0, 0, '$2a$10$/fT7ZhZOGYx6h7fVAJ/re.vDQiOV8ozB6BlBc7tHjnjWGnxX/Tqry', 0, 1, NULL, 0, '911244182570401792', '2021-11-19 05:19:03', NULL, NULL, NULL, NULL, '2021-11-19 05:19:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911277287725334528', 0, 0, '$2a$10$xxq2gcIdL7/ULfn7PG9r3uC6/YjlI10Y6g33.b7CeHg79FrhwfwB.', 0, 1, NULL, 0, '911277287020691456', '2021-11-19 07:30:36', NULL, NULL, NULL, NULL, '2021-11-19 07:30:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911278210245722112', 0, 0, '$2a$10$qbMZc3z4uGKRD21A4a8E0eZiPbbBXpz1Wzg36vH.okNSWekwliciK', 0, 1, NULL, 0, '911278209910177792', '2021-11-19 07:34:16', NULL, NULL, NULL, NULL, '2021-11-19 07:34:16', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911279025324818432', 0, 0, '$2a$10$CGKakdgAYcabBrrswL7tr.VQKB4oimhPST80.5NBuy1PlhlSbVD4m', 0, 1, NULL, 0, '911279025211572224', '2021-11-19 07:37:31', NULL, NULL, NULL, NULL, '2021-11-19 07:37:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911283973156503552', 0, 0, '$2a$10$cQFG9GrkuJQwKmy4r0iSjOew12kEOTqlgHjGIwvkidfGrtIkNKzmy', 0, 1, NULL, 0, '911283973139726336', '2021-11-19 07:57:10', NULL, NULL, NULL, NULL, '2021-11-19 07:57:10', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911284575760547840', 0, 0, '$2a$10$8mf3sepFftVvqfnpBVXrdOdOVt00vF71jnQKHd4DCwqGAKpEFSye6', 0, 1, NULL, 0, '911284575752159232', '2021-11-19 07:59:34', NULL, NULL, NULL, NULL, '2021-11-19 07:59:34', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911287424556990464', 0, 0, '$2a$10$K54tXJqjza/ApkSt2hQ.je04qgG5/RLSk7wY1PjR6aqkABAjWfi42', 0, 1, NULL, 0, '911287424540213248', '2021-11-19 08:10:53', NULL, NULL, NULL, NULL, '2021-11-19 08:10:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911287979757010944', 0, 0, '$2a$10$78pn/3wJdfnysmkIJsSnnuEs9iERSNv9cZalTPGAZdKfD3E0UaOS2', 0, 1, NULL, 0, '911287979736039424', '2021-11-19 08:13:05', NULL, NULL, NULL, NULL, '2021-11-19 08:13:05', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911292763956314112', 0, 0, '$2a$10$bVKCTjq8SzE8lItsWziVoOTgzVj6wbidkNxjwDBHHMRw6m3wR4hem', 0, 1, NULL, 0, '911292763935342592', '2021-11-19 08:32:06', NULL, NULL, NULL, NULL, '2021-11-19 08:32:06', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911632207460696064', 0, 0, '$2a$10$/IDhwaPusUz3.Dasj1e3eu89NvMByExeU2mq9lqy7m5AWMNLQ62ai', 0, 1, NULL, 0, '911632205984301056', '2021-11-20 07:00:56', NULL, NULL, NULL, NULL, '2021-11-20 07:00:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911645637949259776', 0, 0, '$2a$10$UBIJkUrqMHad0q1F.fJz9ePU.RJ5V57xY4pRaVXiYngBG58Zq7Oj2', 0, 1, NULL, 0, '911645637030707200', '2021-11-20 07:54:18', NULL, NULL, NULL, NULL, '2021-11-20 07:54:18', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911682962087477248', 0, 0, '$2a$10$SX90mj/PKUDdg9cgE8EbseQYZrWxVmL2SzlfA0fMD/nyA68jASmfK', 0, 1, NULL, 0, '911682961068261376', '2021-11-20 10:22:37', NULL, NULL, NULL, NULL, '2021-11-20 10:22:37', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911687847151403008', 0, 0, '$2a$10$Zi55tnOkzSyhxwLauVIZAuDPbPo6popPI7nU6lI2U0j50aMp0ne9C', 0, 1, NULL, 0, '911687846199296000', '2021-11-20 10:42:01', NULL, NULL, NULL, NULL, '2021-11-20 10:42:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911692738062188544', 0, 0, '$2a$10$9lIe1yyW/Pv8TO3eWzmSa.pO98KiA1kGykyPUylCofc0tQ34ovjOu', 0, 1, NULL, 0, '911692737051361280', '2021-11-20 11:01:27', NULL, NULL, NULL, NULL, '2021-11-20 11:01:27', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911692958997151744', 0, 0, '$2a$10$7L.NYgXlfM/yO1r7L9L1q.fxFrk9UA2uGX3pK7LitPGfIIjbmjPoO', 0, 1, NULL, 0, '911692957415899136', '2021-11-20 11:02:20', NULL, NULL, NULL, NULL, '2021-11-20 11:02:20', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911702599349043200', 0, 0, '$2a$10$CkJYzj3cZaQSnfDSK3A9nuOfJLVUWvvhIiYLEtrK/DvdrG6Kk8036', 0, 1, NULL, 0, '911702598027837440', '2021-11-20 11:40:38', NULL, NULL, NULL, NULL, '2021-11-20 11:40:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911718582226780160', 0, 0, '$2a$10$AirhyYfyAg1mduUItGDdMeUWNYE7LA94DZ1/gKX3fx11FkgsgXs7.', 0, 1, NULL, 0, '911718581618606080', '2021-11-20 12:44:09', NULL, NULL, NULL, NULL, '2021-11-20 12:44:09', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911741369041551360', 0, 0, '$2a$10$PFv9/F779z7d5F1KN0Ewle6ER1RAbtCrLzigMIxCUE81TbU9GwK8W', 0, 1, NULL, 0, '911741368311742464', '2021-11-20 14:14:42', NULL, NULL, NULL, NULL, '2021-11-20 14:14:42', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('911924331561353216', 0, 0, '$2a$10$2e2X4QYMJVKqMZGnFgAWe.B.eHaO502ChMpf9Innq7aBQhz33Ytl.', 0, 1, NULL, 0, '911924331506827264', '2021-11-21 02:21:43', NULL, NULL, NULL, NULL, '2021-11-21 02:21:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('912030495175344128', 0, 0, '$2a$10$rBqTMNSp6SM1zWf3QDtrtOadTWw1eepi9Voj.OJmA2ZHnujcws1Aq', 0, 1, NULL, 0, '912030495166955520', '2021-11-21 09:23:35', NULL, NULL, NULL, NULL, '2021-11-21 09:23:35', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('912281421060505600', 0, 0, '$2a$10$.i6rRmmFRjFuCdFM93t0XOpIITpnjSzADLa06hNOjiR7wnPgLqooC', 0, 1, NULL, 0, '912281420393611264', '2021-11-22 02:00:40', NULL, NULL, NULL, NULL, '2021-11-22 02:00:40', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('912298373720047616', 0, 0, '$2a$10$/lTkcrEcaD7wQCCTQ9Dch.MEdJtMCNEdipTbgK.zNny2/N/U04hX6', 0, 1, NULL, 0, '912298373707464704', '2021-11-22 03:08:02', NULL, NULL, NULL, NULL, '2021-11-22 03:08:02', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('912299126069133312', 0, 0, '$2a$10$jSs/oTMg2PlTJwygLQX9tOCJ7x58q32Q.xvO71g9jKInUqglwzA22', 0, 1, NULL, 0, '912299125570011136', '2021-11-22 03:11:01', NULL, NULL, NULL, NULL, '2021-11-22 03:11:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_account` VALUES ('912299332831543296', 0, 0, '$2a$10$vvwfdNXLJGlb1Q3.ngFAK.4FwZ5sSP2wj4Xy/Xu1FzgNG7gLsHNXa', 0, 1, NULL, 0, '912299332034625536', '2021-11-22 03:11:51', NULL, NULL, NULL, NULL, '2021-11-22 03:11:51', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_org_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_org_ref`;
CREATE TABLE `sys_user_org_ref` (
  `sys_user_org_id` varchar(32) NOT NULL COMMENT '人员所属机构id',
  `sys_user_id` varchar(32) DEFAULT NULL COMMENT '人员id',
  `sys_org_id` varchar(32) DEFAULT NULL COMMENT '机构id',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人员id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sys_user_org_id`) USING BTREE,
  UNIQUE KEY `index_sys_user_org_ref_id` (`sys_user_org_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_org_ref
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_org_ref` VALUES ('910626036754939901', '910626036754939904', '909819630892089344', NULL, '2021-11-17 20:39:16', NULL, '2021-11-17 20:39:18');
INSERT INTO `sys_user_org_ref` VALUES ('911682961424777216', '911682961068261376', '911682794453729280', '', '2021-11-20 10:22:36', NULL, '2021-11-20 10:22:36');
INSERT INTO `sys_user_org_ref` VALUES ('911687846534840320', '911687846199296000', '911682794453729280', '', '2021-11-20 10:42:01', NULL, '2021-11-20 10:42:01');
INSERT INTO `sys_user_org_ref` VALUES ('911692737370128384', '911692737051361280', '911682601415081984', '', '2021-11-20 11:01:27', NULL, '2021-11-20 11:01:27');
INSERT INTO `sys_user_org_ref` VALUES ('911692957730471936', '911692957415899136', '911682601415081984', '', '2021-11-20 11:02:20', NULL, '2021-11-20 11:33:50');
INSERT INTO `sys_user_org_ref` VALUES ('911702598396936192', '911702598027837440', '911682794453729280', '', '2021-11-20 11:40:38', NULL, '2021-11-20 11:40:51');
INSERT INTO `sys_user_org_ref` VALUES ('912299332500193280', '912299332034625536', '912299331711664128', '', '2021-11-22 03:11:51', NULL, '2021-11-22 03:11:51');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_ref`;
CREATE TABLE `sys_user_role_ref` (
  `sys_user_role_ref_id` varchar(32) NOT NULL COMMENT '用户角色id',
  `sys_user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `sys_role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`sys_user_role_ref_id`) USING BTREE,
  UNIQUE KEY `index_sys_user_role_ref_id` (`sys_user_role_ref_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role_ref
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role_ref` VALUES ('910484544979730432', '910484543536889856', '910473676262998016', NULL, '2021-11-17 03:00:32', NULL, '2021-11-17 03:00:32');
INSERT INTO `sys_user_role_ref` VALUES ('910490649352994816', '910490648031789056', '910473676262998016', NULL, '2021-11-17 03:24:47', NULL, '2021-11-17 03:24:47');
INSERT INTO `sys_user_role_ref` VALUES ('910490732865781760', '910490731544576000', '910473676262998016', NULL, '2021-11-17 03:25:07', NULL, '2021-11-17 03:25:07');
INSERT INTO `sys_user_role_ref` VALUES ('910492850955747328', '910492849500323840', '910473676262998016', NULL, '2021-11-17 03:33:32', NULL, '2021-11-17 03:33:32');
INSERT INTO `sys_user_role_ref` VALUES ('910493232167649280', '910493230691254272', '910473676262998016', NULL, '2021-11-17 03:35:03', NULL, '2021-11-17 03:35:03');
INSERT INTO `sys_user_role_ref` VALUES ('910517709865746432', '910517708531957760', '910473676262998016', NULL, '2021-11-17 05:12:19', NULL, '2021-11-17 05:12:19');
INSERT INTO `sys_user_role_ref` VALUES ('910518141711286272', '910518140318777344', '910473676262998016', NULL, '2021-11-17 05:14:02', NULL, '2021-11-17 05:14:02');
INSERT INTO `sys_user_role_ref` VALUES ('910525499921399808', '910525498264649728', '910473676262998016', NULL, '2021-11-17 05:43:16', NULL, '2021-11-17 05:43:16');
INSERT INTO `sys_user_role_ref` VALUES ('910525881779224576', '910525879841456128', '910473676262998016', NULL, '2021-11-17 05:44:47', NULL, '2021-11-17 05:44:47');
INSERT INTO `sys_user_role_ref` VALUES ('910526123861868544', '910526122284810240', '910473676262998016', NULL, '2021-11-17 05:45:45', NULL, '2021-11-17 05:45:45');
INSERT INTO `sys_user_role_ref` VALUES ('910527165349494784', '910527163759853568', '910473676262998016', NULL, '2021-11-17 05:49:53', NULL, '2021-11-17 05:49:53');
INSERT INTO `sys_user_role_ref` VALUES ('910566434646523904', '910566432863944704', '910473676262998016', NULL, '2021-11-17 08:25:56', NULL, '2021-11-17 08:25:56');
INSERT INTO `sys_user_role_ref` VALUES ('910626701359185920', '910626699564023808', '910473676262998016', NULL, '2021-11-17 12:25:24', NULL, '2021-11-17 12:25:24');
INSERT INTO `sys_user_role_ref` VALUES ('910629390759493632', '910629389031440384', '910473676262998016', NULL, '2021-11-17 12:36:05', NULL, '2021-11-17 12:36:05');
INSERT INTO `sys_user_role_ref` VALUES ('910631486749671424', '910631485214556160', '910473676262998016', NULL, '2021-11-17 12:44:25', NULL, '2021-11-17 12:44:25');
INSERT INTO `sys_user_role_ref` VALUES ('910839434490937344', '910839431965966336', '910473676262998016', NULL, '2021-11-18 02:30:44', NULL, '2021-11-18 02:30:44');
INSERT INTO `sys_user_role_ref` VALUES ('910849445233426432', '910849444671389696', '910473676262998016', NULL, '2021-11-18 03:10:31', NULL, '2021-11-18 03:10:31');
INSERT INTO `sys_user_role_ref` VALUES ('910906175149572096', '910906172893036544', '910473676262998016', NULL, '2021-11-18 06:55:56', NULL, '2021-11-18 06:55:56');
INSERT INTO `sys_user_role_ref` VALUES ('911278211315269632', '911278209910177792', '910473676262998016', NULL, '2021-11-19 07:34:16', NULL, '2021-11-19 07:34:16');
INSERT INTO `sys_user_role_ref` VALUES ('911279025928798208', '911279025211572224', '910473676262998016', NULL, '2021-11-19 07:37:31', NULL, '2021-11-19 07:37:31');
INSERT INTO `sys_user_role_ref` VALUES ('911283973617876992', '911283973139726336', '910473676262998016', NULL, '2021-11-19 07:57:10', NULL, '2021-11-19 07:57:10');
INSERT INTO `sys_user_role_ref` VALUES ('911284576238698496', '911284575752159232', '910473676262998016', NULL, '2021-11-19 07:59:34', NULL, '2021-11-19 07:59:34');
INSERT INTO `sys_user_role_ref` VALUES ('911287425039335424', '911287424540213248', '910473676262998016', NULL, '2021-11-19 08:10:53', NULL, '2021-11-19 08:10:53');
INSERT INTO `sys_user_role_ref` VALUES ('911287980230967296', '911287979736039424', '910473676262998016', NULL, '2021-11-19 08:13:05', NULL, '2021-11-19 08:13:05');
INSERT INTO `sys_user_role_ref` VALUES ('911593354188292098', '910535990047670272', '910473676262998016', NULL, '2021-11-20 04:26:32', NULL, '2021-11-20 04:26:32');
INSERT INTO `sys_user_role_ref` VALUES ('911593354188292100', '910936212213071872', '910473676262998016', NULL, '2021-11-20 04:26:32', NULL, '2021-11-20 04:26:32');
INSERT INTO `sys_user_role_ref` VALUES ('911593377466679298', '910535990047670272', '910473676262998016', NULL, '2021-11-20 04:26:38', NULL, '2021-11-20 04:26:38');
INSERT INTO `sys_user_role_ref` VALUES ('911593377466679300', '910936212213071872', '910473676262998016', NULL, '2021-11-20 04:26:38', NULL, '2021-11-20 04:26:38');
INSERT INTO `sys_user_role_ref` VALUES ('911617101444153336', '910626036754939904', '910473676262998019', NULL, '2021-11-20 06:00:54', NULL, '2021-11-20 06:00:54');
INSERT INTO `sys_user_role_ref` VALUES ('911632206726692864', '911632205984301056', '911250186393616400', NULL, '2021-11-20 07:00:55', NULL, '2021-11-20 07:00:55');
INSERT INTO `sys_user_role_ref` VALUES ('911645637647269888', '911645637030707200', '911250186393616400', NULL, '2021-11-20 07:54:18', NULL, '2021-11-20 07:54:18');
INSERT INTO `sys_user_role_ref` VALUES ('911682961756127232', '911682961068261376', '911204344873877504', NULL, '2021-11-20 10:22:36', NULL, '2021-11-20 10:22:36');
INSERT INTO `sys_user_role_ref` VALUES ('911687846832635904', '911687846199296000', '911205320863252480', NULL, '2021-11-20 10:42:01', NULL, '2021-11-20 10:42:01');
INSERT INTO `sys_user_role_ref` VALUES ('911692737722449920', '911692737051361280', '910947921455742976', NULL, '2021-11-20 11:01:27', NULL, '2021-11-20 11:01:27');
INSERT INTO `sys_user_role_ref` VALUES ('911692958053433344', '911692957415899136', '911648948626653184', NULL, '2021-11-20 11:02:20', NULL, '2021-11-20 11:02:20');
INSERT INTO `sys_user_role_ref` VALUES ('911702598715703296', '911702598027837440', '911205320863252480', NULL, '2021-11-20 11:40:38', NULL, '2021-11-20 11:51:28');
INSERT INTO `sys_user_role_ref` VALUES ('911718583657037824', '911718581618606080', '910473676262998016', NULL, '2021-11-20 12:44:09', NULL, '2021-11-20 12:44:09');
INSERT INTO `sys_user_role_ref` VALUES ('911741370182402048', '911741368311742464', '910473676262998016', NULL, '2021-11-20 14:14:42', NULL, '2021-11-20 14:14:42');
INSERT INTO `sys_user_role_ref` VALUES ('911924332047892480', '911924331506827264', '910473676262998016', NULL, '2021-11-21 02:21:44', NULL, '2021-11-21 02:21:44');
INSERT INTO `sys_user_role_ref` VALUES ('912030495636717568', '912030495166955520', '910473676262998016', NULL, '2021-11-21 09:23:35', NULL, '2021-11-21 09:23:35');
INSERT INTO `sys_user_role_ref` VALUES ('912281422717255680', '912281420393611264', '910473676262998016', NULL, '2021-11-22 02:00:41', NULL, '2021-11-22 02:00:41');
INSERT INTO `sys_user_role_ref` VALUES ('912298374177226752', '912298373707464704', '910473676262998016', NULL, '2021-11-22 03:08:02', NULL, '2021-11-22 03:08:02');
INSERT INTO `sys_user_role_ref` VALUES ('912299127038017536', '912299125570011136', '910473676262998016', NULL, '2021-11-22 03:11:02', NULL, '2021-11-22 03:11:02');
INSERT INTO `sys_user_role_ref` VALUES ('912299333821399040', '912299332034625536', '910473676262998016', NULL, '2021-11-22 03:11:51', NULL, '2021-11-22 03:11:51');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
