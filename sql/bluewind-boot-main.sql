/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云RDS-MySQL
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : rm.mysql.rds.aliyuncs.com:3306
 Source Schema         : bluewind-boot-main

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 26/04/2022 21:38:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_configure
-- ----------------------------
DROP TABLE IF EXISTS `sys_configure`;
CREATE TABLE `sys_configure`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `system_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统名称(简称)',
  `system_full_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统名称(全称)',
  `system_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统logo',
  `website_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站标题',
  `website_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站图标',
  `website_keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站关键字',
  `website_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站描述',
  `record_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站备案号',
  `copyright` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站版权信息',
  `login_back_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆页背景图片',
  `homepage_href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统首页链接',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_configure
-- ----------------------------
INSERT INTO `sys_configure` VALUES (1, '后台管理', 'SpringBoot后台管理模板', 'http://halo.lxyccc.top/logo/bluewind_logo.png', 'bluewind-boot 一个基于SpringBoot的通用后台管理系统', 'http://halo.lxyccc.top/1684bab905d9437ca77413860c29baa6.jpg', '管理系统,SpringBoot,解决方案,开发脚手架', '本项目本着避免重复造轮子的原则建立一套快速开发JavaWEB项目', '', 'Copyright© 1999-2020 SpringBoot 版权所有', 'http://upyun.lxyccc.top/halo/c7cfb930-269e-4239-bce0-1623af5146d5.jpg', '/admin/welcome', '1', '1', '2020-10-20 21:58:50', '2022-03-17 13:26:06');

-- ----------------------------
-- Table structure for sys_dept_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_info`;
CREATE TABLE `sys_dept_info`  (
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `ancestors` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0--未删除1--已删除）',
  `create_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept_info
