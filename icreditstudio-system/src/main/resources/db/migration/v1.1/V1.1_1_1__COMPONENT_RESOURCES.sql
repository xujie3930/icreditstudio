/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : iframe

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 15/03/2021 13:44:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ge_interfaces
-- ----------------------------
DROP TABLE IF EXISTS `ge_interfaces`;
CREATE TABLE `ge_interfaces`  (
                                  `interface_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  `uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口URI',
                                  `method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GET，POST，PUT，DELETE',
                                  `module` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口归属系统模块',
                                  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                                  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '说明',
                                  `need_auth` tinyint(0) NOT NULL DEFAULT 1 COMMENT '是否需要鉴权，0不需要鉴权，1需要鉴权',
                                  `support_auth_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'cert' COMMENT '支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token',
                                  `uri_type` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口地址, 通配符 0:接口地址，1：通配符',
                                  PRIMARY KEY (`interface_id`) USING BTREE,
                                  INDEX `IDX_interface_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_interfaces
-- ----------------------------
INSERT INTO `ge_interfaces` VALUES ('1', '/uaa/user/Session/logout', 'POST', 'IFrame', '用户退出登录', '用户退出登录', 1, 'token', '0');
INSERT INTO `ge_interfaces` VALUES ('10', '^/procdef/\\S*$', 'POST', 'IFrame', '复用工作流-流程定义', '复用工作流-流程定义', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('11', '^/proctask/\\S*$', 'GET', 'IFrame', '复用工作流-流程任务', '复用工作流-流程任务', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('12', '^/proctask/\\S*$', 'POST', 'IFrame', '复用工作流-流程任务', '复用工作流-流程任务', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('13', '^/user/task/\\S*$', 'GET', 'IFrame', '复用工作流-流程用户任务', '复用工作流-流程用户任务', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('14', '^/user/task/\\S*$', 'POST', 'IFrame', '复用工作流-流程用户任务', '复用工作流-流程用户任务', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('15', '^/procinst/\\S*$', 'GET', 'IFrame', '复用工作流-流程实例', '复用工作流-流程实例', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('16', '^/procinst/\\S*$', 'POST', 'IFrame', '复用工作流-流程实例', '复用工作流-流程实例', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('17', '^/data/\\S*$', 'GET', 'IFrame', '复用工作流-数据源接口', '复用工作流-数据源接口', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('18', '^/data/\\S*$', 'POST', 'IFrame', '复用工作流-数据源接口', '复用工作流-数据源接口', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('19', '^/dataSource/\\S*$', 'GET', 'IFrame', '复用工作流-数据源', '复用工作流-数据源', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('2', '/uaa/user/operateLogin', 'POST', 'IFrame', '后台用户登录', '后台用户登录', 0, '_', '0');
INSERT INTO `ge_interfaces` VALUES ('20', '^/dataSource/\\S*$', 'POST', 'IFrame', '复用工作流-数据源', '复用工作流-数据源', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('21', '^/rule/\\S*$', 'GET', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('22', '^/rule/\\S*$', 'POST', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('23', '^/processInstance/\\S*$', 'GET', 'IFrame', '复用工作流-个人创建的流程实例', '复用工作流-个人创建的流程实例', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('24', '^/processInstance/\\S*$', 'POST', 'IFrame', '复用工作流-个人创建的流程实例', '复用工作流-个人创建的流程实例', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('25', '^/loginTo/\\S*$', 'GET', 'IFrame', '前端服务跳转到农商行工作流-JSON操作', '前端服务跳转到农商行工作流-JSON操作', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('26', '^/loginTo/\\S*$', 'POST', 'IFrame', '前端服务跳转到农商行工作流-JSON操作', '前端服务跳转到农商行工作流-JSON操作', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('27', '^/flowable/\\S*$', 'GET', 'IFrame', '复用工作流-表单分组', '复用工作流-表单分组', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('28', '^/flowable/\\S*$', 'POST', 'IFrame', '复用工作流-表单分组', '复用工作流-表单分组', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('29', '^/resources/\\S*$', 'POST', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('3', '^/processForm/\\S*$', 'POST', 'IFrame', '复用工作流-流程表单', '复用工作流-流程表单', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('30', '^/resources/\\S*$', 'GET', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('31', '/uaa/login', 'POST', 'IFrame', 'uaa组件', 'uaa组件', 0, '_', '0');
INSERT INTO `ge_interfaces` VALUES ('32', '/resources/initAuth', 'POST', 'IFrame', 'uaa组件', 'uaa组件', 1, 'token', '0');
INSERT INTO `ge_interfaces` VALUES ('33', '^/org/organization/\\S*$', 'GET', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('34', '^/org/organization/\\S*$', 'POST', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('35', '^/interfaces/interfaces/\\S*$', 'GET', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('36', '^/interfaces/interfaces/\\S*$', 'POST', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('37', '^/processTask/\\S*$', 'POST', 'IFrame', '工作流--流程任务相关', '工作流--流程任务相关', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('38', '^/processTask/\\S*$', 'GET', 'IFrame', '工作流--流程任务相关', '工作流--流程任务相关', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('39', '^/role/\\S*$', 'GET', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('4', '^/processForm/\\S*$', 'GET', 'IFrame', '复用工作流-流程表单', '复用工作流-流程表单', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('40', '^/role/\\S*$', 'POST', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('41', '^/user/\\S*$', 'GET', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('42', '^/user/\\S*$', 'POST', 'IFrame', 'resources组件', 'resources组件', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('43', '^/form/\\S*$', 'POST', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('44', '^/form/\\S*$', 'GET', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('45', '^/res/\\S*$', 'POST', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('46', '^/res/\\S*$', 'GET', 'IFrame', '复用工作流-表单规则', '复用工作流-表单规则', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('5', '^/process/\\S*$', 'POST', 'IFrame', '复用工作流-流程表单和定义分组', '复用工作流-流程表单和定义分组', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('6', '^/process/\\S*$', 'GET', 'IFrame', '复用工作流-流程表单和定义分组', '复用工作流-流程表单和定义分组', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('7', '^/processDefinition/\\S*$', 'GET', 'IFrame', '复用工作流-流程定义', '复用工作流-流程定义', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('8', '^/processDefinition/\\S*$', 'POST', 'IFrame', '复用工作流-流程定义', '复用工作流-流程定义', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('819534833993871360', '/uaa/user/Session/logout', 'POST', 'IFrame', 'test1', '用户退出登录', 1, 'token', '0');
INSERT INTO `ge_interfaces` VALUES ('819534905485783040', '^/procdef/\\S*$', 'POST', 'IFrame', 'test23', '复用工作流-流程定义', 1, 'token', '1');
INSERT INTO `ge_interfaces` VALUES ('9', '^/procdef/\\S*$', 'GET', 'IFrame', '复用工作流-流程定义', '复用工作流-流程定义', 1, 'token', '1');

-- ----------------------------
-- Table structure for ge_organization
-- ----------------------------
DROP TABLE IF EXISTS `ge_organization`;
CREATE TABLE `ge_organization`  (
                                    `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                    `ORG_CODE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编号',
                                    `ORG_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
                                    `PARENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父部门id',
                                    `ORG_ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门地址',
                                    `ICON_PATH` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标地址',
                                    `SORT_NUMBER` int(0) DEFAULT NULL COMMENT '排序字段',
                                    `LINK_MAN_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人名称',
                                    `LINK_MAN_TEL` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人电话',
                                    `ORG_REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                    `DELETE_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志Y.已删除 N.未删除',
                                    `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建时间',
                                    `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
                                    `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                    `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                    `EXTEND_ONE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
                                    `EXTEND_TWO` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
                                    `EXTEND_FOUR` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段3',
                                    `EXTEND_THREE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段4',
                                    PRIMARY KEY (`ID`) USING BTREE,
                                    INDEX `CODE`(`ORG_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_organization
-- ----------------------------
INSERT INTO `ge_organization` VALUES ('526623686398418944', 'UI部门', 'UI部门', '526625398437814272', 'UI部门', '2', 22, 'UI部门', '12312312312', '1', 'N', 1614668481258, '815202389228388352', 1614929735644, '527385972360716288', '1', '1', '1', '1');
INSERT INTO `ge_organization` VALUES ('526625398437814272', '1222', '前端部', '527718373037285376', '浦口区', NULL, NULL, '李四', '17777777777', NULL, 'N', 1614668889440, '815202389228388352', 1614929722323, '527385972360716288', NULL, NULL, NULL, NULL);
INSERT INTO `ge_organization` VALUES ('526625515827994624', '13334', '后端部', '527718373037285376', '桥北', NULL, NULL, '王五', '15555555555', NULL, 'N', 1614668917428, '815202389228388352', 1614929714620, '527385972360716288', NULL, NULL, NULL, NULL);
INSERT INTO `ge_organization` VALUES ('527718373037285376', '0012', '产研中心', NULL, '产研中心', NULL, NULL, '产研中心', '12222520222', NULL, 'N', 1614929474891, '527385972360716288', 1615545821763, '815202389228388352', NULL, NULL, NULL, NULL);
INSERT INTO `ge_organization` VALUES ('819264640021344256', '0013', '交付中心', NULL, '交付中心', NULL, NULL, '交付中心', '12222520222', NULL, 'N', 1615369511481, '819230110338166784', 1615544473168, '815202389228388352', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ge_resources
-- ----------------------------
DROP TABLE IF EXISTS `ge_resources`;
CREATE TABLE `ge_resources`  (
                                 `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                 `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
                                 `CODE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编号',
                                 `PARENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父菜单id',
                                 `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由路径(B 按钮类型URL为后端请求路径)',
                                 `AUTH_IDENTIFICATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
                                 `REDIRECT_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '重定向路径',
                                 `FILE_PATH` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径',
                                 `IS_SHOW` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否在左侧菜单显示 Y 是  N 否',
                                 `IS_CACHE` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否缓存 Y...是 N...否',
                                 `KEEP_ALIVE` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否保留 Y...是 N...否',
                                 `ICON_PATH` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
                                 `SORT_NUMBER` int(0) DEFAULT NULL COMMENT '排序字段',
                                 `INTERNAL_OR_EXTERNAL` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内部或者外部',
                                 `REMARK` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
                                 `TYPE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单类型 M.菜单 D.顶部模块 B按钮',
                                 `DELETE_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                                 `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建时间',
                                 `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
                                 `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                 `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_resources
-- ----------------------------
INSERT INTO `ge_resources` VALUES ('526282579609231360', '工作流引擎', NULL, NULL, '', '', '', '', 'N', NULL, 'N', '', 0, NULL, '', 'D', 'N', 1614587155062, '815202389228388352', 1614670590540, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526283076663615488', '流程定义', NULL, '526283324978995200', '/approvalFlow/form', '', '', 'system-basic/approval-flow/form-manage/index', 'Y', NULL, 'N', 'el-icon-platform-eleme', 0, NULL, '', 'M', 'N', 1614587273569, '815202389228388352', 1614672211883, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526283324978995200', '工作流引擎', NULL, '526282579609231360', '/approvalFlow', '', '/approvalFlow/form', 'layouts/LayoutMain', 'Y', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614587332772, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('526283746678513664', '工作流表单配置', NULL, '526283324978995200', '/approvalFlow/formGenerator', '', '', 'system-basic/approval-flow/form-generator/index', 'N', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614587433313, '815202389228388352', 1614675857398, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526338741935374336', '用户-新增', NULL, '815934076711198720', '/user/user/save', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, 'a', 'B', 'N', 1614600545204, '815202389228388352', 1615527415143, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526604705767727104', '角色管理', NULL, '815933836075589632', '/manage/role', '', '', 'system-basic/manage-system/ManageRole', 'Y', NULL, 'N', '', 2, NULL, '', 'M', 'N', 1614663955923, '815202389228388352', 1614945980242, '527385466917724160');
INSERT INTO `ge_resources` VALUES ('526611313629437952', '部门管理', NULL, '815933836075589632', '/manage/org', '', '', 'system-basic/manage-system/ManageOrg', 'Y', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614665531360, '2', 1614938500976, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526611581981007872', '接口管理', NULL, '815933836075589632', '/manage/interface', '', '', 'system-basic/manage-system/ManageInterface', 'Y', NULL, 'N', '', 4, NULL, '', 'M', 'N', 1614665595340, '2', 1614946000533, '527385466917724160');
INSERT INTO `ge_resources` VALUES ('526611877440364544', '修改密码', NULL, '815933836075589632', '/manage/changepassword', '', '', 'system-basic/manage-system/ChangePassword', 'N', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614665665783, '2', 1614938513097, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526655003253121024', '流程发起', NULL, '526283324978995200', '/approvalFlow/approvalInitiate', '', '', 'system-basic/approval-flow/flow-initiate/index', 'Y', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614675947778, '815202389228388352', 1614675961546, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526655277623517184', '待办任务', NULL, '526283324978995200', '/approvalFlow/approval', '', '', 'system-basic/approval-flow/approval-manage/index', 'Y', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614676013194, '815202389228388352', 1614676021854, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526655668696227840', '工作流流程配置', NULL, '526283324978995200', '/approvalFlow/flow/:key', '', '', 'system-basic/approval-flow/flow-manage/index', 'N', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614676106432, '815202389228388352', 1614676113412, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('526656062600093696', '工作流流程审批', NULL, '526283324978995200', '/approvalFlow/formApproval', '', '', 'system-basic/approval-flow/form-approval/index', 'N', NULL, 'N', '', 0, NULL, '', 'M', 'N', 1614676200346, '815202389228388352', 1614677971329, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('815933618600927232', '系统管理', NULL, NULL, '', '', '', '', 'Y', NULL, 'N', '', 0, NULL, '顶部模块', 'D', 'N', 1614575333988, '815202389228388352', 1614575566304, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('815933836075589632', '系统管理', NULL, '815933618600927232', 'layouts/LayoutMain', '', '/manange/user', 'layouts/LayoutMain', 'Y', NULL, 'N', 'el-icon-delete-solid', 0, NULL, '1', 'M', 'N', 1614575385838, '815202389228388352', 1614848102267, '814913225102573568');
INSERT INTO `ge_resources` VALUES ('815934076711198720', '用户管理', NULL, '815933836075589632', '/manage/user', '', '', 'system-basic/manage-system/ManageUser', 'Y', NULL, 'N', 'el-icon-eleme', 1, NULL, '', 'M', 'N', 1614575443210, '815202389228388352', 1614945964838, '527385466917724160');
INSERT INTO `ge_resources` VALUES ('815961782375014400', '模块管理', NULL, '815933836075589632', '/manage/menu', 'add', '', 'system-basic/manage-system/ManageMenu', 'Y', NULL, 'N', '', 3, NULL, '', 'M', 'N', 1614582048755, '815202389228388352', 1614945993257, '527385466917724160');
INSERT INTO `ge_resources` VALUES ('819628636574601216', '用户-删除', NULL, '815934076711198720', '/user/user/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615456295008, '819236117227417600', 1615527392192, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819628779642310656', '用户-修改', NULL, '815934076711198720', '/user/user/update', 'update', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615456329119, '819236117227417600', 1615527380803, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819629117367668736', '用户-导入', NULL, '815934076711198720', '/user/user/importExcel', 'import', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615456409581, '819236117227417600', 1615527441635, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819629205808762880', '用户-导出', NULL, '815934076711198720', '/user/user/exportExcel', 'export', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615456430724, '819236117227417600', 1615527424304, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819652654560202752', '部门-新增', NULL, '526611313629437952', '/org/organization/save', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462021330, '819236117227417600', 1615527467789, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819653336843440128', '模块-新增', NULL, '815961782375014400', '/resources/resources/save', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462184029, '819236117227417600', 1615527503515, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819653543924617216', '模块-修改', NULL, '815961782375014400', '/resources/resources/update', 'update', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462233397, '819236117227417600', 1615527497988, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819654375550242816', '角色-授予权限', NULL, '526604705767727104', '/role/role/resource', 'auth', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462431676, '819236117227417600', 1615527568830, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819655051449749504', '模块-删除', NULL, '815961782375014400', '/resources/resources/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462592760, '819236117227417600', 1615527492445, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819655484691992576', '部门-删除', NULL, '526611313629437952', '/org/organization/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462696047, '819236117227417600', 1615527455815, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819655970207846400', '部门-修改', NULL, '526611313629437952', '/org/organization/update', 'update', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462811868, '819236117227417600', 1615527472716, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819656365537775616', '用户-重置密码', NULL, '815934076711198720', '/user/useraccount/resetPassword', 'reset', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615462906122, '819236117227417600', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819656823740321792', '部门-导入', NULL, '526611313629437952', '/org/organization/importExcel', 'import', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615463015367, '819236117227417600', 1615527478110, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819656919559196672', '部门-导出', NULL, '526611313629437952', '/org/organization/exportExcel', 'export', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615463038213, '819236117227417600', 1615527462283, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819666656988254208', '角色-新增', NULL, '526604705767727104', '/role/role/save', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615465359731, '815202389228388352', 1615527577587, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('819666915403517952', '角色-修改', NULL, '526604705767727104', '/role/role/update', 'update', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615465421406, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819667022337298432', '角色-删除', NULL, '526604705767727104', '/role/role/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615465446903, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819926440434020352', '模块-导入', NULL, '815961782375014400', '/resources/resources/importExcel', 'import', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527296966, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819926560042987520', '模块-导出', NULL, '815961782375014400', '/resources/resources/exportExcel', 'export', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527325441, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819927941671890944', '接口-新增', NULL, '526611581981007872', '/interfaces/interfaces/save', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527654847, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819928302075850752', '接口-修改', NULL, '526611581981007872', '/interfaces/interfaces/update', 'update', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527740839, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819928401124339712', '接口-删除', NULL, '526611581981007872', '/interfaces/interfaces/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527764392, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819928536185122816', '接口-导入', NULL, '526611581981007872', '/interfaces/interfaces/importExcel', 'import', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527796651, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('819928644494635008', '接口-导出', NULL, '526611581981007872', '/interfaces/interfaces/exportExcel', 'export', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615527822473, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820967247072731136', '表单-新增组', NULL, '526283076663615488', '/process/form/group/add', 'add', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615775444539, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820967860569382912', '表单-新增表单', NULL, '526283076663615488', '', 'create', '', '', 'Y', NULL, 'N', '', 0, NULL, '表格中的新增，去往表单配置页', 'B', 'N', 1615775590805, '815202389228388352', 1615775685317, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('820969054658371584', '表单-预览', NULL, '526283076663615488', '/processForm/queryByKey', 'preview', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615775875499, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820969288947998720', '表单-修改', NULL, '526283076663615488', '', 'edit', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615775931358, '815202389228388352', 1615776137124, '815202389228388352');
INSERT INTO `ge_resources` VALUES ('820969466299949056', '表单-复制', NULL, '526283076663615488', '/form/info/copy', 'copy', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615775973641, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820969653059723264', '表单-发布', NULL, '526283076663615488', '/processForm/edit/state', '2', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776018169, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820969765601288192', '表单-停用', NULL, '526283076663615488', '/processForm/edit/state', '0', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776045001, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820969914847207424', '表单-启用', NULL, '526283076663615488', '/processForm/edit/state', '1', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776080584, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820970105381855232', '表单-删除', NULL, '526283076663615488', '/form/info/delete', 'delete', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776126012, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820970443983822848', '表单-查看流程', NULL, '526283076663615488', '/process/definition/info/xml', 'flowPreview', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776206741, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820970844149784576', '表单-移除流程', NULL, '526283076663615488', '/process/definition/form/deleteByKey', 'flowRemove', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776302147, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820971374385307648', '表单-添加流程', NULL, '526283076663615488', '', 'flowAdd', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776428565, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820972001937072128', '流程-发起审批', NULL, '526655003253121024', '', 'publish', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776578185, '815202389228388352', NULL, NULL);
INSERT INTO `ge_resources` VALUES ('820972517102460928', '表单-查看', NULL, '526655277623517184', '', 'preview', '', '', 'Y', NULL, 'N', '', 0, NULL, '', 'B', 'N', 1615776701010, '815202389228388352', NULL, NULL);

-- ----------------------------
-- Table structure for ge_role
-- ----------------------------
DROP TABLE IF EXISTS `ge_role`;
CREATE TABLE `ge_role`  (
                            `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                            `ROLE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
                            `ROLE_CODE` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色编码',
                            `PARENT_ID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父角色id',
                            `SORT_NUMBER` int(0) DEFAULT NULL COMMENT '排序字段',
                            `ROLE_REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色备注',
                            `DELETE_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                            `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建者id',
                            `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建时间',
                            `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                            `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                            PRIMARY KEY (`ID`) USING BTREE,
                            INDEX `CODE`(`ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_role
-- ----------------------------
INSERT INTO `ge_role` VALUES ('815202389228388311', '超级管理员', '1000', '0', 1, '超级管理员', 'N', 1, '1', 1614947316895, '815202389228388352');
INSERT INTO `ge_role` VALUES ('819873341128331264', '测试', NULL, NULL, NULL, '测试', 'N', 1615514637088, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role` VALUES ('819929116861345792', '管理员', NULL, NULL, NULL, '备份超管', 'N', 1615527935036, '815202389228388352', NULL, NULL);

-- ----------------------------
-- Table structure for ge_role_resources_map
-- ----------------------------
DROP TABLE IF EXISTS `ge_role_resources_map`;
CREATE TABLE `ge_role_resources_map`  (
                                          `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                          `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
                                          `RESOURCES_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单_id',
                                          `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建者id',
                                          `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建时间',
                                          `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                          `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                          PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_role_resources_map
-- ----------------------------
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957440', '819929116861345792', '815933618600927232', 1615530348798, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957441', '819929116861345792', '815933836075589632', 1615530348830, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957442', '819929116861345792', '815934076711198720', 1615530348860, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957443', '819929116861345792', '819628779642310656', 1615530348891, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957444', '819929116861345792', '819628636574601216', 1615530348923, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957445', '819929116861345792', '819629205808762880', 1615530348955, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957446', '819929116861345792', '526338741935374336', 1615530348983, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957447', '819929116861345792', '819629117367668736', 1615530349013, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957448', '819929116861345792', '819656365537775616', 1615530349041, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957449', '819929116861345792', '526611581981007872', 1615530349070, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957450', '819929116861345792', '819928536185122816', 1615530349107, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957451', '819929116861345792', '819927941671890944', 1615530349139, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957452', '819929116861345792', '819928302075850752', 1615530349167, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957453', '819929116861345792', '819928644494635008', 1615530349194, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957454', '819929116861345792', '819928401124339712', 1615530349225, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957455', '819929116861345792', '526611313629437952', 1615530349253, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957456', '819929116861345792', '819656823740321792', 1615530349284, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957457', '819929116861345792', '819656919559196672', 1615530349312, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957458', '819929116861345792', '819652654560202752', 1615530349340, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957459', '819929116861345792', '819655970207846400', 1615530349371, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957460', '819929116861345792', '819655484691992576', 1615530349398, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957461', '819929116861345792', '526604705767727104', 1615530349426, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957462', '819929116861345792', '819666915403517952', 1615530349454, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957463', '819929116861345792', '819666656988254208', 1615530349482, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957464', '819929116861345792', '819654375550242816', 1615530349509, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957465', '819929116861345792', '819667022337298432', 1615530349537, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957466', '819929116861345792', '526611877440364544', 1615530349565, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957467', '819929116861345792', '815961782375014400', 1615530349593, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957468', '819929116861345792', '819926440434020352', 1615530349620, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957469', '819929116861345792', '819926560042987520', 1615530349646, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957470', '819929116861345792', '819653336843440128', 1615530349675, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957471', '819929116861345792', '819655051449749504', 1615530349703, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957472', '819929116861345792', '819653543924617216', 1615530349733, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957473', '819929116861345792', '526282579609231360', 1615530349763, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957474', '819929116861345792', '526283324978995200', 1615530349794, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957475', '819929116861345792', '526655003253121024', 1615530349822, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957476', '819929116861345792', '526655277623517184', 1615530349850, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957477', '819929116861345792', '526283076663615488', 1615530349881, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957478', '819929116861345792', '526655668696227840', 1615530349911, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957479', '819929116861345792', '526656062600093696', 1615530349939, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819939240912957480', '819929116861345792', '526283746678513664', 1615530349967, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144768', '819598500193902592', '819595926170509312', 1615530589353, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144769', '819598500193902592', '819596064834199552', 1615530589380, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144770', '819598500193902592', '815934076711198720', 1615530589408, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144771', '819598500193902592', '819628779642310656', 1615530589437, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144772', '819598500193902592', '819628636574601216', 1615530589464, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144773', '819598500193902592', '819516457301012480', 1615530589492, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819940249882144774', '819598500193902592', '819516702575521792', 1615530589521, '819596860128980992', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819944682368086016', '819873341128331264', '819595926170509312', 1615531646141, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819944682368086017', '819873341128331264', '819596064834199552', 1615531646170, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819944682368086018', '819873341128331264', '819516457301012480', 1615531646199, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('819944682368086019', '819873341128331264', '819516702575521792', 1615531646230, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009856', '815202389228388311', '815933618600927232', 1615778914467, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009857', '815202389228388311', '815933836075589632', 1615778914468, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009858', '815202389228388311', '815934076711198720', 1615778914468, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009859', '815202389228388311', '819628636574601216', 1615778914468, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009860', '815202389228388311', '819629205808762880', 1615778914469, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009861', '815202389228388311', '819656365537775616', 1615778914469, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009862', '815202389228388311', '819628779642310656', 1615778914469, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009863', '815202389228388311', '526338741935374336', 1615778914469, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009864', '815202389228388311', '819629117367668736', 1615778914470, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009865', '815202389228388311', '526611581981007872', 1615778914470, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009866', '815202389228388311', '819928536185122816', 1615778914471, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009867', '815202389228388311', '819928302075850752', 1615778914471, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009868', '815202389228388311', '819928644494635008', 1615778914471, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009869', '815202389228388311', '819927941671890944', 1615778914472, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009870', '815202389228388311', '819928401124339712', 1615778914472, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009871', '815202389228388311', '526604705767727104', 1615778914472, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009872', '815202389228388311', '819666915403517952', 1615778914473, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009873', '815202389228388311', '819666656988254208', 1615778914473, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009874', '815202389228388311', '819654375550242816', 1615778914473, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009875', '815202389228388311', '819667022337298432', 1615778914474, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009876', '815202389228388311', '526611877440364544', 1615778914474, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009877', '815202389228388311', '526611313629437952', 1615778914474, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009878', '815202389228388311', '819656823740321792', 1615778914474, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009879', '815202389228388311', '819652654560202752', 1615778914475, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009880', '815202389228388311', '819656919559196672', 1615778914475, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009881', '815202389228388311', '819655970207846400', 1615778914475, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009882', '815202389228388311', '819655484691992576', 1615778914476, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009883', '815202389228388311', '815961782375014400', 1615778914476, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009884', '815202389228388311', '819655051449749504', 1615778914476, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009885', '815202389228388311', '819926440434020352', 1615778914477, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009886', '815202389228388311', '819926560042987520', 1615778914477, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009887', '815202389228388311', '819653336843440128', 1615778914477, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009888', '815202389228388311', '819653543924617216', 1615778914478, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009889', '815202389228388311', '526282579609231360', 1615778914478, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009890', '815202389228388311', '526283324978995200', 1615778914478, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009891', '815202389228388311', '526283076663615488', 1615778914479, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009892', '815202389228388311', '820969466299949056', 1615778914479, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009893', '815202389228388311', '820967860569382912', 1615778914479, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009894', '815202389228388311', '820967247072731136', 1615778914479, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009895', '815202389228388311', '820969653059723264', 1615778914480, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009896', '815202389228388311', '820969288947998720', 1615778914480, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009897', '815202389228388311', '820970105381855232', 1615778914480, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009898', '815202389228388311', '820970844149784576', 1615778914480, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009899', '815202389228388311', '820970443983822848', 1615778914481, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009900', '815202389228388311', '820969054658371584', 1615778914481, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009901', '815202389228388311', '820969914847207424', 1615778914481, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009902', '815202389228388311', '820969765601288192', 1615778914482, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009903', '815202389228388311', '820971374385307648', 1615778914482, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009904', '815202389228388311', '526656062600093696', 1615778914482, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009905', '815202389228388311', '526283746678513664', 1615778914483, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009906', '815202389228388311', '526655003253121024', 1615778914483, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009907', '815202389228388311', '820972001937072128', 1615778914483, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009908', '815202389228388311', '526655277623517184', 1615778914483, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009909', '815202389228388311', '820972517102460928', 1615778914484, '815202389228388352', NULL, NULL);
INSERT INTO `ge_role_resources_map` VALUES ('820981801014009910', '815202389228388311', '526655668696227840', 1615778914484, '815202389228388352', NULL, NULL);

-- ----------------------------
-- Table structure for ge_user
-- ----------------------------
DROP TABLE IF EXISTS `ge_user`;
CREATE TABLE `ge_user`  (
                            `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                            `USER_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
                            `USER_CODE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工号',
                            `USER_GENDER` smallint(0) DEFAULT NULL COMMENT '性别',
                            `USER_BIRTH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生日',
                            `ID_CARD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
                            `ENTRY_TIME` datetime(0) DEFAULT NULL COMMENT '入职时间',
                            `DEPARTURE_TIME` datetime(0) DEFAULT NULL COMMENT '离职时间',
                            `E_MAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
                            `USER_POSITION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职位',
                            `TEL_PHONE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系方式',
                            `SORT_NUMBER` int(0) DEFAULT NULL COMMENT '排序字段',
                            `DELETE_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志Y.已删除 N.未删除',
                            `PICTURE_PATH` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
                            `USER_REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `CREATE_TIME` bigint(0) DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者id',
                            `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                            `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                            `EXTEND_ONE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段1',
                            `EXTEND_TWO` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段2',
                            `EXTEND_THREE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段3',
                            `EXTEND_FOUR` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展字段4',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_user
-- ----------------------------
INSERT INTO `ge_user` VALUES ('815202389228388352', 'admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '12121212112', NULL, 'N', NULL, '', 1614400995320, '1', 1615358536339, '819215094515937280', NULL, NULL, NULL, NULL);
INSERT INTO `ge_user` VALUES ('820005822578110464', 'test', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '12345678910', NULL, 'N', NULL, '', 1615546223158, '815202389228388352', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ge_user_account
-- ----------------------------
DROP TABLE IF EXISTS `ge_user_account`;
CREATE TABLE `ge_user_account`  (
                                    `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                    `ACCOUNT_EXPIRED` bigint(0) DEFAULT NULL COMMENT '账户是否已过期 Y.过期 N.未过期',
                                    `ACCOUNT_LOCKED` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否锁定 Y.锁定 N.未锁定',
                                    `ACCOUNT_CREDENTIAL` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证书',
                                    `ACCOUNT_IDENTIFIER` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户标识',
                                    `CREDENTIAL_EXPIRED` bigint(0) DEFAULT NULL COMMENT '证书过期时间',
                                    `IDENTITY_TYPE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识类型  1:username,2:email,3:phone,4:wechat,5:qq',
                                    `LAST_LOGIN_IP` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近登陆ip',
                                    `LAST_LOGIN_TIME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最近登陆时间',
                                    `LOGIN_MODE` int(0) NOT NULL COMMENT '登陆模式',
                                    `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                    `DELETE_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志 Y.已删除 N.未删除',
                                    `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建时间',
                                    `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
                                    `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                    `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_user_account
-- ----------------------------
INSERT INTO `ge_user_account` VALUES ('815202389974974464', NULL, 'N', 'g8BIeRTF3qZc8pK+ugvFaQ==', 'admin', NULL, '1', NULL, NULL, 0, '815202389228388352', 'N', 1614400995498, '1', 1614666456055, '815202389228388352');
INSERT INTO `ge_user_account` VALUES ('820005825346351104', NULL, 'N', 'm5/zR7XRHl05QXWtGRaDgg==', 'test', NULL, '1', NULL, NULL, 0, '820005822578110464', 'N', 1615546223765, '815202389228388352', 1615775707042, '820005822578110464');

-- ----------------------------
-- Table structure for ge_user_org_map
-- ----------------------------
DROP TABLE IF EXISTS `ge_user_org_map`;
CREATE TABLE `ge_user_org_map`  (
                                    `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                    `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
                                    `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构id',
                                    `ORG_PATH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构的上下级全路径',
                                    `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建时间',
                                    `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
                                    `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                    `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_user_org_map
-- ----------------------------
INSERT INTO `ge_user_org_map` VALUES ('527791908661338112', '526636099017482240', NULL, '', 1614947007150, '527385466917724160', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('527791955444604928', '526635977764347904', NULL, '', 1614947018304, '527385466917724160', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819218608835178496', '815202389228388352', '527718373037285376', '527718373037285376', 1615358536729, '819215094515937280', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819220139382845440', '819220004347228160', NULL, '', 1615358901639, '819215094515937280', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819223447350718464', '819221111005949952', '527718373037285376', '527718373037285376', 1615359690324, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819223708681023488', '819215094515937280', NULL, '', 1615359752627, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819229669219020800', '819226145542287360', NULL, '', 1615361173728, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819229731651235840', '818878214209470464', NULL, '', 1615361188614, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819235885940912128', '819230110338166784', '526625515827994624', '527718373037285376,526625515827994624', 1615362655911, '819230110338166784', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819236175402414080', '819236117227417600', '527718373037285376', '527718373037285376', 1615362724922, '819230110338166784', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819583819375144960', '819583813146603520', '526625398437814272', '527718373037285376,526625398437814272', 1615445609747, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819596862637174784', '819596860128980992', '527718373037285376', '527718373037285376', 1615448719456, '819230110338166784', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819598579977953280', '819598446963990528', '526625398437814272', '527718373037285376,526625398437814272', 1615449128879, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819634949484191747', '819634949484191744', '526625398437814272', NULL, 1615457800554, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819634949484191751', '819634949484191748', '527718373037285376', NULL, 1615457800560, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727307538435', '819648727307538432', '527718373037285376', NULL, 1615461085912, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727307538439', '819648727307538436', '526625398437814272', NULL, 1615461085946, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727307538443', '819648727307538440', '526625398437814272', NULL, 1615461085979, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727307538447', '819648727307538444', '527718373037285376', NULL, 1615461086013, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727311732737', '819648727307538448', '526625398437814272', NULL, 1615461086047, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727311732741', '819648727311732738', '527718373037285376', NULL, 1615461086080, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727311732745', '819648727311732742', '526625515827994624', NULL, 1615461086114, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648727311732751', '819648727311732748', '527718373037285376', NULL, 1615461086147, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594883', '819648795305594880', '527718373037285376', NULL, 1615461102076, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594887', '819648795305594884', '526625398437814272', NULL, 1615461102109, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594891', '819648795305594888', '526625398437814272', NULL, 1615461102141, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594895', '819648795305594892', '527718373037285376', NULL, 1615461102175, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594899', '819648795305594896', '526625398437814272', NULL, 1615461102212, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795305594907', '819648795305594904', '526625515827994624', NULL, 1615461102279, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819648795309789185', '819648795305594910', '527718373037285376', NULL, 1615461102313, '819236117227417600', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('819667501599444992', '819648795305594908', NULL, '', 1615465561159, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('820004590690054144', '819648795305594900', NULL, '', 1615545929402, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('820005826642391040', '820005822578110464', '819264640021344256', '819264640021344256', 1615546224098, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_org_map` VALUES ('821015672455700480', '820971162086416384', '527718373037285376', '527718373037285376', 1615786990047, '820971162086416384', NULL, NULL);

-- ----------------------------
-- Table structure for ge_user_role_map
-- ----------------------------
DROP TABLE IF EXISTS `ge_user_role_map`;
CREATE TABLE `ge_user_role_map`  (
                                     `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
                                     `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
                                     `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id',
                                     `CREATE_TIME` bigint(0) NOT NULL COMMENT '创建时间',
                                     `CREATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
                                     `LAST_UPDATE_TIME` bigint(0) DEFAULT NULL COMMENT '更新时间',
                                     `LAST_UPDATE_USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者id',
                                     PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ge_user_role_map
-- ----------------------------
INSERT INTO `ge_user_role_map` VALUES ('819218608185061376', '815202389228388352', '815202389228388311', 1615358536575, '819215094515937280', NULL, NULL);
INSERT INTO `ge_user_role_map` VALUES ('819220138053251072', '815202389228388352', '819873341128331264', 1615358901322, '819215094515937280', NULL, NULL);
INSERT INTO `ge_user_role_map` VALUES ('819235884590346240', '815202389228388352', '819929116861345792', 1615362655588, '819230110338166784', NULL, NULL);
INSERT INTO `ge_user_role_map` VALUES ('820005825996468224', '820005822578110464', '819873341128331264', 1615546223920, '815202389228388352', NULL, NULL);
INSERT INTO `ge_user_role_map` VALUES ('820005825996468225', '820005822578110464', '819929116861345792', 1615546223952, '815202389228388352', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
