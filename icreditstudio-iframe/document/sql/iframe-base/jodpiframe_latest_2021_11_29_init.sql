/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.17
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.0.17:3306
 Source Schema         : jodpiframe

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 29/11/2021 13:22:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for advice_track
-- ----------------------------
DROP TABLE IF EXISTS `advice_track`;
CREATE TABLE `advice_track` (
  `advice_track_id` varchar(32) NOT NULL COMMENT 'ID',
  `advice_type` tinyint(4) NOT NULL COMMENT '建议类型',
  `advice_title` varchar(32) DEFAULT NULL COMMENT '建议标题',
  `advice_content` varchar(512) DEFAULT NULL COMMENT '建议内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人用户ID',
  `LAST_UPDATE_TIME` datetime NOT NULL COMMENT '上次更新时间',
  `LAST_UPDATE_USER_ID` varchar(32) NOT NULL COMMENT '上次更新人用户ID',
  `create_user_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
  `advice_number` varchar(32) NOT NULL COMMENT '意见编号',
  `advice_status` int(5) DEFAULT NULL COMMENT '反馈状态，1-待处理，2-已处理',
  `advice_info` varchar(200) DEFAULT NULL COMMENT '意见信息',
  PRIMARY KEY (`advice_track_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='建议跟踪';

-- ----------------------------
-- Table structure for application_scene
-- ----------------------------
DROP TABLE IF EXISTS `application_scene`;
CREATE TABLE `application_scene` (
  `application_scene_id` varchar(32) NOT NULL COMMENT 'ID',
  `application_scene_name` varchar(64) NOT NULL COMMENT '应用场景名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人用户ID',
  `LAST_UPDATE_USER_ID` varchar(32) NOT NULL COMMENT '上次更新人用户ID',
  PRIMARY KEY (`application_scene_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='应用场景表';

-- ----------------------------
-- Records of application_scene
-- ----------------------------
BEGIN;
INSERT INTO `application_scene` VALUES ('1', '社会保险', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('2', '学校教育', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('3', '培训与就业', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('4', '就医与保障', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('5', '交通出行', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('6', '社区周边生活服务', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('7', '政府办事', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('8', '城市安全', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
INSERT INTO `application_scene` VALUES ('9', '其他', '2021-11-18 16:08:03', '2021-11-18 16:08:03', 'system', 'system');
COMMIT;

-- ----------------------------
-- Table structure for download_record
-- ----------------------------
DROP TABLE IF EXISTS `download_record`;
CREATE TABLE `download_record` (
  `download_record_id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_type` tinyint(4) DEFAULT NULL COMMENT '1、个人用户，2、机构用户',
  `source_directory_file_id` varchar(32) NOT NULL COMMENT '资源目录文件查看记录ID',
  `file_id` varchar(64) NOT NULL COMMENT '文件上传minio后返回的ID',
  `download_time` datetime NOT NULL COMMENT '下载时间',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  PRIMARY KEY (`download_record_id`) USING BTREE,
  KEY `index_source_directory_file_id` (`source_directory_file_id`) USING BTREE,
  KEY `index_source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件下载记录';


-- ----------------------------
-- Table structure for interface_invoke_count
-- ----------------------------
DROP TABLE IF EXISTS `interface_invoke_count`;
CREATE TABLE `interface_invoke_count` (
  `interface_invoke_count_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_interface_id` varchar(32) DEFAULT NULL COMMENT '资源目录接口ID',
  `create_day` date NOT NULL COMMENT '创建时间,天',
  `invoke_count` int(11) NOT NULL COMMENT '调用次数',
  `application_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `source_directory_id` varchar(32) NOT NULL DEFAULT '' COMMENT '资源目录ID',
  PRIMARY KEY (`interface_invoke_count_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接口调用日志统计表';


-- ----------------------------
-- Table structure for interface_invoke_record
-- ----------------------------
DROP TABLE IF EXISTS `interface_invoke_record`;
CREATE TABLE `interface_invoke_record` (
  `interface_invoke_record_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_interface_id` varchar(32) DEFAULT NULL COMMENT '资源目录接口ID',
  `create_time` datetime NOT NULL COMMENT '记录时间',
  `application_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
  `source_directory_id` varchar(32) NOT NULL DEFAULT '' COMMENT '资源目录ID',
  PRIMARY KEY (`interface_invoke_record_id`) USING BTREE,
  KEY `index_interface_id` (`source_directory_interface_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接口调用日志记录表';

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `login_record_id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`login_record_id`) USING BTREE,
  KEY `Index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='登录记录';


-- ----------------------------
-- Table structure for scan_record
-- ----------------------------
DROP TABLE IF EXISTS `scan_record`;
CREATE TABLE `scan_record` (
  `scan_record_id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '浏览人',
  `scan_time` datetime NOT NULL COMMENT '浏览时间',
  `source_directory_id` varchar(32) DEFAULT NULL COMMENT '资源目录ID',
  PRIMARY KEY (`scan_record_id`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_scan_time_user_id` (`user_id`,`scan_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='浏览记录';


-- ----------------------------
-- Table structure for scan_record_count
-- ----------------------------
DROP TABLE IF EXISTS `scan_record_count`;
CREATE TABLE `scan_record_count` (
  `scan_record_count_id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '浏览人用户ID',
  `scan_time` date NOT NULL COMMENT '浏览时间(天)',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `scan_count` int(11) NOT NULL COMMENT '浏览次数',
  PRIMARY KEY (`scan_record_count_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='浏览记录统计表';


-- ----------------------------
-- Table structure for source_data_classify
-- ----------------------------
DROP TABLE IF EXISTS `source_data_classify`;
CREATE TABLE `source_data_classify` (
  `source_data_classify_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_topic_id` varchar(32) NOT NULL COMMENT '主题id',
  `classify_name` varchar(64) NOT NULL COMMENT '分类名称',
  `classify_description` varchar(64) DEFAULT NULL COMMENT '分类描述',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `LAST_UPDATE_TIME` datetime DEFAULT NULL COMMENT '上次更新人ID',
  `icon_file_id` varchar(64) DEFAULT NULL COMMENT 'icon图标上传minio返回的id',
  PRIMARY KEY (`source_data_classify_id`) USING BTREE,
  KEY `index_source_topic_id` (`source_topic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主题数据分类表';

-- ----------------------------
-- Records of source_data_classify
-- ----------------------------
BEGIN;
INSERT INTO `source_data_classify` VALUES ('1', '1', '工商信息', '工商信息', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/1.svg');
INSERT INTO `source_data_classify` VALUES ('10', '1', '政策兑现', '政策兑现', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/10.svg');
INSERT INTO `source_data_classify` VALUES ('11', '1', '土地使用情况', '土地使用情况', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/11.svg');
INSERT INTO `source_data_classify` VALUES ('12', '1', '私募基金', '私募基金', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/12.svg');
INSERT INTO `source_data_classify` VALUES ('13', '1', '投资机构', '投资机构', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/13.svg');
INSERT INTO `source_data_classify` VALUES ('14', '2', '新区', '新区', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/14.svg');
INSERT INTO `source_data_classify` VALUES ('15', '2', '全国', '全国', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/15.svg');
INSERT INTO `source_data_classify` VALUES ('16', '3', '政府机构', '政府机构', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/16.svg');
INSERT INTO `source_data_classify` VALUES ('17', '3', '事业单位', '事业单位', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/17.svg');
INSERT INTO `source_data_classify` VALUES ('18', '3', '社会团体', '社会团体', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/18.svg');
INSERT INTO `source_data_classify` VALUES ('19', '3', '文体单位', '文体单位', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/19.svg');
INSERT INTO `source_data_classify` VALUES ('2', '1', '经济风险', '经济风险', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/2.svg');
INSERT INTO `source_data_classify` VALUES ('20', '3', '教育资源', '教育资源', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/20.svg');
INSERT INTO `source_data_classify` VALUES ('21', '3', '卫生资源', '卫生资源', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/21.svg');
INSERT INTO `source_data_classify` VALUES ('22', '3', '养老机构', '养老机构', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/22.svg');
INSERT INTO `source_data_classify` VALUES ('23', '3', '公共厕所', '公共厕所', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/23.svg');
INSERT INTO `source_data_classify` VALUES ('24', '3', '交通资源', '交通资源', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/24.svg');
INSERT INTO `source_data_classify` VALUES ('25', '3', '金融和运营商网点', '金融和运营商网点', '1111 ', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/25.svg');
INSERT INTO `source_data_classify` VALUES ('26', '3', '商业', '商业', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/26.svg');
INSERT INTO `source_data_classify` VALUES ('27', '4', '人才统计', '人才统计', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/27.svg');
INSERT INTO `source_data_classify` VALUES ('28', '4', '人才详情', '人才详情', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/28.svg');
INSERT INTO `source_data_classify` VALUES ('29', '5', '创新载体', '创新载体', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/29.svg');
INSERT INTO `source_data_classify` VALUES ('3', '1', '经营信息', '经营信息', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/3.svg');
INSERT INTO `source_data_classify` VALUES ('30', '5', '创新企业', '创新企业', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/30.svg');
INSERT INTO `source_data_classify` VALUES ('31', '5', '创新奖项', '创新奖项', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/31.svg');
INSERT INTO `source_data_classify` VALUES ('4', '1', '企业发展', '企业发展', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/4.svg');
INSERT INTO `source_data_classify` VALUES ('5', '1', '人员信息', '人员信息', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/5.svg');
INSERT INTO `source_data_classify` VALUES ('6', '1', '司法信息', '司法信息', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/6.svg');
INSERT INTO `source_data_classify` VALUES ('7', '1', '上市信息', '上市信息', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/7.svg');
INSERT INTO `source_data_classify` VALUES ('8', '1', '知识产权', '知识产权', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/8.svg');
INSERT INTO `source_data_classify` VALUES ('9', '1', '税收情况', '税收情况', '1111', '2021-10-29 00:00:00', '2222', '2021-10-29 00:00:00', 'backend/20211110/9.svg');
COMMIT;

-- ----------------------------
-- Table structure for source_directory
-- ----------------------------
DROP TABLE IF EXISTS `source_directory`;
CREATE TABLE `source_directory` (
  `source_directory_id` varchar(32) NOT NULL COMMENT 'ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `LAST_UPDATE_USER_ID` varchar(32) NOT NULL COMMENT '上次更新人ID',
  `publish_user_id` varchar(32) DEFAULT NULL COMMENT '发布人ID',
  `directory_name` varchar(64) NOT NULL COMMENT '目录名称',
  `organization_id` varchar(32) NOT NULL COMMENT '组织ID',
  `source_topic_id` varchar(32) NOT NULL COMMENT '主题ID',
  `source_data_classify_id` varchar(32) NOT NULL COMMENT '分类ID',
  `serial_number` varchar(32) DEFAULT NULL COMMENT '目录编号',
  `use_way` tinyint(4) NOT NULL COMMENT '使用方式：1.接口调用，2.文件下载',
  `update_frequency` tinyint(4) NOT NULL DEFAULT '5' COMMENT '更新频率：1.每天，2.每周，3.每月，4.每年，5.未更新过',
  `open_type` tinyint(4) NOT NULL COMMENT '开放类型：1.有条件开放，2.无条件开放',
  `directory_description` varchar(128) DEFAULT NULL COMMENT '数据描述',
  `publish_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '发布状态：0已发布，1未发布',
  `delete_status` tinyint(4) DEFAULT '0' COMMENT '删除状态，0-未删除，1-已删除',
  `organization_name` varchar(64) NOT NULL DEFAULT '' COMMENT '机构名称',
  PRIMARY KEY (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录';

-- ----------------------------
-- Table structure for source_directory_apply
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_apply`;
CREATE TABLE `source_directory_apply` (
  `source_directory_apply_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `apply_number` varchar(32) NOT NULL COMMENT '申请编号',
  `application_scene_id` varchar(32) NOT NULL COMMENT '应用场景ID',
  `use_description` varchar(200) NOT NULL COMMENT '用途',
  `certify_file_id` varchar(64) DEFAULT NULL COMMENT '证明材料上传minio返回的文件ID',
  `apply_status` tinyint(4) NOT NULL COMMENT '审核状态：1.待审核，2.已通过，3.已驳回',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `apply_user_id` varchar(32) NOT NULL COMMENT '申请人的用户ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审批时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人的用户ID',
  `apply_user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '申请人名称',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '联系方式',
  PRIMARY KEY (`source_directory_apply_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源申请记录';


-- ----------------------------
-- Table structure for source_directory_audit
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_audit`;
CREATE TABLE `source_directory_audit` (
  `source_directory_audit_id` varchar(32) NOT NULL COMMENT 'ID',
  `apply_id` varchar(32) NOT NULL COMMENT '申请ID',
  `audit_time` datetime NOT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人用户ID',
  `audit_status` tinyint(4) NOT NULL COMMENT '审核状态：1.审核不通过，2.审核通过，3.驳回',
  `audit_advice` varchar(200) NOT NULL DEFAULT '' COMMENT '审核意见',
  PRIMARY KEY (`source_directory_audit_id`) USING BTREE,
  KEY `index_apply_id` (`apply_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源审核记录';


-- ----------------------------
-- Table structure for source_directory_base_format
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_base_format`;
CREATE TABLE `source_directory_base_format` (
  `source_directory_base_format_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录基础ID',
  `format_type` varchar(10) NOT NULL COMMENT '格式类型：目前有xls和csv两种',
  PRIMARY KEY (`source_directory_base_format_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录基础信息数据格式';


-- ----------------------------
-- Table structure for source_directory_correct
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_correct`;
CREATE TABLE `source_directory_correct` (
  `source_directory_correct_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `correct_number` varchar(32) NOT NULL COMMENT '纠错编号',
  `correct_content` varchar(200) NOT NULL COMMENT '纠错内容',
  `correct_process_status` tinyint(4) NOT NULL COMMENT '反馈状态：1.待处理，2.已处理',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `LAST_update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人用户ID',
  `LAST_update_user_id` varchar(32) NOT NULL COMMENT '更新人用户ID',
  `correct_info` varchar(200) NOT NULL DEFAULT '' COMMENT '反馈信息',
  PRIMARY KEY (`source_directory_correct_id`) USING BTREE,
  KEY `index_source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='纠错';

-- ----------------------------
-- Table structure for source_directory_data
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_data`;
CREATE TABLE `source_directory_data` (
  `source_directory_data_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `data_name` varchar(32) NOT NULL COMMENT '字段名称',
  `data_description` varchar(128) DEFAULT NULL COMMENT '字段描述',
  PRIMARY KEY (`source_directory_data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录数据项';


-- ----------------------------
-- Table structure for source_directory_favorite
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_favorite`;
CREATE TABLE `source_directory_favorite` (
  `source_directory_favorite_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `create_user_id` varchar(32) NOT NULL COMMENT '收藏用户ID',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`source_directory_favorite_id`) USING BTREE,
  KEY `index_source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收藏';

-- ----------------------------
-- Table structure for source_directory_file
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_file`;
CREATE TABLE `source_directory_file` (
  `source_directory_file_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `file_id` varchar(64) NOT NULL COMMENT '文件上传minio返回的ID',
  `file_description` varchar(128) NOT NULL DEFAULT '' COMMENT '文件描述',
  `file_size` float NOT NULL DEFAULT '0' COMMENT '文件大小,单位KB',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  UNIQUE KEY `unq_source_directory_base_id` (`source_directory_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录文件信息表';

-- ----------------------------
-- Table structure for source_directory_interface
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_interface`;
CREATE TABLE `source_directory_interface` (
  `source_directory_interface_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) DEFAULT NULL COMMENT '资源目录ID',
  `interface_name` varchar(32) NOT NULL COMMENT '接口名称',
  `interface_address` varchar(64) NOT NULL COMMENT '接口地址',
  `interface_description` varchar(128) DEFAULT NULL COMMENT '接口描述',
  `interface_file_id` varchar(64) DEFAULT NULL COMMENT '接口说明文档上传minio后返回的ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`source_directory_interface_id`) USING BTREE,
  KEY `source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录接口信息';

-- ----------------------------
-- Table structure for source_directory_interface_format
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_interface_format`;
CREATE TABLE `source_directory_interface_format` (
  `source_directory_interface_format_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_interface_id` varchar(32) NOT NULL COMMENT '资源目录接口信息id',
  `param_format_type` tinyint(4) NOT NULL COMMENT '请求参数格式：1.JSON,2.form-data',
  PRIMARY KEY (`source_directory_interface_format_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录接口信息支持格式';

-- ----------------------------
-- Table structure for source_directory_interface_param
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_interface_param`;
CREATE TABLE `source_directory_interface_param` (
  `source_directory_interface_param_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_interface_id` varchar(32) NOT NULL COMMENT '接口ID',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `param_description` varchar(64) DEFAULT NULL COMMENT '参数描述',
  `param_type` tinyint(4) NOT NULL COMMENT '参数类型：1.入参，2.出参',
  `param_status` tinyint(4) NOT NULL COMMENT '参数是否必填：0.是，1.否',
  PRIMARY KEY (`source_directory_interface_param_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录接口信息参数';


-- ----------------------------
-- Table structure for source_directory_list
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_list`;
CREATE TABLE `source_directory_list` (
  `source_directory_list_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `directory_name` varchar(64) NOT NULL COMMENT '目录名称',
  `source_topic_id` varchar(32) NOT NULL COMMENT '主题ID',
  `source_topic_name` varchar(64) NOT NULL DEFAULT '' COMMENT '主题名称',
  `source_data_classify_id` varchar(32) NOT NULL DEFAULT '' COMMENT '主题ID',
  `source_data_classify_name` varchar(64) NOT NULL DEFAULT '' COMMENT '主题数据分类名称',
  `open_type` tinyint(4) NOT NULL COMMENT '开放类型：1.有条件开放，2.无条件开放',
  `use_way` tinyint(4) NOT NULL COMMENT '使用方式：1.接口调用，2.文件下载',
  `update_frequency` tinyint(4) NOT NULL DEFAULT '5' COMMENT '更新频率：1.每天，2.每周，3.每月，4.每年，5.未更新过',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `directory_description` varchar(128) NOT NULL DEFAULT '' COMMENT '数据描述',
  `xls_exist_status` tinyint(4) NOT NULL COMMENT '是否存在xls数据格式0是1否',
  `csv_exist_status` tinyint(4) NOT NULL COMMENT '是否存在csv数据格式0是1否',
  `publish_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '发布状态：0已发布，1未发布',
  `delete_status` tinyint(4) DEFAULT '0' COMMENT '删除状态，0-未删除，1-已删除',
  PRIMARY KEY (`source_directory_list_id`) USING BTREE,
  KEY `index_source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录列表';


-- ----------------------------
-- Table structure for source_directory_record_count
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_record_count`;
CREATE TABLE `source_directory_record_count` (
  `source_directory_record_count_id` varchar(32) NOT NULL COMMENT 'ID',
  `record_count` int(11) NOT NULL COMMENT '统计数量',
  `create_day` date NOT NULL COMMENT '统计时间',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  PRIMARY KEY (`source_directory_record_count_id`) USING BTREE,
  KEY `index_source_directory_id` (`source_directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for source_directory_search
-- ----------------------------
DROP TABLE IF EXISTS `source_directory_search`;
CREATE TABLE `source_directory_search` (
  `source_directory_search_id` varchar(32) NOT NULL COMMENT 'ID',
  `source_directory_id` varchar(32) NOT NULL COMMENT '资源目录ID',
  `directory_name` varchar(64) NOT NULL COMMENT '资源标题',
  `classify_name` varchar(64) NOT NULL DEFAULT '' COMMENT '数据分类',
  `directory_description` varchar(128) NOT NULL DEFAULT '' COMMENT '数据描述',
  `data_name` varchar(64) NOT NULL DEFAULT '' COMMENT '数据项字段名称',
  `data_descrption` varchar(128) NOT NULL DEFAULT '' COMMENT '数据项字段描述',
  `interface_name` varchar(64) NOT NULL DEFAULT '' COMMENT '接口名称',
  `request_param_name` varchar(64) NOT NULL DEFAULT '' COMMENT '接口入参字段名称',
  `request_param_descrption` varchar(128) NOT NULL DEFAULT '' COMMENT '接口入参字段描述',
  `response_param_name` varchar(64) NOT NULL DEFAULT '' COMMENT '接口出参字段名称',
  `response_param_descrption` varchar(128) NOT NULL DEFAULT '' COMMENT '接口出参字段描述',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`source_directory_search_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源目录搜索表';


-- ----------------------------
-- Table structure for source_topic
-- ----------------------------
DROP TABLE IF EXISTS `source_topic`;
CREATE TABLE `source_topic` (
  `source_topic_id` varchar(32) NOT NULL COMMENT 'ID',
  `topic_name` varchar(32) NOT NULL COMMENT '主题名称',
  `topic_description` varchar(64) DEFAULT NULL COMMENT '主题描述',
  `create_user_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `LAST_UPDATE_USER_ID` varchar(32) NOT NULL COMMENT '更新时间',
  `LAST_UPDATE_TIME` datetime NOT NULL COMMENT '上次更新人ID',
  PRIMARY KEY (`source_topic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='主题';

-- ----------------------------
-- Records of source_topic
-- ----------------------------
BEGIN;
INSERT INTO `source_topic` VALUES ('1', '法人主题', '法人主题', '1111', '2021-01-01 00:00:00', '2222', '2021-10-01 00:00:00');
INSERT INTO `source_topic` VALUES ('2', '产业主题', '产业主题', '1111', '2021-01-01 00:00:00', '2222', '2021-10-01 00:00:00');
INSERT INTO `source_topic` VALUES ('3', '公共资源', '公共资源', '1111', '2021-10-01 00:00:00', '2222', '2021-10-01 00:00:00');
INSERT INTO `source_topic` VALUES ('4', '人才资源', '人才资源', '1111', '2021-10-01 00:00:00', '2222', '2021-10-01 00:00:00');
INSERT INTO `source_topic` VALUES ('5', '创新发展', '创新发展', '1111', '2021-10-01 00:00:00', '2222', '2021-10-01 00:00:00');
COMMIT;

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
  `dict_data_parent_id` varchar(32) DEFAULT '' COMMENT '字典数据父id(多级字典时使用)',
  PRIMARY KEY (`sys_dict_data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` VALUES ('1', 1, '浦口区', '320111', 1, 1, NULL, '', '2021-11-25 14:45:43', '', '2021-11-25 14:47:22', '');
INSERT INTO `sys_dict_data` VALUES ('10', 7, '盘城街道', '320111007000', 1, 1, NULL, '', '2021-11-25 14:54:47', '', '2021-11-25 14:54:47', '1');
INSERT INTO `sys_dict_data` VALUES ('11', 8, '星甸街道', '320111008000', 1, 1, NULL, '', '2021-11-25 14:55:39', '', '2021-11-25 14:55:39', '1');
INSERT INTO `sys_dict_data` VALUES ('12', 9, '永宁街道', '320111009000', 1, 1, NULL, '', '2021-11-25 14:56:14', '', '2021-11-25 14:56:14', '1');
INSERT INTO `sys_dict_data` VALUES ('13', 1, '大厂街道', '320116001000', 1, 1, NULL, '', '2021-11-25 14:58:17', '', '2021-11-25 14:58:17', '2');
INSERT INTO `sys_dict_data` VALUES ('14', 2, '葛塘街道', '320116004000', 1, 1, NULL, '', '2021-11-25 15:01:03', '', '2021-11-25 15:01:03', '2');
INSERT INTO `sys_dict_data` VALUES ('15', 3, '长芦街道', '320116005000', 1, 1, NULL, '', '2021-11-25 15:01:54', '', '2021-11-25 15:01:54', '2');
INSERT INTO `sys_dict_data` VALUES ('16', 4, '雄州街道', '320116006000', 1, 1, NULL, '', '2021-11-25 15:02:37', '', '2021-11-25 15:02:37', '2');
INSERT INTO `sys_dict_data` VALUES ('17', 5, '龙池街道', '320116007000', 1, 1, NULL, '', '2021-11-25 15:03:09', '', '2021-11-25 15:03:09', '2');
INSERT INTO `sys_dict_data` VALUES ('18', 6, '程桥街道', '320116008000', 1, 1, NULL, '', '2021-11-25 15:03:52', '', '2021-11-25 15:03:52', '2');
INSERT INTO `sys_dict_data` VALUES ('19', 7, '金牛湖街道', '320116009000', 1, 1, NULL, '', '2021-11-25 15:04:23', '', '2021-11-25 15:04:23', '2');
INSERT INTO `sys_dict_data` VALUES ('2', 2, '六合区', '320116', 1, 1, NULL, '', '2021-11-25 14:46:45', '', '2021-11-25 14:47:22', '');
INSERT INTO `sys_dict_data` VALUES ('20', 8, '横梁街道', '320116010000', 1, 1, NULL, '', '2021-11-25 15:04:58', '', '2021-11-25 15:04:58', '2');
INSERT INTO `sys_dict_data` VALUES ('21', 9, '龙袍街道', '320116011000', 1, 1, NULL, '', '2021-11-25 15:05:32', '', '2021-11-25 15:05:32', '2');
INSERT INTO `sys_dict_data` VALUES ('22', 10, '马鞍街道', '320116012000', 1, 1, NULL, '', '2021-11-25 15:06:06', '', '2021-11-25 15:06:06', '2');
INSERT INTO `sys_dict_data` VALUES ('23', 11, '冶山镇', '320116101000', 1, 1, NULL, '', '2021-11-25 15:06:54', '', '2021-11-25 15:06:54', '2');
INSERT INTO `sys_dict_data` VALUES ('24', 12, '竹镇镇', '320116110000', 1, 1, NULL, '', '2021-11-25 15:08:04', '', '2021-11-25 15:08:04', '2');
INSERT INTO `sys_dict_data` VALUES ('25', 1, '八卦洲街道', '320113009000', 1, 1, NULL, '', '2021-11-25 15:11:42', '', '2021-11-25 15:11:42', '3');
INSERT INTO `sys_dict_data` VALUES ('3', 3, '栖霞区', '320113', 1, 1, NULL, '', '2021-11-25 14:48:11', '', '2021-11-25 14:48:11', '');
INSERT INTO `sys_dict_data` VALUES ('4', 1, '泰山街道', '320111001000', 1, 1, NULL, '', '2021-11-25 14:50:14', '', '2021-11-25 14:50:14', '1');
INSERT INTO `sys_dict_data` VALUES ('5', 2, '顶山街道', '320111002000', 1, 1, NULL, '', '2021-11-25 14:51:37', '', '2021-11-25 14:51:37', '1');
INSERT INTO `sys_dict_data` VALUES ('6', 3, '沿江街道', '320111003000', 1, 1, NULL, '', '2021-11-25 14:52:27', '', '2021-11-25 14:52:27', '1');
INSERT INTO `sys_dict_data` VALUES ('7', 4, '江浦街道', '320111004000', 1, 1, NULL, '', '2021-11-25 14:53:02', '', '2021-11-25 14:53:02', '1');
INSERT INTO `sys_dict_data` VALUES ('8', 5, '桥林街道', '320111005000', 1, 1, NULL, '', '2021-11-25 14:53:31', '', '2021-11-25 14:53:31', '1');
INSERT INTO `sys_dict_data` VALUES ('9', 6, '汤泉街道', '320111006000', 1, 1, NULL, '', '2021-11-25 14:54:13', '', '2021-11-25 14:54:13', '1');
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
INSERT INTO `sys_dict_type` VALUES ('1', '江北新区行政区划', '', 1, '本行政区划仅供江北新区使用', '-1', '2021-11-25 14:42:55', '-1', '2021-11-25 14:43:19');
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
INSERT INTO `sys_org` VALUES ('909819630892089344', '南京金宁汇科技有限公司', '2020219827656789', 0, NULL, '22222', 3, '15601691000', 1, '2021-11-25 19:28:26', NULL, NULL, NULL, NULL, '2021-11-25 11:28:26', NULL, NULL, NULL, NULL, NULL, NULL, '0212', 2, '南京市江北新区孵鹰大厦B座22-01', '金宁汇科技', '管理员', '管理员');
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
INSERT INTO `sys_resource` VALUES ('910464899388276736', '删除', 1, 'L-005', '910462683696857088', 1, '/api/sys/org/delete/*', 1, 3, NULL, NULL, '2021-11-23 22:37:40', NULL, NULL, NULL, NULL, '2021-11-23 22:37:40', NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO `sys_resource` VALUES ('910634765835567104', '提交申请', 1, 'J-034', '911896056462049280', 1, '/*/backend/apply/source/directory/applySource', 1, NULL, NULL, NULL, '2021-11-22 20:25:23', NULL, NULL, NULL, NULL, '2021-11-22 20:25:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910634890096017408', '申请详情', 1, 'J-035', '911896056462049280', 1, '/*/backend/apply/source/directory/detail', 1, NULL, NULL, NULL, '2021-11-22 20:25:28', NULL, NULL, NULL, NULL, '2021-11-22 20:25:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910634964389724160', '申请列表', 1, 'J-036', '911896056462049280', 1, '/*/backend/apply/source/directory/list', 1, NULL, NULL, NULL, '2021-11-22 20:25:32', NULL, NULL, NULL, NULL, '2021-11-22 20:25:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635019125391360', '个人收藏', 1, 'J-037', '911896056462049280', 1, '/*/backend/source/directory/favorite', 1, NULL, NULL, NULL, '2021-11-22 20:25:35', NULL, NULL, NULL, NULL, '2021-11-22 20:25:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635079074578432', '是否已收藏', 1, 'J-038', '911896056462049280', 1, '/*/backend/source/directory/favorite/checkFavorite', 1, NULL, NULL, NULL, '2021-11-22 20:25:39', NULL, NULL, NULL, NULL, '2021-11-22 20:25:39', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635134728798208', '收藏列表', 1, 'J-039', '911896056462049280', 1, '/*/backend/source/directory/favorite/listFavorite', 1, NULL, NULL, NULL, '2021-11-22 20:25:43', NULL, NULL, NULL, NULL, '2021-11-22 20:25:43', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635186905939968', '纠错提交', 1, 'J-040', '911896056462049280', 1, '/*/backend/source/directory/correct/correctSubmit', 1, NULL, NULL, NULL, '2021-11-22 20:25:47', NULL, NULL, NULL, NULL, '2021-11-22 20:25:47', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635239120830464', '纠错详情', 1, 'J-041', '911896056462049280', 1, '/*/backend/source/directory/correct/detailCorrect', 1, NULL, NULL, NULL, '2021-11-22 20:25:51', NULL, NULL, NULL, NULL, '2021-11-22 20:25:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635384881283072', '接口调用', 1, 'J-043', '911896056462049280', 1, '/*/backend/source/directory/interface/sourceDirectoryInterfaceInfo', 1, NULL, NULL, NULL, '2021-11-22 20:25:59', NULL, NULL, NULL, NULL, '2021-11-22 20:25:59', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635437737902080', '文件查看', 1, 'J-044', '912390564912037888', 1, '/*/backend/source/directory/file', 1, NULL, NULL, NULL, '2021-11-22 20:26:03', NULL, NULL, NULL, NULL, '2021-11-22 20:26:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635485179674624', '删除', 1, 'J-045', '912390564912037888', 1, '/*/backend/source/directory/file/deleteSourceFile', 1, NULL, NULL, NULL, '2021-11-22 20:26:08', NULL, NULL, NULL, NULL, '2021-11-22 20:26:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635543463723008', '下载', 1, 'J-046', '912390564912037888', 1, '/*/backend/source/directory/file/download', 1, NULL, NULL, NULL, '2021-11-22 20:26:11', NULL, NULL, NULL, NULL, '2021-11-22 20:26:11', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635599415738368', '文件列表', 1, 'J-047', '911903886820769792', 1, '/*/backend/source/directory/file/manageQueryDirectoryFile', 1, NULL, NULL, NULL, '2021-11-22 20:26:15', NULL, NULL, NULL, NULL, '2021-11-22 20:26:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635708312453120', '资源文件上传/替换', 1, 'J-049', '911896056462049280', 1, '/*/backend/source/directory/file/uploadSourceFile', 1, NULL, NULL, NULL, '2021-11-22 20:26:19', NULL, NULL, NULL, NULL, '2021-11-22 20:26:19', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635761492033536', '新增', 1, 'J-050', '912394775959699456', 1, '/*/backend/source/directory/add', 1, NULL, NULL, NULL, '2021-11-22 20:26:25', NULL, NULL, NULL, NULL, '2021-11-22 20:26:25', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635818270326784', '删除', 1, 'J-051', '912394775959699456', 1, '/*/backend/source/directory/deleteSourceDirectory', 1, NULL, NULL, NULL, '2021-11-22 20:26:29', NULL, NULL, NULL, NULL, '2021-11-22 20:26:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635872024526848', '江北链系统', 1, 'J-052', '911896056462049280', 1, '/*/backend/source/directory/downloadSourceDirectory', 1, NULL, NULL, NULL, '2021-11-22 20:26:32', NULL, NULL, NULL, NULL, '2021-11-22 20:26:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910635947958206464', '更新', 1, 'J-053', '912394775959699456', 1, '/*/backend/source/directory/edit', 1, NULL, NULL, NULL, '2021-11-22 20:26:35', NULL, NULL, NULL, NULL, '2021-11-22 20:26:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('910636014916075520', '资源管理列表', 1, 'J-054', '911279075891347456', 1, '/*/backend/source/directory/managerSourceList', 1, NULL, NULL, NULL, '2021-11-22 20:26:38', NULL, NULL, NULL, NULL, '2021-11-22 20:26:38', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911220088584011776', '删除', 1, 'L-034', '910471840462274560', 1, '/api/sys/user/internal/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:34:36', NULL, NULL, NULL, NULL, '2021-11-22 13:34:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911220506210861056', '批量删除', 1, 'L-035', '910471840462274560', 1, '/api/sys/user/internal/delete/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911264547786981376', '调整状态', 1, 'L-036', '910471840462274560', 1, '/api/sys/user/internal/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911265318825885696', '批量调整状态', 1, 'L-037', '910471840462274560', 1, '/api/sys/user/internal/update-status/batch', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911266821833752576', '角色管理', 0, 'L-038', '910458825671180288', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, '2021-11-21 08:22:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911266991778562048', '创建', 1, 'L-039', '911266821833752576', 1, '/api/sys/role/create', 1, 3, NULL, NULL, '2021-11-22 13:37:43', NULL, NULL, NULL, NULL, '2021-11-22 13:37:43', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911272927503056896', '删除', 1, 'L-040', '911266821833752576', 1, '/api/sys/role/delete/*', 1, 3, NULL, NULL, '2021-11-22 13:34:40', NULL, NULL, NULL, NULL, '2021-11-22 13:34:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273050970783744', '查看', 1, 'L-041', '911266821833752576', 1, '/api/sys/role/get/*', 1, 3, NULL, NULL, '2021-11-22 13:34:44', NULL, NULL, NULL, NULL, '2021-11-22 13:34:44', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273192671150080', '精简信息列表', 1, 'L-042', '911266821833752576', 1, '/api/sys/role/list-simple', 1, 3, NULL, NULL, '2021-11-23 09:58:35', NULL, NULL, NULL, NULL, '2021-11-23 09:58:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273277576445952', '分页查询', 1, 'L-043', '911266821833752576', 1, '/api/sys/role/page', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273420799344640', '更新', 1, 'L-044', '911266821833752576', 1, '/api/sys/role/update', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273539665920000', '调整状态', 1, 'L-045', '911266821833752576', 1, '/api/sys/role/update-status', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273753030164480', '相关用户查询', 1, 'L-046', '911266821833752576', 1, '/api/sys/role/user/page', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273861541003264', '添加用户', 1, 'L-047', '911266821833752576', 1, '/api/sys/role/allocate/user', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911273965257752576', '授权', 1, 'L-048', '911266821833752576', 1, '/api/sys/role/auth', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911274190005338112', '移除人员', 1, 'L-049', '911266821833752576', 1, '/api/sys/role/remove/user', 1, 3, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, '2021-11-21 08:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911279075891347456', '开放目录', 0, 'L-050', NULL, 1, '/*/backend/source/directory/managerSourceList', 1, 3, NULL, NULL, '2021-11-23 09:40:34', NULL, NULL, NULL, NULL, '2021-11-23 09:40:34', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911742977389363200', '账号信息', 0, 'L-052', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:45:18', NULL, NULL, NULL, NULL, '2021-11-21 08:45:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911743107379232768', '个人资料', 0, 'L-053', '911742977389363200', 1, '', 1, 3, NULL, NULL, '2021-11-24 16:44:21', NULL, NULL, NULL, NULL, '2021-11-24 16:44:21', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911743189088468992', '密码修改', 1, 'L-054', '911742977389363200', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857187565862912', '数据申请', 1, 'L-055', '911899907676176384', 1, '/*/backend/apply/source/directory/list', 1, 3, NULL, NULL, '2021-11-23 09:41:26', NULL, NULL, NULL, NULL, '2021-11-23 09:41:26', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857289659416576', '互动管理', 0, 'L-056', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:45:35', NULL, NULL, NULL, NULL, '2021-11-21 08:45:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857398677766144', '纠错管理', 0, 'L-057', '911857289659416576', 1, '', 1, 3, NULL, NULL, '2021-11-24 15:26:54', NULL, NULL, NULL, NULL, '2021-11-24 15:26:54', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911857452847202304', '建议跟踪', 1, 'L-058', '911857289659416576', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, '2021-11-21 08:22:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911896056462049280', '子应用', 0, 'J-001', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:31:49', NULL, NULL, NULL, NULL, '2021-11-21 08:31:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911899907676176384', '个人中心', 0, 'L-059', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 08:47:40', NULL, NULL, NULL, NULL, '2021-11-21 08:47:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911901584017850368', '收藏管理', 0, 'L-061', '911899907676176384', 1, '', 1, 3, NULL, NULL, '2021-11-21 08:52:46', NULL, NULL, NULL, NULL, '2021-11-21 08:52:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902013506191360', '个人收藏', 1, 'L-062', '911901584017850368', 1, '/*/backend/source/directory/favorite', 1, 3, NULL, NULL, '2021-11-23 09:40:41', NULL, NULL, NULL, NULL, '2021-11-23 09:40:41', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902247925841920', '检查是否收藏', 1, 'L-063', '911901584017850368', 1, '/*/backend/source/directory/favorite/checkFavorite', 1, 3, NULL, NULL, '2021-11-23 09:40:45', NULL, NULL, NULL, NULL, '2021-11-23 09:40:45', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902588771762176', '收藏列表', 1, 'L-064', '911901584017850368', 1, '/*/backend/source/directory/favorite/listFavorite', 1, 3, NULL, NULL, '2021-11-23 09:40:49', NULL, NULL, NULL, NULL, '2021-11-23 09:40:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911902914602074112', '纠错提交', 1, 'L-065', '911857398677766144', 1, '/*/backend/source/directory/correct/correctSubmit', 1, 3, NULL, NULL, '2021-11-23 09:40:54', NULL, NULL, NULL, NULL, '2021-11-23 09:40:54', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903017899393024', '详情', 1, 'L-066', '911857398677766144', 1, '/*/backend/source/directory/correct/detailCorrect', 1, 3, NULL, NULL, '2021-11-23 09:40:58', NULL, NULL, NULL, NULL, '2021-11-23 09:40:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903187856785408', '纠错列表', 1, 'L-067', '911857398677766144', 1, '/*/backend/source/directory/correct/listCorrect', 1, 3, NULL, NULL, '2021-11-23 09:41:03', NULL, NULL, NULL, NULL, '2021-11-23 09:41:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('911903886820769792', '资源管理', 0, 'L-068', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-21 01:00:29', NULL, NULL, NULL, NULL, '2021-11-21 01:00:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912352470926098432', '系统资源管理', 0, 'L-069', '910458825671180288', 1, '', 1, 3, NULL, NULL, '2021-11-22 06:43:00', NULL, NULL, NULL, NULL, '2021-11-22 06:43:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912352776577613824', '创建', 1, 'L-070', '912352470926098432', 1, '/api/sys/resource/create', 1, 3, NULL, NULL, '2021-11-22 06:44:13', NULL, NULL, NULL, NULL, '2021-11-22 06:44:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912352953019400192', '删除', 1, 'L-071', '912352470926098432', 1, '/api/sys/resource/delete/*', 1, 3, NULL, NULL, '2021-11-22 06:44:55', NULL, NULL, NULL, NULL, '2021-11-22 06:44:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912353073945378816', '查看', 1, 'L-072', '912352470926098432', 1, '/api/sys/resource/get/*', 1, 3, NULL, NULL, '2021-11-22 06:45:24', NULL, NULL, NULL, NULL, '2021-11-22 06:45:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912353178446462976', '菜单列表', 1, 'L-073', '912352470926098432', 1, '/api/sys/resource/list', 1, 3, NULL, NULL, '2021-11-22 06:45:48', NULL, NULL, NULL, NULL, '2021-11-22 06:45:48', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912353314513879040', '精简菜单查询', 1, 'L-074', '912352470926098432', 1, '/api/sys/resource/list-all-simple', 1, 3, NULL, NULL, '2021-11-22 06:46:21', NULL, NULL, NULL, NULL, '2021-11-22 06:46:21', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912353428720582656', '树形查询', 1, 'L-075', '912352470926098432', 1, '/api/sys/resource/tree-sync', 1, 3, NULL, NULL, '2021-11-22 06:46:48', NULL, NULL, NULL, NULL, '2021-11-22 06:46:48', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912353513227419648', '更新', 1, 'L-076', '912352470926098432', 1, '/api/sys/resource/update', 1, 3, NULL, NULL, '2021-11-22 06:47:08', NULL, NULL, NULL, NULL, '2021-11-22 06:47:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912359072118865920', '申请管理', 0, 'L-077', NULL, 1, '/*/backend/apply/source/directory/list', 1, 3, NULL, NULL, '2021-11-23 09:41:08', NULL, NULL, NULL, NULL, '2021-11-23 09:41:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912390564912037888', '文件信息', 0, 'L-078', '911896056462049280', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:14:22', NULL, NULL, NULL, NULL, '2021-11-22 09:14:22', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912394775959699456', '资源目录', 0, 'L-079', '911903886820769792', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:31:06', NULL, NULL, NULL, NULL, '2021-11-22 09:31:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912395983000698880', '新增', 1, 'L-080', '912359072118865920', 1, '/*/backend/source/directory/audit/add', 1, 3, NULL, NULL, '2021-11-23 09:41:12', NULL, NULL, NULL, NULL, '2021-11-23 09:41:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912396430100922368', '详情', 1, 'L-081', '912359072118865920', 1, '/*/backend/source/directory/audit/get', 1, 3, NULL, NULL, '2021-11-23 09:41:16', NULL, NULL, NULL, NULL, '2021-11-23 09:41:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912396527610101760', '列表', 1, 'L-082', '912359072118865920', 1, '/*/backend/source/directory/audit/list', 1, 3, NULL, NULL, '2021-11-23 09:40:17', NULL, NULL, NULL, NULL, '2021-11-23 09:40:17', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397258840866816', '标签管理', 1, 'L-083', '911903886820769792', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:40:58', NULL, NULL, NULL, NULL, '2021-11-22 09:40:58', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397317363990528', '企业管理', 1, 'L-084', '911903886820769792', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:41:12', NULL, NULL, NULL, NULL, '2021-11-22 09:41:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397522863915008', '质检管理', 0, 'L-085', NULL, 1, '', 1, 3, NULL, NULL, '2021-11-22 09:42:01', NULL, NULL, NULL, NULL, '2021-11-22 09:42:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397641491415040', '质检首页', 1, 'L-086', '912397522863915008', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:42:29', NULL, NULL, NULL, NULL, '2021-11-22 09:42:29', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397738614718464', '规则分类', 0, 'L-087', '912397522863915008', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:42:52', NULL, NULL, NULL, NULL, '2021-11-22 09:42:52', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912397843350683648', '质量规则', 1, 'L-088', '912397738614718464', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:43:17', NULL, NULL, NULL, NULL, '2021-11-22 09:43:17', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912398209035272192', '规则检测', 0, 'L-090', '912397522863915008', 1, '', 1, 3, NULL, NULL, '2021-11-22 17:45:50', NULL, NULL, NULL, NULL, '2021-11-22 17:45:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912398420486914048', '质检总览', 1, 'L-091', '912398209035272192', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:45:35', NULL, NULL, NULL, NULL, '2021-11-22 09:45:35', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912398632496398336', '质检任务', 1, 'L-092', '912398209035272192', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:46:26', NULL, NULL, NULL, NULL, '2021-11-22 09:46:26', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912398937288081408', '质检结果', 0, 'L-093', '912397522863915008', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:47:38', NULL, NULL, NULL, NULL, '2021-11-22 09:47:38', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912399085699334144', '综合分析', 1, 'L-094', '912398937288081408', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:48:14', NULL, NULL, NULL, NULL, '2021-11-22 09:48:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912399167299518464', '质检结果', 1, 'L-095', '912398937288081408', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:48:33', NULL, NULL, NULL, NULL, '2021-11-22 09:48:33', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912399254104834048', '多维度分析', 1, 'L-096', '912398937288081408', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:48:54', NULL, NULL, NULL, NULL, '2021-11-22 09:48:54', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912399485944987648', '数据修改', 1, 'L-097', '912397522863915008', 1, '', 1, 3, NULL, NULL, '2021-11-22 09:49:49', NULL, NULL, NULL, NULL, '2021-11-22 09:49:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912442378210508800', '用户登出', 1, 'L-098', '910466624694255616', 1, '/api/sys/user/logout', 1, 3, NULL, NULL, '2021-11-22 20:41:19', NULL, NULL, NULL, NULL, '2021-11-22 20:41:19', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912641442361376768', '数据资源', 0, 'T-001', NULL, 1, '', 1, 1, NULL, NULL, '2021-11-24 20:13:09', NULL, NULL, NULL, NULL, '2021-11-24 20:13:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912641928661565440', '开放统计', 1, 'T-002', NULL, 1, '', 1, 1, NULL, NULL, '2021-11-23 01:53:12', NULL, NULL, NULL, NULL, '2021-11-23 01:53:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912652480297304064', '分页查询', 1, 'L-100', '910462683696857088', 1, '/api/sys/org/page', 1, 1, NULL, NULL, '2021-11-23 02:35:08', NULL, NULL, NULL, NULL, '2021-11-23 02:35:08', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912724682250125312', '查询待添加用户', 1, 'L-101', '911266821833752576', 1, '/api/sys/role/remaining/user-page', 1, 3, NULL, NULL, '2021-11-23 15:29:18', NULL, NULL, NULL, NULL, '2021-11-23 15:29:18', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912725459035226112', '通用接口', 0, 'L-102', '910466624694255616', 1, '', 1, 3, NULL, NULL, '2021-11-23 15:25:47', NULL, NULL, NULL, NULL, '2021-11-23 15:25:47', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912725769891872768', '外部系统获取token', 1, 'L-103', '912725459035226112', 1, '/api/sys/user/generate/token', 1, 3, NULL, NULL, '2021-11-23 07:26:21', NULL, NULL, NULL, NULL, '2021-11-23 07:26:21', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912731620606214144', '修改资源目录接口', 1, 'L-104', '911279075891347456', 1, '/*/backend/source/directory/interface/addInterface ', 1, 3, NULL, NULL, '2021-11-24 15:14:41', NULL, NULL, NULL, NULL, '2021-11-24 15:14:41', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912731806216749056', '处理接口文档', 1, 'L-105', '911279075891347456', 1, '/*/backend/source/directory/interface/handleInterfaceWord', 1, 3, NULL, NULL, '2021-11-24 15:14:46', NULL, NULL, NULL, NULL, '2021-11-24 15:14:46', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912731951763292160', '添加/修改接口参数', 1, 'L-106', '911279075891347456', 1, '/*/backend/source/directory/interface/param/addInterfaceParam', 1, 3, NULL, NULL, '2021-11-24 15:14:49', NULL, NULL, NULL, NULL, '2021-11-24 15:14:49', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912732055501012992', '删除接口参数', 1, 'L-107', '911279075891347456', 1, '/*/backend/source/directory/interface/param/deleteInterfaceParam', 1, 3, NULL, NULL, '2021-11-24 15:14:53', NULL, NULL, NULL, NULL, '2021-11-24 15:14:53', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912732147138166784', '导出接口参数', 1, 'L-108', '911279075891347456', 1, '/*/backend/source/directory/interface/param/exportInterfaceParam', 1, 3, NULL, NULL, '2021-11-24 15:14:56', NULL, NULL, NULL, NULL, '2021-11-24 15:14:56', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912732277639741440', '导入接口参数', 1, 'L-109', '911279075891347456', 1, '/*/backend/source/directory/interface/param/importInterfaceParam', 1, 3, NULL, NULL, '2021-11-24 15:15:00', NULL, NULL, NULL, NULL, '2021-11-24 15:15:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912733134674460672', '发布状态', 1, 'L-110', '911279075891347456', 1, '/*/backend/source/directory/publishSource', 1, 3, NULL, NULL, '2021-11-24 15:15:03', NULL, NULL, NULL, NULL, '2021-11-24 15:15:03', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912734023258734592', '开放目录详情', 1, 'L-111', '911279075891347456', 1, '/*/backend/source/directory/manageSourceDirectoryDetail', 1, 3, NULL, NULL, '2021-11-24 15:15:07', NULL, NULL, NULL, NULL, '2021-11-24 15:15:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('912749928667676672', '组织机构精简信息', 1, 'L-112', '910462683696857088', 1, '/api/sys/org/list-simple', 1, 3, NULL, NULL, '2021-11-23 17:06:31', NULL, NULL, NULL, NULL, '2021-11-23 17:06:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913006331571470336', '用户-角色分页查询', 1, 'L-113', '912725459035226112', 1, '/api/sys/user-role-ref/page', 1, 3, NULL, NULL, '2021-11-24 02:01:12', NULL, NULL, NULL, NULL, '2021-11-24 02:01:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913006529827831808', '用户-机构分页查询', 1, 'L-114', '912725459035226112', 1, '/api/sys/user-org-ref/page', 1, 3, NULL, NULL, '2021-11-24 02:02:00', NULL, NULL, NULL, NULL, '2021-11-24 02:02:00', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913023952786292736', '查看个人资料', 1, 'L-115', '911743107379232768', 1, '/api/sys/user/info', 1, 3, NULL, NULL, '2021-11-24 16:45:01', NULL, NULL, NULL, NULL, '2021-11-24 16:45:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913071645386604544', '待审核总数', 1, 'L-116', '912359072118865920', 1, '/*/backend/source/directory/audit/noAuditCount', 1, 3, NULL, NULL, '2021-11-24 15:05:15', NULL, NULL, NULL, NULL, '2021-11-24 15:05:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913074483038781440', '用户更改密码', 1, 'L-117', '911743107379232768', 1, '/api/sys/user/password/update', 1, 3, NULL, NULL, '2021-11-24 16:45:36', NULL, NULL, NULL, NULL, '2021-11-24 16:45:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913084362386309120', '文件管理', 0, 'L-118', '911896056462049280', 1, '', 1, 3, NULL, NULL, '2021-11-24 07:11:16', NULL, NULL, NULL, NULL, '2021-11-24 07:11:16', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913084526467481600', '文件下载', 1, 'L-119', '913084362386309120', 1, '/*/backend/file/download ', 1, 3, NULL, NULL, '2021-11-24 07:11:55', NULL, NULL, NULL, NULL, '2021-11-24 07:11:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913109237037531136', '更换手机号', 0, 'L-120', '911743107379232768', 1, '', 1, 3, NULL, NULL, '2021-11-24 08:50:07', NULL, NULL, NULL, NULL, '2021-11-24 08:50:07', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913109432995414016', '用户名密码校验', 1, 'L-121', '913109237037531136', 1, '/api/sys/user/validate', 1, 3, NULL, NULL, '2021-11-24 08:50:54', NULL, NULL, NULL, NULL, '2021-11-24 08:50:54', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913109583705145344', '手机号校验', 1, 'L-122', '913109237037531136', 1, '/api/sys/user/validate/phone', 1, 3, NULL, NULL, '2021-11-24 08:51:30', NULL, NULL, NULL, NULL, '2021-11-24 08:51:30', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913109765775687680', '个人信息变更', 1, 'L-123', '911743107379232768', 1, '/api/sys/user/info/update', 1, 3, NULL, NULL, '2021-11-24 16:53:23', NULL, NULL, NULL, NULL, '2021-11-24 16:53:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913159960412553216', '接口调用', 0, 'L-124', '912641442361376768', 1, '', 1, 3, NULL, NULL, '2021-11-24 12:11:40', NULL, NULL, NULL, NULL, '2021-11-24 12:11:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES ('913160168148041728', '接口文档下载', 1, 'L-125', '913159960412553216', 1, '/*/backend/source/directory/interface/downloadInterfaceWord', 1, 3, NULL, NULL, '2021-11-24 12:12:30', NULL, NULL, NULL, NULL, '2021-11-24 12:12:30', NULL, NULL, NULL, NULL, NULL);
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
  `creator_depart_name` varchar(64) DEFAULT NULL COMMENT '创建人部门名称',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updater_name` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `updater_depart_id` varchar(32) DEFAULT NULL COMMENT '更新人部门id',
  `updater_depart_name` varchar(64) DEFAULT NULL COMMENT '更新人部门名称',
  `parent_role_id` varchar(32) DEFAULT NULL COMMENT '上级角色id',
  `category` int(1) DEFAULT NULL COMMENT '角色类别',
  PRIMARY KEY (`sys_role_id`) USING BTREE,
  UNIQUE KEY `index_sys_role_id` (`sys_role_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('910473676262998016', 'EX_USER_ROLE', 'ex-001', NULL, NULL, 1, 1, '2021-11-17 10:53:05', NULL, NULL, NULL, NULL, '2021-11-22 14:45:40', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('910473676262998019', 'ADMIN', 'in-001', '超级管理员', NULL, 1, 2, '2021-11-17 20:26:06', NULL, NULL, NULL, NULL, '2021-11-25 16:02:15', '', NULL, NULL, NULL, NULL, 2);
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
INSERT INTO `sys_role_resource_ref` VALUES ('912372506386497546', '910473676262998016', '910635658588979200', NULL, '2021-11-22 08:02:37', NULL, '2021-11-22 08:02:37');
INSERT INTO `sys_role_resource_ref` VALUES ('912372559637381120', '910473676262998016', '911900316755034112', NULL, '2021-11-22 08:02:49', NULL, '2021-11-22 08:02:49');
INSERT INTO `sys_role_resource_ref` VALUES ('912710750114414592', '910473676262998016', '911742977389363200', NULL, '2021-11-23 06:26:40', NULL, '2021-11-23 06:26:40');
INSERT INTO `sys_role_resource_ref` VALUES ('912710750114414593', '910473676262998016', '911743107379232768', NULL, '2021-11-23 06:26:40', NULL, '2021-11-23 06:26:40');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605824', '910473676262998016', '912397258840866816', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605825', '910473676262998016', '911857289659416576', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605826', '910473676262998016', '910635818270326784', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605827', '910473676262998016', '911743189088468992', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605828', '910473676262998016', '912394775959699456', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605829', '910473676262998016', '911902247925841920', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605830', '910473676262998016', '912397317363990528', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605831', '910473676262998016', '911857398677766144', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605832', '910473676262998016', '910635761492033536', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605833', '910473676262998016', '911857187565862912', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605834', '910473676262998016', '911857452847202304', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916310605835', '910473676262998016', '911901584017850368', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800128', '910473676262998016', '910635599415738368', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800129', '910473676262998016', '911902588771762176', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800130', '910473676262998016', '911903886820769792', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800131', '910473676262998016', '910635947958206464', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800132', '910473676262998016', '911902013506191360', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912732916314800133', '910473676262998016', '911899907676176384', NULL, '2021-11-23 07:54:45', NULL, '2021-11-23 07:54:45');
INSERT INTO `sys_role_resource_ref` VALUES ('912734165026209803', '910473676262998016', '910635303901855744', NULL, '2021-11-23 07:59:43', NULL, '2021-11-23 07:59:43');

INSERT INTO `sys_role_resource_ref` VALUES ('913093700324687872', '910473676262998016', '911903187856785408', NULL, '2021-11-24 07:48:23', NULL, '2021-11-24 07:48:23');
INSERT INTO `sys_role_resource_ref` VALUES ('913093700324687873', '910473676262998016', '911903017899393024', NULL, '2021-11-24 07:48:23', NULL, '2021-11-24 07:48:23');
INSERT INTO `sys_role_resource_ref` VALUES ('913093700324687874', '910473676262998016', '911902914602074112', NULL, '2021-11-24 07:48:23', NULL, '2021-11-24 07:48:23');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491634626560', '910473676262998016', '913074483038781440', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491638820864', '910473676262998016', '913109432995414016', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491638820865', '910473676262998016', '913109583705145344', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491638820866', '910473676262998016', '913109237037531136', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491638820867', '910473676262998016', '913109765775687680', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913151491638820868', '910473676262998016', '913023952786292736', NULL, '2021-11-24 11:38:01', NULL, '2021-11-24 11:38:01');
INSERT INTO `sys_role_resource_ref` VALUES ('913164060642508800', '910473676262998016', '913159960412553216', NULL, '2021-11-24 12:27:58', NULL, '2021-11-24 12:27:58');
INSERT INTO `sys_role_resource_ref` VALUES ('913164060642508801', '910473676262998016', '913160168148041728', NULL, '2021-11-24 12:27:58', NULL, '2021-11-24 12:27:58');
INSERT INTO `sys_role_resource_ref` VALUES ('913164060642508802', '910473676262998016', '912641442361376768', NULL, '2021-11-24 12:27:58', NULL, '2021-11-24 12:27:58');

INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117888', '910473676262998016', '910635134728798208', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117889', '910473676262998016', '913084362386309120', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117890', '910473676262998016', '910635019125391360', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117891', '910473676262998016', '911896056462049280', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117892', '910473676262998016', '910635485179674624', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117893', '910473676262998016', '910635872024526848', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117894', '910473676262998016', '910635186905939968', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117895', '910473676262998016', '910635708312453120', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117896', '910473676262998016', '910634765835567104', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117897', '910473676262998016', '912390564912037888', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117898', '910473676262998016', '910634964389724160', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117899', '910473676262998016', '910635384881283072', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117900', '910473676262998016', '910635437737902080', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117901', '910473676262998016', '910635239120830464', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117902', '910473676262998016', '910634890096017408', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117903', '910473676262998016', '910635543463723008', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117904', '910473676262998016', '913084526467481600', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');
INSERT INTO `sys_role_resource_ref` VALUES ('913828574174117905', '910473676262998016', '910635079074578432', NULL, '2021-11-26 08:28:30', NULL, '2021-11-26 08:28:30');


INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482817', '910473676262998019', '911272927503056896', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482818', '910473676262998019', '912353428720582656', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482822', '910473676262998019', '912353314513879040', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482827', '910473676262998019', '911274190005338112', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482830', '910473676262998019', '912352776577613824', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482832', '910473676262998019', '912353178446462976', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482833', '910473676262998019', '911266991778562048', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482834', '910473676262998019', '912353513227419648', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482838', '910473676262998019', '911266821833752576', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482841', '910473676262998019', '912352470926098432', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482842', '910473676262998019', '912353073945378816', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482843', '910473676262998019', '910458825671180288', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482848', '910473676262998019', '911273050970783744', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482849', '910473676262998019', '911273539665920000', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482851', '910473676262998019', '912352953019400192', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946504482864', '910473676262998019', '911273861541003264', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946508677120', '910473676262998019', '911273965257752576', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946508677123', '910473676262998019', '911273277576445952', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946508677124', '910473676262998019', '911273420799344640', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912444946508677130', '910473676262998019', '911273753030164480', NULL, '2021-11-22 12:50:28', NULL, '2021-11-22 12:50:28');
INSERT INTO `sys_role_resource_ref` VALUES ('912745654785146880', '910473676262998019', '912724682250125312', NULL, '2021-11-23 08:45:22', NULL, '2021-11-23 08:45:22');
INSERT INTO `sys_role_resource_ref` VALUES ('912745727497601024', '910473676262998019', '911273192671150080', NULL, '2021-11-23 08:45:39', NULL, '2021-11-23 08:45:39');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077730373636', '910473676262998019', '910465301781413888', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077730373638', '910473676262998019', '910464289360314368', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077730373639', '910473676262998019', '910462683696857088', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077730373643', '910473676262998019', '910463396514627584', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077734567936', '910473676262998019', '912652480297304064', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077734567941', '910473676262998019', '910465124161028096', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077734567944', '910473676262998019', '910465428852047872', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912746077734567953', '910473676262998019', '910464899388276736', NULL, '2021-11-23 08:47:03', NULL, '2021-11-23 08:47:03');
INSERT INTO `sys_role_resource_ref` VALUES ('912752791703781376', '910473676262998019', '912749928667676672', NULL, '2021-11-23 09:13:44', NULL, '2021-11-23 09:13:44');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361664', '910473676262998019', '912731806216749056', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361665', '910473676262998019', '910636014916075520', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361666', '910473676262998019', '912732147138166784', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361667', '910473676262998019', '912733134674460672', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361668', '910473676262998019', '912732055501012992', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361669', '910473676262998019', '912734023258734592', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361670', '910473676262998019', '911279075891347456', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361671', '910473676262998019', '912731951763292160', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361672', '910473676262998019', '912732277639741440', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('912756957893361673', '910473676262998019', '912731620606214144', NULL, '2021-11-23 09:30:17', NULL, '2021-11-23 09:30:17');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066112', '910473676262998019', '913071645386604544', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066113', '910473676262998019', '912397258840866816', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066114', '910473676262998019', '910635818270326784', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066115', '910473676262998019', '912359072118865920', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066116', '910473676262998019', '912399085699334144', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066117', '910473676262998019', '912398937288081408', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066118', '910473676262998019', '910635761492033536', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066119', '910473676262998019', '912641928661565440', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066120', '910473676262998019', '910635599415738368', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066121', '910473676262998019', '910635947958206464', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066122', '910473676262998019', '912398420486914048', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066123', '910473676262998019', '912397641491415040', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066124', '910473676262998019', '912641442361376768', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066125', '910473676262998019', '912399485944987648', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066126', '910473676262998019', '912398632496398336', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066127', '910473676262998019', '912399167299518464', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066128', '910473676262998019', '912396430100922368', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066129', '910473676262998019', '912395983000698880', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066130', '910473676262998019', '912396527610101760', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066131', '910473676262998019', '912394775959699456', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066132', '910473676262998019', '912397317363990528', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066133', '910473676262998019', '912397738614718464', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066134', '910473676262998019', '911903886820769792', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066135', '910473676262998019', '912397522863915008', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066136', '910473676262998019', '912398209035272192', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066137', '910473676262998019', '912399254104834048', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913151406335066138', '910473676262998019', '912397843350683648', NULL, '2021-11-24 11:37:41', NULL, '2021-11-24 11:37:41');
INSERT INTO `sys_role_resource_ref` VALUES ('913164082515804160', '910473676262998019', '913159960412553216', NULL, '2021-11-24 12:28:03', NULL, '2021-11-24 12:28:03');
INSERT INTO `sys_role_resource_ref` VALUES ('913164082515804161', '910473676262998019', '913160168148041728', NULL, '2021-11-24 12:28:03', NULL, '2021-11-24 12:28:03');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261888', '910473676262998019', '910471098246627328', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261889', '910473676262998019', '910470792024686592', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261890', '910473676262998019', '910469669666684928', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261891', '910473676262998019', '910470298816479232', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261892', '910473676262998019', '911220088584011776', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261893', '910473676262998019', '912725769891872768', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261894', '910473676262998019', '910468767874547712', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261895', '910473676262998019', '911265318825885696', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261896', '910473676262998019', '910468436977516544', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261897', '910473676262998019', '910472223045713920', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261898', '910473676262998019', '910471598677426176', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261899', '910473676262998019', '910470728174796800', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261900', '910473676262998019', '912725459035226112', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261901', '910473676262998019', '910468067878764544', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261902', '910473676262998019', '910468881213030400', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261903', '910473676262998019', '910471671528292352', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261904', '910473676262998019', '910470026811670528', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261905', '910473676262998019', '910471342820687872', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261906', '910473676262998019', '913006331571470336', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261907', '910473676262998019', '910472333729202176', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261908', '910473676262998019', '910470876141453312', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261909', '910473676262998019', '912442378210508800', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261910', '910473676262998019', '910469023659982848', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261911', '910473676262998019', '910471399162773504', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261912', '910473676262998019', '910466624694255616', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261913', '910473676262998019', '911264547786981376', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261914', '910473676262998019', '913006529827831808', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261915', '910473676262998019', '910473676262998016', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261916', '910473676262998019', '911220506210861056', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261917', '910473676262998019', '910471840462274560', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261918', '910473676262998019', '910473421639385088', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261919', '910473676262998019', '910468663608344576', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913173837028261920', '910473676262998019', '910473050309263360', NULL, '2021-11-24 13:06:49', NULL, '2021-11-24 13:06:49');
INSERT INTO `sys_role_resource_ref` VALUES ('913825234488918016', '910473676262998019', '913084362386309120', NULL, '2021-11-26 08:15:14', NULL, '2021-11-26 08:15:14');
INSERT INTO `sys_role_resource_ref` VALUES ('913825234488918017', '910473676262998019', '911896056462049280', NULL, '2021-11-26 08:15:14', NULL, '2021-11-26 08:15:14');
INSERT INTO `sys_role_resource_ref` VALUES ('913825234488918018', '910473676262998019', '913084526467481600', NULL, '2021-11-26 08:15:14', NULL, '2021-11-26 08:15:14');
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
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户是否启用: 0->否 1->是',
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
  `profession` int(1) DEFAULT NULL COMMENT '个人职业',
  `industry` int(1) DEFAULT NULL COMMENT '所在行业',
  PRIMARY KEY (`sys_user_id`) USING BTREE,
  UNIQUE KEY `index_user_id` (`sys_user_id`) USING BTREE COMMENT '主键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('910626036754939904', 'admin', NULL, NULL, '18855051258', '2928503333@qq.com', 1, NULL, 3, 0, 1, '2021-11-17 12:22:46', '', NULL, '', NULL, '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '911846681287327334', NULL, NULL);
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
INSERT INTO `sys_user_account` VALUES ('910626037899984896', 0, 0, '$2a$10$x3bjuD0IS5QH7beyJipH5OTaOlmnEpKqCMey1A/G/KVWLXvCrJx2O', 0, 1, NULL, 0, '910626036754939904', '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, '2021-11-17 12:22:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO `sys_user_role_ref` VALUES ('911617101444153336', '910626036754939904', '910473676262998019', NULL, '2021-11-20 06:00:54', NULL, '2021-11-20 06:00:54');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