-- ----------------------------
INSERT INTO `sys_dept_info` VALUES ('100', '0', '0', '蓝风集团', 0, 'admin', '17862659966', '17862659966@qq.com', '0', '0', NULL, '2022-01-02 15:07:09', NULL, '2022-01-02 15:08:02');
INSERT INTO `sys_dept_info` VALUES ('101', '100', '0,100', '蓝风软件股份有限公司', 1, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-01-02 15:08:21');
INSERT INTO `sys_dept_info` VALUES ('102', '100', '0,100', '蓝风软件科技有限公司', 2, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-01-02 15:08:39');
INSERT INTO `sys_dept_info` VALUES ('103', '101', '0,100,101', '电子商务事业部', 1, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', '1', '2022-04-17 19:13:27');
INSERT INTO `sys_dept_info` VALUES ('104', '101', '0,100,101', '软件股份第二事业部', 2, 'admin', '17862659966', NULL, '0', '1', NULL, '2022-01-02 15:07:10', NULL, '2022-04-17 19:07:43');
INSERT INTO `sys_dept_info` VALUES ('105', '102', '0,100,102', '软件科技第一事业部', 1, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-01-02 15:09:39');
INSERT INTO `sys_dept_info` VALUES ('106', '102', '0,100,102', '软件科技第二事业部', 2, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-04-17 16:28:52');
INSERT INTO `sys_dept_info` VALUES ('1515614000550080512', '101', '0,100,101', '软件股份第三事业部', 3, '张三', '17862719598', 'jqcgj@mfk.app', '0', '1', '1', '2022-04-17 16:51:51', NULL, '2022-04-17 19:07:57');
INSERT INTO `sys_dept_info` VALUES ('1515615315540148224', '103', '0,100,101,103', '物联网产品部', 0, '李四', '17862719599', '3333333@qq.com', '0', '0', '1', '2022-04-17 16:57:04', '1', '2022-04-17 18:59:33');
INSERT INTO `sys_dept_info` VALUES ('1515646339421286400', '101', '0,100,101', '智慧医疗事业部', 1, '张三', '17862719599', '2222229@qq.com', '0', '0', '1', '2022-04-17 19:00:21', '1', '2022-04-17 19:08:53');
INSERT INTO `sys_dept_info` VALUES ('1515646486955929600', '101', '0,100,101', '智慧城市事业部', 2, '张三', '17862719599', '2222229@qq.com', '0', '0', '1', '2022-04-17 19:00:56', '1', '2022-04-17 19:09:05');
INSERT INTO `sys_dept_info` VALUES ('200', '100', '0,100', '测试父级部门', 3, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-01-02 15:07:36');
INSERT INTO `sys_dept_info` VALUES ('201', '200', '0,100,200', '测试子级部门', 1, 'admin', '17862659966', NULL, '0', '0', NULL, '2022-01-02 15:07:10', NULL, '2022-01-02 15:07:37');

-- ----------------------------
-- Table structure for sys_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_info`;
CREATE TABLE `sys_dict_info`  (
  `dict_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务主键',
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标识',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典码',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--正常1--冻结）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--正常，1--已删除）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统数据字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_info
-- ----------------------------
INSERT INTO `sys_dict_info` VALUES ('1515144385559932928', 'test_code', '测试字典', '1', '1', '测试字典', 1, '0', '0', '2022-04-16 09:45:46', '2022-04-16 09:45:46', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515144385580904448', 'test_code', '测试字典', '3', '3', '测试字典', 2, '0', '0', '2022-04-16 09:45:46', '2022-04-16 09:45:46', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515145333120315392', 'banner_type', '轮播类型', '0', '默认', '轮播类型', 1, '0', '0', '2022-04-16 09:49:32', '2022-04-16 09:49:32', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515145333128704000', 'banner_type', '轮播类型', '1', '指定内容', '轮播类型', 2, '0', '0', '2022-04-16 09:49:32', '2022-04-16 09:49:32', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515145333132898304', 'banner_type', '轮播类型', '2', '指定链接', '轮播类型', 3, '0', '0', '2022-04-16 09:49:32', '2022-04-16 09:49:32', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515146812689408000', 'notice_type', '公告类型', '0', '默认', '公告类型', 1, '0', '0', '2022-04-16 09:55:24', '2022-04-16 09:55:24', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515146812697796608', 'notice_type', '公告类型', '1', '指定内容', '公告类型', 2, '0', '0', '2022-04-16 09:55:24', '2022-04-16 09:55:24', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('1515146812701990912', 'notice_type', '公告类型', '2', '指定链接', '公告类型', 3, '0', '0', '2022-04-16 09:55:25', '2022-04-16 09:55:25', '1', NULL);
INSERT INTO `sys_dict_info` VALUES ('32132132131', 'user_sex', '性别', '0', '未知', '用户性别', 1, '0', '0', '2022-04-15 15:41:52', '2022-04-15 15:42:17', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('32132132132', 'user_sex', '性别', '1', '男', '用户性别', 2, '0', '0', '2022-04-15 15:30:25', '2022-04-15 15:42:19', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('32132132133', 'user_sex', '性别', '2', '女', '用户性别', 3, '0', '0', '2022-04-15 15:30:25', '2022-04-15 15:42:20', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('43123131212', 'itfc_key_status', '服务密钥状态', '0', '正常', '服务密钥的状态', 0, '0', '0', '2022-04-15 22:55:56', '2022-04-15 22:56:38', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('43123131299', 'itfc_key_status', '服务密钥状态', '1', '停用', '服务密钥的状态', 0, '0', '0', '2022-04-15 22:55:56', '2022-04-15 22:56:38', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456334', 'menu_type', '菜单权限类型', '0', '模块', '菜单权限类型', 1, '0', '0', '2022-04-15 15:31:05', '2022-04-15 15:34:16', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456335', 'menu_type', '菜单权限类型', '1', '目录', '菜单权限类型', 2, '0', '0', '2022-04-15 15:31:05', '2022-04-15 15:37:02', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456336', 'menu_type', '菜单权限类型', '2', '菜单', '菜单权限类型', 3, '0', '0', '2022-04-15 15:31:05', '2022-04-15 15:37:03', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456337', 'menu_type', '菜单权限类型', '3', '按钮', '菜单权限类型', 4, '0', '0', '2022-04-15 15:31:05', '2022-04-15 15:37:05', '0', NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456339', 'login_status', '登录状态', '1', '失败', '登录日志的记录状态', 1, '0', '0', '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456340', 'login_status', '登录状态', '0', '成功', '登录日志的记录状态', 2, '0', '0', '2022-04-15 15:36:12', '2022-04-15 15:38:26', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456341', 'permission_target', '权限菜单打开方式', '_blank', '外链', '菜单是内链/外链', 2, '0', '0', '2022-04-15 15:39:24', '2022-04-15 15:40:11', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456342', 'permission_target', '权限菜单打开方式', '_self', '内链', '菜单是内链/外链', 1, '0', '0', '2022-04-15 15:38:59', '2022-04-15 15:43:16', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456343', 'user_status', '用户状态', '0', '正常', '用户的禁/启用状态', 1, '0', '0', '2022-04-15 15:40:20', '2022-04-15 15:41:26', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456344', 'user_status', '用户状态', '1', '锁定', '用户的禁/启用状态', 2, '0', '0', '2022-04-15 15:40:41', '2022-04-15 15:41:29', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456345', 'quartz_data_status', '定时任务状态', '0', '正常', '定时任务的禁/启用状态', 1, '0', '0', '2022-04-15 15:43:55', '2022-04-15 15:45:55', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456346', 'quartz_data_status', '定时任务状态', '1', '禁用', '定时任务的禁/启用状态', 2, '0', '0', '2022-04-15 15:44:09', '2022-04-15 15:44:50', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456347', 'quartz_status', '定时任务启动状态', '0', '启动', '定时任务启动状态', 1, '0', '0', '2022-04-15 15:46:19', '2022-04-15 15:47:02', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456348', 'quartz_status', '定时任务启动状态', '1', '停止', '定时任务启动状态', 2, '0', '0', '2022-04-15 15:46:22', '2022-04-15 15:47:34', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456349', 'role_status', '角色状态', '0', '正常', '角色的禁/启用状态', 1, '0', '0', '2022-04-15 15:49:32', '2022-04-15 15:50:21', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456350', 'role_status', '角色状态', '1', '停用', '角色的禁/启用状态', 1, '0', '0', '2022-04-15 15:49:59', '2022-04-15 15:50:29', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456352', 'dict_status', '数据字典状态', '1', '停用', '数据字典的禁/启用状态', 2, '0', '0', '2022-04-15 15:51:45', '2022-04-15 15:54:46', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456353', 'dict_status', '数据字典状态', '0', '正常', '数据字典的禁/启用状态', 1, '0', '0', '2022-04-15 15:51:20', '2022-04-15 15:56:23', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456354', 'syspostinfo_status', '岗位状态', '0', '正常', NULL, 1, '0', '0', '2022-04-15 15:55:13', '2022-04-15 15:57:15', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456355', 'syspostinfo_status', '岗位状态', '1', '停用', NULL, 2, '0', '0', '2022-04-15 15:55:26', '2022-04-15 15:56:43', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456357', 'sys_job_group', '定时任务分组', 'SYSTEM', '系统', '定时任务分组', 2, '0', '0', '2022-04-15 22:50:46', '2022-04-15 22:51:09', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456358', 'sys_job_group', '定时任务分组', 'DEFAULT', '默认', '定时任务分组', 1, '0', '0', '2022-04-15 22:49:58', '2022-04-15 22:52:55', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456360', 'sys_job_status', '定时任务状态', '0', '正常', '定时任务状态', 0, '0', '0', '2022-04-15 22:53:11', '2022-04-15 22:55:21', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456361', 'sys_job_status', '定时任务状态', '1', '暂停', '定时任务状态', 0, '0', '0', '2022-04-15 22:54:46', '2022-04-15 22:55:20', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456390', 'sysdeptinfo_status', '部门状态', '0', '正常', NULL, 1, '0', '0', '2022-04-15 15:55:13', '2022-04-15 15:57:15', NULL, NULL);
INSERT INTO `sys_dict_info` VALUES ('76763456391', 'sysdeptinfo_status', '部门状态', '1', '停用', NULL, 2, '0', '0', '2022-04-15 15:55:13', '2022-04-15 15:57:15', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dim_city
-- ----------------------------
DROP TABLE IF EXISTS `sys_dim_city`;
CREATE TABLE `sys_dim_city`  (
  `city_code` int(11) NOT NULL COMMENT '城市编码',
  `city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `province_code` int(11) NULL DEFAULT NULL COMMENT '省份编码',
  PRIMARY KEY (`city_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '城市' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dim_city
-- ----------------------------
INSERT INTO `sys_dim_city` VALUES (110100, '市辖区', 110000);
INSERT INTO `sys_dim_city` VALUES (120100, '市辖区', 120000);
INSERT INTO `sys_dim_city` VALUES (130100, '石家庄市', 130000);
INSERT INTO `sys_dim_city` VALUES (130200, '唐山市', 130000);
INSERT INTO `sys_dim_city` VALUES (130300, '秦皇岛市', 130000);
INSERT INTO `sys_dim_city` VALUES (130400, '邯郸市', 130000);
INSERT INTO `sys_dim_city` VALUES (130500, '邢台市', 130000);
INSERT INTO `sys_dim_city` VALUES (130600, '保定市', 130000);
INSERT INTO `sys_dim_city` VALUES (130700, '张家口市', 130000);
INSERT INTO `sys_dim_city` VALUES (130800, '承德市', 130000);
INSERT INTO `sys_dim_city` VALUES (130900, '沧州市', 130000);
INSERT INTO `sys_dim_city` VALUES (131000, '廊坊市', 130000);
INSERT INTO `sys_dim_city` VALUES (131100, '衡水市', 130000);
INSERT INTO `sys_dim_city` VALUES (139000, '省直辖县级行政区划', 130000);
INSERT INTO `sys_dim_city` VALUES (140100, '太原市', 140000);
INSERT INTO `sys_dim_city` VALUES (140200, '大同市', 140000);
INSERT INTO `sys_dim_city` VALUES (140300, '阳泉市', 140000);
INSERT INTO `sys_dim_city` VALUES (140400, '长治市', 140000);
INSERT INTO `sys_dim_city` VALUES (140500, '晋城市', 140000);
INSERT INTO `sys_dim_city` VALUES (140600, '朔州市', 140000);
INSERT INTO `sys_dim_city` VALUES (140700, '晋中市', 140000);
INSERT INTO `sys_dim_city` VALUES (140800, '运城市', 140000);
INSERT INTO `sys_dim_city` VALUES (140900, '忻州市', 140000);
INSERT INTO `sys_dim_city` VALUES (141000, '临汾市', 140000);
INSERT INTO `sys_dim_city` VALUES (141100, '吕梁市', 140000);
INSERT INTO `sys_dim_city` VALUES (150100, '呼和浩特市', 150000);
INSERT INTO `sys_dim_city` VALUES (150200, '包头市', 150000);
INSERT INTO `sys_dim_city` VALUES (150300, '乌海市', 150000);
INSERT INTO `sys_dim_city` VALUES (150400, '赤峰市', 150000);
INSERT INTO `sys_dim_city` VALUES (150500, '通辽市', 150000);
INSERT INTO `sys_dim_city` VALUES (150600, '鄂尔多斯市', 150000);
INSERT INTO `sys_dim_city` VALUES (150700, '呼伦贝尔市', 150000);
INSERT INTO `sys_dim_city` VALUES (150800, '巴彦淖尔市', 150000);
INSERT INTO `sys_dim_city` VALUES (150900, '乌兰察布市', 150000);
INSERT INTO `sys_dim_city` VALUES (152200, '兴安盟', 150000);
INSERT INTO `sys_dim_city` VALUES (152500, '锡林郭勒盟', 150000);
INSERT INTO `sys_dim_city` VALUES (152900, '阿拉善盟', 150000);
INSERT INTO `sys_dim_city` VALUES (210100, '沈阳市', 210000);
INSERT INTO `sys_dim_city` VALUES (210200, '大连市', 210000);
INSERT INTO `sys_dim_city` VALUES (210300, '鞍山市', 210000);
INSERT INTO `sys_dim_city` VALUES (210400, '抚顺市', 210000);
INSERT INTO `sys_dim_city` VALUES (210500, '本溪市', 210000);
INSERT INTO `sys_dim_city` VALUES (210600, '丹东市', 210000);
INSERT INTO `sys_dim_city` VALUES (210700, '锦州市', 210000);
INSERT INTO `sys_dim_city` VALUES (210800, '营口市', 210000);
INSERT INTO `sys_dim_city` VALUES (210900, '阜新市', 210000);
INSERT INTO `sys_dim_city` VALUES (211000, '辽阳市', 210000);
INSERT INTO `sys_dim_city` VALUES (211100, '盘锦市', 210000);
INSERT INTO `sys_dim_city` VALUES (211200, '铁岭市', 210000);
INSERT INTO `sys_dim_city` VALUES (211300, '朝阳市', 210000);
INSERT INTO `sys_dim_city` VALUES (211400, '葫芦岛市', 210000);
INSERT INTO `sys_dim_city` VALUES (220100, '长春市', 220000);
INSERT INTO `sys_dim_city` VALUES (220200, '吉林市', 220000);
INSERT INTO `sys_dim_city` VALUES (220300, '四平市', 220000);
INSERT INTO `sys_dim_city` VALUES (220400, '辽源市', 220000);
INSERT INTO `sys_dim_city` VALUES (220500, '通化市', 220000);
INSERT INTO `sys_dim_city` VALUES (220600, '白山市', 220000);
INSERT INTO `sys_dim_city` VALUES (220700, '松原市', 220000);
INSERT INTO `sys_dim_city` VALUES (220800, '白城市', 220000);
INSERT INTO `sys_dim_city` VALUES (222400, '延边朝鲜族自治州', 220000);
INSERT INTO `sys_dim_city` VALUES (230100, '哈尔滨市', 230000);
INSERT INTO `sys_dim_city` VALUES (230200, '齐齐哈尔市', 230000);
INSERT INTO `sys_dim_city` VALUES (230300, '鸡西市', 230000);
INSERT INTO `sys_dim_city` VALUES (230400, '鹤岗市', 230000);
INSERT INTO `sys_dim_city` VALUES (230500, '双鸭山市', 230000);
INSERT INTO `sys_dim_city` VALUES (230600, '大庆市', 230000);
INSERT INTO `sys_dim_city` VALUES (230700, '伊春市', 230000);
INSERT INTO `sys_dim_city` VALUES (230800, '佳木斯市', 230000);
INSERT INTO `sys_dim_city` VALUES (230900, '七台河市', 230000);
INSERT INTO `sys_dim_city` VALUES (231000, '牡丹江市', 230000);
INSERT INTO `sys_dim_city` VALUES (231100, '黑河市', 230000);
INSERT INTO `sys_dim_city` VALUES (231200, '绥化市', 230000);
INSERT INTO `sys_dim_city` VALUES (232700, '大兴安岭地区', 230000);
INSERT INTO `sys_dim_city` VALUES (310100, '市辖区', 310000);
INSERT INTO `sys_dim_city` VALUES (320100, '南京市', 320000);
INSERT INTO `sys_dim_city` VALUES (320200, '无锡市', 320000);
INSERT INTO `sys_dim_city` VALUES (320300, '徐州市', 320000);
INSERT INTO `sys_dim_city` VALUES (320400, '常州市', 320000);
INSERT INTO `sys_dim_city` VALUES (320500, '苏州市', 320000);
INSERT INTO `sys_dim_city` VALUES (320600, '南通市', 320000);
INSERT INTO `sys_dim_city` VALUES (320700, '连云港市', 320000);
INSERT INTO `sys_dim_city` VALUES (320800, '淮安市', 320000);
INSERT INTO `sys_dim_city` VALUES (320900, '盐城市', 320000);
INSERT INTO `sys_dim_city` VALUES (321000, '扬州市', 320000);
INSERT INTO `sys_dim_city` VALUES (321100, '镇江市', 320000);
INSERT INTO `sys_dim_city` VALUES (321200, '泰州市', 320000);
INSERT INTO `sys_dim_city` VALUES (321300, '宿迁市', 320000);
INSERT INTO `sys_dim_city` VALUES (330100, '杭州市', 330000);
INSERT INTO `sys_dim_city` VALUES (330200, '宁波市', 330000);
INSERT INTO `sys_dim_city` VALUES (330300, '温州市', 330000);
INSERT INTO `sys_dim_city` VALUES (330400, '嘉兴市', 330000);
INSERT INTO `sys_dim_city` VALUES (330500, '湖州市', 330000);
INSERT INTO `sys_dim_city` VALUES (330600, '绍兴市', 330000);
INSERT INTO `sys_dim_city` VALUES (330700, '金华市', 330000);
INSERT INTO `sys_dim_city` VALUES (330800, '衢州市', 330000);
INSERT INTO `sys_dim_city` VALUES (330900, '舟山市', 330000);
INSERT INTO `sys_dim_city` VALUES (331000, '台州市', 330000);
INSERT INTO `sys_dim_city` VALUES (331100, '丽水市', 330000);
INSERT INTO `sys_dim_city` VALUES (340100, '合肥市', 340000);
INSERT INTO `sys_dim_city` VALUES (340200, '芜湖市', 340000);
INSERT INTO `sys_dim_city` VALUES (340300, '蚌埠市', 340000);
INSERT INTO `sys_dim_city` VALUES (340400, '淮南市', 340000);
INSERT INTO `sys_dim_city` VALUES (340500, '马鞍山市', 340000);
INSERT INTO `sys_dim_city` VALUES (340600, '淮北市', 340000);
INSERT INTO `sys_dim_city` VALUES (340700, '铜陵市', 340000);
INSERT INTO `sys_dim_city` VALUES (340800, '安庆市', 340000);
INSERT INTO `sys_dim_city` VALUES (341000, '黄山市', 340000);
INSERT INTO `sys_dim_city` VALUES (341100, '滁州市', 340000);
INSERT INTO `sys_dim_city` VALUES (341200, '阜阳市', 340000);
INSERT INTO `sys_dim_city` VALUES (341300, '宿州市', 340000);
INSERT INTO `sys_dim_city` VALUES (341500, '六安市', 340000);
INSERT INTO `sys_dim_city` VALUES (341600, '亳州市', 340000);
INSERT INTO `sys_dim_city` VALUES (341700, '池州市', 340000);
INSERT INTO `sys_dim_city` VALUES (341800, '宣城市', 340000);
INSERT INTO `sys_dim_city` VALUES (350100, '福州市', 350000);
INSERT INTO `sys_dim_city` VALUES (350200, '厦门市', 350000);
INSERT INTO `sys_dim_city` VALUES (350300, '莆田市', 350000);
INSERT INTO `sys_dim_city` VALUES (350400, '三明市', 350000);
INSERT INTO `sys_dim_city` VALUES (350500, '泉州市', 350000);
INSERT INTO `sys_dim_city` VALUES (350600, '漳州市', 350000);
INSERT INTO `sys_dim_city` VALUES (350700, '南平市', 350000);
INSERT INTO `sys_dim_city` VALUES (350800, '龙岩市', 350000);
INSERT INTO `sys_dim_city` VALUES (350900, '宁德市', 350000);
INSERT INTO `sys_dim_city` VALUES (360100, '南昌市', 360000);
INSERT INTO `sys_dim_city` VALUES (360200, '景德镇市', 360000);
INSERT INTO `sys_dim_city` VALUES (360300, '萍乡市', 360000);
INSERT INTO `sys_dim_city` VALUES (360400, '九江市', 360000);
INSERT INTO `sys_dim_city` VALUES (360500, '新余市', 360000);
INSERT INTO `sys_dim_city` VALUES (360600, '鹰潭市', 360000);
INSERT INTO `sys_dim_city` VALUES (360700, '赣州市', 360000);
INSERT INTO `sys_dim_city` VALUES (360800, '吉安市', 360000);
INSERT INTO `sys_dim_city` VALUES (360900, '宜春市', 360000);
INSERT INTO `sys_dim_city` VALUES (361000, '抚州市', 360000);
INSERT INTO `sys_dim_city` VALUES (361100, '上饶市', 360000);
INSERT INTO `sys_dim_city` VALUES (370100, '济南市', 370000);
INSERT INTO `sys_dim_city` VALUES (370200, '青岛市', 370000);
INSERT INTO `sys_dim_city` VALUES (370300, '淄博市', 370000);
INSERT INTO `sys_dim_city` VALUES (370400, '枣庄市', 370000);
INSERT INTO `sys_dim_city` VALUES (370500, '东营市', 370000);
INSERT INTO `sys_dim_city` VALUES (370600, '烟台市', 370000);
INSERT INTO `sys_dim_city` VALUES (370700, '潍坊市', 370000);
INSERT INTO `sys_dim_city` VALUES (370800, '济宁市', 370000);
INSERT INTO `sys_dim_city` VALUES (370900, '泰安市', 370000);
INSERT INTO `sys_dim_city` VALUES (371000, '威海市', 370000);
INSERT INTO `sys_dim_city` VALUES (371100, '日照市', 370000);
INSERT INTO `sys_dim_city` VALUES (371200, '莱芜市', 370000);
INSERT INTO `sys_dim_city` VALUES (371300, '临沂市', 370000);
INSERT INTO `sys_dim_city` VALUES (371400, '德州市', 370000);
INSERT INTO `sys_dim_city` VALUES (371500, '聊城市', 370000);
INSERT INTO `sys_dim_city` VALUES (371600, '滨州市', 370000);
INSERT INTO `sys_dim_city` VALUES (371700, '菏泽市', 370000);
INSERT INTO `sys_dim_city` VALUES (410100, '郑州市', 410000);
INSERT INTO `sys_dim_city` VALUES (410200, '开封市', 410000);
INSERT INTO `sys_dim_city` VALUES (410300, '洛阳市', 410000);
INSERT INTO `sys_dim_city` VALUES (410400, '平顶山市', 410000);
INSERT INTO `sys_dim_city` VALUES (410500, '安阳市', 410000);
INSERT INTO `sys_dim_city` VALUES (410600, '鹤壁市', 410000);
INSERT INTO `sys_dim_city` VALUES (410700, '新乡市', 410000);
INSERT INTO `sys_dim_city` VALUES (410800, '焦作市', 410000);
INSERT INTO `sys_dim_city` VALUES (410900, '濮阳市', 410000);
INSERT INTO `sys_dim_city` VALUES (411000, '许昌市', 410000);
INSERT INTO `sys_dim_city` VALUES (411100, '漯河市', 410000);
INSERT INTO `sys_dim_city` VALUES (411200, '三门峡市', 410000);
INSERT INTO `sys_dim_city` VALUES (411300, '南阳市', 410000);
INSERT INTO `sys_dim_city` VALUES (411400, '商丘市', 410000);
INSERT INTO `sys_dim_city` VALUES (411500, '信阳市', 410000);
INSERT INTO `sys_dim_city` VALUES (411600, '周口市', 410000);
INSERT INTO `sys_dim_city` VALUES (411700, '驻马店市', 410000);
INSERT INTO `sys_dim_city` VALUES (419000, '省直辖县级行政区划', 410000);
INSERT INTO `sys_dim_city` VALUES (420100, '武汉市', 420000);
INSERT INTO `sys_dim_city` VALUES (420200, '黄石市', 420000);
INSERT INTO `sys_dim_city` VALUES (420300, '十堰市', 420000);
INSERT INTO `sys_dim_city` VALUES (420500, '宜昌市', 420000);
INSERT INTO `sys_dim_city` VALUES (420600, '襄阳市', 420000);
INSERT INTO `sys_dim_city` VALUES (420700, '鄂州市', 420000);
INSERT INTO `sys_dim_city` VALUES (420800, '荆门市', 420000);
INSERT INTO `sys_dim_city` VALUES (420900, '孝感市', 420000);
INSERT INTO `sys_dim_city` VALUES (421000, '荆州市', 420000);
INSERT INTO `sys_dim_city` VALUES (421100, '黄冈市', 420000);
INSERT INTO `sys_dim_city` VALUES (421200, '咸宁市', 420000);
INSERT INTO `sys_dim_city` VALUES (421300, '随州市', 420000);
INSERT INTO `sys_dim_city` VALUES (422800, '恩施土家族苗族自治州', 420000);
INSERT INTO `sys_dim_city` VALUES (429000, '省直辖县级行政区划', 420000);
INSERT INTO `sys_dim_city` VALUES (430100, '长沙市', 430000);
INSERT INTO `sys_dim_city` VALUES (430200, '株洲市', 430000);
INSERT INTO `sys_dim_city` VALUES (430300, '湘潭市', 430000);
INSERT INTO `sys_dim_city` VALUES (430400, '衡阳市', 430000);
INSERT INTO `sys_dim_city` VALUES (430500, '邵阳市', 430000);
INSERT INTO `sys_dim_city` VALUES (430600, '岳阳市', 430000);
INSERT INTO `sys_dim_city` VALUES (430700, '常德市', 430000);
INSERT INTO `sys_dim_city` VALUES (430800, '张家界市', 430000);
INSERT INTO `sys_dim_city` VALUES (430900, '益阳市', 430000);
INSERT INTO `sys_dim_city` VALUES (431000, '郴州市', 430000);
INSERT INTO `sys_dim_city` VALUES (431100, '永州市', 430000);
INSERT INTO `sys_dim_city` VALUES (431200, '怀化市', 430000);
INSERT INTO `sys_dim_city` VALUES (431300, '娄底市', 430000);
INSERT INTO `sys_dim_city` VALUES (433100, '湘西土家族苗族自治州', 430000);
INSERT INTO `sys_dim_city` VALUES (440100, '广州市', 440000);
INSERT INTO `sys_dim_city` VALUES (440200, '韶关市', 440000);
INSERT INTO `sys_dim_city` VALUES (440300, '深圳市', 440000);
INSERT INTO `sys_dim_city` VALUES (440400, '珠海市', 440000);
INSERT INTO `sys_dim_city` VALUES (440500, '汕头市', 440000);
INSERT INTO `sys_dim_city` VALUES (440600, '佛山市', 440000);
INSERT INTO `sys_dim_city` VALUES (440700, '江门市', 440000);
INSERT INTO `sys_dim_city` VALUES (440800, '湛江市', 440000);
INSERT INTO `sys_dim_city` VALUES (440900, '茂名市', 440000);
INSERT INTO `sys_dim_city` VALUES (441200, '肇庆市', 440000);
INSERT INTO `sys_dim_city` VALUES (441300, '惠州市', 440000);
INSERT INTO `sys_dim_city` VALUES (441400, '梅州市', 440000);
INSERT INTO `sys_dim_city` VALUES (441500, '汕尾市', 440000);
INSERT INTO `sys_dim_city` VALUES (441600, '河源市', 440000);
INSERT INTO `sys_dim_city` VALUES (441700, '阳江市', 440000);
INSERT INTO `sys_dim_city` VALUES (441800, '清远市', 440000);
INSERT INTO `sys_dim_city` VALUES (441900, '东莞市', 440000);
INSERT INTO `sys_dim_city` VALUES (442000, '中山市', 440000);
INSERT INTO `sys_dim_city` VALUES (445100, '潮州市', 440000);
INSERT INTO `sys_dim_city` VALUES (445200, '揭阳市', 440000);
INSERT INTO `sys_dim_city` VALUES (445300, '云浮市', 440000);
INSERT INTO `sys_dim_city` VALUES (450100, '南宁市', 450000);
INSERT INTO `sys_dim_city` VALUES (450200, '柳州市', 450000);
INSERT INTO `sys_dim_city` VALUES (450300, '桂林市', 450000);
INSERT INTO `sys_dim_city` VALUES (450400, '梧州市', 450000);
INSERT INTO `sys_dim_city` VALUES (450500, '北海市', 450000);
INSERT INTO `sys_dim_city` VALUES (450600, '防城港市', 450000);
INSERT INTO `sys_dim_city` VALUES (450700, '钦州市', 450000);
INSERT INTO `sys_dim_city` VALUES (450800, '贵港市', 450000);
INSERT INTO `sys_dim_city` VALUES (450900, '玉林市', 450000);
INSERT INTO `sys_dim_city` VALUES (451000, '百色市', 450000);
INSERT INTO `sys_dim_city` VALUES (451100, '贺州市', 450000);
INSERT INTO `sys_dim_city` VALUES (451200, '河池市', 450000);
INSERT INTO `sys_dim_city` VALUES (451300, '来宾市', 450000);
INSERT INTO `sys_dim_city` VALUES (451400, '崇左市', 450000);
INSERT INTO `sys_dim_city` VALUES (460100, '海口市', 460000);
INSERT INTO `sys_dim_city` VALUES (460200, '三亚市', 460000);
INSERT INTO `sys_dim_city` VALUES (460300, '三沙市', 460000);
INSERT INTO `sys_dim_city` VALUES (460400, '儋州市', 460000);
INSERT INTO `sys_dim_city` VALUES (469000, '省直辖县级行政区划', 460000);
INSERT INTO `sys_dim_city` VALUES (500100, '市辖区', 500000);
INSERT INTO `sys_dim_city` VALUES (500200, '县', 500000);
INSERT INTO `sys_dim_city` VALUES (510100, '成都市', 510000);
INSERT INTO `sys_dim_city` VALUES (510300, '自贡市', 510000);
INSERT INTO `sys_dim_city` VALUES (510400, '攀枝花市', 510000);
INSERT INTO `sys_dim_city` VALUES (510500, '泸州市', 510000);
INSERT INTO `sys_dim_city` VALUES (510600, '德阳市', 510000);
INSERT INTO `sys_dim_city` VALUES (510700, '绵阳市', 510000);
INSERT INTO `sys_dim_city` VALUES (510800, '广元市', 510000);
INSERT INTO `sys_dim_city` VALUES (510900, '遂宁市', 510000);
INSERT INTO `sys_dim_city` VALUES (511000, '内江市', 510000);
INSERT INTO `sys_dim_city` VALUES (511100, '乐山市', 510000);
INSERT INTO `sys_dim_city` VALUES (511300, '南充市', 510000);
INSERT INTO `sys_dim_city` VALUES (511400, '眉山市', 510000);
INSERT INTO `sys_dim_city` VALUES (511500, '宜宾市', 510000);
INSERT INTO `sys_dim_city` VALUES (511600, '广安市', 510000);
INSERT INTO `sys_dim_city` VALUES (511700, '达州市', 510000);
INSERT INTO `sys_dim_city` VALUES (511800, '雅安市', 510000);
INSERT INTO `sys_dim_city` VALUES (511900, '巴中市', 510000);
INSERT INTO `sys_dim_city` VALUES (512000, '资阳市', 510000);
INSERT INTO `sys_dim_city` VALUES (513200, '阿坝藏族羌族自治州', 510000);
INSERT INTO `sys_dim_city` VALUES (513300, '甘孜藏族自治州', 510000);
INSERT INTO `sys_dim_city` VALUES (513400, '凉山彝族自治州', 510000);
INSERT INTO `sys_dim_city` VALUES (520100, '贵阳市', 520000);
INSERT INTO `sys_dim_city` VALUES (520200, '六盘水市', 520000);
INSERT INTO `sys_dim_city` VALUES (520300, '遵义市', 520000);
INSERT INTO `sys_dim_city` VALUES (520400, '安顺市', 520000);
INSERT INTO `sys_dim_city` VALUES (520500, '毕节市', 520000);
INSERT INTO `sys_dim_city` VALUES (520600, '铜仁市', 520000);
INSERT INTO `sys_dim_city` VALUES (522300, '黔西南布依族苗族自治州', 520000);
INSERT INTO `sys_dim_city` VALUES (522600, '黔东南苗族侗族自治州', 520000);
INSERT INTO `sys_dim_city` VALUES (522700, '黔南布依族苗族自治州', 520000);
INSERT INTO `sys_dim_city` VALUES (530100, '昆明市', 530000);
INSERT INTO `sys_dim_city` VALUES (530300, '曲靖市', 530000);
INSERT INTO `sys_dim_city` VALUES (530400, '玉溪市', 530000);
INSERT INTO `sys_dim_city` VALUES (530500, '保山市', 530000);
INSERT INTO `sys_dim_city` VALUES (530600, '昭通市', 530000);
INSERT INTO `sys_dim_city` VALUES (530700, '丽江市', 530000);
INSERT INTO `sys_dim_city` VALUES (530800, '普洱市', 530000);
INSERT INTO `sys_dim_city` VALUES (530900, '临沧市', 530000);
INSERT INTO `sys_dim_city` VALUES (532300, '楚雄彝族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (532500, '红河哈尼族彝族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (532600, '文山壮族苗族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (532800, '西双版纳傣族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (532900, '大理白族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (533100, '德宏傣族景颇族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (533300, '怒江傈僳族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (533400, '迪庆藏族自治州', 530000);
INSERT INTO `sys_dim_city` VALUES (540100, '拉萨市', 540000);
INSERT INTO `sys_dim_city` VALUES (540200, '日喀则市', 540000);
INSERT INTO `sys_dim_city` VALUES (540300, '昌都市', 540000);
INSERT INTO `sys_dim_city` VALUES (540400, '林芝市', 540000);
INSERT INTO `sys_dim_city` VALUES (540500, '山南市', 540000);
INSERT INTO `sys_dim_city` VALUES (542400, '那曲地区', 540000);
INSERT INTO `sys_dim_city` VALUES (542500, '阿里地区', 540000);
INSERT INTO `sys_dim_city` VALUES (610100, '西安市', 610000);
INSERT INTO `sys_dim_city` VALUES (610200, '铜川市', 610000);
INSERT INTO `sys_dim_city` VALUES (610300, '宝鸡市', 610000);
INSERT INTO `sys_dim_city` VALUES (610400, '咸阳市', 610000);
INSERT INTO `sys_dim_city` VALUES (610500, '渭南市', 610000);
INSERT INTO `sys_dim_city` VALUES (610600, '延安市', 610000);
INSERT INTO `sys_dim_city` VALUES (610700, '汉中市', 610000);
INSERT INTO `sys_dim_city` VALUES (610800, '榆林市', 610000);
INSERT INTO `sys_dim_city` VALUES (610900, '安康市', 610000);
INSERT INTO `sys_dim_city` VALUES (611000, '商洛市', 610000);
INSERT INTO `sys_dim_city` VALUES (620100, '兰州市', 620000);
INSERT INTO `sys_dim_city` VALUES (620200, '嘉峪关市', 620000);
INSERT INTO `sys_dim_city` VALUES (620300, '金昌市', 620000);
INSERT INTO `sys_dim_city` VALUES (620400, '白银市', 620000);
INSERT INTO `sys_dim_city` VALUES (620500, '天水市', 620000);
INSERT INTO `sys_dim_city` VALUES (620600, '武威市', 620000);
INSERT INTO `sys_dim_city` VALUES (620700, '张掖市', 620000);
INSERT INTO `sys_dim_city` VALUES (620800, '平凉市', 620000);
INSERT INTO `sys_dim_city` VALUES (620900, '酒泉市', 620000);
INSERT INTO `sys_dim_city` VALUES (621000, '庆阳市', 620000);
INSERT INTO `sys_dim_city` VALUES (621100, '定西市', 620000);
INSERT INTO `sys_dim_city` VALUES (621200, '陇南市', 620000);
INSERT INTO `sys_dim_city` VALUES (622900, '临夏回族自治州', 620000);
INSERT INTO `sys_dim_city` VALUES (623000, '甘南藏族自治州', 620000);
INSERT INTO `sys_dim_city` VALUES (630100, '西宁市', 630000);
INSERT INTO `sys_dim_city` VALUES (630200, '海东市', 630000);
INSERT INTO `sys_dim_city` VALUES (632200, '海北藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (632300, '黄南藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (632500, '海南藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (632600, '果洛藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (632700, '玉树藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (632800, '海西蒙古族藏族自治州', 630000);
INSERT INTO `sys_dim_city` VALUES (640100, '银川市', 640000);
INSERT INTO `sys_dim_city` VALUES (640200, '石嘴山市', 640000);
INSERT INTO `sys_dim_city` VALUES (640300, '吴忠市', 640000);
INSERT INTO `sys_dim_city` VALUES (640400, '固原市', 640000);
INSERT INTO `sys_dim_city` VALUES (640500, '中卫市', 640000);
INSERT INTO `sys_dim_city` VALUES (650100, '乌鲁木齐市', 650000);
INSERT INTO `sys_dim_city` VALUES (650200, '克拉玛依市', 650000);
INSERT INTO `sys_dim_city` VALUES (650400, '吐鲁番市', 650000);
INSERT INTO `sys_dim_city` VALUES (650500, '哈密市', 650000);
INSERT INTO `sys_dim_city` VALUES (652300, '昌吉回族自治州', 650000);
INSERT INTO `sys_dim_city` VALUES (652700, '博尔塔拉蒙古自治州', 650000);
INSERT INTO `sys_dim_city` VALUES (652800, '巴音郭楞蒙古自治州', 650000);
INSERT INTO `sys_dim_city` VALUES (652900, '阿克苏地区', 650000);
INSERT INTO `sys_dim_city` VALUES (653000, '克孜勒苏柯尔克孜自治州', 650000);
INSERT INTO `sys_dim_city` VALUES (653100, '喀什地区', 650000);
INSERT INTO `sys_dim_city` VALUES (653200, '和田地区', 650000);
INSERT INTO `sys_dim_city` VALUES (654000, '伊犁哈萨克自治州', 650000);
INSERT INTO `sys_dim_city` VALUES (654200, '塔城地区', 650000);
INSERT INTO `sys_dim_city` VALUES (654300, '阿勒泰地区', 650000);
INSERT INTO `sys_dim_city` VALUES (659000, '自治区直辖县级行政区划', 650000);

-- ----------------------------
-- Table structure for sys_dim_county
-- ----------------------------
DROP TABLE IF EXISTS `sys_dim_county`;
CREATE TABLE `sys_dim_county`  (
  `county_code` int(11) NOT NULL COMMENT '区县编码',
  `county_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区县名称',
  `city_code` int(11) NULL DEFAULT NULL COMMENT '城市编码',
  `province_code` int(11) NULL DEFAULT NULL COMMENT '省份编码',
  PRIMARY KEY (`county_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区县' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dim_county
-- ----------------------------
INSERT INTO `sys_dim_county` VALUES (110101, '东城区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110102, '西城区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110105, '朝阳区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110106, '丰台区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110107, '石景山区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110108, '海淀区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110109, '门头沟区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110111, '房山区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110112, '通州区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110113, '顺义区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110114, '昌平区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110115, '大兴区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110116, '怀柔区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110117, '平谷区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110118, '密云区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (110119, '延庆区', 110100, 110000);
INSERT INTO `sys_dim_county` VALUES (120101, '和平区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120102, '河东区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120103, '河西区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120104, '南开区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120105, '河北区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120106, '红桥区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120110, '东丽区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120111, '西青区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120112, '津南区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120113, '北辰区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120114, '武清区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120115, '宝坻区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120116, '滨海新区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120117, '宁河区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120118, '静海区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (120119, '蓟州区', 120100, 120000);
INSERT INTO `sys_dim_county` VALUES (130101, '市辖区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130102, '长安区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130104, '桥西区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130105, '新华区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130107, '井陉矿区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130108, '裕华区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130109, '藁城区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130110, '鹿泉区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130111, '栾城区', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130121, '井陉县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130123, '正定县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130125, '行唐县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130126, '灵寿县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130127, '高邑县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130128, '深泽县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130129, '赞皇县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130130, '无极县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130131, '平山县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130132, '元氏县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130133, '赵县', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130183, '晋州市', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130184, '新乐市', 130100, 130000);
INSERT INTO `sys_dim_county` VALUES (130201, '市辖区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130202, '路南区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130203, '路北区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130204, '古冶区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130205, '开平区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130207, '丰南区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130208, '丰润区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130209, '曹妃甸区', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130223, '滦县', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130224, '滦南县', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130225, '乐亭县', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130227, '迁西县', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130229, '玉田县', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130281, '遵化市', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130283, '迁安市', 130200, 130000);
INSERT INTO `sys_dim_county` VALUES (130301, '市辖区', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130302, '海港区', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130303, '山海关区', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130304, '北戴河区', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130306, '抚宁区', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130321, '青龙满族自治县', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130322, '昌黎县', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130324, '卢龙县', 130300, 130000);
INSERT INTO `sys_dim_county` VALUES (130401, '市辖区', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130402, '邯山区', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130403, '丛台区', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130404, '复兴区', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130406, '峰峰矿区', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130421, '邯郸县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130423, '临漳县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130424, '成安县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130425, '大名县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130426, '涉县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130427, '磁县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130428, '肥乡县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130429, '永年县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130430, '邱县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130431, '鸡泽县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130432, '广平县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130433, '馆陶县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130434, '魏县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130435, '曲周县', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130481, '武安市', 130400, 130000);
INSERT INTO `sys_dim_county` VALUES (130501, '市辖区', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130502, '桥东区', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130503, '桥西区', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130521, '邢台县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130522, '临城县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130523, '内丘县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130524, '柏乡县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130525, '隆尧县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130526, '任县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130527, '南和县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130528, '宁晋县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130529, '巨鹿县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130530, '新河县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130531, '广宗县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130532, '平乡县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130533, '威县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130534, '清河县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130535, '临西县', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130581, '南宫市', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130582, '沙河市', 130500, 130000);
INSERT INTO `sys_dim_county` VALUES (130601, '市辖区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130602, '竞秀区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130606, '莲池区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130607, '满城区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130608, '清苑区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130609, '徐水区', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130623, '涞水县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130624, '阜平县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130626, '定兴县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130627, '唐县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130628, '高阳县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130629, '容城县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130630, '涞源县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130631, '望都县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130632, '安新县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130633, '易县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130634, '曲阳县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130635, '蠡县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130636, '顺平县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130637, '博野县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130638, '雄县', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130681, '涿州市', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130683, '安国市', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130684, '高碑店市', 130600, 130000);
INSERT INTO `sys_dim_county` VALUES (130701, '市辖区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130702, '桥东区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130703, '桥西区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130705, '宣化区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130706, '下花园区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130708, '万全区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130709, '崇礼区', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130722, '张北县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130723, '康保县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130724, '沽源县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130725, '尚义县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130726, '蔚县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130727, '阳原县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130728, '怀安县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130730, '怀来县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130731, '涿鹿县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130732, '赤城县', 130700, 130000);
INSERT INTO `sys_dim_county` VALUES (130801, '市辖区', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130802, '双桥区', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130803, '双滦区', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130804, '鹰手营子矿区', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130821, '承德县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130822, '兴隆县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130823, '平泉县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130824, '滦平县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130825, '隆化县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130826, '丰宁满族自治县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130827, '宽城满族自治县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130828, '围场满族蒙古族自治县', 130800, 130000);
INSERT INTO `sys_dim_county` VALUES (130901, '市辖区', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130902, '新华区', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130903, '运河区', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130921, '沧县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130922, '青县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130923, '东光县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130924, '海兴县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130925, '盐山县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130926, '肃宁县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130927, '南皮县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130928, '吴桥县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130929, '献县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130930, '孟村回族自治县', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130981, '泊头市', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130982, '任丘市', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130983, '黄骅市', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (130984, '河间市', 130900, 130000);
INSERT INTO `sys_dim_county` VALUES (131001, '市辖区', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131002, '安次区', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131003, '广阳区', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131022, '固安县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131023, '永清县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131024, '香河县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131025, '大城县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131026, '文安县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131028, '大厂回族自治县', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131081, '霸州市', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131082, '三河市', 131000, 130000);
INSERT INTO `sys_dim_county` VALUES (131101, '市辖区', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131102, '桃城区', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131103, '冀州区', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131121, '枣强县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131122, '武邑县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131123, '武强县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131124, '饶阳县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131125, '安平县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131126, '故城县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131127, '景县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131128, '阜城县', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (131182, '深州市', 131100, 130000);
INSERT INTO `sys_dim_county` VALUES (139001, '定州市', 139000, 130000);
INSERT INTO `sys_dim_county` VALUES (139002, '辛集市', 139000, 130000);
INSERT INTO `sys_dim_county` VALUES (140101, '市辖区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140105, '小店区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140106, '迎泽区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140107, '杏花岭区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140108, '尖草坪区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140109, '万柏林区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140110, '晋源区', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140121, '清徐县', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140122, '阳曲县', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140123, '娄烦县', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140181, '古交市', 140100, 140000);
INSERT INTO `sys_dim_county` VALUES (140201, '市辖区', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140202, '城区', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140203, '矿区', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140211, '南郊区', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140212, '新荣区', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140221, '阳高县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140222, '天镇县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140223, '广灵县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140224, '灵丘县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140225, '浑源县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140226, '左云县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140227, '大同县', 140200, 140000);
INSERT INTO `sys_dim_county` VALUES (140301, '市辖区', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140302, '城区', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140303, '矿区', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140311, '郊区', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140321, '平定县', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140322, '盂县', 140300, 140000);
INSERT INTO `sys_dim_county` VALUES (140401, '市辖区', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140402, '城区', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140411, '郊区', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140421, '长治县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140423, '襄垣县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140424, '屯留县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140425, '平顺县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140426, '黎城县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140427, '壶关县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140428, '长子县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140429, '武乡县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140430, '沁县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140431, '沁源县', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140481, '潞城市', 140400, 140000);
INSERT INTO `sys_dim_county` VALUES (140501, '市辖区', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140502, '城区', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140521, '沁水县', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140522, '阳城县', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140524, '陵川县', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140525, '泽州县', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140581, '高平市', 140500, 140000);
INSERT INTO `sys_dim_county` VALUES (140601, '市辖区', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140602, '朔城区', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140603, '平鲁区', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140621, '山阴县', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140622, '应县', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140623, '右玉县', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140624, '怀仁县', 140600, 140000);
INSERT INTO `sys_dim_county` VALUES (140701, '市辖区', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140702, '榆次区', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140721, '榆社县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140722, '左权县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140723, '和顺县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140724, '昔阳县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140725, '寿阳县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140726, '太谷县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140727, '祁县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140728, '平遥县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140729, '灵石县', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140781, '介休市', 140700, 140000);
INSERT INTO `sys_dim_county` VALUES (140801, '市辖区', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140802, '盐湖区', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140821, '临猗县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140822, '万荣县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140823, '闻喜县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140824, '稷山县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140825, '新绛县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140826, '绛县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140827, '垣曲县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140828, '夏县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140829, '平陆县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140830, '芮城县', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140881, '永济市', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140882, '河津市', 140800, 140000);
INSERT INTO `sys_dim_county` VALUES (140901, '市辖区', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140902, '忻府区', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140921, '定襄县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140922, '五台县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140923, '代县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140924, '繁峙县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140925, '宁武县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140926, '静乐县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140927, '神池县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140928, '五寨县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140929, '岢岚县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140930, '河曲县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140931, '保德县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140932, '偏关县', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (140981, '原平市', 140900, 140000);
INSERT INTO `sys_dim_county` VALUES (141001, '市辖区', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141002, '尧都区', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141021, '曲沃县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141022, '翼城县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141023, '襄汾县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141024, '洪洞县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141025, '古县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141026, '安泽县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141027, '浮山县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141028, '吉县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141029, '乡宁县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141030, '大宁县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141031, '隰县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141032, '永和县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141033, '蒲县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141034, '汾西县', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141081, '侯马市', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141082, '霍州市', 141000, 140000);
INSERT INTO `sys_dim_county` VALUES (141101, '市辖区', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141102, '离石区', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141121, '文水县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141122, '交城县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141123, '兴县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141124, '临县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141125, '柳林县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141126, '石楼县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141127, '岚县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141128, '方山县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141129, '中阳县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141130, '交口县', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141181, '孝义市', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (141182, '汾阳市', 141100, 140000);
INSERT INTO `sys_dim_county` VALUES (150101, '市辖区', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150102, '新城区', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150103, '回民区', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150104, '玉泉区', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150105, '赛罕区', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150121, '土默特左旗', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150122, '托克托县', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150123, '和林格尔县', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150124, '清水河县', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150125, '武川县', 150100, 150000);
INSERT INTO `sys_dim_county` VALUES (150201, '市辖区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150202, '东河区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150203, '昆都仑区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150204, '青山区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150205, '石拐区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150206, '白云鄂博矿区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150207, '九原区', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150221, '土默特右旗', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150222, '固阳县', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150223, '达尔罕茂明安联合旗', 150200, 150000);
INSERT INTO `sys_dim_county` VALUES (150301, '市辖区', 150300, 150000);
INSERT INTO `sys_dim_county` VALUES (150302, '海勃湾区', 150300, 150000);
INSERT INTO `sys_dim_county` VALUES (150303, '海南区', 150300, 150000);
INSERT INTO `sys_dim_county` VALUES (150304, '乌达区', 150300, 150000);
INSERT INTO `sys_dim_county` VALUES (150401, '市辖区', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150402, '红山区', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150403, '元宝山区', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150404, '松山区', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150421, '阿鲁科尔沁旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150422, '巴林左旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150423, '巴林右旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150424, '林西县', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150425, '克什克腾旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150426, '翁牛特旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150428, '喀喇沁旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150429, '宁城县', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150430, '敖汉旗', 150400, 150000);
INSERT INTO `sys_dim_county` VALUES (150501, '市辖区', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150502, '科尔沁区', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150521, '科尔沁左翼中旗', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150522, '科尔沁左翼后旗', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150523, '开鲁县', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150524, '库伦旗', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150525, '奈曼旗', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150526, '扎鲁特旗', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150581, '霍林郭勒市', 150500, 150000);
INSERT INTO `sys_dim_county` VALUES (150601, '市辖区', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150602, '东胜区', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150603, '康巴什区', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150621, '达拉特旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150622, '准格尔旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150623, '鄂托克前旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150624, '鄂托克旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150625, '杭锦旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150626, '乌审旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150627, '伊金霍洛旗', 150600, 150000);
INSERT INTO `sys_dim_county` VALUES (150701, '市辖区', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150702, '海拉尔区', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150703, '扎赉诺尔区', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150721, '阿荣旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150722, '莫力达瓦达斡尔族自治旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150723, '鄂伦春自治旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150724, '鄂温克族自治旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150725, '陈巴尔虎旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150726, '新巴尔虎左旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150727, '新巴尔虎右旗', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150781, '满洲里市', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150782, '牙克石市', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150783, '扎兰屯市', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150784, '额尔古纳市', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150785, '根河市', 150700, 150000);
INSERT INTO `sys_dim_county` VALUES (150801, '市辖区', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150802, '临河区', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150821, '五原县', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150822, '磴口县', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150823, '乌拉特前旗', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150824, '乌拉特中旗', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150825, '乌拉特后旗', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150826, '杭锦后旗', 150800, 150000);
INSERT INTO `sys_dim_county` VALUES (150901, '市辖区', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150902, '集宁区', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150921, '卓资县', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150922, '化德县', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150923, '商都县', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150924, '兴和县', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150925, '凉城县', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150926, '察哈尔右翼前旗', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150927, '察哈尔右翼中旗', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150928, '察哈尔右翼后旗', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150929, '四子王旗', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (150981, '丰镇市', 150900, 150000);
INSERT INTO `sys_dim_county` VALUES (152201, '乌兰浩特市', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152202, '阿尔山市', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152221, '科尔沁右翼前旗', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152222, '科尔沁右翼中旗', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152223, '扎赉特旗', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152224, '突泉县', 152200, 150000);
INSERT INTO `sys_dim_county` VALUES (152501, '二连浩特市', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152502, '锡林浩特市', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152522, '阿巴嘎旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152523, '苏尼特左旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152524, '苏尼特右旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152525, '东乌珠穆沁旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152526, '西乌珠穆沁旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152527, '太仆寺旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152528, '镶黄旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152529, '正镶白旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152530, '正蓝旗', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152531, '多伦县', 152500, 150000);
INSERT INTO `sys_dim_county` VALUES (152921, '阿拉善左旗', 152900, 150000);
INSERT INTO `sys_dim_county` VALUES (152922, '阿拉善右旗', 152900, 150000);
INSERT INTO `sys_dim_county` VALUES (152923, '额济纳旗', 152900, 150000);
INSERT INTO `sys_dim_county` VALUES (210101, '市辖区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210102, '和平区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210103, '沈河区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210104, '大东区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210105, '皇姑区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210106, '铁西区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210111, '苏家屯区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210112, '浑南区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210113, '沈北新区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210114, '于洪区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210115, '辽中区', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210123, '康平县', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210124, '法库县', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210181, '新民市', 210100, 210000);
INSERT INTO `sys_dim_county` VALUES (210201, '市辖区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210202, '中山区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210203, '西岗区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210204, '沙河口区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210211, '甘井子区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210212, '旅顺口区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210213, '金州区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210214, '普兰店区', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210224, '长海县', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210281, '瓦房店市', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210283, '庄河市', 210200, 210000);
INSERT INTO `sys_dim_county` VALUES (210301, '市辖区', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210302, '铁东区', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210303, '铁西区', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210304, '立山区', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210311, '千山区', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210321, '台安县', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210323, '岫岩满族自治县', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210381, '海城市', 210300, 210000);
INSERT INTO `sys_dim_county` VALUES (210401, '市辖区', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210402, '新抚区', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210403, '东洲区', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210404, '望花区', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210411, '顺城区', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210421, '抚顺县', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210422, '新宾满族自治县', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210423, '清原满族自治县', 210400, 210000);
INSERT INTO `sys_dim_county` VALUES (210501, '市辖区', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210502, '平山区', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210503, '溪湖区', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210504, '明山区', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210505, '南芬区', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210521, '本溪满族自治县', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210522, '桓仁满族自治县', 210500, 210000);
INSERT INTO `sys_dim_county` VALUES (210601, '市辖区', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210602, '元宝区', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210603, '振兴区', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210604, '振安区', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210624, '宽甸满族自治县', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210681, '东港市', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210682, '凤城市', 210600, 210000);
INSERT INTO `sys_dim_county` VALUES (210701, '市辖区', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210702, '古塔区', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210703, '凌河区', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210711, '太和区', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210726, '黑山县', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210727, '义县', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210781, '凌海市', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210782, '北镇市', 210700, 210000);
INSERT INTO `sys_dim_county` VALUES (210801, '市辖区', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210802, '站前区', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210803, '西市区', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210804, '鲅鱼圈区', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210811, '老边区', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210881, '盖州市', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210882, '大石桥市', 210800, 210000);
INSERT INTO `sys_dim_county` VALUES (210901, '市辖区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210902, '海州区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210903, '新邱区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210904, '太平区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210905, '清河门区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210911, '细河区', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210921, '阜新蒙古族自治县', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (210922, '彰武县', 210900, 210000);
INSERT INTO `sys_dim_county` VALUES (211001, '市辖区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211002, '白塔区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211003, '文圣区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211004, '宏伟区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211005, '弓长岭区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211011, '太子河区', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211021, '辽阳县', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211081, '灯塔市', 211000, 210000);
INSERT INTO `sys_dim_county` VALUES (211101, '市辖区', 211100, 210000);
INSERT INTO `sys_dim_county` VALUES (211102, '双台子区', 211100, 210000);
INSERT INTO `sys_dim_county` VALUES (211103, '兴隆台区', 211100, 210000);
INSERT INTO `sys_dim_county` VALUES (211104, '大洼区', 211100, 210000);
INSERT INTO `sys_dim_county` VALUES (211122, '盘山县', 211100, 210000);
INSERT INTO `sys_dim_county` VALUES (211201, '市辖区', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211202, '银州区', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211204, '清河区', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211221, '铁岭县', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211223, '西丰县', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211224, '昌图县', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211281, '调兵山市', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211282, '开原市', 211200, 210000);
INSERT INTO `sys_dim_county` VALUES (211301, '市辖区', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211302, '双塔区', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211303, '龙城区', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211321, '朝阳县', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211322, '建平县', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211324, '喀喇沁左翼蒙古族自治县', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211381, '北票市', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211382, '凌源市', 211300, 210000);
INSERT INTO `sys_dim_county` VALUES (211401, '市辖区', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211402, '连山区', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211403, '龙港区', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211404, '南票区', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211421, '绥中县', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211422, '建昌县', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (211481, '兴城市', 211400, 210000);
INSERT INTO `sys_dim_county` VALUES (220101, '市辖区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220102, '南关区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220103, '宽城区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220104, '朝阳区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220105, '二道区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220106, '绿园区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220112, '双阳区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220113, '九台区', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220122, '农安县', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220182, '榆树市', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220183, '德惠市', 220100, 220000);
INSERT INTO `sys_dim_county` VALUES (220201, '市辖区', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220202, '昌邑区', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220203, '龙潭区', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220204, '船营区', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220211, '丰满区', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220221, '永吉县', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220281, '蛟河市', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220282, '桦甸市', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220283, '舒兰市', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220284, '磐石市', 220200, 220000);
INSERT INTO `sys_dim_county` VALUES (220301, '市辖区', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220302, '铁西区', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220303, '铁东区', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220322, '梨树县', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220323, '伊通满族自治县', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220381, '公主岭市', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220382, '双辽市', 220300, 220000);
INSERT INTO `sys_dim_county` VALUES (220401, '市辖区', 220400, 220000);
INSERT INTO `sys_dim_county` VALUES (220402, '龙山区', 220400, 220000);
INSERT INTO `sys_dim_county` VALUES (220403, '西安区', 220400, 220000);
INSERT INTO `sys_dim_county` VALUES (220421, '东丰县', 220400, 220000);
INSERT INTO `sys_dim_county` VALUES (220422, '东辽县', 220400, 220000);
INSERT INTO `sys_dim_county` VALUES (220501, '市辖区', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220502, '东昌区', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220503, '二道江区', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220521, '通化县', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220523, '辉南县', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220524, '柳河县', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220581, '梅河口市', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220582, '集安市', 220500, 220000);
INSERT INTO `sys_dim_county` VALUES (220601, '市辖区', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220602, '浑江区', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220605, '江源区', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220621, '抚松县', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220622, '靖宇县', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220623, '长白朝鲜族自治县', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220681, '临江市', 220600, 220000);
INSERT INTO `sys_dim_county` VALUES (220701, '市辖区', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220702, '宁江区', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220721, '前郭尔罗斯蒙古族自治县', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220722, '长岭县', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220723, '乾安县', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220781, '扶余市', 220700, 220000);
INSERT INTO `sys_dim_county` VALUES (220801, '市辖区', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (220802, '洮北区', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (220821, '镇赉县', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (220822, '通榆县', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (220881, '洮南市', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (220882, '大安市', 220800, 220000);
INSERT INTO `sys_dim_county` VALUES (222401, '延吉市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222402, '图们市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222403, '敦化市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222404, '珲春市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222405, '龙井市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222406, '和龙市', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222424, '汪清县', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (222426, '安图县', 222400, 220000);
INSERT INTO `sys_dim_county` VALUES (230101, '市辖区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230102, '道里区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230103, '南岗区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230104, '道外区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230108, '平房区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230109, '松北区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230110, '香坊区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230111, '呼兰区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230112, '阿城区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230113, '双城区', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230123, '依兰县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230124, '方正县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230125, '宾县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230126, '巴彦县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230127, '木兰县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230128, '通河县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230129, '延寿县', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230183, '尚志市', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230184, '五常市', 230100, 230000);
INSERT INTO `sys_dim_county` VALUES (230201, '市辖区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230202, '龙沙区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230203, '建华区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230204, '铁锋区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230205, '昂昂溪区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230206, '富拉尔基区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230207, '碾子山区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230208, '梅里斯达斡尔族区', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230221, '龙江县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230223, '依安县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230224, '泰来县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230225, '甘南县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230227, '富裕县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230229, '克山县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230230, '克东县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230231, '拜泉县', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230281, '讷河市', 230200, 230000);
INSERT INTO `sys_dim_county` VALUES (230301, '市辖区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230302, '鸡冠区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230303, '恒山区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230304, '滴道区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230305, '梨树区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230306, '城子河区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230307, '麻山区', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230321, '鸡东县', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230381, '虎林市', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230382, '密山市', 230300, 230000);
INSERT INTO `sys_dim_county` VALUES (230401, '市辖区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230402, '向阳区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230403, '工农区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230404, '南山区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230405, '兴安区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230406, '东山区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230407, '兴山区', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230421, '萝北县', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230422, '绥滨县', 230400, 230000);
INSERT INTO `sys_dim_county` VALUES (230501, '市辖区', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230502, '尖山区', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230503, '岭东区', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230505, '四方台区', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230506, '宝山区', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230521, '集贤县', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230522, '友谊县', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230523, '宝清县', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230524, '饶河县', 230500, 230000);
INSERT INTO `sys_dim_county` VALUES (230601, '市辖区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230602, '萨尔图区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230603, '龙凤区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230604, '让胡路区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230605, '红岗区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230606, '大同区', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230621, '肇州县', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230622, '肇源县', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230623, '林甸县', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230624, '杜尔伯特蒙古族自治县', 230600, 230000);
INSERT INTO `sys_dim_county` VALUES (230701, '市辖区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230702, '伊春区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230703, '南岔区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230704, '友好区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230705, '西林区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230706, '翠峦区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230707, '新青区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230708, '美溪区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230709, '金山屯区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230710, '五营区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230711, '乌马河区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230712, '汤旺河区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230713, '带岭区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230714, '乌伊岭区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230715, '红星区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230716, '上甘岭区', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230722, '嘉荫县', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230781, '铁力市', 230700, 230000);
INSERT INTO `sys_dim_county` VALUES (230801, '市辖区', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230803, '向阳区', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230804, '前进区', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230805, '东风区', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230811, '郊区', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230822, '桦南县', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230826, '桦川县', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230828, '汤原县', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230881, '同江市', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230882, '富锦市', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230883, '抚远市', 230800, 230000);
INSERT INTO `sys_dim_county` VALUES (230901, '市辖区', 230900, 230000);
INSERT INTO `sys_dim_county` VALUES (230902, '新兴区', 230900, 230000);
INSERT INTO `sys_dim_county` VALUES (230903, '桃山区', 230900, 230000);
INSERT INTO `sys_dim_county` VALUES (230904, '茄子河区', 230900, 230000);
INSERT INTO `sys_dim_county` VALUES (230921, '勃利县', 230900, 230000);
INSERT INTO `sys_dim_county` VALUES (231001, '市辖区', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231002, '东安区', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231003, '阳明区', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231004, '爱民区', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231005, '西安区', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231025, '林口县', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231081, '绥芬河市', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231083, '海林市', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231084, '宁安市', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231085, '穆棱市', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231086, '东宁市', 231000, 230000);
INSERT INTO `sys_dim_county` VALUES (231101, '市辖区', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231102, '爱辉区', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231121, '嫩江县', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231123, '逊克县', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231124, '孙吴县', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231181, '北安市', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231182, '五大连池市', 231100, 230000);
INSERT INTO `sys_dim_county` VALUES (231201, '市辖区', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231202, '北林区', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231221, '望奎县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231222, '兰西县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231223, '青冈县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231224, '庆安县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231225, '明水县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231226, '绥棱县', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231281, '安达市', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231282, '肇东市', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (231283, '海伦市', 231200, 230000);
INSERT INTO `sys_dim_county` VALUES (232721, '呼玛县', 232700, 230000);
INSERT INTO `sys_dim_county` VALUES (232722, '塔河县', 232700, 230000);
INSERT INTO `sys_dim_county` VALUES (232723, '漠河县', 232700, 230000);
INSERT INTO `sys_dim_county` VALUES (310101, '黄浦区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310104, '徐汇区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310105, '长宁区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310106, '静安区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310107, '普陀区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310109, '虹口区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310110, '杨浦区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310112, '闵行区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310113, '宝山区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310114, '嘉定区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310115, '浦东新区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310116, '金山区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310117, '松江区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310118, '青浦区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310120, '奉贤区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (310151, '崇明区', 310100, 310000);
INSERT INTO `sys_dim_county` VALUES (320101, '市辖区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320102, '玄武区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320104, '秦淮区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320105, '建邺区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320106, '鼓楼区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320111, '浦口区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320113, '栖霞区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320114, '雨花台区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320115, '江宁区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320116, '六合区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320117, '溧水区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320118, '高淳区', 320100, 320000);
INSERT INTO `sys_dim_county` VALUES (320201, '市辖区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320205, '锡山区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320206, '惠山区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320211, '滨湖区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320213, '梁溪区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320214, '新吴区', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320281, '江阴市', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320282, '宜兴市', 320200, 320000);
INSERT INTO `sys_dim_county` VALUES (320301, '市辖区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320302, '鼓楼区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320303, '云龙区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320305, '贾汪区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320311, '泉山区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320312, '铜山区', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320321, '丰县', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320322, '沛县', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320324, '睢宁县', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320381, '新沂市', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320382, '邳州市', 320300, 320000);
INSERT INTO `sys_dim_county` VALUES (320401, '市辖区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320402, '天宁区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320404, '钟楼区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320411, '新北区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320412, '武进区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320413, '金坛区', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320481, '溧阳市', 320400, 320000);
INSERT INTO `sys_dim_county` VALUES (320501, '市辖区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320505, '虎丘区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320506, '吴中区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320507, '相城区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320508, '姑苏区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320509, '吴江区', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320581, '常熟市', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320582, '张家港市', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320583, '昆山市', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320585, '太仓市', 320500, 320000);
INSERT INTO `sys_dim_county` VALUES (320601, '市辖区', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320602, '崇川区', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320611, '港闸区', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320612, '通州区', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320621, '海安县', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320623, '如东县', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320681, '启东市', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320682, '如皋市', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320684, '海门市', 320600, 320000);
INSERT INTO `sys_dim_county` VALUES (320701, '市辖区', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320703, '连云区', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320706, '海州区', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320707, '赣榆区', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320722, '东海县', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320723, '灌云县', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320724, '灌南县', 320700, 320000);
INSERT INTO `sys_dim_county` VALUES (320801, '市辖区', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320803, '淮安区', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320804, '淮阴区', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320812, '清江浦区', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320813, '洪泽区', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320826, '涟水县', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320830, '盱眙县', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320831, '金湖县', 320800, 320000);
INSERT INTO `sys_dim_county` VALUES (320901, '市辖区', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320902, '亭湖区', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320903, '盐都区', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320904, '大丰区', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320921, '响水县', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320922, '滨海县', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320923, '阜宁县', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320924, '射阳县', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320925, '建湖县', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (320981, '东台市', 320900, 320000);
INSERT INTO `sys_dim_county` VALUES (321001, '市辖区', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321002, '广陵区', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321003, '邗江区', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321012, '江都区', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321023, '宝应县', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321081, '仪征市', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321084, '高邮市', 321000, 320000);
INSERT INTO `sys_dim_county` VALUES (321101, '市辖区', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321102, '京口区', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321111, '润州区', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321112, '丹徒区', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321181, '丹阳市', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321182, '扬中市', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321183, '句容市', 321100, 320000);
INSERT INTO `sys_dim_county` VALUES (321201, '市辖区', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321202, '海陵区', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321203, '高港区', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321204, '姜堰区', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321281, '兴化市', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321282, '靖江市', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321283, '泰兴市', 321200, 320000);
INSERT INTO `sys_dim_county` VALUES (321301, '市辖区', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (321302, '宿城区', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (321311, '宿豫区', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (321322, '沭阳县', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (321323, '泗阳县', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (321324, '泗洪县', 321300, 320000);
INSERT INTO `sys_dim_county` VALUES (330101, '市辖区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330102, '上城区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330103, '下城区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330104, '江干区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330105, '拱墅区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330106, '西湖区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330108, '滨江区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330109, '萧山区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330110, '余杭区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330111, '富阳区', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330122, '桐庐县', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330127, '淳安县', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330182, '建德市', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330185, '临安市', 330100, 330000);
INSERT INTO `sys_dim_county` VALUES (330201, '市辖区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330203, '海曙区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330204, '江东区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330205, '江北区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330206, '北仑区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330211, '镇海区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330212, '鄞州区', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330225, '象山县', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330226, '宁海县', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330281, '余姚市', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330282, '慈溪市', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330283, '奉化市', 330200, 330000);
INSERT INTO `sys_dim_county` VALUES (330301, '市辖区', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330302, '鹿城区', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330303, '龙湾区', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330304, '瓯海区', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330305, '洞头区', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330324, '永嘉县', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330326, '平阳县', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330327, '苍南县', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330328, '文成县', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330329, '泰顺县', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330381, '瑞安市', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330382, '乐清市', 330300, 330000);
INSERT INTO `sys_dim_county` VALUES (330401, '市辖区', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330402, '南湖区', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330411, '秀洲区', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330421, '嘉善县', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330424, '海盐县', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330481, '海宁市', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330482, '平湖市', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330483, '桐乡市', 330400, 330000);
INSERT INTO `sys_dim_county` VALUES (330501, '市辖区', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330502, '吴兴区', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330503, '南浔区', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330521, '德清县', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330522, '长兴县', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330523, '安吉县', 330500, 330000);
INSERT INTO `sys_dim_county` VALUES (330601, '市辖区', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330602, '越城区', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330603, '柯桥区', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330604, '上虞区', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330624, '新昌县', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330681, '诸暨市', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330683, '嵊州市', 330600, 330000);
INSERT INTO `sys_dim_county` VALUES (330701, '市辖区', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330702, '婺城区', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330703, '金东区', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330723, '武义县', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330726, '浦江县', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330727, '磐安县', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330781, '兰溪市', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330782, '义乌市', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330783, '东阳市', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330784, '永康市', 330700, 330000);
INSERT INTO `sys_dim_county` VALUES (330801, '市辖区', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330802, '柯城区', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330803, '衢江区', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330822, '常山县', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330824, '开化县', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330825, '龙游县', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330881, '江山市', 330800, 330000);
INSERT INTO `sys_dim_county` VALUES (330901, '市辖区', 330900, 330000);
INSERT INTO `sys_dim_county` VALUES (330902, '定海区', 330900, 330000);
INSERT INTO `sys_dim_county` VALUES (330903, '普陀区', 330900, 330000);
INSERT INTO `sys_dim_county` VALUES (330921, '岱山县', 330900, 330000);
INSERT INTO `sys_dim_county` VALUES (330922, '嵊泗县', 330900, 330000);
INSERT INTO `sys_dim_county` VALUES (331001, '市辖区', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331002, '椒江区', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331003, '黄岩区', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331004, '路桥区', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331021, '玉环县', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331022, '三门县', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331023, '天台县', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331024, '仙居县', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331081, '温岭市', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331082, '临海市', 331000, 330000);
INSERT INTO `sys_dim_county` VALUES (331101, '市辖区', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331102, '莲都区', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331121, '青田县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331122, '缙云县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331123, '遂昌县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331124, '松阳县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331125, '云和县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331126, '庆元县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331127, '景宁畲族自治县', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (331181, '龙泉市', 331100, 330000);
INSERT INTO `sys_dim_county` VALUES (340101, '市辖区', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340102, '瑶海区', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340103, '庐阳区', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340104, '蜀山区', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340111, '包河区', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340121, '长丰县', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340122, '肥东县', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340123, '肥西县', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340124, '庐江县', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340181, '巢湖市', 340100, 340000);
INSERT INTO `sys_dim_county` VALUES (340201, '市辖区', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340202, '镜湖区', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340203, '弋江区', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340207, '鸠江区', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340208, '三山区', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340221, '芜湖县', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340222, '繁昌县', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340223, '南陵县', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340225, '无为县', 340200, 340000);
INSERT INTO `sys_dim_county` VALUES (340301, '市辖区', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340302, '龙子湖区', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340303, '蚌山区', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340304, '禹会区', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340311, '淮上区', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340321, '怀远县', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340322, '五河县', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340323, '固镇县', 340300, 340000);
INSERT INTO `sys_dim_county` VALUES (340401, '市辖区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340402, '大通区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340403, '田家庵区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340404, '谢家集区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340405, '八公山区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340406, '潘集区', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340421, '凤台县', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340422, '寿县', 340400, 340000);
INSERT INTO `sys_dim_county` VALUES (340501, '市辖区', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340503, '花山区', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340504, '雨山区', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340506, '博望区', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340521, '当涂县', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340522, '含山县', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340523, '和县', 340500, 340000);
INSERT INTO `sys_dim_county` VALUES (340601, '市辖区', 340600, 340000);
INSERT INTO `sys_dim_county` VALUES (340602, '杜集区', 340600, 340000);
INSERT INTO `sys_dim_county` VALUES (340603, '相山区', 340600, 340000);
INSERT INTO `sys_dim_county` VALUES (340604, '烈山区', 340600, 340000);
INSERT INTO `sys_dim_county` VALUES (340621, '濉溪县', 340600, 340000);
INSERT INTO `sys_dim_county` VALUES (340701, '市辖区', 340700, 340000);
INSERT INTO `sys_dim_county` VALUES (340705, '铜官区', 340700, 340000);
INSERT INTO `sys_dim_county` VALUES (340706, '义安区', 340700, 340000);
INSERT INTO `sys_dim_county` VALUES (340711, '郊区', 340700, 340000);
INSERT INTO `sys_dim_county` VALUES (340722, '枞阳县', 340700, 340000);
INSERT INTO `sys_dim_county` VALUES (340801, '市辖区', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340802, '迎江区', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340803, '大观区', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340811, '宜秀区', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340822, '怀宁县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340824, '潜山县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340825, '太湖县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340826, '宿松县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340827, '望江县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340828, '岳西县', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (340881, '桐城市', 340800, 340000);
INSERT INTO `sys_dim_county` VALUES (341001, '市辖区', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341002, '屯溪区', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341003, '黄山区', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341004, '徽州区', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341021, '歙县', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341022, '休宁县', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341023, '黟县', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341024, '祁门县', 341000, 340000);
INSERT INTO `sys_dim_county` VALUES (341101, '市辖区', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341102, '琅琊区', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341103, '南谯区', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341122, '来安县', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341124, '全椒县', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341125, '定远县', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341126, '凤阳县', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341181, '天长市', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341182, '明光市', 341100, 340000);
INSERT INTO `sys_dim_county` VALUES (341201, '市辖区', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341202, '颍州区', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341203, '颍东区', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341204, '颍泉区', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341221, '临泉县', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341222, '太和县', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341225, '阜南县', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341226, '颍上县', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341282, '界首市', 341200, 340000);
INSERT INTO `sys_dim_county` VALUES (341301, '市辖区', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341302, '埇桥区', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341321, '砀山县', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341322, '萧县', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341323, '灵璧县', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341324, '泗县', 341300, 340000);
INSERT INTO `sys_dim_county` VALUES (341501, '市辖区', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341502, '金安区', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341503, '裕安区', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341504, '叶集区', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341522, '霍邱县', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341523, '舒城县', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341524, '金寨县', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341525, '霍山县', 341500, 340000);
INSERT INTO `sys_dim_county` VALUES (341601, '市辖区', 341600, 340000);
INSERT INTO `sys_dim_county` VALUES (341602, '谯城区', 341600, 340000);
INSERT INTO `sys_dim_county` VALUES (341621, '涡阳县', 341600, 340000);
INSERT INTO `sys_dim_county` VALUES (341622, '蒙城县', 341600, 340000);
INSERT INTO `sys_dim_county` VALUES (341623, '利辛县', 341600, 340000);
INSERT INTO `sys_dim_county` VALUES (341701, '市辖区', 341700, 340000);
INSERT INTO `sys_dim_county` VALUES (341702, '贵池区', 341700, 340000);
INSERT INTO `sys_dim_county` VALUES (341721, '东至县', 341700, 340000);
INSERT INTO `sys_dim_county` VALUES (341722, '石台县', 341700, 340000);
INSERT INTO `sys_dim_county` VALUES (341723, '青阳县', 341700, 340000);
INSERT INTO `sys_dim_county` VALUES (341801, '市辖区', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341802, '宣州区', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341821, '郎溪县', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341822, '广德县', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341823, '泾县', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341824, '绩溪县', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341825, '旌德县', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (341881, '宁国市', 341800, 340000);
INSERT INTO `sys_dim_county` VALUES (350101, '市辖区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350102, '鼓楼区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350103, '台江区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350104, '仓山区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350105, '马尾区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350111, '晋安区', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350121, '闽侯县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350122, '连江县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350123, '罗源县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350124, '闽清县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350125, '永泰县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350128, '平潭县', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350181, '福清市', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350182, '长乐市', 350100, 350000);
INSERT INTO `sys_dim_county` VALUES (350201, '市辖区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350203, '思明区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350205, '海沧区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350206, '湖里区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350211, '集美区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350212, '同安区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350213, '翔安区', 350200, 350000);
INSERT INTO `sys_dim_county` VALUES (350301, '市辖区', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350302, '城厢区', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350303, '涵江区', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350304, '荔城区', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350305, '秀屿区', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350322, '仙游县', 350300, 350000);
INSERT INTO `sys_dim_county` VALUES (350401, '市辖区', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350402, '梅列区', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350403, '三元区', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350421, '明溪县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350423, '清流县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350424, '宁化县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350425, '大田县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350426, '尤溪县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350427, '沙县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350428, '将乐县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350429, '泰宁县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350430, '建宁县', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350481, '永安市', 350400, 350000);
INSERT INTO `sys_dim_county` VALUES (350501, '市辖区', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350502, '鲤城区', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350503, '丰泽区', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350504, '洛江区', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350505, '泉港区', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350521, '惠安县', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350524, '安溪县', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350525, '永春县', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350526, '德化县', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350527, '金门县', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350581, '石狮市', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350582, '晋江市', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350583, '南安市', 350500, 350000);
INSERT INTO `sys_dim_county` VALUES (350601, '市辖区', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350602, '芗城区', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350603, '龙文区', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350622, '云霄县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350623, '漳浦县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350624, '诏安县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350625, '长泰县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350626, '东山县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350627, '南靖县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350628, '平和县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350629, '华安县', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350681, '龙海市', 350600, 350000);
INSERT INTO `sys_dim_county` VALUES (350701, '市辖区', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350702, '延平区', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350703, '建阳区', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350721, '顺昌县', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350722, '浦城县', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350723, '光泽县', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350724, '松溪县', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350725, '政和县', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350781, '邵武市', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350782, '武夷山市', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350783, '建瓯市', 350700, 350000);
INSERT INTO `sys_dim_county` VALUES (350801, '市辖区', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350802, '新罗区', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350803, '永定区', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350821, '长汀县', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350823, '上杭县', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350824, '武平县', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350825, '连城县', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350881, '漳平市', 350800, 350000);
INSERT INTO `sys_dim_county` VALUES (350901, '市辖区', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350902, '蕉城区', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350921, '霞浦县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350922, '古田县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350923, '屏南县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350924, '寿宁县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350925, '周宁县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350926, '柘荣县', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350981, '福安市', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (350982, '福鼎市', 350900, 350000);
INSERT INTO `sys_dim_county` VALUES (360101, '市辖区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360102, '东湖区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360103, '西湖区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360104, '青云谱区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360105, '湾里区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360111, '青山湖区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360112, '新建区', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360121, '南昌县', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360123, '安义县', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360124, '进贤县', 360100, 360000);
INSERT INTO `sys_dim_county` VALUES (360201, '市辖区', 360200, 360000);
INSERT INTO `sys_dim_county` VALUES (360202, '昌江区', 360200, 360000);
INSERT INTO `sys_dim_county` VALUES (360203, '珠山区', 360200, 360000);
INSERT INTO `sys_dim_county` VALUES (360222, '浮梁县', 360200, 360000);
INSERT INTO `sys_dim_county` VALUES (360281, '乐平市', 360200, 360000);
INSERT INTO `sys_dim_county` VALUES (360301, '市辖区', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360302, '安源区', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360313, '湘东区', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360321, '莲花县', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360322, '上栗县', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360323, '芦溪县', 360300, 360000);
INSERT INTO `sys_dim_county` VALUES (360401, '市辖区', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360402, '濂溪区', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360403, '浔阳区', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360421, '九江县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360423, '武宁县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360424, '修水县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360425, '永修县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360426, '德安县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360428, '都昌县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360429, '湖口县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360430, '彭泽县', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360481, '瑞昌市', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360482, '共青城市', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360483, '庐山市', 360400, 360000);
INSERT INTO `sys_dim_county` VALUES (360501, '市辖区', 360500, 360000);
INSERT INTO `sys_dim_county` VALUES (360502, '渝水区', 360500, 360000);
INSERT INTO `sys_dim_county` VALUES (360521, '分宜县', 360500, 360000);
INSERT INTO `sys_dim_county` VALUES (360601, '市辖区', 360600, 360000);
INSERT INTO `sys_dim_county` VALUES (360602, '月湖区', 360600, 360000);
INSERT INTO `sys_dim_county` VALUES (360622, '余江县', 360600, 360000);
INSERT INTO `sys_dim_county` VALUES (360681, '贵溪市', 360600, 360000);
INSERT INTO `sys_dim_county` VALUES (360701, '市辖区', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360702, '章贡区', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360703, '南康区', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360721, '赣县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360722, '信丰县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360723, '大余县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360724, '上犹县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360725, '崇义县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360726, '安远县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360727, '龙南县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360728, '定南县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360729, '全南县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360730, '宁都县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360731, '于都县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360732, '兴国县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360733, '会昌县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360734, '寻乌县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360735, '石城县', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360781, '瑞金市', 360700, 360000);
INSERT INTO `sys_dim_county` VALUES (360801, '市辖区', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360802, '吉州区', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360803, '青原区', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360821, '吉安县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360822, '吉水县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360823, '峡江县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360824, '新干县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360825, '永丰县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360826, '泰和县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360827, '遂川县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360828, '万安县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360829, '安福县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360830, '永新县', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360881, '井冈山市', 360800, 360000);
INSERT INTO `sys_dim_county` VALUES (360901, '市辖区', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360902, '袁州区', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360921, '奉新县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360922, '万载县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360923, '上高县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360924, '宜丰县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360925, '靖安县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360926, '铜鼓县', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360981, '丰城市', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360982, '樟树市', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (360983, '高安市', 360900, 360000);
INSERT INTO `sys_dim_county` VALUES (361001, '市辖区', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361002, '临川区', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361021, '南城县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361022, '黎川县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361023, '南丰县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361024, '崇仁县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361025, '乐安县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361026, '宜黄县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361027, '金溪县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361028, '资溪县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361029, '东乡县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361030, '广昌县', 361000, 360000);
INSERT INTO `sys_dim_county` VALUES (361101, '市辖区', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361102, '信州区', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361103, '广丰区', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361121, '上饶县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361123, '玉山县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361124, '铅山县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361125, '横峰县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361126, '弋阳县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361127, '余干县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361128, '鄱阳县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361129, '万年县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361130, '婺源县', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (361181, '德兴市', 361100, 360000);
INSERT INTO `sys_dim_county` VALUES (370101, '市辖区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370102, '历下区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370103, '市中区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370104, '槐荫区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370105, '天桥区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370112, '历城区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370113, '长清区', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370124, '平阴县', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370125, '济阳县', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370126, '商河县', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370181, '章丘市', 370100, 370000);
INSERT INTO `sys_dim_county` VALUES (370201, '市辖区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370202, '市南区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370203, '市北区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370211, '黄岛区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370212, '崂山区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370213, '李沧区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370214, '城阳区', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370281, '胶州市', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370282, '即墨市', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370283, '平度市', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370285, '莱西市', 370200, 370000);
INSERT INTO `sys_dim_county` VALUES (370301, '市辖区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370302, '淄川区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370303, '张店区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370304, '博山区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370305, '临淄区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370306, '周村区', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370321, '桓台县', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370322, '高青县', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370323, '沂源县', 370300, 370000);
INSERT INTO `sys_dim_county` VALUES (370401, '市辖区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370402, '市中区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370403, '薛城区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370404, '峄城区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370405, '台儿庄区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370406, '山亭区', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370481, '滕州市', 370400, 370000);
INSERT INTO `sys_dim_county` VALUES (370501, '市辖区', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370502, '东营区', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370503, '河口区', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370505, '垦利区', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370522, '利津县', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370523, '广饶县', 370500, 370000);
INSERT INTO `sys_dim_county` VALUES (370601, '市辖区', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370602, '芝罘区', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370611, '福山区', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370612, '牟平区', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370613, '莱山区', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370634, '长岛县', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370681, '龙口市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370682, '莱阳市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370683, '莱州市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370684, '蓬莱市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370685, '招远市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370686, '栖霞市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370687, '海阳市', 370600, 370000);
INSERT INTO `sys_dim_county` VALUES (370701, '市辖区', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370702, '潍城区', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370703, '寒亭区', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370704, '坊子区', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370705, '奎文区', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370724, '临朐县', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370725, '昌乐县', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370781, '青州市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370782, '诸城市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370783, '寿光市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370784, '安丘市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370785, '高密市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370786, '昌邑市', 370700, 370000);
INSERT INTO `sys_dim_county` VALUES (370801, '市辖区', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370811, '任城区', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370812, '兖州区', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370826, '微山县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370827, '鱼台县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370828, '金乡县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370829, '嘉祥县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370830, '汶上县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370831, '泗水县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370832, '梁山县', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370881, '曲阜市', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370883, '邹城市', 370800, 370000);
INSERT INTO `sys_dim_county` VALUES (370901, '市辖区', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370902, '泰山区', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370911, '岱岳区', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370921, '宁阳县', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370923, '东平县', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370982, '新泰市', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (370983, '肥城市', 370900, 370000);
INSERT INTO `sys_dim_county` VALUES (371001, '市辖区', 371000, 370000);
INSERT INTO `sys_dim_county` VALUES (371002, '环翠区', 371000, 370000);
INSERT INTO `sys_dim_county` VALUES (371003, '文登区', 371000, 370000);
INSERT INTO `sys_dim_county` VALUES (371082, '荣成市', 371000, 370000);
INSERT INTO `sys_dim_county` VALUES (371083, '乳山市', 371000, 370000);
INSERT INTO `sys_dim_county` VALUES (371101, '市辖区', 371100, 370000);
INSERT INTO `sys_dim_county` VALUES (371102, '东港区', 371100, 370000);
INSERT INTO `sys_dim_county` VALUES (371103, '岚山区', 371100, 370000);
INSERT INTO `sys_dim_county` VALUES (371121, '五莲县', 371100, 370000);
INSERT INTO `sys_dim_county` VALUES (371122, '莒县', 371100, 370000);
INSERT INTO `sys_dim_county` VALUES (371201, '市辖区', 371200, 370000);
INSERT INTO `sys_dim_county` VALUES (371202, '莱城区', 371200, 370000);
INSERT INTO `sys_dim_county` VALUES (371203, '钢城区', 371200, 370000);
INSERT INTO `sys_dim_county` VALUES (371301, '市辖区', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371302, '兰山区', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371311, '罗庄区', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371312, '河东区', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371321, '沂南县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371322, '郯城县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371323, '沂水县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371324, '兰陵县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371325, '费县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371326, '平邑县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371327, '莒南县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371328, '蒙阴县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371329, '临沭县', 371300, 370000);
INSERT INTO `sys_dim_county` VALUES (371401, '市辖区', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371402, '德城区', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371403, '陵城区', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371422, '宁津县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371423, '庆云县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371424, '临邑县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371425, '齐河县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371426, '平原县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371427, '夏津县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371428, '武城县', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371481, '乐陵市', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371482, '禹城市', 371400, 370000);
INSERT INTO `sys_dim_county` VALUES (371501, '市辖区', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371502, '东昌府区', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371521, '阳谷县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371522, '莘县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371523, '茌平县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371524, '东阿县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371525, '冠县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371526, '高唐县', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371581, '临清市', 371500, 370000);
INSERT INTO `sys_dim_county` VALUES (371601, '市辖区', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371602, '滨城区', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371603, '沾化区', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371621, '惠民县', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371622, '阳信县', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371623, '无棣县', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371625, '博兴县', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371626, '邹平县', 371600, 370000);
INSERT INTO `sys_dim_county` VALUES (371701, '市辖区', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371702, '牡丹区', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371703, '定陶区', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371721, '曹县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371722, '单县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371723, '成武县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371724, '巨野县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371725, '郓城县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371726, '鄄城县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (371728, '东明县', 371700, 370000);
INSERT INTO `sys_dim_county` VALUES (410101, '市辖区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410102, '中原区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410103, '二七区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410104, '管城回族区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410105, '金水区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410106, '上街区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410108, '惠济区', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410122, '中牟县', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410181, '巩义市', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410182, '荥阳市', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410183, '新密市', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410184, '新郑市', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410185, '登封市', 410100, 410000);
INSERT INTO `sys_dim_county` VALUES (410201, '市辖区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410202, '龙亭区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410203, '顺河回族区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410204, '鼓楼区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410205, '禹王台区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410211, '金明区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410212, '祥符区', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410221, '杞县', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410222, '通许县', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410223, '尉氏县', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410225, '兰考县', 410200, 410000);
INSERT INTO `sys_dim_county` VALUES (410301, '市辖区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410302, '老城区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410303, '西工区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410304, '瀍河回族区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410305, '涧西区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410306, '吉利区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410311, '洛龙区', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410322, '孟津县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410323, '新安县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410324, '栾川县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410325, '嵩县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410326, '汝阳县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410327, '宜阳县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410328, '洛宁县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410329, '伊川县', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410381, '偃师市', 410300, 410000);
INSERT INTO `sys_dim_county` VALUES (410401, '市辖区', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410402, '新华区', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410403, '卫东区', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410404, '石龙区', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410411, '湛河区', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410421, '宝丰县', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410422, '叶县', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410423, '鲁山县', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410425, '郏县', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410481, '舞钢市', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410482, '汝州市', 410400, 410000);
INSERT INTO `sys_dim_county` VALUES (410501, '市辖区', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410502, '文峰区', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410503, '北关区', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410505, '殷都区', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410506, '龙安区', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410522, '安阳县', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410523, '汤阴县', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410526, '滑县', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410527, '内黄县', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410581, '林州市', 410500, 410000);
INSERT INTO `sys_dim_county` VALUES (410601, '市辖区', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410602, '鹤山区', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410603, '山城区', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410611, '淇滨区', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410621, '浚县', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410622, '淇县', 410600, 410000);
INSERT INTO `sys_dim_county` VALUES (410701, '市辖区', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410702, '红旗区', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410703, '卫滨区', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410704, '凤泉区', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410711, '牧野区', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410721, '新乡县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410724, '获嘉县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410725, '原阳县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410726, '延津县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410727, '封丘县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410728, '长垣县', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410781, '卫辉市', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410782, '辉县市', 410700, 410000);
INSERT INTO `sys_dim_county` VALUES (410801, '市辖区', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410802, '解放区', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410803, '中站区', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410804, '马村区', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410811, '山阳区', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410821, '修武县', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410822, '博爱县', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410823, '武陟县', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410825, '温县', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410882, '沁阳市', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410883, '孟州市', 410800, 410000);
INSERT INTO `sys_dim_county` VALUES (410901, '市辖区', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410902, '华龙区', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410922, '清丰县', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410923, '南乐县', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410926, '范县', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410927, '台前县', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (410928, '濮阳县', 410900, 410000);
INSERT INTO `sys_dim_county` VALUES (411001, '市辖区', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411002, '魏都区', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411023, '许昌县', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411024, '鄢陵县', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411025, '襄城县', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411081, '禹州市', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411082, '长葛市', 411000, 410000);
INSERT INTO `sys_dim_county` VALUES (411101, '市辖区', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411102, '源汇区', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411103, '郾城区', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411104, '召陵区', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411121, '舞阳县', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411122, '临颍县', 411100, 410000);
INSERT INTO `sys_dim_county` VALUES (411201, '市辖区', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411202, '湖滨区', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411203, '陕州区', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411221, '渑池县', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411224, '卢氏县', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411281, '义马市', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411282, '灵宝市', 411200, 410000);
INSERT INTO `sys_dim_county` VALUES (411301, '市辖区', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411302, '宛城区', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411303, '卧龙区', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411321, '南召县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411322, '方城县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411323, '西峡县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411324, '镇平县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411325, '内乡县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411326, '淅川县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411327, '社旗县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411328, '唐河县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411329, '新野县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411330, '桐柏县', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411381, '邓州市', 411300, 410000);
INSERT INTO `sys_dim_county` VALUES (411401, '市辖区', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411402, '梁园区', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411403, '睢阳区', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411421, '民权县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411422, '睢县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411423, '宁陵县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411424, '柘城县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411425, '虞城县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411426, '夏邑县', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411481, '永城市', 411400, 410000);
INSERT INTO `sys_dim_county` VALUES (411501, '市辖区', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411502, '浉河区', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411503, '平桥区', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411521, '罗山县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411522, '光山县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411523, '新县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411524, '商城县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411525, '固始县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411526, '潢川县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411527, '淮滨县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411528, '息县', 411500, 410000);
INSERT INTO `sys_dim_county` VALUES (411601, '市辖区', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411602, '川汇区', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411621, '扶沟县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411622, '西华县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411623, '商水县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411624, '沈丘县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411625, '郸城县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411626, '淮阳县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411627, '太康县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411628, '鹿邑县', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411681, '项城市', 411600, 410000);
INSERT INTO `sys_dim_county` VALUES (411701, '市辖区', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411702, '驿城区', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411721, '西平县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411722, '上蔡县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411723, '平舆县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411724, '正阳县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411725, '确山县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411726, '泌阳县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411727, '汝南县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411728, '遂平县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (411729, '新蔡县', 411700, 410000);
INSERT INTO `sys_dim_county` VALUES (419001, '济源市', 419000, 410000);
INSERT INTO `sys_dim_county` VALUES (420101, '市辖区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420102, '江岸区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420103, '江汉区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420104, '硚口区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420105, '汉阳区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420106, '武昌区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420107, '青山区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420111, '洪山区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420112, '东西湖区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420113, '汉南区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420114, '蔡甸区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420115, '江夏区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420116, '黄陂区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420117, '新洲区', 420100, 420000);
INSERT INTO `sys_dim_county` VALUES (420201, '市辖区', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420202, '黄石港区', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420203, '西塞山区', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420204, '下陆区', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420205, '铁山区', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420222, '阳新县', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420281, '大冶市', 420200, 420000);
INSERT INTO `sys_dim_county` VALUES (420301, '市辖区', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420302, '茅箭区', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420303, '张湾区', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420304, '郧阳区', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420322, '郧西县', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420323, '竹山县', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420324, '竹溪县', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420325, '房县', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420381, '丹江口市', 420300, 420000);
INSERT INTO `sys_dim_county` VALUES (420501, '市辖区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420502, '西陵区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420503, '伍家岗区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420504, '点军区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420505, '猇亭区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420506, '夷陵区', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420525, '远安县', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420526, '兴山县', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420527, '秭归县', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420528, '长阳土家族自治县', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420529, '五峰土家族自治县', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420581, '宜都市', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420582, '当阳市', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420583, '枝江市', 420500, 420000);
INSERT INTO `sys_dim_county` VALUES (420601, '市辖区', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420602, '襄城区', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420606, '樊城区', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420607, '襄州区', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420624, '南漳县', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420625, '谷城县', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420626, '保康县', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420682, '老河口市', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420683, '枣阳市', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420684, '宜城市', 420600, 420000);
INSERT INTO `sys_dim_county` VALUES (420701, '市辖区', 420700, 420000);
INSERT INTO `sys_dim_county` VALUES (420702, '梁子湖区', 420700, 420000);
INSERT INTO `sys_dim_county` VALUES (420703, '华容区', 420700, 420000);
INSERT INTO `sys_dim_county` VALUES (420704, '鄂城区', 420700, 420000);
INSERT INTO `sys_dim_county` VALUES (420801, '市辖区', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420802, '东宝区', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420804, '掇刀区', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420821, '京山县', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420822, '沙洋县', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420881, '钟祥市', 420800, 420000);
INSERT INTO `sys_dim_county` VALUES (420901, '市辖区', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420902, '孝南区', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420921, '孝昌县', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420922, '大悟县', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420923, '云梦县', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420981, '应城市', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420982, '安陆市', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (420984, '汉川市', 420900, 420000);
INSERT INTO `sys_dim_county` VALUES (421001, '市辖区', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421002, '沙市区', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421003, '荆州区', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421022, '公安县', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421023, '监利县', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421024, '江陵县', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421081, '石首市', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421083, '洪湖市', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421087, '松滋市', 421000, 420000);
INSERT INTO `sys_dim_county` VALUES (421101, '市辖区', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421102, '黄州区', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421121, '团风县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421122, '红安县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421123, '罗田县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421124, '英山县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421125, '浠水县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421126, '蕲春县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421127, '黄梅县', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421181, '麻城市', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421182, '武穴市', 421100, 420000);
INSERT INTO `sys_dim_county` VALUES (421201, '市辖区', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421202, '咸安区', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421221, '嘉鱼县', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421222, '通城县', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421223, '崇阳县', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421224, '通山县', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421281, '赤壁市', 421200, 420000);
INSERT INTO `sys_dim_county` VALUES (421301, '市辖区', 421300, 420000);
INSERT INTO `sys_dim_county` VALUES (421303, '曾都区', 421300, 420000);
INSERT INTO `sys_dim_county` VALUES (421321, '随县', 421300, 420000);
INSERT INTO `sys_dim_county` VALUES (421381, '广水市', 421300, 420000);
INSERT INTO `sys_dim_county` VALUES (422801, '恩施市', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422802, '利川市', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422822, '建始县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422823, '巴东县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422825, '宣恩县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422826, '咸丰县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422827, '来凤县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (422828, '鹤峰县', 422800, 420000);
INSERT INTO `sys_dim_county` VALUES (429004, '仙桃市', 429000, 420000);
INSERT INTO `sys_dim_county` VALUES (429005, '潜江市', 429000, 420000);
INSERT INTO `sys_dim_county` VALUES (429006, '天门市', 429000, 420000);
INSERT INTO `sys_dim_county` VALUES (429021, '神农架林区', 429000, 420000);
INSERT INTO `sys_dim_county` VALUES (430101, '市辖区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430102, '芙蓉区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430103, '天心区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430104, '岳麓区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430105, '开福区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430111, '雨花区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430112, '望城区', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430121, '长沙县', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430124, '宁乡县', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430181, '浏阳市', 430100, 430000);
INSERT INTO `sys_dim_county` VALUES (430201, '市辖区', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430202, '荷塘区', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430203, '芦淞区', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430204, '石峰区', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430211, '天元区', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430221, '株洲县', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430223, '攸县', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430224, '茶陵县', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430225, '炎陵县', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430281, '醴陵市', 430200, 430000);
INSERT INTO `sys_dim_county` VALUES (430301, '市辖区', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430302, '雨湖区', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430304, '岳塘区', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430321, '湘潭县', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430381, '湘乡市', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430382, '韶山市', 430300, 430000);
INSERT INTO `sys_dim_county` VALUES (430401, '市辖区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430405, '珠晖区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430406, '雁峰区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430407, '石鼓区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430408, '蒸湘区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430412, '南岳区', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430421, '衡阳县', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430422, '衡南县', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430423, '衡山县', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430424, '衡东县', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430426, '祁东县', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430481, '耒阳市', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430482, '常宁市', 430400, 430000);
INSERT INTO `sys_dim_county` VALUES (430501, '市辖区', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430502, '双清区', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430503, '大祥区', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430511, '北塔区', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430521, '邵东县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430522, '新邵县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430523, '邵阳县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430524, '隆回县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430525, '洞口县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430527, '绥宁县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430528, '新宁县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430529, '城步苗族自治县', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430581, '武冈市', 430500, 430000);
INSERT INTO `sys_dim_county` VALUES (430601, '市辖区', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430602, '岳阳楼区', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430603, '云溪区', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430611, '君山区', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430621, '岳阳县', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430623, '华容县', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430624, '湘阴县', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430626, '平江县', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430681, '汨罗市', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430682, '临湘市', 430600, 430000);
INSERT INTO `sys_dim_county` VALUES (430701, '市辖区', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430702, '武陵区', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430703, '鼎城区', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430721, '安乡县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430722, '汉寿县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430723, '澧县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430724, '临澧县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430725, '桃源县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430726, '石门县', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430781, '津市市', 430700, 430000);
INSERT INTO `sys_dim_county` VALUES (430801, '市辖区', 430800, 430000);
INSERT INTO `sys_dim_county` VALUES (430802, '永定区', 430800, 430000);
INSERT INTO `sys_dim_county` VALUES (430811, '武陵源区', 430800, 430000);
INSERT INTO `sys_dim_county` VALUES (430821, '慈利县', 430800, 430000);
INSERT INTO `sys_dim_county` VALUES (430822, '桑植县', 430800, 430000);
INSERT INTO `sys_dim_county` VALUES (430901, '市辖区', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430902, '资阳区', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430903, '赫山区', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430921, '南县', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430922, '桃江县', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430923, '安化县', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (430981, '沅江市', 430900, 430000);
INSERT INTO `sys_dim_county` VALUES (431001, '市辖区', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431002, '北湖区', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431003, '苏仙区', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431021, '桂阳县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431022, '宜章县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431023, '永兴县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431024, '嘉禾县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431025, '临武县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431026, '汝城县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431027, '桂东县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431028, '安仁县', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431081, '资兴市', 431000, 430000);
INSERT INTO `sys_dim_county` VALUES (431101, '市辖区', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431102, '零陵区', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431103, '冷水滩区', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431121, '祁阳县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431122, '东安县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431123, '双牌县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431124, '道县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431125, '江永县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431126, '宁远县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431127, '蓝山县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431128, '新田县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431129, '江华瑶族自治县', 431100, 430000);
INSERT INTO `sys_dim_county` VALUES (431201, '市辖区', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431202, '鹤城区', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431221, '中方县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431222, '沅陵县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431223, '辰溪县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431224, '溆浦县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431225, '会同县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431226, '麻阳苗族自治县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431227, '新晃侗族自治县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431228, '芷江侗族自治县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431229, '靖州苗族侗族自治县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431230, '通道侗族自治县', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431281, '洪江市', 431200, 430000);
INSERT INTO `sys_dim_county` VALUES (431301, '市辖区', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (431302, '娄星区', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (431321, '双峰县', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (431322, '新化县', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (431381, '冷水江市', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (431382, '涟源市', 431300, 430000);
INSERT INTO `sys_dim_county` VALUES (433101, '吉首市', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433122, '泸溪县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433123, '凤凰县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433124, '花垣县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433125, '保靖县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433126, '古丈县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433127, '永顺县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (433130, '龙山县', 433100, 430000);
INSERT INTO `sys_dim_county` VALUES (440101, '市辖区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440103, '荔湾区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440104, '越秀区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440105, '海珠区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440106, '天河区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440111, '白云区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440112, '黄埔区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440113, '番禺区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440114, '花都区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440115, '南沙区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440117, '从化区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440118, '增城区', 440100, 440000);
INSERT INTO `sys_dim_county` VALUES (440201, '市辖区', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440203, '武江区', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440204, '浈江区', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440205, '曲江区', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440222, '始兴县', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440224, '仁化县', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440229, '翁源县', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440232, '乳源瑶族自治县', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440233, '新丰县', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440281, '乐昌市', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440282, '南雄市', 440200, 440000);
INSERT INTO `sys_dim_county` VALUES (440301, '市辖区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440303, '罗湖区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440304, '福田区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440305, '南山区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440306, '宝安区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440307, '龙岗区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440308, '盐田区', 440300, 440000);
INSERT INTO `sys_dim_county` VALUES (440401, '市辖区', 440400, 440000);
INSERT INTO `sys_dim_county` VALUES (440402, '香洲区', 440400, 440000);
INSERT INTO `sys_dim_county` VALUES (440403, '斗门区', 440400, 440000);
INSERT INTO `sys_dim_county` VALUES (440404, '金湾区', 440400, 440000);
INSERT INTO `sys_dim_county` VALUES (440501, '市辖区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440507, '龙湖区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440511, '金平区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440512, '濠江区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440513, '潮阳区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440514, '潮南区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440515, '澄海区', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440523, '南澳县', 440500, 440000);
INSERT INTO `sys_dim_county` VALUES (440601, '市辖区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440604, '禅城区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440605, '南海区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440606, '顺德区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440607, '三水区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440608, '高明区', 440600, 440000);
INSERT INTO `sys_dim_county` VALUES (440701, '市辖区', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440703, '蓬江区', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440704, '江海区', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440705, '新会区', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440781, '台山市', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440783, '开平市', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440784, '鹤山市', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440785, '恩平市', 440700, 440000);
INSERT INTO `sys_dim_county` VALUES (440801, '市辖区', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440802, '赤坎区', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440803, '霞山区', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440804, '坡头区', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440811, '麻章区', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440823, '遂溪县', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440825, '徐闻县', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440881, '廉江市', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440882, '雷州市', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440883, '吴川市', 440800, 440000);
INSERT INTO `sys_dim_county` VALUES (440901, '市辖区', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (440902, '茂南区', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (440904, '电白区', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (440981, '高州市', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (440982, '化州市', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (440983, '信宜市', 440900, 440000);
INSERT INTO `sys_dim_county` VALUES (441201, '市辖区', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441202, '端州区', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441203, '鼎湖区', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441204, '高要区', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441223, '广宁县', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441224, '怀集县', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441225, '封开县', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441226, '德庆县', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441284, '四会市', 441200, 440000);
INSERT INTO `sys_dim_county` VALUES (441301, '市辖区', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441302, '惠城区', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441303, '惠阳区', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441322, '博罗县', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441323, '惠东县', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441324, '龙门县', 441300, 440000);
INSERT INTO `sys_dim_county` VALUES (441401, '市辖区', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441402, '梅江区', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441403, '梅县区', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441422, '大埔县', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441423, '丰顺县', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441424, '五华县', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441426, '平远县', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441427, '蕉岭县', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441481, '兴宁市', 441400, 440000);
INSERT INTO `sys_dim_county` VALUES (441501, '市辖区', 441500, 440000);
INSERT INTO `sys_dim_county` VALUES (441502, '城区', 441500, 440000);
INSERT INTO `sys_dim_county` VALUES (441521, '海丰县', 441500, 440000);
INSERT INTO `sys_dim_county` VALUES (441523, '陆河县', 441500, 440000);
INSERT INTO `sys_dim_county` VALUES (441581, '陆丰市', 441500, 440000);
INSERT INTO `sys_dim_county` VALUES (441601, '市辖区', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441602, '源城区', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441621, '紫金县', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441622, '龙川县', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441623, '连平县', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441624, '和平县', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441625, '东源县', 441600, 440000);
INSERT INTO `sys_dim_county` VALUES (441701, '市辖区', 441700, 440000);
INSERT INTO `sys_dim_county` VALUES (441702, '江城区', 441700, 440000);
INSERT INTO `sys_dim_county` VALUES (441704, '阳东区', 441700, 440000);
INSERT INTO `sys_dim_county` VALUES (441721, '阳西县', 441700, 440000);
INSERT INTO `sys_dim_county` VALUES (441781, '阳春市', 441700, 440000);
INSERT INTO `sys_dim_county` VALUES (441801, '市辖区', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441802, '清城区', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441803, '清新区', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441821, '佛冈县', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441823, '阳山县', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441825, '连山壮族瑶族自治县', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441826, '连南瑶族自治县', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441881, '英德市', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (441882, '连州市', 441800, 440000);
INSERT INTO `sys_dim_county` VALUES (445101, '市辖区', 445100, 440000);
INSERT INTO `sys_dim_county` VALUES (445102, '湘桥区', 445100, 440000);
INSERT INTO `sys_dim_county` VALUES (445103, '潮安区', 445100, 440000);
INSERT INTO `sys_dim_county` VALUES (445122, '饶平县', 445100, 440000);
INSERT INTO `sys_dim_county` VALUES (445201, '市辖区', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445202, '榕城区', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445203, '揭东区', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445222, '揭西县', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445224, '惠来县', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445281, '普宁市', 445200, 440000);
INSERT INTO `sys_dim_county` VALUES (445301, '市辖区', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (445302, '云城区', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (445303, '云安区', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (445321, '新兴县', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (445322, '郁南县', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (445381, '罗定市', 445300, 440000);
INSERT INTO `sys_dim_county` VALUES (450101, '市辖区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450102, '兴宁区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450103, '青秀区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450105, '江南区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450107, '西乡塘区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450108, '良庆区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450109, '邕宁区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450110, '武鸣区', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450123, '隆安县', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450124, '马山县', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450125, '上林县', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450126, '宾阳县', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450127, '横县', 450100, 450000);
INSERT INTO `sys_dim_county` VALUES (450201, '市辖区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450202, '城中区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450203, '鱼峰区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450204, '柳南区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450205, '柳北区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450206, '柳江区', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450222, '柳城县', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450223, '鹿寨县', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450224, '融安县', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450225, '融水苗族自治县', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450226, '三江侗族自治县', 450200, 450000);
INSERT INTO `sys_dim_county` VALUES (450301, '市辖区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450302, '秀峰区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450303, '叠彩区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450304, '象山区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450305, '七星区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450311, '雁山区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450312, '临桂区', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450321, '阳朔县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450323, '灵川县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450324, '全州县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450325, '兴安县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450326, '永福县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450327, '灌阳县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450328, '龙胜各族自治县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450329, '资源县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450330, '平乐县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450331, '荔浦县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450332, '恭城瑶族自治县', 450300, 450000);
INSERT INTO `sys_dim_county` VALUES (450401, '市辖区', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450403, '万秀区', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450405, '长洲区', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450406, '龙圩区', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450421, '苍梧县', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450422, '藤县', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450423, '蒙山县', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450481, '岑溪市', 450400, 450000);
INSERT INTO `sys_dim_county` VALUES (450501, '市辖区', 450500, 450000);
INSERT INTO `sys_dim_county` VALUES (450502, '海城区', 450500, 450000);
INSERT INTO `sys_dim_county` VALUES (450503, '银海区', 450500, 450000);
INSERT INTO `sys_dim_county` VALUES (450512, '铁山港区', 450500, 450000);
INSERT INTO `sys_dim_county` VALUES (450521, '合浦县', 450500, 450000);
INSERT INTO `sys_dim_county` VALUES (450601, '市辖区', 450600, 450000);
INSERT INTO `sys_dim_county` VALUES (450602, '港口区', 450600, 450000);
INSERT INTO `sys_dim_county` VALUES (450603, '防城区', 450600, 450000);
INSERT INTO `sys_dim_county` VALUES (450621, '上思县', 450600, 450000);
INSERT INTO `sys_dim_county` VALUES (450681, '东兴市', 450600, 450000);
INSERT INTO `sys_dim_county` VALUES (450701, '市辖区', 450700, 450000);
INSERT INTO `sys_dim_county` VALUES (450702, '钦南区', 450700, 450000);
INSERT INTO `sys_dim_county` VALUES (450703, '钦北区', 450700, 450000);
INSERT INTO `sys_dim_county` VALUES (450721, '灵山县', 450700, 450000);
INSERT INTO `sys_dim_county` VALUES (450722, '浦北县', 450700, 450000);
INSERT INTO `sys_dim_county` VALUES (450801, '市辖区', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450802, '港北区', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450803, '港南区', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450804, '覃塘区', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450821, '平南县', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450881, '桂平市', 450800, 450000);
INSERT INTO `sys_dim_county` VALUES (450901, '市辖区', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450902, '玉州区', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450903, '福绵区', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450921, '容县', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450922, '陆川县', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450923, '博白县', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450924, '兴业县', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (450981, '北流市', 450900, 450000);
INSERT INTO `sys_dim_county` VALUES (451001, '市辖区', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451002, '右江区', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451021, '田阳县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451022, '田东县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451023, '平果县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451024, '德保县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451026, '那坡县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451027, '凌云县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451028, '乐业县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451029, '田林县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451030, '西林县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451031, '隆林各族自治县', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451081, '靖西市', 451000, 450000);
INSERT INTO `sys_dim_county` VALUES (451101, '市辖区', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451102, '八步区', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451103, '平桂区', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451121, '昭平县', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451122, '钟山县', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451123, '富川瑶族自治县', 451100, 450000);
INSERT INTO `sys_dim_county` VALUES (451201, '市辖区', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451202, '金城江区', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451221, '南丹县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451222, '天峨县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451223, '凤山县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451224, '东兰县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451225, '罗城仫佬族自治县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451226, '环江毛南族自治县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451227, '巴马瑶族自治县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451228, '都安瑶族自治县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451229, '大化瑶族自治县', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451281, '宜州市', 451200, 450000);
INSERT INTO `sys_dim_county` VALUES (451301, '市辖区', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451302, '兴宾区', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451321, '忻城县', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451322, '象州县', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451323, '武宣县', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451324, '金秀瑶族自治县', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451381, '合山市', 451300, 450000);
INSERT INTO `sys_dim_county` VALUES (451401, '市辖区', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451402, '江州区', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451421, '扶绥县', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451422, '宁明县', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451423, '龙州县', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451424, '大新县', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451425, '天等县', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (451481, '凭祥市', 451400, 450000);
INSERT INTO `sys_dim_county` VALUES (460101, '市辖区', 460100, 460000);
INSERT INTO `sys_dim_county` VALUES (460105, '秀英区', 460100, 460000);
INSERT INTO `sys_dim_county` VALUES (460106, '龙华区', 460100, 460000);
INSERT INTO `sys_dim_county` VALUES (460107, '琼山区', 460100, 460000);
INSERT INTO `sys_dim_county` VALUES (460108, '美兰区', 460100, 460000);
INSERT INTO `sys_dim_county` VALUES (460201, '市辖区', 460200, 460000);
INSERT INTO `sys_dim_county` VALUES (460202, '海棠区', 460200, 460000);
INSERT INTO `sys_dim_county` VALUES (460203, '吉阳区', 460200, 460000);
INSERT INTO `sys_dim_county` VALUES (460204, '天涯区', 460200, 460000);
INSERT INTO `sys_dim_county` VALUES (460205, '崖州区', 460200, 460000);
INSERT INTO `sys_dim_county` VALUES (469001, '五指山市', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469002, '琼海市', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469005, '文昌市', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469006, '万宁市', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469007, '东方市', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469021, '定安县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469022, '屯昌县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469023, '澄迈县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469024, '临高县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469025, '白沙黎族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469026, '昌江黎族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469027, '乐东黎族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469028, '陵水黎族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469029, '保亭黎族苗族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (469030, '琼中黎族苗族自治县', 469000, 460000);
INSERT INTO `sys_dim_county` VALUES (500101, '万州区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500102, '涪陵区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500103, '渝中区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500104, '大渡口区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500105, '江北区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500106, '沙坪坝区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500107, '九龙坡区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500108, '南岸区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500109, '北碚区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500110, '綦江区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500111, '大足区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500112, '渝北区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500113, '巴南区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500114, '黔江区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500115, '长寿区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500116, '江津区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500117, '合川区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500118, '永川区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500119, '南川区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500120, '璧山区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500151, '铜梁区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500152, '潼南区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500153, '荣昌区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500154, '开州区', 500100, 500000);
INSERT INTO `sys_dim_county` VALUES (500228, '梁平县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500229, '城口县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500230, '丰都县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500231, '垫江县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500232, '武隆县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500233, '忠县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500235, '云阳县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500236, '奉节县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500237, '巫山县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500238, '巫溪县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500240, '石柱土家族自治县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500241, '秀山土家族苗族自治县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500242, '酉阳土家族苗族自治县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (500243, '彭水苗族土家族自治县', 500200, 500000);
INSERT INTO `sys_dim_county` VALUES (510101, '市辖区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510104, '锦江区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510105, '青羊区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510106, '金牛区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510107, '武侯区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510108, '成华区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510112, '龙泉驿区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510113, '青白江区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510114, '新都区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510115, '温江区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510116, '双流区', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510121, '金堂县', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510124, '郫县', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510129, '大邑县', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510131, '蒲江县', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510132, '新津县', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510181, '都江堰市', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510182, '彭州市', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510183, '邛崃市', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510184, '崇州市', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510185, '简阳市', 510100, 510000);
INSERT INTO `sys_dim_county` VALUES (510301, '市辖区', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510302, '自流井区', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510303, '贡井区', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510304, '大安区', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510311, '沿滩区', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510321, '荣县', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510322, '富顺县', 510300, 510000);
INSERT INTO `sys_dim_county` VALUES (510401, '市辖区', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510402, '东区', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510403, '西区', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510411, '仁和区', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510421, '米易县', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510422, '盐边县', 510400, 510000);
INSERT INTO `sys_dim_county` VALUES (510501, '市辖区', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510502, '江阳区', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510503, '纳溪区', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510504, '龙马潭区', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510521, '泸县', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510522, '合江县', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510524, '叙永县', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510525, '古蔺县', 510500, 510000);
INSERT INTO `sys_dim_county` VALUES (510601, '市辖区', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510603, '旌阳区', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510623, '中江县', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510626, '罗江县', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510681, '广汉市', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510682, '什邡市', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510683, '绵竹市', 510600, 510000);
INSERT INTO `sys_dim_county` VALUES (510701, '市辖区', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510703, '涪城区', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510704, '游仙区', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510705, '安州区', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510722, '三台县', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510723, '盐亭县', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510725, '梓潼县', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510726, '北川羌族自治县', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510727, '平武县', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510781, '江油市', 510700, 510000);
INSERT INTO `sys_dim_county` VALUES (510801, '市辖区', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510802, '利州区', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510811, '昭化区', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510812, '朝天区', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510821, '旺苍县', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510822, '青川县', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510823, '剑阁县', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510824, '苍溪县', 510800, 510000);
INSERT INTO `sys_dim_county` VALUES (510901, '市辖区', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (510903, '船山区', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (510904, '安居区', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (510921, '蓬溪县', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (510922, '射洪县', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (510923, '大英县', 510900, 510000);
INSERT INTO `sys_dim_county` VALUES (511001, '市辖区', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511002, '市中区', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511011, '东兴区', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511024, '威远县', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511025, '资中县', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511028, '隆昌县', 511000, 510000);
INSERT INTO `sys_dim_county` VALUES (511101, '市辖区', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511102, '市中区', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511111, '沙湾区', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511112, '五通桥区', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511113, '金口河区', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511123, '犍为县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511124, '井研县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511126, '夹江县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511129, '沐川县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511132, '峨边彝族自治县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511133, '马边彝族自治县', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511181, '峨眉山市', 511100, 510000);
INSERT INTO `sys_dim_county` VALUES (511301, '市辖区', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511302, '顺庆区', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511303, '高坪区', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511304, '嘉陵区', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511321, '南部县', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511322, '营山县', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511323, '蓬安县', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511324, '仪陇县', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511325, '西充县', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511381, '阆中市', 511300, 510000);
INSERT INTO `sys_dim_county` VALUES (511401, '市辖区', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511402, '东坡区', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511403, '彭山区', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511421, '仁寿县', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511423, '洪雅县', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511424, '丹棱县', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511425, '青神县', 511400, 510000);
INSERT INTO `sys_dim_county` VALUES (511501, '市辖区', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511502, '翠屏区', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511503, '南溪区', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511521, '宜宾县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511523, '江安县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511524, '长宁县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511525, '高县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511526, '珙县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511527, '筠连县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511528, '兴文县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511529, '屏山县', 511500, 510000);
INSERT INTO `sys_dim_county` VALUES (511601, '市辖区', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511602, '广安区', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511603, '前锋区', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511621, '岳池县', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511622, '武胜县', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511623, '邻水县', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511681, '华蓥市', 511600, 510000);
INSERT INTO `sys_dim_county` VALUES (511701, '市辖区', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511702, '通川区', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511703, '达川区', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511722, '宣汉县', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511723, '开江县', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511724, '大竹县', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511725, '渠县', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511781, '万源市', 511700, 510000);
INSERT INTO `sys_dim_county` VALUES (511801, '市辖区', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511802, '雨城区', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511803, '名山区', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511822, '荥经县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511823, '汉源县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511824, '石棉县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511825, '天全县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511826, '芦山县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511827, '宝兴县', 511800, 510000);
INSERT INTO `sys_dim_county` VALUES (511901, '市辖区', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (511902, '巴州区', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (511903, '恩阳区', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (511921, '通江县', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (511922, '南江县', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (511923, '平昌县', 511900, 510000);
INSERT INTO `sys_dim_county` VALUES (512001, '市辖区', 512000, 510000);
INSERT INTO `sys_dim_county` VALUES (512002, '雁江区', 512000, 510000);
INSERT INTO `sys_dim_county` VALUES (512021, '安岳县', 512000, 510000);
INSERT INTO `sys_dim_county` VALUES (512022, '乐至县', 512000, 510000);
INSERT INTO `sys_dim_county` VALUES (513201, '马尔康市', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513221, '汶川县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513222, '理县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513223, '茂县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513224, '松潘县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513225, '九寨沟县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513226, '金川县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513227, '小金县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513228, '黑水县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513230, '壤塘县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513231, '阿坝县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513232, '若尔盖县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513233, '红原县', 513200, 510000);
INSERT INTO `sys_dim_county` VALUES (513301, '康定市', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513322, '泸定县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513323, '丹巴县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513324, '九龙县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513325, '雅江县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513326, '道孚县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513327, '炉霍县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513328, '甘孜县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513329, '新龙县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513330, '德格县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513331, '白玉县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513332, '石渠县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513333, '色达县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513334, '理塘县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513335, '巴塘县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513336, '乡城县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513337, '稻城县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513338, '得荣县', 513300, 510000);
INSERT INTO `sys_dim_county` VALUES (513401, '西昌市', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513422, '木里藏族自治县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513423, '盐源县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513424, '德昌县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513425, '会理县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513426, '会东县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513427, '宁南县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513428, '普格县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513429, '布拖县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513430, '金阳县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513431, '昭觉县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513432, '喜德县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513433, '冕宁县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513434, '越西县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513435, '甘洛县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513436, '美姑县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (513437, '雷波县', 513400, 510000);
INSERT INTO `sys_dim_county` VALUES (520101, '市辖区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520102, '南明区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520103, '云岩区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520111, '花溪区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520112, '乌当区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520113, '白云区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520115, '观山湖区', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520121, '开阳县', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520122, '息烽县', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520123, '修文县', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520181, '清镇市', 520100, 520000);
INSERT INTO `sys_dim_county` VALUES (520201, '钟山区', 520200, 520000);
INSERT INTO `sys_dim_county` VALUES (520203, '六枝特区', 520200, 520000);
INSERT INTO `sys_dim_county` VALUES (520221, '水城县', 520200, 520000);
INSERT INTO `sys_dim_county` VALUES (520222, '盘县', 520200, 520000);
INSERT INTO `sys_dim_county` VALUES (520301, '市辖区', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520302, '红花岗区', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520303, '汇川区', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520304, '播州区', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520322, '桐梓县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520323, '绥阳县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520324, '正安县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520325, '道真仡佬族苗族自治县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520326, '务川仡佬族苗族自治县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520327, '凤冈县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520328, '湄潭县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520329, '余庆县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520330, '习水县', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520381, '赤水市', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520382, '仁怀市', 520300, 520000);
INSERT INTO `sys_dim_county` VALUES (520401, '市辖区', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520402, '西秀区', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520403, '平坝区', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520422, '普定县', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520423, '镇宁布依族苗族自治县', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520424, '关岭布依族苗族自治县', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520425, '紫云苗族布依族自治县', 520400, 520000);
INSERT INTO `sys_dim_county` VALUES (520501, '市辖区', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520502, '七星关区', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520521, '大方县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520522, '黔西县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520523, '金沙县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520524, '织金县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520525, '纳雍县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520526, '威宁彝族回族苗族自治县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520527, '赫章县', 520500, 520000);
INSERT INTO `sys_dim_county` VALUES (520601, '市辖区', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520602, '碧江区', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520603, '万山区', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520621, '江口县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520622, '玉屏侗族自治县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520623, '石阡县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520624, '思南县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520625, '印江土家族苗族自治县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520626, '德江县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520627, '沿河土家族自治县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (520628, '松桃苗族自治县', 520600, 520000);
INSERT INTO `sys_dim_county` VALUES (522301, '兴义市', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522322, '兴仁县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522323, '普安县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522324, '晴隆县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522325, '贞丰县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522326, '望谟县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522327, '册亨县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522328, '安龙县', 522300, 520000);
INSERT INTO `sys_dim_county` VALUES (522601, '凯里市', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522622, '黄平县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522623, '施秉县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522624, '三穗县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522625, '镇远县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522626, '岑巩县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522627, '天柱县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522628, '锦屏县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522629, '剑河县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522630, '台江县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522631, '黎平县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522632, '榕江县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522633, '从江县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522634, '雷山县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522635, '麻江县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522636, '丹寨县', 522600, 520000);
INSERT INTO `sys_dim_county` VALUES (522701, '都匀市', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522702, '福泉市', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522722, '荔波县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522723, '贵定县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522725, '瓮安县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522726, '独山县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522727, '平塘县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522728, '罗甸县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522729, '长顺县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522730, '龙里县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522731, '惠水县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (522732, '三都水族自治县', 522700, 520000);
INSERT INTO `sys_dim_county` VALUES (530101, '市辖区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530102, '五华区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530103, '盘龙区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530111, '官渡区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530112, '西山区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530113, '东川区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530114, '呈贡区', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530122, '晋宁县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530124, '富民县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530125, '宜良县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530126, '石林彝族自治县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530127, '嵩明县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530128, '禄劝彝族苗族自治县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530129, '寻甸回族彝族自治县', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530181, '安宁市', 530100, 530000);
INSERT INTO `sys_dim_county` VALUES (530301, '市辖区', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530302, '麒麟区', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530303, '沾益区', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530321, '马龙县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530322, '陆良县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530323, '师宗县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530324, '罗平县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530325, '富源县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530326, '会泽县', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530381, '宣威市', 530300, 530000);
INSERT INTO `sys_dim_county` VALUES (530401, '市辖区', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530402, '红塔区', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530403, '江川区', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530422, '澄江县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530423, '通海县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530424, '华宁县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530425, '易门县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530426, '峨山彝族自治县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530427, '新平彝族傣族自治县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530428, '元江哈尼族彝族傣族自治县', 530400, 530000);
INSERT INTO `sys_dim_county` VALUES (530501, '市辖区', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530502, '隆阳区', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530521, '施甸县', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530523, '龙陵县', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530524, '昌宁县', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530581, '腾冲市', 530500, 530000);
INSERT INTO `sys_dim_county` VALUES (530601, '市辖区', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530602, '昭阳区', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530621, '鲁甸县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530622, '巧家县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530623, '盐津县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530624, '大关县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530625, '永善县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530626, '绥江县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530627, '镇雄县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530628, '彝良县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530629, '威信县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530630, '水富县', 530600, 530000);
INSERT INTO `sys_dim_county` VALUES (530701, '市辖区', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530702, '古城区', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530721, '玉龙纳西族自治县', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530722, '永胜县', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530723, '华坪县', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530724, '宁蒗彝族自治县', 530700, 530000);
INSERT INTO `sys_dim_county` VALUES (530801, '市辖区', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530802, '思茅区', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530821, '宁洱哈尼族彝族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530822, '墨江哈尼族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530823, '景东彝族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530824, '景谷傣族彝族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530825, '镇沅彝族哈尼族拉祜族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530826, '江城哈尼族彝族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530827, '孟连傣族拉祜族佤族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530828, '澜沧拉祜族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530829, '西盟佤族自治县', 530800, 530000);
INSERT INTO `sys_dim_county` VALUES (530901, '市辖区', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530902, '临翔区', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530921, '凤庆县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530922, '云县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530923, '永德县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530924, '镇康县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530925, '双江拉祜族佤族布朗族傣族自治县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530926, '耿马傣族佤族自治县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (530927, '沧源佤族自治县', 530900, 530000);
INSERT INTO `sys_dim_county` VALUES (532301, '楚雄市', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532322, '双柏县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532323, '牟定县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532324, '南华县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532325, '姚安县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532326, '大姚县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532327, '永仁县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532328, '元谋县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532329, '武定县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532331, '禄丰县', 532300, 530000);
INSERT INTO `sys_dim_county` VALUES (532501, '个旧市', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532502, '开远市', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532503, '蒙自市', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532504, '弥勒市', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532523, '屏边苗族自治县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532524, '建水县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532525, '石屏县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532527, '泸西县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532528, '元阳县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532529, '红河县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532530, '金平苗族瑶族傣族自治县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532531, '绿春县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532532, '河口瑶族自治县', 532500, 530000);
INSERT INTO `sys_dim_county` VALUES (532601, '文山市', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532622, '砚山县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532623, '西畴县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532624, '麻栗坡县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532625, '马关县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532626, '丘北县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532627, '广南县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532628, '富宁县', 532600, 530000);
INSERT INTO `sys_dim_county` VALUES (532801, '景洪市', 532800, 530000);
INSERT INTO `sys_dim_county` VALUES (532822, '勐海县', 532800, 530000);
INSERT INTO `sys_dim_county` VALUES (532823, '勐腊县', 532800, 530000);
INSERT INTO `sys_dim_county` VALUES (532901, '大理市', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532922, '漾濞彝族自治县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532923, '祥云县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532924, '宾川县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532925, '弥渡县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532926, '南涧彝族自治县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532927, '巍山彝族回族自治县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532928, '永平县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532929, '云龙县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532930, '洱源县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532931, '剑川县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (532932, '鹤庆县', 532900, 530000);
INSERT INTO `sys_dim_county` VALUES (533102, '瑞丽市', 533100, 530000);
INSERT INTO `sys_dim_county` VALUES (533103, '芒市', 533100, 530000);
INSERT INTO `sys_dim_county` VALUES (533122, '梁河县', 533100, 530000);
INSERT INTO `sys_dim_county` VALUES (533123, '盈江县', 533100, 530000);
INSERT INTO `sys_dim_county` VALUES (533124, '陇川县', 533100, 530000);
INSERT INTO `sys_dim_county` VALUES (533301, '泸水市', 533300, 530000);
INSERT INTO `sys_dim_county` VALUES (533323, '福贡县', 533300, 530000);
INSERT INTO `sys_dim_county` VALUES (533324, '贡山独龙族怒族自治县', 533300, 530000);
INSERT INTO `sys_dim_county` VALUES (533325, '兰坪白族普米族自治县', 533300, 530000);
INSERT INTO `sys_dim_county` VALUES (533401, '香格里拉市', 533400, 530000);
INSERT INTO `sys_dim_county` VALUES (533422, '德钦县', 533400, 530000);
INSERT INTO `sys_dim_county` VALUES (533423, '维西傈僳族自治县', 533400, 530000);
INSERT INTO `sys_dim_county` VALUES (540101, '市辖区', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540102, '城关区', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540103, '堆龙德庆区', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540121, '林周县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540122, '当雄县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540123, '尼木县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540124, '曲水县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540126, '达孜县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540127, '墨竹工卡县', 540100, 540000);
INSERT INTO `sys_dim_county` VALUES (540202, '桑珠孜区', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540221, '南木林县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540222, '江孜县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540223, '定日县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540224, '萨迦县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540225, '拉孜县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540226, '昂仁县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540227, '谢通门县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540228, '白朗县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540229, '仁布县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540230, '康马县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540231, '定结县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540232, '仲巴县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540233, '亚东县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540234, '吉隆县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540235, '聂拉木县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540236, '萨嘎县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540237, '岗巴县', 540200, 540000);
INSERT INTO `sys_dim_county` VALUES (540302, '卡若区', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540321, '江达县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540322, '贡觉县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540323, '类乌齐县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540324, '丁青县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540325, '察雅县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540326, '八宿县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540327, '左贡县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540328, '芒康县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540329, '洛隆县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540330, '边坝县', 540300, 540000);
INSERT INTO `sys_dim_county` VALUES (540402, '巴宜区', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540421, '工布江达县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540422, '米林县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540423, '墨脱县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540424, '波密县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540425, '察隅县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540426, '朗县', 540400, 540000);
INSERT INTO `sys_dim_county` VALUES (540501, '市辖区', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540502, '乃东区', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540521, '扎囊县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540522, '贡嘎县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540523, '桑日县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540524, '琼结县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540525, '曲松县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540526, '措美县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540527, '洛扎县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540528, '加查县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540529, '隆子县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540530, '错那县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (540531, '浪卡子县', 540500, 540000);
INSERT INTO `sys_dim_county` VALUES (542421, '那曲县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542422, '嘉黎县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542423, '比如县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542424, '聂荣县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542425, '安多县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542426, '申扎县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542427, '索县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542428, '班戈县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542429, '巴青县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542430, '尼玛县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542431, '双湖县', 542400, 540000);
INSERT INTO `sys_dim_county` VALUES (542521, '普兰县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542522, '札达县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542523, '噶尔县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542524, '日土县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542525, '革吉县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542526, '改则县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (542527, '措勤县', 542500, 540000);
INSERT INTO `sys_dim_county` VALUES (610101, '市辖区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610102, '新城区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610103, '碑林区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610104, '莲湖区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610111, '灞桥区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610112, '未央区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610113, '雁塔区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610114, '阎良区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610115, '临潼区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610116, '长安区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610117, '高陵区', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610122, '蓝田县', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610124, '周至县', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610125, '户县', 610100, 610000);
INSERT INTO `sys_dim_county` VALUES (610201, '市辖区', 610200, 610000);
INSERT INTO `sys_dim_county` VALUES (610202, '王益区', 610200, 610000);
INSERT INTO `sys_dim_county` VALUES (610203, '印台区', 610200, 610000);
INSERT INTO `sys_dim_county` VALUES (610204, '耀州区', 610200, 610000);
INSERT INTO `sys_dim_county` VALUES (610222, '宜君县', 610200, 610000);
INSERT INTO `sys_dim_county` VALUES (610301, '市辖区', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610302, '渭滨区', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610303, '金台区', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610304, '陈仓区', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610322, '凤翔县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610323, '岐山县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610324, '扶风县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610326, '眉县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610327, '陇县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610328, '千阳县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610329, '麟游县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610330, '凤县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610331, '太白县', 610300, 610000);
INSERT INTO `sys_dim_county` VALUES (610401, '市辖区', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610402, '秦都区', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610403, '杨陵区', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610404, '渭城区', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610422, '三原县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610423, '泾阳县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610424, '乾县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610425, '礼泉县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610426, '永寿县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610427, '彬县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610428, '长武县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610429, '旬邑县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610430, '淳化县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610431, '武功县', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610481, '兴平市', 610400, 610000);
INSERT INTO `sys_dim_county` VALUES (610501, '市辖区', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610502, '临渭区', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610503, '华州区', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610522, '潼关县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610523, '大荔县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610524, '合阳县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610525, '澄城县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610526, '蒲城县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610527, '白水县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610528, '富平县', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610581, '韩城市', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610582, '华阴市', 610500, 610000);
INSERT INTO `sys_dim_county` VALUES (610601, '市辖区', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610602, '宝塔区', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610603, '安塞区', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610621, '延长县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610622, '延川县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610623, '子长县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610625, '志丹县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610626, '吴起县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610627, '甘泉县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610628, '富县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610629, '洛川县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610630, '宜川县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610631, '黄龙县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610632, '黄陵县', 610600, 610000);
INSERT INTO `sys_dim_county` VALUES (610701, '市辖区', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610702, '汉台区', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610721, '南郑县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610722, '城固县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610723, '洋县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610724, '西乡县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610725, '勉县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610726, '宁强县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610727, '略阳县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610728, '镇巴县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610729, '留坝县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610730, '佛坪县', 610700, 610000);
INSERT INTO `sys_dim_county` VALUES (610801, '市辖区', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610802, '榆阳区', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610803, '横山区', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610821, '神木县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610822, '府谷县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610824, '靖边县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610825, '定边县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610826, '绥德县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610827, '米脂县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610828, '佳县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610829, '吴堡县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610830, '清涧县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610831, '子洲县', 610800, 610000);
INSERT INTO `sys_dim_county` VALUES (610901, '市辖区', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610902, '汉滨区', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610921, '汉阴县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610922, '石泉县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610923, '宁陕县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610924, '紫阳县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610925, '岚皋县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610926, '平利县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610927, '镇坪县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610928, '旬阳县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (610929, '白河县', 610900, 610000);
INSERT INTO `sys_dim_county` VALUES (611001, '市辖区', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611002, '商州区', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611021, '洛南县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611022, '丹凤县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611023, '商南县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611024, '山阳县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611025, '镇安县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (611026, '柞水县', 611000, 610000);
INSERT INTO `sys_dim_county` VALUES (620101, '市辖区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620102, '城关区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620103, '七里河区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620104, '西固区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620105, '安宁区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620111, '红古区', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620121, '永登县', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620122, '皋兰县', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620123, '榆中县', 620100, 620000);
INSERT INTO `sys_dim_county` VALUES (620201, '市辖区', 620200, 620000);
INSERT INTO `sys_dim_county` VALUES (620301, '市辖区', 620300, 620000);
INSERT INTO `sys_dim_county` VALUES (620302, '金川区', 620300, 620000);
INSERT INTO `sys_dim_county` VALUES (620321, '永昌县', 620300, 620000);
INSERT INTO `sys_dim_county` VALUES (620401, '市辖区', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620402, '白银区', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620403, '平川区', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620421, '靖远县', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620422, '会宁县', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620423, '景泰县', 620400, 620000);
INSERT INTO `sys_dim_county` VALUES (620501, '市辖区', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620502, '秦州区', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620503, '麦积区', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620521, '清水县', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620522, '秦安县', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620523, '甘谷县', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620524, '武山县', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620525, '张家川回族自治县', 620500, 620000);
INSERT INTO `sys_dim_county` VALUES (620601, '市辖区', 620600, 620000);
INSERT INTO `sys_dim_county` VALUES (620602, '凉州区', 620600, 620000);
INSERT INTO `sys_dim_county` VALUES (620621, '民勤县', 620600, 620000);
INSERT INTO `sys_dim_county` VALUES (620622, '古浪县', 620600, 620000);
INSERT INTO `sys_dim_county` VALUES (620623, '天祝藏族自治县', 620600, 620000);
INSERT INTO `sys_dim_county` VALUES (620701, '市辖区', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620702, '甘州区', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620721, '肃南裕固族自治县', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620722, '民乐县', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620723, '临泽县', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620724, '高台县', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620725, '山丹县', 620700, 620000);
INSERT INTO `sys_dim_county` VALUES (620801, '市辖区', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620802, '崆峒区', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620821, '泾川县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620822, '灵台县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620823, '崇信县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620824, '华亭县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620825, '庄浪县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620826, '静宁县', 620800, 620000);
INSERT INTO `sys_dim_county` VALUES (620901, '市辖区', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620902, '肃州区', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620921, '金塔县', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620922, '瓜州县', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620923, '肃北蒙古族自治县', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620924, '阿克塞哈萨克族自治县', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620981, '玉门市', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (620982, '敦煌市', 620900, 620000);
INSERT INTO `sys_dim_county` VALUES (621001, '市辖区', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621002, '西峰区', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621021, '庆城县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621022, '环县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621023, '华池县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621024, '合水县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621025, '正宁县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621026, '宁县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621027, '镇原县', 621000, 620000);
INSERT INTO `sys_dim_county` VALUES (621101, '市辖区', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621102, '安定区', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621121, '通渭县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621122, '陇西县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621123, '渭源县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621124, '临洮县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621125, '漳县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621126, '岷县', 621100, 620000);
INSERT INTO `sys_dim_county` VALUES (621201, '市辖区', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621202, '武都区', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621221, '成县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621222, '文县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621223, '宕昌县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621224, '康县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621225, '西和县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621226, '礼县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621227, '徽县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (621228, '两当县', 621200, 620000);
INSERT INTO `sys_dim_county` VALUES (622901, '临夏市', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622921, '临夏县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622922, '康乐县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622923, '永靖县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622924, '广河县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622925, '和政县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622926, '东乡族自治县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (622927, '积石山保安族东乡族撒拉族自治县', 622900, 620000);
INSERT INTO `sys_dim_county` VALUES (623001, '合作市', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623021, '临潭县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623022, '卓尼县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623023, '舟曲县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623024, '迭部县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623025, '玛曲县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623026, '碌曲县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (623027, '夏河县', 623000, 620000);
INSERT INTO `sys_dim_county` VALUES (630101, '市辖区', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630102, '城东区', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630103, '城中区', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630104, '城西区', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630105, '城北区', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630121, '大通回族土族自治县', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630122, '湟中县', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630123, '湟源县', 630100, 630000);
INSERT INTO `sys_dim_county` VALUES (630202, '乐都区', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (630203, '平安区', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (630222, '民和回族土族自治县', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (630223, '互助土族自治县', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (630224, '化隆回族自治县', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (630225, '循化撒拉族自治县', 630200, 630000);
INSERT INTO `sys_dim_county` VALUES (632221, '门源回族自治县', 632200, 630000);
INSERT INTO `sys_dim_county` VALUES (632222, '祁连县', 632200, 630000);
INSERT INTO `sys_dim_county` VALUES (632223, '海晏县', 632200, 630000);
INSERT INTO `sys_dim_county` VALUES (632224, '刚察县', 632200, 630000);
INSERT INTO `sys_dim_county` VALUES (632321, '同仁县', 632300, 630000);
INSERT INTO `sys_dim_county` VALUES (632322, '尖扎县', 632300, 630000);
INSERT INTO `sys_dim_county` VALUES (632323, '泽库县', 632300, 630000);
INSERT INTO `sys_dim_county` VALUES (632324, '河南蒙古族自治县', 632300, 630000);
INSERT INTO `sys_dim_county` VALUES (632521, '共和县', 632500, 630000);
INSERT INTO `sys_dim_county` VALUES (632522, '同德县', 632500, 630000);
INSERT INTO `sys_dim_county` VALUES (632523, '贵德县', 632500, 630000);
INSERT INTO `sys_dim_county` VALUES (632524, '兴海县', 632500, 630000);
INSERT INTO `sys_dim_county` VALUES (632525, '贵南县', 632500, 630000);
INSERT INTO `sys_dim_county` VALUES (632621, '玛沁县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632622, '班玛县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632623, '甘德县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632624, '达日县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632625, '久治县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632626, '玛多县', 632600, 630000);
INSERT INTO `sys_dim_county` VALUES (632701, '玉树市', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632722, '杂多县', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632723, '称多县', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632724, '治多县', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632725, '囊谦县', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632726, '曲麻莱县', 632700, 630000);
INSERT INTO `sys_dim_county` VALUES (632801, '格尔木市', 632800, 630000);
INSERT INTO `sys_dim_county` VALUES (632802, '德令哈市', 632800, 630000);
INSERT INTO `sys_dim_county` VALUES (632821, '乌兰县', 632800, 630000);
INSERT INTO `sys_dim_county` VALUES (632822, '都兰县', 632800, 630000);
INSERT INTO `sys_dim_county` VALUES (632823, '天峻县', 632800, 630000);
INSERT INTO `sys_dim_county` VALUES (640101, '市辖区', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640104, '兴庆区', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640105, '西夏区', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640106, '金凤区', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640121, '永宁县', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640122, '贺兰县', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640181, '灵武市', 640100, 640000);
INSERT INTO `sys_dim_county` VALUES (640201, '市辖区', 640200, 640000);
INSERT INTO `sys_dim_county` VALUES (640202, '大武口区', 640200, 640000);
INSERT INTO `sys_dim_county` VALUES (640205, '惠农区', 640200, 640000);
INSERT INTO `sys_dim_county` VALUES (640221, '平罗县', 640200, 640000);
INSERT INTO `sys_dim_county` VALUES (640301, '市辖区', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640302, '利通区', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640303, '红寺堡区', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640323, '盐池县', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640324, '同心县', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640381, '青铜峡市', 640300, 640000);
INSERT INTO `sys_dim_county` VALUES (640401, '市辖区', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640402, '原州区', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640422, '西吉县', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640423, '隆德县', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640424, '泾源县', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640425, '彭阳县', 640400, 640000);
INSERT INTO `sys_dim_county` VALUES (640501, '市辖区', 640500, 640000);
INSERT INTO `sys_dim_county` VALUES (640502, '沙坡头区', 640500, 640000);
INSERT INTO `sys_dim_county` VALUES (640521, '中宁县', 640500, 640000);
INSERT INTO `sys_dim_county` VALUES (640522, '海原县', 640500, 640000);
INSERT INTO `sys_dim_county` VALUES (650101, '市辖区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650102, '天山区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650103, '沙依巴克区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650104, '新市区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650105, '水磨沟区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650106, '头屯河区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650107, '达坂城区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650109, '米东区', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650121, '乌鲁木齐县', 650100, 650000);
INSERT INTO `sys_dim_county` VALUES (650201, '市辖区', 650200, 650000);
INSERT INTO `sys_dim_county` VALUES (650202, '独山子区', 650200, 650000);
INSERT INTO `sys_dim_county` VALUES (650203, '克拉玛依区', 650200, 650000);
INSERT INTO `sys_dim_county` VALUES (650204, '白碱滩区', 650200, 650000);
INSERT INTO `sys_dim_county` VALUES (650205, '乌尔禾区', 650200, 650000);
INSERT INTO `sys_dim_county` VALUES (650402, '高昌区', 650400, 650000);
INSERT INTO `sys_dim_county` VALUES (650421, '鄯善县', 650400, 650000);
INSERT INTO `sys_dim_county` VALUES (650422, '托克逊县', 650400, 650000);
INSERT INTO `sys_dim_county` VALUES (650502, '伊州区', 650500, 650000);
INSERT INTO `sys_dim_county` VALUES (650521, '巴里坤哈萨克自治县', 650500, 650000);
INSERT INTO `sys_dim_county` VALUES (650522, '伊吾县', 650500, 650000);
INSERT INTO `sys_dim_county` VALUES (652301, '昌吉市', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652302, '阜康市', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652323, '呼图壁县', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652324, '玛纳斯县', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652325, '奇台县', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652327, '吉木萨尔县', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652328, '木垒哈萨克自治县', 652300, 650000);
INSERT INTO `sys_dim_county` VALUES (652701, '博乐市', 652700, 650000);
INSERT INTO `sys_dim_county` VALUES (652702, '阿拉山口市', 652700, 650000);
INSERT INTO `sys_dim_county` VALUES (652722, '精河县', 652700, 650000);
INSERT INTO `sys_dim_county` VALUES (652723, '温泉县', 652700, 650000);
INSERT INTO `sys_dim_county` VALUES (652801, '库尔勒市', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652822, '轮台县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652823, '尉犁县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652824, '若羌县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652825, '且末县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652826, '焉耆回族自治县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652827, '和静县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652828, '和硕县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652829, '博湖县', 652800, 650000);
INSERT INTO `sys_dim_county` VALUES (652901, '阿克苏市', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652922, '温宿县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652923, '库车县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652924, '沙雅县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652925, '新和县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652926, '拜城县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652927, '乌什县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652928, '阿瓦提县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (652929, '柯坪县', 652900, 650000);
INSERT INTO `sys_dim_county` VALUES (653001, '阿图什市', 653000, 650000);
INSERT INTO `sys_dim_county` VALUES (653022, '阿克陶县', 653000, 650000);
INSERT INTO `sys_dim_county` VALUES (653023, '阿合奇县', 653000, 650000);
INSERT INTO `sys_dim_county` VALUES (653024, '乌恰县', 653000, 650000);
INSERT INTO `sys_dim_county` VALUES (653101, '喀什市', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653121, '疏附县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653122, '疏勒县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653123, '英吉沙县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653124, '泽普县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653125, '莎车县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653126, '叶城县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653127, '麦盖提县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653128, '岳普湖县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653129, '伽师县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653130, '巴楚县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653131, '塔什库尔干塔吉克自治县', 653100, 650000);
INSERT INTO `sys_dim_county` VALUES (653201, '和田市', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653221, '和田县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653222, '墨玉县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653223, '皮山县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653224, '洛浦县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653225, '策勒县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653226, '于田县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (653227, '民丰县', 653200, 650000);
INSERT INTO `sys_dim_county` VALUES (654002, '伊宁市', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654003, '奎屯市', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654004, '霍尔果斯市', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654021, '伊宁县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654022, '察布查尔锡伯自治县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654023, '霍城县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654024, '巩留县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654025, '新源县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654026, '昭苏县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654027, '特克斯县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654028, '尼勒克县', 654000, 650000);
INSERT INTO `sys_dim_county` VALUES (654201, '塔城市', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654202, '乌苏市', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654221, '额敏县', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654223, '沙湾县', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654224, '托里县', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654225, '裕民县', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654226, '和布克赛尔蒙古自治县', 654200, 650000);
INSERT INTO `sys_dim_county` VALUES (654301, '阿勒泰市', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654321, '布尔津县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654322, '富蕴县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654323, '福海县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654324, '哈巴河县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654325, '青河县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (654326, '吉木乃县', 654300, 650000);
INSERT INTO `sys_dim_county` VALUES (659001, '石河子市', 659000, 650000);
INSERT INTO `sys_dim_county` VALUES (659002, '阿拉尔市', 659000, 650000);
INSERT INTO `sys_dim_county` VALUES (659003, '图木舒克市', 659000, 650000);
INSERT INTO `sys_dim_county` VALUES (659004, '五家渠市', 659000, 650000);
INSERT INTO `sys_dim_county` VALUES (659006, '铁门关市', 659000, 650000);

-- ----------------------------
-- Table structure for sys_dim_province
-- ----------------------------
DROP TABLE IF EXISTS `sys_dim_province`;
CREATE TABLE `sys_dim_province`  (
  `province_code` int(11) NOT NULL COMMENT '省份编码',
  `province_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份名称',
  PRIMARY KEY (`province_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '省份' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dim_province
-- ----------------------------
INSERT INTO `sys_dim_province` VALUES (110000, '北京市');
INSERT INTO `sys_dim_province` VALUES (120000, '天津市');
INSERT INTO `sys_dim_province` VALUES (130000, '河北省');
INSERT INTO `sys_dim_province` VALUES (140000, '山西省');
INSERT INTO `sys_dim_province` VALUES (150000, '内蒙古自治区');
INSERT INTO `sys_dim_province` VALUES (210000, '辽宁省');
INSERT INTO `sys_dim_province` VALUES (220000, '吉林省');
INSERT INTO `sys_dim_province` VALUES (230000, '黑龙江省');
INSERT INTO `sys_dim_province` VALUES (310000, '上海市');
INSERT INTO `sys_dim_province` VALUES (320000, '江苏省');
INSERT INTO `sys_dim_province` VALUES (330000, '浙江省');
INSERT INTO `sys_dim_province` VALUES (340000, '安徽省');
INSERT INTO `sys_dim_province` VALUES (350000, '福建省');
INSERT INTO `sys_dim_province` VALUES (360000, '江西省');
INSERT INTO `sys_dim_province` VALUES (370000, '山东省');
INSERT INTO `sys_dim_province` VALUES (410000, '河南省');
INSERT INTO `sys_dim_province` VALUES (420000, '湖北省');
INSERT INTO `sys_dim_province` VALUES (430000, '湖南省');
INSERT INTO `sys_dim_province` VALUES (440000, '广东省');
INSERT INTO `sys_dim_province` VALUES (450000, '广西壮族自治区');
INSERT INTO `sys_dim_province` VALUES (460000, '海南省');
INSERT INTO `sys_dim_province` VALUES (500000, '重庆市');
INSERT INTO `sys_dim_province` VALUES (510000, '四川省');
INSERT INTO `sys_dim_province` VALUES (520000, '贵州');
INSERT INTO `sys_dim_province` VALUES (530000, '云南省');
INSERT INTO `sys_dim_province` VALUES (540000, '西藏自治区');
INSERT INTO `sys_dim_province` VALUES (610000, '陕西省');
INSERT INTO `sys_dim_province` VALUES (620000, '甘肃省');
INSERT INTO `sys_dim_province` VALUES (630000, '青海省');
INSERT INTO `sys_dim_province` VALUES (640000, '宁夏回族自治区');
INSERT INTO `sys_dim_province` VALUES (650000, '新疆维吾尔自治区');

-- ----------------------------
-- Table structure for sys_email_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_email_log`;
CREATE TABLE `sys_email_log`  (
  `log_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `subject` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型(0--文本,1--html,2--图片,3--附件,4--模板)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送状态(0--成功,1--失败)',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除,1--已删除）',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件发送日志记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_email_log
-- ----------------------------
INSERT INTO `sys_email_log` VALUES ('1', '1446108432@qq.com', '测试邮件', '1', '0', '1', '2020-11-05 11:08:15', '0', '2020-11-05 11:08:15', '0');
INSERT INTO `sys_email_log` VALUES ('10', '184974699@qq.com', '我是测试的2', '1', '0', '1', '2021-04-15 23:53:51', '0', '2021-04-15 23:53:51', '0');
INSERT INTO `sys_email_log` VALUES ('11', '184974699@qq.com', '测试测试测试', '4', '0', '1', '2021-04-15 23:55:31', '0', '2021-04-15 23:55:31', '0');
INSERT INTO `sys_email_log` VALUES ('12', '184974699@qq.com', 'eqweqweqwewq', '0', '0', NULL, '2021-04-17 02:48:44', '0', '2021-04-17 02:48:44', '0');
INSERT INTO `sys_email_log` VALUES ('13', '184974699@qq.com', 'eqweqweqwewq', '0', '0', NULL, '2021-04-17 02:49:51', '0', '2021-04-17 02:49:51', '0');
INSERT INTO `sys_email_log` VALUES ('14', '184974699@qq.com', '8888', '0', '0', NULL, '2021-04-17 02:55:23', '0', '2021-04-17 02:55:23', '0');
INSERT INTO `sys_email_log` VALUES ('15', '184974699@qq.com', 'eqweqweqwewq', '1', '0', NULL, '2021-04-17 03:04:29', '0', '2021-04-17 03:04:29', '0');
INSERT INTO `sys_email_log` VALUES ('1518518996698038272', '184974699@qq.com', '我又测试邮件了', '0', '0', '1', '2022-04-25 17:15:16', NULL, '2022-04-25 17:15:16', '0');
INSERT INTO `sys_email_log` VALUES ('1518520581784240128', '184974699@qq.com', 'dd', '4', '0', '1', '2022-04-25 17:21:34', NULL, '2022-04-25 17:21:34', '0');
INSERT INTO `sys_email_log` VALUES ('16', '184974699@qq.com', 'eqweqweqwewq', '0', '0', NULL, '2021-04-17 03:05:15', '0', '2021-04-17 03:05:15', '0');
INSERT INTO `sys_email_log` VALUES ('17', '184974699@qq.com', 'eqweqweqwewq', '0', '0', NULL, '2021-04-17 03:06:36', '0', '2021-04-17 03:06:36', '0');
INSERT INTO `sys_email_log` VALUES ('18', '184974699@qq.com', '8888', '0', '0', NULL, '2021-04-17 03:11:10', '0', '2021-04-17 03:11:10', '0');
INSERT INTO `sys_email_log` VALUES ('19', '184974699@qq.com', '测试html邮件', '1', '0', NULL, '2021-04-17 03:11:43', '0', '2021-04-17 03:11:43', '0');
INSERT INTO `sys_email_log` VALUES ('2', '1446108432@qq.com', '文本邮件', '0', '0', '1', '2020-11-05 11:25:27', '0', '2020-11-05 11:25:27', '0');
INSERT INTO `sys_email_log` VALUES ('20', '184974699@qq.com', '测试模板邮件', '4', '0', NULL, '2021-04-17 03:13:01', '0', '2021-04-17 03:13:01', '0');
INSERT INTO `sys_email_log` VALUES ('21', '184974699@qq.com', '测试111邮箱', '0', '1', NULL, '2021-04-17 03:32:27', '0', '2021-04-17 03:32:27', '0');
INSERT INTO `sys_email_log` VALUES ('22', '184974699@qq.com', '测试111邮箱', '0', '0', NULL, '2021-04-17 03:35:02', '0', '2021-04-17 03:35:02', '0');
INSERT INTO `sys_email_log` VALUES ('23', '184974699@qq.com', '测试111邮箱', '0', '0', NULL, '2021-07-28 20:46:20', '0', '2021-07-28 20:46:20', '0');
INSERT INTO `sys_email_log` VALUES ('24', '184974699@qq.com', '来自meco-server的邮件', '0', '1', NULL, '2021-08-21 20:10:58', '0', '2021-08-21 20:10:58', '0');
INSERT INTO `sys_email_log` VALUES ('25', '184974699@qq.com', '来自meco-server的邮件', '0', '1', NULL, '2021-08-21 20:14:02', '0', '2021-08-21 20:14:02', '0');
INSERT INTO `sys_email_log` VALUES ('3', '1446108432@qq.com', '入职通知书', '1', '0', '1', '2020-11-05 12:07:07', '0', '2020-11-05 12:07:07', '0');
INSERT INTO `sys_email_log` VALUES ('4', '1446108432@qq.com', '账户注册验证', '4', '1', '1', '2020-11-05 14:29:48', '0', '2020-11-05 14:29:48', '0');
INSERT INTO `sys_email_log` VALUES ('5', '1446108432@qq.com', '账户注册验证', '4', '0', '1', '2020-11-05 14:30:36', '0', '2020-11-05 14:30:36', '0');
INSERT INTO `sys_email_log` VALUES ('6', '1446108432@qq.com', '测试', '0', '0', '1', '2020-12-04 15:10:46', '0', '2020-12-04 15:10:46', '0');
INSERT INTO `sys_email_log` VALUES ('7', 'leisure@petalmail.com', '测试发送邮件', '0', '0', '1', '2021-04-15 23:36:21', '0', '2021-04-15 23:36:21', '0');
INSERT INTO `sys_email_log` VALUES ('8', '184974699@qq.com', '菜单', '0', '0', '1', '2021-04-15 23:37:40', '0', '2021-04-15 23:37:40', '0');
INSERT INTO `sys_email_log` VALUES ('9', '184974699@qq.com', '我是测试的', '0', '0', '1', '2021-04-15 23:52:42', '0', '2021-04-15 23:52:42', '0');

-- ----------------------------
-- Table structure for sys_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_files`;
CREATE TABLE `sys_files`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `suffix` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件后缀（png、doc等）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除1--已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统附件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_files
-- ----------------------------
INSERT INTO `sys_files` VALUES (1, '66666.png', '66666.png', 'png', '1', '2021-01-11 17:35:03', '1');
INSERT INTO `sys_files` VALUES (2, '张伟-请坐.jpg', 'business/20210111/325b66c0-8f00-4e81-ac5d-62093c17e5e4.jpg', 'jpg', '0', '2021-01-11 20:09:50', '1');
INSERT INTO `sys_files` VALUES (3, '张伟-专业团队.jpg', 'business/20210111/ec5d63fb-6e28-4f8f-ac0c-56446f75fc12.jpg', 'jpg', '0', '2021-01-11 20:10:34', '1');
INSERT INTO `sys_files` VALUES (4, 'cat.jpg', 'default/20210111/39c6083d-2348-4cb2-9b9f-1ed289f5d333.jpg', 'jpg', '0', '2021-01-11 20:47:37', '0');
INSERT INTO `sys_files` VALUES (5, 'timg.png', 'default/20210111/e621c49b-fee5-46fa-a0aa-f1812479d4ac.png', 'png', '0', '2021-01-11 20:50:11', '0');

-- ----------------------------
-- Table structure for sys_id_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_id_table`;
CREATE TABLE `sys_id_table`  (
  `id_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `id_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务流水号编码',
  `id_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务流水号名称',
  `id_value` int(11) NOT NULL COMMENT '业务流水号当前值',
  `id_length` int(11) NOT NULL COMMENT '业务流水号长度',
  `has_prefix` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否有前缀 0有，1没有',
  `id_prefix` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务流水号前缀',
  `has_suffix` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否有后缀 0有，1没有',
  `id_suffix` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务流水号后缀',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统流水号表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_id_table
-- ----------------------------
INSERT INTO `sys_id_table` VALUES ('1459791704598126593', 'id_test_one', '测试流水号1', 290, 10, '0', 'BB', '0', 'yyyy', '测试流水号1', '2021-03-28 15:40:41', '2022-04-13 23:27:48', NULL, '1');
INSERT INTO `sys_id_table` VALUES ('1459791704598126594', 'id_test_1', '测试流水号2', 1, 10, '0', 'te', '0', 'ster', '测试流水号2', '2021-04-11 15:15:30', '2022-04-06 22:35:47', '1', '1');

-- ----------------------------
-- Table structure for sys_itfc_key
-- ----------------------------
DROP TABLE IF EXISTS `sys_itfc_key`;
CREATE TABLE `sys_itfc_key`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `itfc_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密钥',
  `owner` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用方',
  `descript` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--使用1--停用）',
  `valid_period` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key的有效期',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除1--已删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'itfc服务密钥信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_itfc_key
-- ----------------------------
INSERT INTO `sys_itfc_key` VALUES (1, 'dsasdutepwkwsdasd', '测试key', '测试key1', '0', '20210728', '0', '0', '2021-06-11 21:15:43', '1', '2021-07-10 22:53:48');
INSERT INTO `sys_itfc_key` VALUES (71, 'ee81d86d98a845c1a390afdd27139fe0', '中正有限责任公司', '中正有限责任公司\n', '0', '20210831', '0', '1', '2021-06-25 22:50:48', '1', '2022-04-06 14:08:29');
INSERT INTO `sys_itfc_key` VALUES (72, '95ba4c7b78bb46ca89e74b67e01bb0f4', 'dsa', 'sda', '0', '20210610', '1', '1', '2021-06-25 22:54:42', '0', '2021-06-26 11:22:43');
INSERT INTO `sys_itfc_key` VALUES (73, '28a6fc8997be4fb293eff1434061eb12', 'XO软件技术公司', 'XO软件技术公司', '0', '20211023', '0', '1', '2021-07-18 22:44:21', '1', '2021-10-23 16:59:21');
INSERT INTO `sys_itfc_key` VALUES (74, '2daf06fb0a6c4dd5897c784e17a700b0', '4444', '44444', '0', '20211117', '1', '1', '2021-11-17 22:17:03', '1', '2021-11-17 22:18:01');

-- ----------------------------
-- Table structure for sys_itfc_key_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_itfc_key_permission`;
CREATE TABLE `sys_itfc_key_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `itfc_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密钥',
  `itfc_permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'itfc密钥权限对照表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_itfc_key_permission
-- ----------------------------
INSERT INTO `sys_itfc_key_permission` VALUES (103, 'ee81d86d98a845c1a390afdd27139fe0', '1403605968955363329', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (104, 'ee81d86d98a845c1a390afdd27139fe0', '1414451450911858688', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (105, 'ee81d86d98a845c1a390afdd27139fe0', '1403605968955363332', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (106, 'ee81d86d98a845c1a390afdd27139fe0', '1403605968955363328', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (107, 'ee81d86d98a845c1a390afdd27139fe0', '1403605968951169031', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (108, 'ee81d86d98a845c1a390afdd27139fe0', '1414451700946903040', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (109, 'ee81d86d98a845c1a390afdd27139fe0', '1414454980065243136', '2021-07-18 22:43:48', '2021-07-18 22:43:48');
INSERT INTO `sys_itfc_key_permission` VALUES (115, 'dsasdutepwkwsdasd', '1414451700946903040', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (116, 'dsasdutepwkwsdasd', '1414454980065243136', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (117, 'dsasdutepwkwsdasd', '1403605968955363329', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (118, 'dsasdutepwkwsdasd', '1414451450911858688', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (119, 'dsasdutepwkwsdasd', '1403605968955363332', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (120, 'dsasdutepwkwsdasd', '1403605968951169031', '2021-10-23 16:58:28', '2021-10-23 16:58:28');
INSERT INTO `sys_itfc_key_permission` VALUES (121, 'dsasdutepwkwsdasd', '1403605968955363328', '2021-10-23 16:58:28', '2021-10-23 16:58:28');

-- ----------------------------
-- Table structure for sys_itfc_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_itfc_permission`;
CREATE TABLE `sys_itfc_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型（0是模块，1是具体接口）',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源值',
  `descript` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--正常1--停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除1--已删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'itfc服务权限信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_itfc_permission
-- ----------------------------
INSERT INTO `sys_itfc_permission` VALUES (71, '1403605968955363329', '0', '登陆日志rest服务', '0', 'itfc:loginlog', '登陆日志rest服务', '0', '0', '0', '2021-06-12 14:53:33', '0', '2021-07-17 15:28:35');
INSERT INTO `sys_itfc_permission` VALUES (72, '1403605968955363328', '1403605968955363329', 'getAllLoginLog', '1', 'itfc:loginlog:getAllLoginLog', '1', '0', '0', '0', '2021-06-12 14:55:46', '0', '2021-07-17 15:28:38');
INSERT INTO `sys_itfc_permission` VALUES (73, '1403605968951169031', '1403605968955363329', 'getSomeLoginLog', '1', 'itfc:loginlog:getSomeLoginLog', '2', '0', '0', '0', '2021-06-12 14:56:19', '0', '2021-07-17 15:28:42');
INSERT INTO `sys_itfc_permission` VALUES (74, '1403605968955363332', '1403605968955363329', 'updateLoginLog', '1', 'update:LoginLog', 'update:LoginLog3222', '0', '0', '0', '2021-07-01 20:01:17', '1', '2021-07-11 21:37:57');
INSERT INTO `sys_itfc_permission` VALUES (75, '1414451450911858688', '1403605968955363329', 'insertLoginLog', '1', 'insert:LoginLog', 'weqweq326', '0', '0', '1', '2021-07-12 13:07:59', '1', '2021-10-23 17:09:59');
INSERT INTO `sys_itfc_permission` VALUES (76, '1414451700946903040', '0', '操作日志rest服务', '0', 'getAll:operaLog', 'getAll:operaLog2', '0', '0', '1', '2021-07-12 13:08:59', '1', '2021-07-12 22:09:20');
INSERT INTO `sys_itfc_permission` VALUES (77, '1414454980065243136', '1414451700946903040', '查询', '1', 'operatelog:list', '666', '0', '0', '1', '2021-07-12 13:22:01', '1', '2021-10-23 17:10:26');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '创建者',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '更新人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('0432fdbbd21a4331aec44d4b7cfff77b', '系统无参定时器测试', 'DEFAULT', 'bluewindTask.bwNoParams', '*/10 * * * * ?', '3', '0', '1', '1', '1', '2021-08-28 16:32:31', '2022-04-12 00:07:29', NULL);
INSERT INTO `sys_job` VALUES ('d8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '0/5 * * * * ?', '3', '0', '1', '1', '1', '2021-08-28 13:26:41', '2021-11-14 15:55:58', NULL);
INSERT INTO `sys_job` VALUES ('d9b93921aafe45b7989edf305da704be', '测试定时器3', 'DEFAULT', 'bluewindTask.bwNoParams', '4 * * * * ? *', '3', '1', '1', '1', '1', '2021-09-11 10:58:47', '2021-09-19 11:04:18', NULL);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务日志ID',
  `job_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
INSERT INTO `sys_job_log` VALUES ('1459791704598126592', '0432fdbbd21a4331aec44d4b7cfff77b', '系统测试3', 'DEFAULT', 'bluewindTask.bwNoParams', '系统测试3 总共耗时：2毫秒', '0', '', '2021-11-14 15:53:59');
INSERT INTO `sys_job_log` VALUES ('1459792067887767552', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：1毫秒', '0', '', '2021-11-14 15:55:25');
INSERT INTO `sys_job_log` VALUES ('1459792089027059712', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-14 15:55:30');
INSERT INTO `sys_job_log` VALUES ('1459792110006968320', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-14 15:55:35');
INSERT INTO `sys_job_log` VALUES ('1459792130970099712', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-14 15:55:40');
INSERT INTO `sys_job_log` VALUES ('1459792151950008320', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-14 15:55:45');
INSERT INTO `sys_job_log` VALUES ('1459792172736978944', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：1毫秒', '0', '', '2021-11-14 15:55:50');
INSERT INTO `sys_job_log` VALUES ('1459792193700110336', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-14 15:55:55');
INSERT INTO `sys_job_log` VALUES ('1460984515243339776', '0432fdbbd21a4331aec44d4b7cfff77b', '系统无参定时器测试', 'DEFAULT', 'bluewindTask.bwNoParams', '系统无参定时器测试 总共耗时：1毫秒', '0', '', '2021-11-17 22:53:45');
INSERT INTO `sys_job_log` VALUES ('1460984552157409280', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2021-11-17 22:53:54');
INSERT INTO `sys_job_log` VALUES ('1511585411363905536', '0432fdbbd21a4331aec44d4b7cfff77b', '系统无参定时器测试', 'DEFAULT', 'bluewindTask.bwNoParams', '系统无参定时器测试 总共耗时：2毫秒', '0', '', '2022-04-06 14:03:40');
INSERT INTO `sys_job_log` VALUES ('1511585488316801024', 'd8c8ea0f9091456db61e6e48c9496843', '系统测试(有参)', 'DEFAULT', 'bluewindTask.bwParams(\'嘿嘿\')', '系统测试(有参) 总共耗时：0毫秒', '0', '', '2022-04-06 14:03:59');
INSERT INTO `sys_job_log` VALUES ('1511585497389080576', 'd9b93921aafe45b7989edf305da704be', '测试定时器3', 'DEFAULT', 'bluewindTask.bwNoParams', '测试定时器3 总共耗时：0毫秒', '0', '', '2022-04-06 14:04:01');
INSERT INTO `sys_job_log` VALUES ('1511714486437572608', '0432fdbbd21a4331aec44d4b7cfff77b', '系统无参定时器测试', 'DEFAULT', 'bluewindTask.bwNoParams', '系统无参定时器测试 总共耗时：1毫秒', '0', '', '2022-04-06 22:36:35');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `log_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `session_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `descript` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--成功1--失败）',
  `location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('1465874224211599360', 'd1862b85ecb2499b94dbad2e781ebb38', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-01 10:43:45');
INSERT INTO `sys_login_log` VALUES ('1465939227165483008', '5ee9cad4fa374fd8a214b137ce4e2ff6', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-01 15:02:02');
INSERT INTO `sys_login_log` VALUES ('1466067521924526080', '9485abc6f876413d88452c71999d7cce', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-01 23:31:50');
INSERT INTO `sys_login_log` VALUES ('1466068718811115520', '200d3f1e30d842578e677b9d576e4e61', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-01 23:36:35');
INSERT INTO `sys_login_log` VALUES ('1468224048827416576', '361e939c5f734c7e8075fefa768b817e', '2.0.4.176', '用户登录成功！', '0', '法国卢瓦尔河谷', 'admin', '2021-12-07 22:21:07');
INSERT INTO `sys_login_log` VALUES ('1468940295451803648', '1ce751b36bb74ec9aa2b74435918b3cd', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-09 21:47:13');
INSERT INTO `sys_login_log` VALUES ('1471741282663096320', '4e205ccaf9c84eddbbf5cb2d72b95e29', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-17 15:17:21');
INSERT INTO `sys_login_log` VALUES ('1472762469949042688', '9e33a6952cd5408ea5a4fc3b4aacf4aa', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-20 10:55:11');
INSERT INTO `sys_login_log` VALUES ('1472762792872701952', '64f7e3f9a4f44fc3ae6f1298bcfbcd50', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-20 10:56:27');
INSERT INTO `sys_login_log` VALUES ('1472799648164773888', '4c2ca22c187e4661a05cef8b59ad93a1', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2021-12-20 13:22:54');
INSERT INTO `sys_login_log` VALUES ('1477458685227347968', 'b88575a117cc4500bd0e49d8d51c6661', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 09:56:15');
INSERT INTO `sys_login_log` VALUES ('1477459540529819648', 'f9e4ce872c614361809592a9d23ec099', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 09:59:39');
INSERT INTO `sys_login_log` VALUES ('1477459707383427072', '6cb5e29594cf46c4ac7fe575a3db28ff', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 10:00:18');
INSERT INTO `sys_login_log` VALUES ('1477459922115014656', '82f30e47e163430ca176cec1a6756218', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 10:01:10');
INSERT INTO `sys_login_log` VALUES ('1477471516932997120', '4a52cb3a99f145d69fb6e41a29603a3a', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 10:47:15');
INSERT INTO `sys_login_log` VALUES ('1477529474003066880', '0ad66b6e495f41beb74cf978a0cb451f', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-01-02 14:37:33');
INSERT INTO `sys_login_log` VALUES ('1493945678460604416', '2cfc4cdef9904a6ba45e93314e7d8c48', '192.168.55.192', '用户登录成功！', '0', '本地局域网', 'admin', '2022-02-16 21:49:40');
INSERT INTO `sys_login_log` VALUES ('1497553931408625664', 'f672c799ab27419cac0281785c516374', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-02-26 20:47:35');
INSERT INTO `sys_login_log` VALUES ('1504328296255545344', 'b6ad1f00b06041ef80b9ac5c645e034a', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-03-17 13:26:30');
INSERT INTO `sys_login_log` VALUES ('1505900026498310144', '31fba89fc16a45dabe9829cb293fd0cb', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-03-21 21:31:59');
INSERT INTO `sys_login_log` VALUES ('1507224022868148224', 'b6866ccb4ac7490699665bb1aeef0c9f', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-03-25 13:13:05');
INSERT INTO `sys_login_log` VALUES ('1511323944315904000', '1367a1e434cb4e88ae09d0257810e8cb', '192.168.0.103', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-05 20:44:44');
INSERT INTO `sys_login_log` VALUES ('1511330821966503936', '76d76f41278c4e3e993244836e6d0f77', '192.168.0.103', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-05 21:12:04');
INSERT INTO `sys_login_log` VALUES ('1511575078126395392', '1fff8c2888fc4880856bb9fbe48fbcd7', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 13:22:37');
INSERT INTO `sys_login_log` VALUES ('1511577768363175936', '3d14a7dee0a04d1c96d83a97c26df40a', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 13:33:18');
INSERT INTO `sys_login_log` VALUES ('1511600986830897152', '984da16197ef44fa94e1d1afb42a2917', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 15:05:35');
INSERT INTO `sys_login_log` VALUES ('1511607145175953408', 'e2f88c05bc4643b9a20f97077be4ebd9', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 15:30:02');
INSERT INTO `sys_login_log` VALUES ('1511607610961801216', 'c0ca95e9277947bb9239ededd648da5b', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 15:31:53');
INSERT INTO `sys_login_log` VALUES ('1511607840667054080', 'c33a8f5b077843b9a2af370506c43e09', NULL, '用户登录成功！', '0', 'ip为空，无法获取位置', 'admin', '2022-04-06 15:32:48');
INSERT INTO `sys_login_log` VALUES ('1511702323441848320', '7378bfa410304be2963b6339d926b830', '192.168.0.104', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 21:48:16');
INSERT INTO `sys_login_log` VALUES ('1511703088021524480', 'c7dcdf6153e3489994ce8f63c5e2ccff', '192.168.0.104', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 21:51:17');
INSERT INTO `sys_login_log` VALUES ('1511713785179303936', 'c7bce856643b4cd19e4521a7a9d6e1d5', '192.168.0.104', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-06 22:33:48');
INSERT INTO `sys_login_log` VALUES ('1511970169488343040', '4d236771fdb04438ab05a2f4ba7e38a2', '10.49.12.120', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-07 15:32:34');
INSERT INTO `sys_login_log` VALUES ('1512437181580525568', '10612c36021541509b97276b9a56f0b8', '192.168.0.101', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-08 22:28:18');
INSERT INTO `sys_login_log` VALUES ('1512443557836333056', '5c20e954be0a45ed930be76a321143fe', NULL, '用户登录成功！', '0', 'ip为空，无法获取位置', 'admin', '2022-04-08 22:53:38');
INSERT INTO `sys_login_log` VALUES ('1512448674146926592', 'e949ede02e25468583eba32365ae0ba3', '192.168.0.101', '用户登录成功！', '0', '本地局域网', 'admin', '2022-04-08 23:13:58');
INSERT INTO `sys_login_log` VALUES ('1513543464320397312', '2cce8712e0c24df2b24b09ababc56bc4', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-11 23:44:17');
INSERT INTO `sys_login_log` VALUES ('1514263535717511168', '9b279b5266a741b99da73a72489645aa', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-13 23:25:35');
INSERT INTO `sys_login_log` VALUES ('1514431710434398208', '11b67a68fd524ccd8ae30c5d4ec8f390', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-14 10:33:52');
INSERT INTO `sys_login_log` VALUES ('1514804122819407872', 'a9af641874774478a0c9d5ea1d9f6ba6', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-15 11:13:42');
INSERT INTO `sys_login_log` VALUES ('1514882084101394432', '0eba526728524368a8aa53ef520e5683', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-15 16:23:29');
INSERT INTO `sys_login_log` VALUES ('1514982454258397184', '2540d3b19a354fdebb4fea4ce2a2b8bd', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-15 23:02:18');
INSERT INTO `sys_login_log` VALUES ('1514996193127641088', '6d16e41719f842b484159105c2f9b577', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-15 23:56:54');
INSERT INTO `sys_login_log` VALUES ('1515137919476019200', '93c8f36f21f8420ab4065e43bcca09b8', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-16 09:20:04');
INSERT INTO `sys_login_log` VALUES ('1515169248820563968', 'c272c90f976543dfafb484d3db08aeca', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-16 11:24:34');
INSERT INTO `sys_login_log` VALUES ('1515170535616253952', '6e3b06fff1084f48830030e71edfb902', '127.0.0.1', '用户登录成功！', '0', '局域网，无法获取位置', 'admin', '2022-04-16 11:29:40');
INSERT INTO `sys_login_log` VALUES ('1515217671012843520', '38b7baaeba9b4c3b80d4c1e9d93f529e', '127.0.0.1', '用户登录成功！', '0', '局域网，无法获取位置', 'admin', '2022-04-16 14:36:58');
INSERT INTO `sys_login_log` VALUES ('1515217778768707584', '503b8f9a5fca4bd8b5f1456cb1a21d49', '127.0.0.1', '用户登录成功！', '0', '局域网，无法获取位置', 'admin', '2022-04-16 14:37:24');
INSERT INTO `sys_login_log` VALUES ('1515607940570865664', 'cc360a99da4d4678a380558eef5febf7', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-17 16:27:47');
INSERT INTO `sys_login_log` VALUES ('1515626338308960256', '40df2314c8814216805e5435f783aceb', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-17 17:40:53');
INSERT INTO `sys_login_log` VALUES ('1515641116062875648', 'a2452a4b9c884788aaf999c7a1d691e3', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-17 18:39:36');
INSERT INTO `sys_login_log` VALUES ('1515659107253964800', '5532e6d9adfc46b79c1cecf2f96e6414', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-17 19:51:06');
INSERT INTO `sys_login_log` VALUES ('1516072216870735872', '481b6ae6d8944dc3ba02af64a04f0c23', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-18 23:12:38');
INSERT INTO `sys_login_log` VALUES ('1516590498726580224', '298cf6b8f5d845ff8441bca38a559aac', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-20 09:32:06');
INSERT INTO `sys_login_log` VALUES ('1517098205746302976', '9ae66b6f8d8f46c1a2ec2ca4c7b6565e', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-21 19:09:34');
INSERT INTO `sys_login_log` VALUES ('1517491114223960064', '1d538ab0eaa644ce8fee93676aea81d2', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-22 21:10:51');
INSERT INTO `sys_login_log` VALUES ('1517819259655626752', '217b7a44aceb4607b7aebda0edd319a0', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-23 18:54:46');
INSERT INTO `sys_login_log` VALUES ('1517830684647866368', '093b4912e80540d2ac45a9cd3c77aa73', NULL, '用户登录成功！', '0', 'ip为空，无法获取位置', 'admin', '2022-04-23 19:40:09');
INSERT INTO `sys_login_log` VALUES ('1518250186870251520', 'ab45552b85d54c1aaba6290abbc561a9', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-24 23:27:07');
INSERT INTO `sys_login_log` VALUES ('1518460054406361088', '4de84a079af0466e8c8bf0dadb5ee676', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-25 13:21:04');
INSERT INTO `sys_login_log` VALUES ('1518460406442684416', 'fbcb625e5da945529bdf716a3f60910d', NULL, '用户登录成功！', '0', 'ip为空，无法获取位置', 'admin', '2022-04-25 13:22:27');
INSERT INTO `sys_login_log` VALUES ('1518518830452604928', '0f5e333dbd59458f8e295aad34644408', '0:0:0:0:0:0:0:1', '用户登录成功！', '0', '获取位置失败', 'admin', '2022-04-25 17:14:37');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `log_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作模块',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ip',
  `spend_time` int(11) NULL DEFAULT 0 COMMENT '耗时',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES ('1466068956703649792', '数据字典', '分页查询页面', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.baseDictQureyInit', '/bluewind/sysdict/baseDictQureyInit', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 51, '1', '2021-12-01 23:37:32');
INSERT INTO `sys_oper_log` VALUES ('1466068958125518848', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 99, '1', '2021-12-01 23:37:32');
INSERT INTO `sys_oper_log` VALUES ('1466068968749690880', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 96, '1', '2021-12-01 23:37:35');
INSERT INTO `sys_oper_log` VALUES ('1466069137427820544', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 88, '1', '2021-12-01 23:38:15');
INSERT INTO `sys_oper_log` VALUES ('1466069632322138112', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 89, '1', '2021-12-01 23:40:13');
INSERT INTO `sys_oper_log` VALUES ('1466070216521576448', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 121, '1', '2021-12-01 23:42:32');
INSERT INTO `sys_oper_log` VALUES ('1466070389142351872', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 97, '1', '2021-12-01 23:43:14');
INSERT INTO `sys_oper_log` VALUES ('1466071550940962816', '数据字典', '分页查询页面', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.baseDictQureyInit', '/bluewind/sysdict/baseDictQureyInit', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 207, '1', '2021-12-01 23:47:51');
INSERT INTO `sys_oper_log` VALUES ('1466071554250268672', '数据字典', '分页查询', 'com.bluewind.boot.module.sys.sysbasedict.controller.SysBaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '0:0:0:0:0:0:0:1', 157, '1', '2021-12-01 23:47:51');
INSERT INTO `sys_oper_log` VALUES ('1511584079466864640', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.baseDictQureyInit', '/bluewind/sysdict/baseDictQureyInit', '分页查询页面初始化', '10.49.12.120', 32, '1', '2022-04-06 13:58:23');
INSERT INTO `sys_oper_log` VALUES ('1511584080909705216', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 58, '1', '2022-04-06 13:58:23');
INSERT INTO `sys_oper_log` VALUES ('1511584089545777152', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 66, '1', '2022-04-06 13:58:25');
INSERT INTO `sys_oper_log` VALUES ('1511584092074942464', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 58, '1', '2022-04-06 13:58:26');
INSERT INTO `sys_oper_log` VALUES ('1511584094109179904', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 60, '1', '2022-04-06 13:58:26');
INSERT INTO `sys_oper_log` VALUES ('1511584095178727424', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 58, '1', '2022-04-06 13:58:26');
INSERT INTO `sys_oper_log` VALUES ('1511584096512516096', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 58, '1', '2022-04-06 13:58:27');
INSERT INTO `sys_oper_log` VALUES ('1511584155429904384', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 67, '1', '2022-04-06 13:58:41');
INSERT INTO `sys_oper_log` VALUES ('1511584188736872448', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 60, '1', '2022-04-06 13:58:49');
INSERT INTO `sys_oper_log` VALUES ('1511584193061199872', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 60, '1', '2022-04-06 13:58:50');
INSERT INTO `sys_oper_log` VALUES ('1511584198140502016', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 60, '1', '2022-04-06 13:58:51');
INSERT INTO `sys_oper_log` VALUES ('1511584202884259840', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 64, '1', '2022-04-06 13:58:52');
INSERT INTO `sys_oper_log` VALUES ('1511601083195031552', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.baseDictQureyInit', '/bluewind/sysdict/baseDictQureyInit', '分页查询页面初始化', '10.49.12.120', 236, '1', '2022-04-06 15:05:57');
INSERT INTO `sys_oper_log` VALUES ('1511601085376069632', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 201, '1', '2022-04-06 15:05:57');
INSERT INTO `sys_oper_log` VALUES ('1511601116254535680', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '10.49.12.120', 422, '1', '2022-04-06 15:06:04');
INSERT INTO `sys_oper_log` VALUES ('1511702945792675840', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.baseDictQureyInit', '/bluewind/sysdict/baseDictQureyInit', '分页查询页面初始化', '192.168.0.104', 27, '1', '2022-04-06 21:50:43');
INSERT INTO `sys_oper_log` VALUES ('1511702947210350592', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '192.168.0.104', 49, '1', '2022-04-06 21:50:44');
INSERT INTO `sys_oper_log` VALUES ('1511702969561796608', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.getSysDictList', '/bluewind/sysdict/getSysDictList', '分页查询', '192.168.0.104', 50, '1', '2022-04-06 21:50:49');
INSERT INTO `sys_oper_log` VALUES ('1511713838379855872', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '192.168.0.104', 41, '1', '2022-04-06 22:34:00');
INSERT INTO `sys_oper_log` VALUES ('1511713840955158528', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 222, '1', '2022-04-06 22:34:01');
INSERT INTO `sys_oper_log` VALUES ('1511713885666439168', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 61, '1', '2022-04-06 22:34:12');
INSERT INTO `sys_oper_log` VALUES ('1511713980847779840', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 50, '1', '2022-04-06 22:34:34');
INSERT INTO `sys_oper_log` VALUES ('1511714025177378816', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 67, '1', '2022-04-06 22:34:45');
INSERT INTO `sys_oper_log` VALUES ('1511714039010193408', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 50, '1', '2022-04-06 22:34:48');
INSERT INTO `sys_oper_log` VALUES ('1511714072346521600', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '192.168.0.104', 54, '1', '2022-04-06 22:34:56');
INSERT INTO `sys_oper_log` VALUES ('1514263817130143744', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 47, '1', '2022-04-13 23:26:42');
INSERT INTO `sys_oper_log` VALUES ('1514263820590444544', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 341, '1', '2022-04-13 23:26:43');
INSERT INTO `sys_oper_log` VALUES ('1514263884796850176', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 71, '1', '2022-04-13 23:26:58');
INSERT INTO `sys_oper_log` VALUES ('1514263991441223680', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 61, '1', '2022-04-13 23:27:23');
INSERT INTO `sys_oper_log` VALUES ('1514882115776778240', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 33, '1', '2022-04-15 16:23:36');
INSERT INTO `sys_oper_log` VALUES ('1514882117550968832', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 161, '1', '2022-04-15 16:23:36');
INSERT INTO `sys_oper_log` VALUES ('1514882197649592320', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 61, '1', '2022-04-15 16:23:55');
INSERT INTO `sys_oper_log` VALUES ('1514883281015812096', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 218, '1', '2022-04-15 16:28:14');
INSERT INTO `sys_oper_log` VALUES ('1514883287076581376', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 47, '1', '2022-04-15 16:28:15');
INSERT INTO `sys_oper_log` VALUES ('1514883289534443520', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 77, '1', '2022-04-15 16:28:16');
INSERT INTO `sys_oper_log` VALUES ('1514883582959562752', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 84, '1', '2022-04-15 16:29:26');
INSERT INTO `sys_oper_log` VALUES ('1514883626521604096', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-15 16:29:36');
INSERT INTO `sys_oper_log` VALUES ('1514883678006685696', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 28, '1', '2022-04-15 16:29:48');
INSERT INTO `sys_oper_log` VALUES ('1514883679281754112', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 65, '1', '2022-04-15 16:29:49');
INSERT INTO `sys_oper_log` VALUES ('1514884863479005184', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 281, '1', '2022-04-15 16:34:31');
INSERT INTO `sys_oper_log` VALUES ('1514886863422771200', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 250, '1', '2022-04-15 16:42:28');
INSERT INTO `sys_oper_log` VALUES ('1514886955315777536', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 61, '1', '2022-04-15 16:42:50');
INSERT INTO `sys_oper_log` VALUES ('1514887012534472704', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 35, '1', '2022-04-15 16:43:03');
INSERT INTO `sys_oper_log` VALUES ('1514887013956341760', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 63, '1', '2022-04-15 16:43:04');
INSERT INTO `sys_oper_log` VALUES ('1514887602884632576', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 331, '1', '2022-04-15 16:45:24');
INSERT INTO `sys_oper_log` VALUES ('1514887624799870976', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 65, '1', '2022-04-15 16:45:29');
INSERT INTO `sys_oper_log` VALUES ('1514887644659900416', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 38, '1', '2022-04-15 16:45:34');
INSERT INTO `sys_oper_log` VALUES ('1514887645955940352', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 61, '1', '2022-04-15 16:45:34');
INSERT INTO `sys_oper_log` VALUES ('1514887986770558976', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 386, '1', '2022-04-15 16:46:56');
INSERT INTO `sys_oper_log` VALUES ('1514888185517654016', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 45, '1', '2022-04-15 16:47:43');
INSERT INTO `sys_oper_log` VALUES ('1514888187463811072', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 71, '1', '2022-04-15 16:47:43');
INSERT INTO `sys_oper_log` VALUES ('1514888202336813056', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 63, '1', '2022-04-15 16:47:47');
INSERT INTO `sys_oper_log` VALUES ('1514888433363410944', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 432, '1', '2022-04-15 16:48:42');
INSERT INTO `sys_oper_log` VALUES ('1514892047602319360', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 410, '1', '2022-04-15 17:03:04');
INSERT INTO `sys_oper_log` VALUES ('1514892080640851968', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 574, '1', '2022-04-15 17:03:12');
INSERT INTO `sys_oper_log` VALUES ('1514892769936965632', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 56, '1', '2022-04-15 17:05:56');
INSERT INTO `sys_oper_log` VALUES ('1514996213335797760', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 47, '1', '2022-04-15 23:56:58');
INSERT INTO `sys_oper_log` VALUES ('1514996216439582720', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 389, '1', '2022-04-15 23:56:59');
INSERT INTO `sys_oper_log` VALUES ('1514996228624035840', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 70, '1', '2022-04-15 23:57:02');
INSERT INTO `sys_oper_log` VALUES ('1514996257300492288', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 95, '1', '2022-04-15 23:57:09');
INSERT INTO `sys_oper_log` VALUES ('1514997235378630656', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 104, '1', '2022-04-16 00:01:02');
INSERT INTO `sys_oper_log` VALUES ('1514997414991310848', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 79, '1', '2022-04-16 00:01:45');
INSERT INTO `sys_oper_log` VALUES ('1514997716359340032', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 131, '1', '2022-04-16 00:02:57');
INSERT INTO `sys_oper_log` VALUES ('1514997721606414336', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 266, '1', '2022-04-16 00:02:58');
INSERT INTO `sys_oper_log` VALUES ('1514999232037040128', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 266, '1', '2022-04-16 00:08:58');
INSERT INTO `sys_oper_log` VALUES ('1514999573179961344', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 279, '1', '2022-04-16 00:10:19');
INSERT INTO `sys_oper_log` VALUES ('1515000385198145536', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 151, '1', '2022-04-16 00:13:33');
INSERT INTO `sys_oper_log` VALUES ('1515000390038372352', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 255, '1', '2022-04-16 00:13:34');
INSERT INTO `sys_oper_log` VALUES ('1515000467649773568', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 62, '1', '2022-04-16 00:13:53');
INSERT INTO `sys_oper_log` VALUES ('1515000904624947200', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 69, '1', '2022-04-16 00:15:37');
INSERT INTO `sys_oper_log` VALUES ('1515000913068081152', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 62, '1', '2022-04-16 00:15:39');
INSERT INTO `sys_oper_log` VALUES ('1515002757043404800', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 229, '1', '2022-04-16 00:22:58');
INSERT INTO `sys_oper_log` VALUES ('1515002761527115776', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 255, '1', '2022-04-16 00:22:59');
INSERT INTO `sys_oper_log` VALUES ('1515002775485759488', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 75, '1', '2022-04-16 00:23:03');
INSERT INTO `sys_oper_log` VALUES ('1515002789108858880', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 78, '1', '2022-04-16 00:23:06');
INSERT INTO `sys_oper_log` VALUES ('1515002803214303232', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 74, '1', '2022-04-16 00:23:09');
INSERT INTO `sys_oper_log` VALUES ('1515002866980306944', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 71, '1', '2022-04-16 00:23:25');
INSERT INTO `sys_oper_log` VALUES ('1515003027898974208', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 66, '1', '2022-04-16 00:24:03');
INSERT INTO `sys_oper_log` VALUES ('1515003030415556608', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 72, '1', '2022-04-16 00:24:04');
INSERT INTO `sys_oper_log` VALUES ('1515003521223086080', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 319, '1', '2022-04-16 00:26:01');
INSERT INTO `sys_oper_log` VALUES ('1515003533373984768', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 36, '1', '2022-04-16 00:26:04');
INSERT INTO `sys_oper_log` VALUES ('1515003536737816576', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 70, '1', '2022-04-16 00:26:04');
INSERT INTO `sys_oper_log` VALUES ('1515004047507574784', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 70, '1', '2022-04-16 00:28:06');
INSERT INTO `sys_oper_log` VALUES ('1515005715107065856', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 296, '1', '2022-04-16 00:34:44');
INSERT INTO `sys_oper_log` VALUES ('1515005834774753280', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 71, '1', '2022-04-16 00:35:12');
INSERT INTO `sys_oper_log` VALUES ('1515005854928384000', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 77, '1', '2022-04-16 00:35:17');
INSERT INTO `sys_oper_log` VALUES ('1515007877589135360', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 306, '1', '2022-04-16 00:43:19');
INSERT INTO `sys_oper_log` VALUES ('1515008125870960640', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 75, '1', '2022-04-16 00:44:18');
INSERT INTO `sys_oper_log` VALUES ('1515008176907251712', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 74, '1', '2022-04-16 00:44:31');
INSERT INTO `sys_oper_log` VALUES ('1515008189276254208', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 64, '1', '2022-04-16 00:44:34');
INSERT INTO `sys_oper_log` VALUES ('1515137937331171328', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 32, '1', '2022-04-16 09:20:08');
INSERT INTO `sys_oper_log` VALUES ('1515137939101167616', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 249, '1', '2022-04-16 09:20:09');
INSERT INTO `sys_oper_log` VALUES ('1515138062480814080', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 70, '1', '2022-04-16 09:20:38');
INSERT INTO `sys_oper_log` VALUES ('1515138110304268288', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 111, '1', '2022-04-16 09:20:50');
INSERT INTO `sys_oper_log` VALUES ('1515138125684781056', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 64, '1', '2022-04-16 09:20:53');
INSERT INTO `sys_oper_log` VALUES ('1515138126720774144', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 60, '1', '2022-04-16 09:20:54');
INSERT INTO `sys_oper_log` VALUES ('1515138141061099520', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:20:57');
INSERT INTO `sys_oper_log` VALUES ('1515138167745261568', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 54, '1', '2022-04-16 09:21:03');
INSERT INTO `sys_oper_log` VALUES ('1515138170559639552', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:21:04');
INSERT INTO `sys_oper_log` VALUES ('1515138446939107328', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:22:10');
INSERT INTO `sys_oper_log` VALUES ('1515138620256137216', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 55, '1', '2022-04-16 09:22:51');
INSERT INTO `sys_oper_log` VALUES ('1515139526228168704', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 218, '1', '2022-04-16 09:26:27');
INSERT INTO `sys_oper_log` VALUES ('1515139537573761024', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 77, '1', '2022-04-16 09:26:30');
INSERT INTO `sys_oper_log` VALUES ('1515139994349379584', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 244, '1', '2022-04-16 09:28:19');
INSERT INTO `sys_oper_log` VALUES ('1515144300595916800', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 322, '1', '2022-04-16 09:45:25');
INSERT INTO `sys_oper_log` VALUES ('1515144339875573760', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 68, '1', '2022-04-16 09:45:35');
INSERT INTO `sys_oper_log` VALUES ('1515144355675516928', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 55, '1', '2022-04-16 09:45:39');
INSERT INTO `sys_oper_log` VALUES ('1515144393961123840', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:45:48');
INSERT INTO `sys_oper_log` VALUES ('1515144559854235648', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:46:27');
INSERT INTO `sys_oper_log` VALUES ('1515144618473828352', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 60, '1', '2022-04-16 09:46:41');
INSERT INTO `sys_oper_log` VALUES ('1515144631912378368', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 54, '1', '2022-04-16 09:46:44');
INSERT INTO `sys_oper_log` VALUES ('1515144638229000192', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 56, '1', '2022-04-16 09:46:46');
INSERT INTO `sys_oper_log` VALUES ('1515144819544567808', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 26, '1', '2022-04-16 09:47:29');
INSERT INTO `sys_oper_log` VALUES ('1515144820786081792', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 56, '1', '2022-04-16 09:47:29');
INSERT INTO `sys_oper_log` VALUES ('1515145341563449344', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 57, '1', '2022-04-16 09:49:34');
INSERT INTO `sys_oper_log` VALUES ('1515146416709361664', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 307, '1', '2022-04-16 09:53:50');
INSERT INTO `sys_oper_log` VALUES ('1515146504051548160', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 63, '1', '2022-04-16 09:54:11');
INSERT INTO `sys_oper_log` VALUES ('1515146821224816640', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 68, '1', '2022-04-16 09:55:26');
INSERT INTO `sys_oper_log` VALUES ('1515170557997060096', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '127.0.0.1', 37, '1', '2022-04-16 11:29:46');
INSERT INTO `sys_oper_log` VALUES ('1515170560337481728', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '127.0.0.1', 263, '1', '2022-04-16 11:29:46');
INSERT INTO `sys_oper_log` VALUES ('1515171241224015872', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '127.0.0.1', 51, '1', '2022-04-16 11:32:29');
INSERT INTO `sys_oper_log` VALUES ('1515171242608136192', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '127.0.0.1', 97, '1', '2022-04-16 11:32:29');
INSERT INTO `sys_oper_log` VALUES ('1515171257040736256', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '127.0.0.1', 58, '1', '2022-04-16 11:32:32');
INSERT INTO `sys_oper_log` VALUES ('1515626380256194560', '数据字典', '分页查询页面', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.init', '/bluewind/dict/init', '分页查询页面初始化', '0:0:0:0:0:0:0:1', 40, '1', '2022-04-17 17:41:02');
INSERT INTO `sys_oper_log` VALUES ('1515626383146070016', '数据字典', '分页查询', 'com.bluewind.boot.module.system.basedict.controller.BaseDictController.list', '/bluewind/dict/list', '分页查询', '0:0:0:0:0:0:0:1', 325, '1', '2022-04-17 17:41:03');

-- ----------------------------
-- Table structure for sys_permission_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_info`;
CREATE TABLE `sys_permission_info`  (
  `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '类型（0是模块，1是目录，2是菜单，3是按钮）',
  `sign` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源值',
  `href` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `target` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '_self' COMMENT '打开方式（_self--内链_blank外链）',
  `descript` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--正常1--停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除1--已删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统菜单权限信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission_info
-- ----------------------------
INSERT INTO `sys_permission_info` VALUES ('1356622216053755904', '0', '平台管理', '0', NULL, '', 1, 'fa fa-address-book', '_self', '1级组件', '0', '0', NULL, '2021-02-12 21:19:59', NULL, '2021-06-30 15:27:31');
INSERT INTO `sys_permission_info` VALUES ('1356622327209861120', '0', '业务管理', '0', NULL, '', 2, 'fa fa-lemon-o', '_self', '1级组件', '0', '0', NULL, NULL, NULL, '2021-02-02 23:16:45');
INSERT INTO `sys_permission_info` VALUES ('1356622373770813440', '0', '组件管理', '0', NULL, '', 3, 'fa fa-slideshare', '_self', '1级组件', '0', '0', NULL, NULL, NULL, '2021-02-12 21:18:49');
INSERT INTO `sys_permission_info` VALUES ('1356622845051572224', '1356622373770813440', '多级菜单', '1', '', '', 1, 'fa fa-meetup', '', NULL, '0', '0', NULL, NULL, NULL, '2021-02-02 23:59:53');
INSERT INTO `sys_permission_info` VALUES ('1356622944976826368', '1356622373770813440', '失效菜单', '2', NULL, '/page/error.html', 2, 'fa fa-superpowers', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-07 14:41:16');
INSERT INTO `sys_permission_info` VALUES ('1356623034781810688', '1356622845051572224', '按钮1', '1', NULL, '', 1, 'fa fa-calendar', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-07 14:26:50');
INSERT INTO `sys_permission_info` VALUES ('1356623193826586624', '1356623034781810688', '按钮2', '1', NULL, '', 1, 'fa fa-snowflake-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-07 14:27:17');
INSERT INTO `sys_permission_info` VALUES ('1356623247861440512', '1356623193826586624', '按钮3', '2', NULL, '/components/button', 1, 'fa fa-snowflake-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:03');
INSERT INTO `sys_permission_info` VALUES ('1356623355334021120', '1356623193826586624', '表单4', '2', NULL, '/components/form', 2, 'fa fa-calendar', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:06');
INSERT INTO `sys_permission_info` VALUES ('1356623395763740672', '1356622373770813440', '图标列表', '2', NULL, '/components/icon', 3, 'fa fa-dot-circle-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:07');
INSERT INTO `sys_permission_info` VALUES ('1356623422948618240', '1356622373770813440', '图标选择', '2', NULL, '/components/iconPicker', 4, 'fa fa-adn', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:09');
INSERT INTO `sys_permission_info` VALUES ('1356623540277268480', '1356622373770813440', '颜色选择', '2', NULL, '/components/colorSelect', 5, 'fa fa-dashboard', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:11');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774272', '1356622373770813440', '下拉选择', '2', NULL, '/components/tableSelect', 6, 'fa fa-angle-double-down', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:13');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774273', '1356622373770813440', '文件上传', '2', NULL, '/components/upload', 7, 'fa fa-arrow-up', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:15');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774274', '1356622373770813440', '富文本编辑器', '2', NULL, '/components/editor', 8, 'fa fa-edit', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:17');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774275', '1356622373770813440', '省市县区选择器', '2', NULL, '/components/area', 9, 'fa fa-rocket', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:23');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774276', '1356622373770813440', '主页模板', '1', NULL, '', 10, 'fa fa-home', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-02 23:23:35');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774277', '1356622373770813440', '菜单管理', '2', NULL, '/components/menu', 11, 'fa fa-window-maximize', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:27');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774278', '1356622373770813440', '系统设置', '2', NULL, '/components/setting', 12, 'fa fa-gears', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:29');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774279', '1356622373770813440', '表格示例', '2', NULL, '/components/table', 13, 'fa fa-file-text', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:37');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774280', '1356622373770813440', '表单示例', '1', NULL, '', 14, 'fa fa-calendar', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-02 23:23:36');
INSERT INTO `sys_permission_info` VALUES ('1356623692622774281', '1356622373770813440', '登录模板', '1', NULL, '', 15, 'fa fa-flag-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-02 23:23:36');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047744', '1356622373770813440', '异常页面', '1', NULL, '', 16, 'fa fa-home', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-09 22:08:20');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047745', '1356622373770813440', '其它界面', '1', NULL, '', 17, 'fa fa-snowflake-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-02-02 23:23:37');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047746', '1356623692622774276', '主页一', '2', NULL, '/components/welcome1', 1, 'fa fa-tachometer', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:40');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047747', '1356623692622774276', '主页二', '2', NULL, '/components/welcome2', 2, 'fa fa-tachometer', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:42');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047748', '1356623692622774276', '主页三', '2', NULL, '/components/welcome3', 3, 'fa fa-tachometer', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:44');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047749', '1356623692622774280', '普通表单', '2', NULL, '/components/form', 1, 'fa fa-list-alt', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:46');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047750', '1356623692622774280', '分步表单', '2', NULL, '/components/formStep', 2, 'fa fa-navicon', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:48');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047751', '1356623692622774281', '登录-1', '2', NULL, '/components/login1', 1, 'fa fa-stumbleupon-circle', '_blank', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:54');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047752', '1356623692622774281', '登录-2', '2', NULL, '/components/login2', 2, 'fa fa-viacoin', '_blank', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:56');
INSERT INTO `sys_permission_info` VALUES ('1356623905446047753', '1356623692622774281', '登录-3', '2', NULL, '/components/login3', 3, 'fa fa-tags', '_blank', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:21:58');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570497', '1356623905446047744', '404页面', '2', NULL, '/components/html404', 1, 'fa fa-hourglass-end', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:22:00');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570498', '1356623905446047745', '按钮示例', '2', NULL, '/components/button', 1, 'fa fa-snowflake-o', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:22:02');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570499', '1356623905446047745', '弹出层', '2', NULL, '/components/layer', 2, 'fa fa-shield', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 15:22:23');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570500', '1356622216053755904', '权限管理', '1', NULL, '', 2, 'fa fa-gears', '_self', NULL, '0', '0', NULL, NULL, NULL, '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570501', '1356624071998570500', '用户管理', '2', 'system:user:init', '/userinfo/init', 1, 'fa fa-gears', '_self', NULL, '0', '0', NULL, NULL, NULL, '2022-04-06 22:02:30');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570502', '1356624071998570500', '角色管理', '2', 'system:role:init', '/roleinfo/init', 2, 'fa fa fa-gears', '_self', '', '0', '0', '0', '2021-01-31 22:58:51', '1', '2022-04-06 22:08:49');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570503', '1356624071998570500', '菜单管理', '2', 'system:permission:init', '/permission/init', 3, 'fa fa fa-gears', '_self', '', '0', '0', '0', '2021-01-31 22:58:51', '1', '2022-04-06 22:12:54');
INSERT INTO `sys_permission_info` VALUES ('1356624071998570504', '1356622216053755904', '系统监控', '1', NULL, NULL, 1, 'fa fa-gears', '_self', NULL, '0', '0', '0', '2021-02-19 13:07:13', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1362630867860459520', '1356624071998570504', '登陆日志', '2', 'system:loginlog:init', '/loginlog/init', 0, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-02-19 13:11:30', '1', '2022-04-06 22:16:26');
INSERT INTO `sys_permission_info` VALUES ('1363385220818423808', '1356624071998570504', '服务器监控', '2', 'system:serverlook:init', '/serverinfo/init', 2, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-02-21 15:09:02', '1', '2022-04-06 22:00:21');
INSERT INTO `sys_permission_info` VALUES ('1363856602749427712', '1356622216053755904', '系统设置', '1', 'base:info', '', 0, 'fa fa-bookmark', '_self', '', '0', '0', '1', '2021-02-22 22:22:08', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1363856859898011648', '1363856602749427712', '数据字典', '2', 'sys:basedict:init', '/dict/init', 0, 'fa fa fa-adjust', '_self', '', '0', '0', '1', '2021-02-22 22:23:10', '1', '2022-04-06 22:29:40');
INSERT INTO `sys_permission_info` VALUES ('1366257271839412224', '1356624071998570501', '新增', '3', 'system:user:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-01 13:21:32', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1366260043151405056', '1356624071998570501', '删除', '3', 'system:user:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-01 13:32:32', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1366260043151405057', '1356624071998570501', '编辑', '3', 'system:user:edit', NULL, 2, 'fa fa-home', '_self', NULL, '0', '0', '0', '2021-03-01 13:53:13', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1366260043151405058', '1356624071998570501', '角色', '3', 'system:user:authorize', NULL, 3, 'fa fa-home', '_self', NULL, '0', '0', '0', '2021-03-01 13:54:16', '0', '2021-03-01 14:56:43');
INSERT INTO `sys_permission_info` VALUES ('1366260043151405059', '1356624071998570501', '修改密码', '3', 'system:user:editpassword', NULL, 4, 'fa fa-home', '_self', NULL, '0', '0', '0', '2021-03-01 13:54:56', '0', '2021-03-01 14:59:17');
INSERT INTO `sys_permission_info` VALUES ('1367001277164482560', '1356624071998570504', 'druid监控', '2', 'system:druidadmin:init', '/druid', 3, 'fa fa fa-adn', '_self', '', '0', '0', '1', '2021-03-03 14:37:56', '1', '2021-08-13 13:23:06');
INSERT INTO `sys_permission_info` VALUES ('1367765397289906176', '1356624071998570504', '操作日志', '2', 'system:operlog:init', '/operlog/init', 1, 'fa fa fa-calendar', '_self', '', '0', '0', '1', '2021-03-05 09:14:17', '1', '2022-04-06 22:15:09');
INSERT INTO `sys_permission_info` VALUES ('1374747437312167936', '1356624071998570504', 'Websocket测试', '2', 'websocket:init', '/websocket/init', 5, 'fa fa-adjust', '_self', '', '0', '0', '1', '2021-03-24 15:38:27', '0', '2021-03-24 15:38:27');
INSERT INTO `sys_permission_info` VALUES ('1375651227775606784', '1356624071998570502', '新增', '3', 'system:role:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-27 03:29:46', '0', '2021-03-27 03:29:46');
INSERT INTO `sys_permission_info` VALUES ('1375651443106979840', '1356624071998570502', '删除', '3', 'system:role:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-27 03:30:37', '0', '2021-03-27 03:30:37');
INSERT INTO `sys_permission_info` VALUES ('1375651585042227200', '1356624071998570502', '编辑', '3', 'system:role:update', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-27 03:31:10', '0', '2021-03-27 03:31:10');
INSERT INTO `sys_permission_info` VALUES ('1375651784099700736', '1356624071998570502', '禁用启用', '3', 'system:role:enableordisable', '', 3, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-03-27 03:31:58', '1', '2021-03-27 03:48:41');
INSERT INTO `sys_permission_info` VALUES ('1375651939947454464', '1356624071998570502', '授权', '3', 'system:role:auth', '', 4, 'fa fa-home', '_self', '', '0', '0', '1', '2021-03-27 03:32:35', '0', '2021-03-27 03:32:35');
INSERT INTO `sys_permission_info` VALUES ('1379079117025284096', '1363856602749427712', '业务流水号', '2', 'system:idtable:init', '/idtable/init', 2, 'fa fa fa-database', '_self', '业务流水号', '0', '0', '1', '2021-04-05 14:31:02', '1', '2022-04-06 22:24:54');
INSERT INTO `sys_permission_info` VALUES ('1383248198383104000', '1356622216053755904', '邮件服务', '1', 'email_service', '', 4, 'fa fa-address-book-o', '_self', '', '0', '0', '1', '2021-04-17 02:37:24', '0', '2021-04-17 02:37:24');
INSERT INTO `sys_permission_info` VALUES ('1383248448330067968', '1383248198383104000', '邮件发送', '2', 'email_send', '/sysmail/email/init', 0, 'fa fa-address-book', '_self', '', '0', '0', '1', '2021-04-17 02:38:23', '0', '2021-04-17 02:38:23');
INSERT INTO `sys_permission_info` VALUES ('1384741066913136640', '1363856602749427712', '网站配置', '2', 'system:config:init', '/config/init', 4, 'fa fa fa fa-android', '_self', '', '0', '0', '1', '2021-04-21 05:29:32', '1', '2022-04-06 22:27:57');
INSERT INTO `sys_permission_info` VALUES ('1385520263748186112', '1384741066913136640', '保存', '3', 'system:config:save', '', 0, 'fa fa-home', '_self', '网页配置保存', '0', '0', '1', '2021-04-23 17:05:47', '0', '2021-04-23 17:05:47');
INSERT INTO `sys_permission_info` VALUES ('1401046812895322112', '1356624071998570501', '导出pdf', '3', 'system:user:downloadPdf', '', 5, 'fa fa-home', '_self', '导出pdf', '0', '0', '1', '2021-06-05 13:22:45', '0', '2021-06-05 13:22:45');
INSERT INTO `sys_permission_info` VALUES ('1403617442299260928', '1356622216053755904', '服务管理', '1', 'system:itfc:', '', 5, 'fa fa fa-bookmark', '_self', '服务管理', '0', '0', '1', '2021-06-12 15:37:31', '1', '2021-08-13 13:13:24');
INSERT INTO `sys_permission_info` VALUES ('1403617991660810240', '1403617442299260928', '服务密钥', '2', 'system:itfckey:init', '/itfckey/init', 1, 'fa fa fa fa-home', '_self', '', '0', '0', '1', '2021-06-12 15:39:42', '1', '2022-04-06 22:23:01');
INSERT INTO `sys_permission_info` VALUES ('1403618360277217280', '1403617442299260928', '服务权限', '2', 'system:itfcpermission:init', '/itfcpermission/init', 2, 'fa fa fa fa-home', '_self', '', '0', '0', '1', '2021-06-12 15:41:10', '1', '2022-04-06 22:21:33');
INSERT INTO `sys_permission_info` VALUES ('1420386404618768384', '1356624071998570504', '在线用户', '2', 'system:useronline:init', '/useronline/init', 6, 'fa fa fa-codepen', '_self', '', '0', '0', '1', '2021-07-28 22:11:23', '1', '2022-04-06 21:58:13');
INSERT INTO `sys_permission_info` VALUES ('1426048058156847104', '1356624071998570503', '删除', '3', 'system:permission:delete', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:08:46', '0', '2021-08-13 13:08:46');
INSERT INTO `sys_permission_info` VALUES ('1426048592427290624', '1356624071998570503', '新增', '3', 'system:permission:add', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:10:53', '0', '2021-08-13 13:10:53');
INSERT INTO `sys_permission_info` VALUES ('1426048692151062528', '1356624071998570503', '编辑', '3', 'system:permission:edit', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:11:17', '0', '2021-08-13 13:11:17');
INSERT INTO `sys_permission_info` VALUES ('1426048926205808640', '1356624071998570503', '禁用启用', '3', 'system:permission:disoren', '', 3, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:12:13', '0', '2021-08-13 13:12:13');
INSERT INTO `sys_permission_info` VALUES ('1426049544312000512', '1403617991660810240', '新增', '3', 'system:itfckey:add', '', 0, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:14:40', '1', '2021-10-23 15:22:10');
INSERT INTO `sys_permission_info` VALUES ('1426049723656245248', '1403617991660810240', '删除', '3', 'system:itfckey:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:15:23', '0', '2021-08-13 13:15:23');
INSERT INTO `sys_permission_info` VALUES ('1426049867319545856', '1403617991660810240', '编辑', '3', 'system:itfckey:edit', '', 3, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:15:57', '0', '2021-08-13 13:15:57');
INSERT INTO `sys_permission_info` VALUES ('1426050015730798592', '1403617991660810240', '禁用启用', '3', 'system:itfckey:disoren', '', 4, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:16:33', '0', '2021-08-13 13:16:33');
INSERT INTO `sys_permission_info` VALUES ('1426050236284080128', '1403617991660810240', '授权', '3', 'system:itfckey:authorize', '', 5, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:17:25', '0', '2021-08-13 13:17:25');
INSERT INTO `sys_permission_info` VALUES ('1426050733669814272', '1403618360277217280', '新增', '3', 'system:itfcpermission:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:19:24', '0', '2021-08-13 13:19:24');
INSERT INTO `sys_permission_info` VALUES ('1426050819334279168', '1403618360277217280', '编辑', '3', 'system:itfcpermission:edit', '', 1, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:19:44', '1', '2021-08-13 13:20:41');
INSERT INTO `sys_permission_info` VALUES ('1426050917795565568', '1403618360277217280', '删除', '3', 'system:itfcpermission:delete', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:20:08', '0', '2021-08-13 13:20:19');
INSERT INTO `sys_permission_info` VALUES ('1426052156788772864', '1379079117025284096', '新增', '3', 'system:idtable:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:25:03', '0', '2021-08-13 13:25:03');
INSERT INTO `sys_permission_info` VALUES ('1426052217211916288', '1379079117025284096', '删除', '3', 'system:idtable:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:25:18', '0', '2021-08-13 13:25:18');
INSERT INTO `sys_permission_info` VALUES ('1426052294944952320', '1379079117025284096', '编辑', '3', 'system:idtable:edit', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:25:36', '0', '2021-08-13 13:25:36');
INSERT INTO `sys_permission_info` VALUES ('1426053204626575360', '1363856859898011648', '新增', '3', 'sys:basedict:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:29:13', '0', '2021-08-13 13:29:13');
INSERT INTO `sys_permission_info` VALUES ('1426053263602683904', '1363856859898011648', '删除', '3', 'sys:basedict:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:29:27', '0', '2021-08-13 13:29:27');
INSERT INTO `sys_permission_info` VALUES ('1426053344749883392', '1363856859898011648', '编辑', '3', 'sys:basedict:edit', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:29:46', '0', '2021-08-13 13:29:46');
INSERT INTO `sys_permission_info` VALUES ('1426053449271939072', '1363856859898011648', '禁用启用', '3', 'sys:basedict:disoren', '', 3, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:30:11', '0', '2021-08-13 13:30:11');
INSERT INTO `sys_permission_info` VALUES ('1426053589923729408', '1363856859898011648', '配置', '3', 'sys:basedict:configuration', '', 4, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-13 13:30:45', '0', '2021-08-13 13:30:45');
INSERT INTO `sys_permission_info` VALUES ('1431230594149711872', '1363856602749427712', '定时任务', '2', 'system:job:init', '/job/init', 5, 'fa fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-27 20:22:19', '1', '2022-04-06 22:17:44');
INSERT INTO `sys_permission_info` VALUES ('1431538412783583232', '1431230594149711872', '新增', '3', 'sys:job:add', '', 0, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-28 16:45:29', '1', '2021-08-28 16:47:14');
INSERT INTO `sys_permission_info` VALUES ('1431538557407379456', '1431230594149711872', '删除', '3', 'sys:job:delete', '', 1, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-28 16:46:03', '1', '2021-08-28 16:47:33');
INSERT INTO `sys_permission_info` VALUES ('1431538629335498752', '1431230594149711872', '编辑', '3', 'sys:job:edit', '', 2, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-08-28 16:46:20', '1', '2021-08-28 16:47:46');
INSERT INTO `sys_permission_info` VALUES ('1431538723468263424', '1431230594149711872', '执行一次', '3', 'sys:job:executeonce', '', 3, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-28 16:46:43', '0', '2021-08-28 16:46:43');
INSERT INTO `sys_permission_info` VALUES ('1431538808277090304', '1431230594149711872', '执行日志', '3', 'sys:job:record', '', 4, 'fa fa-home', '_self', '', '0', '0', '1', '2021-08-28 16:47:03', '0', '2021-08-28 16:47:03');
INSERT INTO `sys_permission_info` VALUES ('1458664123436806144', '1356624071998570501', '导出excel', '3', 'system:user:exportExcel', '', 8, 'fa fa fa-home', '_self', '', '0', '0', '1', '2021-11-11 13:13:22', '1', '2021-11-17 22:40:55');
INSERT INTO `sys_permission_info` VALUES ('1466067936934129664', '1356624071998570500', '岗位管理', '2', 'system:post:init', '/postinfo/init', 3, 'fa fa-home', '_self', '', '0', '0', '1', '2021-12-01 23:33:29', NULL, '2022-04-06 22:11:13');
INSERT INTO `sys_permission_info` VALUES ('1466068282603499520', '1466067936934129664', '新增', '3', 'system:post:add', '', 0, 'fa fa-home', '_self', '', '0', '0', '1', '2021-12-01 23:34:51', NULL, '2021-12-01 23:34:51');
INSERT INTO `sys_permission_info` VALUES ('1477474786258567168', '1466067936934129664', '删除', '3', 'system:post:delete', '', 1, 'fa fa-home', '_self', '', '0', '0', '1', '2022-01-02 11:00:13', NULL, '2022-01-02 11:00:13');
INSERT INTO `sys_permission_info` VALUES ('1477474891283939328', '1466067936934129664', '修改', '3', 'system:post:update', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2022-01-02 11:00:38', NULL, '2022-01-02 11:00:38');
INSERT INTO `sys_permission_info` VALUES ('1511330246660317184', '1356624071998570500', '部门管理', '2', 'sysdeptinfo:init', '/deptinfo/init', 0, 'fa fa-cogs', '_self', '', '0', '0', '1', '2022-04-05 21:09:46', NULL, '2022-04-06 22:26:13');
INSERT INTO `sys_permission_info` VALUES ('1511330456019001344', '1511330246660317184', '新增', '3', 'system:dept:add', '', 0, 'fa fa fa-home', '_self', '', '0', '0', '1', '2022-04-05 21:10:36', '1', '2022-04-13 23:53:07');
INSERT INTO `sys_permission_info` VALUES ('1511583410945138688', '1511330246660317184', '修改', '3', 'system:dept:update', '', 1, 'fa fa fa-home', '_self', '', '0', '0', '1', '2022-04-06 13:55:43', '1', '2022-04-13 23:52:41');
INSERT INTO `sys_permission_info` VALUES ('1514269497674051584', '1511330246660317184', '删除', '3', 'system:dept:delete', '', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2022-04-13 23:49:16', NULL, '2022-04-13 23:49:16');
INSERT INTO `sys_permission_info` VALUES ('1518460292667994112', '1356624071998570504', '缓存监控', '2', 'system:cacheinfo:edit', '/cacheinfo/init', 2, 'fa fa-home', '_self', '', '0', '0', '1', '2022-04-25 13:22:00', NULL, '2022-04-25 13:22:00');

-- ----------------------------
-- Table structure for sys_post_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_post_info`;
CREATE TABLE `sys_post_info`  (
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务主键',
  `post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `descript` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post_info
-- ----------------------------
INSERT INTO `sys_post_info` VALUES ('23466322432', 'general_manager', '总经理', 1, '0', '1', '2021-12-20 13:24:36', '', '2022-04-06 22:42:15', '董事长的小弟');
INSERT INTO `sys_post_info` VALUES ('31415358687', 'ceo', '董事长', 2, '0', '1', '2021-12-01 23:53:43', '', '2022-04-07 15:51:18', '公司地位最高的人');

-- ----------------------------
-- Table structure for sys_role_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_info`;
CREATE TABLE `sys_role_info`  (
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `sign` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标志',
  `descript` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0--正常1--停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态（0--未删除1--已删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_info
-- ----------------------------
INSERT INTO `sys_role_info` VALUES ('1263999620210087296', '嘿嘿', 'heihei', 'dasdasdsdsa', '0', '1', '1', '2021-02-01 22:29:26', '0', '2021-02-03 00:12:15');
INSERT INTO `sys_role_info` VALUES ('1263999628210287296', '测试角色3', 'testtset', '测试角色3', '0', '0', '6', '2020-10-23 15:48:54', '0', '2022-04-06 13:53:10');
INSERT INTO `sys_role_info` VALUES ('1263999628210487209', '爱吃甜品的猫', 'dasdasdas', '', '0', '1', '1', '2021-02-02 00:11:58', '0', '2021-02-02 16:09:59');
INSERT INTO `sys_role_info` VALUES ('1263999628210487211', 'th', 'ttttt', '', '0', '1', '1', '2021-02-02 00:16:40', '0', '2021-02-02 16:09:59');
INSERT INTO `sys_role_info` VALUES ('1263999628210487222', '刘星宇', 'liuxingyu', '刘星宇的角色', '1', '0', '1', '2021-02-02 00:19:06', '0', '2021-11-25 11:35:12');
INSERT INTO `sys_role_info` VALUES ('1263999628210487223', 'dasdasd', 'dasdadsdas', '', '0', '1', '1', '2021-02-02 00:14:36', '0', '2022-04-06 13:53:22');
INSERT INTO `sys_role_info` VALUES ('1263999628210487291', '测试角色2', 'awc ', '测试角色2', '0', '0', '6', '2020-10-21 22:34:47', '0', '2021-02-02 16:09:58');
INSERT INTO `sys_role_info` VALUES ('1263999628210487296', '超级管理员', 'administrator', '超级管理员，拥有至高无上的权力', '0', '0', '0', '2020-09-06 19:40:31', '0', '2021-02-02 16:09:57');
INSERT INTO `sys_role_info` VALUES ('1263999628210487297', '测试人员', 'test', '测试人员', '0', '0', '0', '2020-09-06 19:40:31', '0', '2021-02-02 16:09:57');
INSERT INTO `sys_role_info` VALUES ('1263999628210487298', '测试角色1', '11', '测试角色1', '0', '0', '6', '2020-10-15 21:19:49', '0', '2021-11-17 22:34:33');
INSERT INTO `sys_role_info` VALUES ('1356642120790933504', '测试账号', 'dasdasdas', 'dsaddasda', '0', '1', '1', '2021-02-03 00:34:22', '0', '2021-02-03 00:35:20');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色id',
  `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '权限id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-权限关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1356622216053755904', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1363856602749427712', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1363856859898011648', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1379079117025284096', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426052156788772864', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426052217211916288', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426053204626575360', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426053263602683904', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426053344749883392', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487222', '1426053449271939072', '2021-11-14 18:56:31', '2021-11-14 18:56:31');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1356622216053755904', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1356624071998570500', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1356624071998570501', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1356624071998570502', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1356624071998570503', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1366257271839412224', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1366260043151405056', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1366260043151405057', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1366260043151405058', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1366260043151405059', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1375651227775606784', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1375651443106979840', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1375651585042227200', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1375651784099700736', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1375651939947454464', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1401046812895322112', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1426048058156847104', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1426048592427290624', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1426048692151062528', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487291', '1426048926205808640', '2021-08-30 18:11:35', '2021-08-30 18:11:35');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356622216053755904', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356622327209861120', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356622373770813440', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356622845051572224', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356622944976826368', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623034781810688', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623193826586624', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623247861440512', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623355334021120', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623395763740672', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623422948618240', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623540277268480', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774272', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774273', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774274', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774275', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774276', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774277', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774278', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774279', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774280', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623692622774281', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047744', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047745', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047746', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047747', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047748', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047749', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047750', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047751', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047752', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356623905446047753', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570497', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570498', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570499', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570500', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570501', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570502', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570503', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1356624071998570504', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1362630867860459520', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1363385220818423808', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1363856602749427712', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1363856859898011648', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1366257271839412224', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1366260043151405056', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1366260043151405057', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1366260043151405058', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1366260043151405059', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1367001277164482560', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1367765397289906176', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1374747437312167936', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1375651227775606784', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1375651443106979840', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1375651585042227200', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1375651784099700736', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1375651939947454464', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1379079117025284096', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1383248198383104000', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1383248448330067968', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1384741066913136640', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1385520263748186112', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1401046812895322112', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1403617442299260928', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1403617991660810240', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1403618360277217280', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1420386404618768384', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426048058156847104', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426048592427290624', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426048692151062528', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426048926205808640', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426049544312000512', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426049723656245248', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426049867319545856', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426050015730798592', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426050236284080128', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426050733669814272', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426050819334279168', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426050917795565568', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426052156788772864', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426052217211916288', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426052294944952320', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426053204626575360', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426053263602683904', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426053344749883392', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426053449271939072', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1426053589923729408', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431230594149711872', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431538412783583232', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431538557407379456', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431538629335498752', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431538723468263424', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1431538808277090304', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1458664123436806144', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1466067936934129664', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1466068282603499520', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1477474786258567168', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1477474891283939328', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1511330246660317184', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1511330456019001344', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1511583410945138688', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487296', '1518460292667994112', '2022-04-25 13:22:19', '2022-04-25 13:22:19');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1356622216053755904', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1356624071998570500', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1356624071998570501', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1356624071998570502', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1356624071998570503', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1366257271839412224', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1366260043151405056', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1366260043151405057', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1366260043151405058', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1366260043151405059', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1375651227775606784', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1375651443106979840', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1375651585042227200', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1375651784099700736', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1375651939947454464', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1401046812895322112', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1426048058156847104', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1426048592427290624', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1426048692151062528', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487297', '1426048926205808640', '2021-10-23 14:51:01', '2021-10-23 14:51:01');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1356622216053755904', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1363856602749427712', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1363856859898011648', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1426053204626575360', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1426053263602683904', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1426053344749883392', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1426053449271939072', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1426053589923729408', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431230594149711872', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431538412783583232', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431538557407379456', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431538629335498752', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431538723468263424', '2021-08-30 18:11:16', '2021-08-30 18:11:16');
INSERT INTO `sys_role_permission` VALUES ('1263999628210487298', '1431538808277090304', '2021-08-30 18:11:16', '2021-08-30 18:11:16');

-- ----------------------------
-- Table structure for sys_rule
-- ----------------------------
DROP TABLE IF EXISTS `sys_rule`;
CREATE TABLE `sys_rule`  (
  `rule_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `rule_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则内码',
  `rule_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则值',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0--正常 1--停用）',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则描述',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统业务规则表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_rule
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '性别（0--未知 1--男 2--女）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态（0--正常 1--冻结）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0--未删除1--已删除）',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
INSERT INTO `sys_user_info` VALUES ('1', 'admin', '0e1f05509d6dd6b14d7a8458fea8b5714fac365d45ad99678a5cd561158a90c4', '超级管理员', '100', '17862719592', '111@qq.com', 'http://halo.lxyccc.top/头像.jpg', '1', '0', '0', '0', '2020-09-06 19:40:49', '1', '2022-04-23 20:15:24');
INSERT INTO `sys_user_info` VALUES ('10', 'xiaolan', 'c6fbfcf124670417dc0b8485171d6bb9', '小兰', '103', '15286776337', '15286776337@163.com', 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '1', '2022-04-21 19:17:23');
INSERT INTO `sys_user_info` VALUES ('11', 'xiaozi', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '小紫', '101', '13288990099', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:28');
INSERT INTO `sys_user_info` VALUES ('12', 'xiaoqing', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '小青', '101', '15286776337', '15286776337@163.com', 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:28:30', '1', '2022-04-23 20:15:28');
INSERT INTO `sys_user_info` VALUES ('13', 'liuxing', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '流星雨', '101', '15286776337', 'jqcgj@mfk.app', 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:28:30', '1', '2022-04-23 20:15:29');
INSERT INTO `sys_user_info` VALUES ('14', 'huangzai', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '黄二郎', '101', '1', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:28:30', '0', '2022-04-23 20:15:30');
INSERT INTO `sys_user_info` VALUES ('15', 'superadmin', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '超级管理员', '101', '17899999999', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:28:30', '0', '2022-04-23 20:15:32');
INSERT INTO `sys_user_info` VALUES ('1516084831123689472', 'ceshiceshi', '28741572107c094aa0794a472a1cf73c4e60df301fd835bd056065e97b39a097', '测试测试', '101', '17862719591', '184974699@qq.com', '', '1', '0', '0', '1', '2022-04-19 00:02:45', NULL, '2022-04-19 00:02:45');
INSERT INTO `sys_user_info` VALUES ('17', 'liuxingyu01', 'c6fbfcf124670417dc0b8485171d6bb9', '黑呵呵', '104', '15286779077', NULL, 'http://halo.lxyccc.top/车.png', '2', '1', '0', '1', '2021-01-25 13:06:22', '0', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('18', '测试上传头像', '31a326b90dee28fad25e4e3b653cd7ea', 'avatar', '104', '15286779044', NULL, 'http://halo.lxyccc.top/44dsdsdsds4.jpg', '1', '0', '0', '1', '2021-03-27 08:35:47', '0', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('2', '13888888888', '0e1f05509d6dd6b14d7a8458fea8b5714fac365d45ad99678a5cd561158a90c4', '测试账号', '104', '13888888886', '123456798@qq.com', 'http://halo.lxyccc.top/头像.jpg', '1', '0', '0', '1', '2020-09-30 09:35:28', '1', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('3', 'zhangsan', '0e1f05509d6dd6b14d7a8458fea8b5714fac365d45ad99678a5cd561158a90c4', '张三', '104', '13244444444', NULL, 'http://halo.lxyccc.top/导航标注_16.png', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('4', 'lisi', 'df3db4b61f6d08899d10f5beb578a3699373b24841d9cb43ae123a688168ad2e', '李四', '104', '13067395515', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '1', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('5', 'wangwu', 'c6fbfcf124670417dc0b8485171d6bb9', '王五', '104', '13888888886', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:49');
INSERT INTO `sys_user_info` VALUES ('6', 'zhaoliu', 'c6fbfcf124670417dc0b8485171d6bb9', '赵六', '105', '18888888888', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:50');
INSERT INTO `sys_user_info` VALUES ('7', 'xiaohong', 'c6fbfcf124670417dc0b8485171d6bb9', '小红', '105', '13255667980', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:51');
INSERT INTO `sys_user_info` VALUES ('8', 'xiaohuang', 'c6fbfcf124670417dc0b8485171d6bb9', '小黄', '105', '15286779044', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '2', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:52');
INSERT INTO `sys_user_info` VALUES ('9', 'xiaolv', '99B26BE5F5F7AF4A576DFB6DF0DD38FF', '大绿', '105', '17862719592', NULL, 'http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif', '1', '0', '0', '0', '2021-01-07 15:26:01', '0', '2022-04-23 20:15:53');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES ('1', '23466322432', '2022-04-08 22:29:13', '2022-04-08 22:29:13');
INSERT INTO `sys_user_post` VALUES ('1', '31415358687', '2022-04-08 22:29:13', '2022-04-08 22:29:13');
INSERT INTO `sys_user_post` VALUES ('10', '23466322432', '2022-04-21 19:17:24', '2022-04-21 19:17:24');
INSERT INTO `sys_user_post` VALUES ('1477483060669382656', '23466322432', '2022-01-02 14:38:28', '2022-01-02 14:38:28');
INSERT INTO `sys_user_post` VALUES ('1477483060669382656', '31415358687', '2022-01-02 14:38:28', '2022-01-02 14:38:28');
INSERT INTO `sys_user_post` VALUES ('1516084831123689472', '23466322432', '2022-04-19 00:02:45', '2022-04-19 00:02:45');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1263999628210487296', '2022-04-06 13:51:07', '2022-04-06 13:51:07');
INSERT INTO `sys_user_role` VALUES ('10', '1263999628210287296', '2022-04-20 09:42:36', '2022-04-20 09:42:36');
INSERT INTO `sys_user_role` VALUES ('10', '1263999628210487291', '2022-04-20 09:42:36', '2022-04-20 09:42:36');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210287296', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210487223', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210487291', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210487296', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210487297', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('17', '1263999628210487298', '2021-11-17 22:14:48', '2021-11-17 22:14:48');
INSERT INTO `sys_user_role` VALUES ('18', '1263999628210487223', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('18', '1263999628210487291', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('18', '1263999628210487296', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('18', '1263999628210487297', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('18', '1263999628210487298', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210287296', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210487223', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210487291', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210487296', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210487297', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('2', '1263999628210487298', '2021-10-23 14:49:27', '2021-10-23 14:49:27');
INSERT INTO `sys_user_role` VALUES ('20', '1263999628210487223', '2021-06-18 22:18:23', '2021-06-18 22:18:23');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210287296', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210487223', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210487291', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210487296', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210487297', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('21', '1263999628210487298', '2021-02-06 15:34:27', '2021-02-06 15:34:27');
INSERT INTO `sys_user_role` VALUES ('3', '1263999628210487291', '2021-08-30 18:11:48', '2021-08-30 18:11:48');
INSERT INTO `sys_user_role` VALUES ('3', '1263999628210487298', '2021-08-30 18:11:48', '2021-08-30 18:11:48');
INSERT INTO `sys_user_role` VALUES ('4', '1263999628210487291', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('5', '1263999628210487291', '2021-02-03 00:28:27', '2021-02-03 00:28:27');
INSERT INTO `sys_user_role` VALUES ('6', '1263999628210487291', '2021-02-03 00:28:27', '2021-02-03 00:28:27');

SET FOREIGN_KEY_CHECKS = 1;
