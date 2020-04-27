/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : book_manager

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-04-27 16:14:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '书名',
  `author` varchar(255) NOT NULL COMMENT '书籍作者',
  `price` double NOT NULL COMMENT '书籍单价',
  `publisher` varchar(255) NOT NULL COMMENT '出版社名称',
  `publish_time` date NOT NULL COMMENT '书籍出版日期',
  `stock_sum` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '《计算机组成原理》', '唐朔飞', '43', '高等教育出版社', '2008-01-01', '9042');
INSERT INTO `book` VALUES ('2', '《数据库系统概论》', ' 王珊/萨师煊', '39.6', '高等教育出版社', '2014-01-01', '1442');
INSERT INTO `book` VALUES ('3', '《毛泽东思想和中国特色社会主义理论体系概论》', ' 本书编写组', '25', '高等教育出版社', '2013-01-01', '540');
INSERT INTO `book` VALUES ('4', '《计算机应用基础》', '姜帆', '42', '武汉理工大学出版社', '2017-09-01', '1200');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '班级名',
  `major_id` int(11) NOT NULL COMMENT '专业id',
  `user_id` int(11) DEFAULT NULL COMMENT '班级负责人的用户id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1表示未删除，0表示已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('1', '软件工程1801', '1', '7', '1');
INSERT INTO `class` VALUES ('2', '软件工程1802', '1', '8', '1');
INSERT INTO `class` VALUES ('3', '软件工程1803', '1', '2', '1');
INSERT INTO `class` VALUES ('4', '软件工程1804', '1', '3', '1');
INSERT INTO `class` VALUES ('6', '网络工程1802', '2', '6', '1');
INSERT INTO `class` VALUES ('12', '网络工程1801', '2', '21', '1');
INSERT INTO `class` VALUES ('15', '信息与计算科学1801', '5', '30', '1');

-- ----------------------------
-- Table structure for class_book
-- ----------------------------
DROP TABLE IF EXISTS `class_book`;
CREATE TABLE `class_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `book_sum` int(11) DEFAULT '0' COMMENT '该班级领了该书的数量',
  `receive_date` date DEFAULT NULL COMMENT '领取日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_book
-- ----------------------------
INSERT INTO `class_book` VALUES ('1', '4', '1', '34', '2020-01-22');
INSERT INTO `class_book` VALUES ('2', '1', '2', '42', '2020-02-20');
INSERT INTO `class_book` VALUES ('3', '1', '3', '42', '2019-11-27');
INSERT INTO `class_book` VALUES ('4', '2', '1', '45', '2020-04-02');
INSERT INTO `class_book` VALUES ('5', '2', '2', '45', '2020-03-18');
INSERT INTO `class_book` VALUES ('6', '2', '3', '45', '2020-02-19');
INSERT INTO `class_book` VALUES ('7', '3', '1', '45', '2020-02-11');
INSERT INTO `class_book` VALUES ('8', '3', '2', '45', '2020-02-18');
INSERT INTO `class_book` VALUES ('9', '3', '3', '45', '2020-01-01');
INSERT INTO `class_book` VALUES ('10', '4', '1', '41', '2020-01-21');
INSERT INTO `class_book` VALUES ('11', '4', '2', '41', '2020-02-19');
INSERT INTO `class_book` VALUES ('13', '3', '1', '12', '2020-01-03');
INSERT INTO `class_book` VALUES ('14', '1', '3', '20', '2020-03-20');
INSERT INTO `class_book` VALUES ('19', '2', '1', '13', '2020-01-03');
INSERT INTO `class_book` VALUES ('20', '4', '2', '23', '2020-04-01');
INSERT INTO `class_book` VALUES ('21', '6', '1', '43', '2020-04-09');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `book_id` int(11) NOT NULL,
  `hour` int(11) NOT NULL COMMENT '课时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '计算机组成原理', '1', '48');
INSERT INTO `course` VALUES ('2', '数据库原理概论', '2', '42');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '专业名',
  `school_id` int(11) DEFAULT NULL COMMENT '学院id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1表示未删除，0表示已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('1', '软件工程', '1', '1');
INSERT INTO `major` VALUES ('2', '网络工程', '1', '1');
INSERT INTO `major` VALUES ('3', '物联网工程', '1', '1');
INSERT INTO `major` VALUES ('4', '计算机科学与技术', '1', '0');
INSERT INTO `major` VALUES ('5', '信息与计算科学', '2', '1');

-- ----------------------------
-- Table structure for major_course
-- ----------------------------
DROP TABLE IF EXISTS `major_course`;
CREATE TABLE `major_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `major_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major_course
-- ----------------------------
INSERT INTO `major_course` VALUES ('1', '1', '1');
INSERT INTO `major_course` VALUES ('2', '1', '2');
INSERT INTO `major_course` VALUES ('3', '2', '1');
INSERT INTO `major_course` VALUES ('4', '2', '2');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '父类菜单',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `url` varchar(255) NOT NULL DEFAULT '#' COMMENT '请求地址',
  `type` char(1) NOT NULL DEFAULT '' COMMENT '菜单类型（M目录，C菜单，F按钮）',
  `perms` varchar(255) NOT NULL DEFAULT '' COMMENT '权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '用户管理', '0', '#', 'M', '');
INSERT INTO `resource` VALUES ('2', '用户新增', '1', ' /user/addUser', 'C', 'user:create');
INSERT INTO `resource` VALUES ('3', '用户查询', '1', '/user/allUser', 'C', 'user:get');
INSERT INTO `resource` VALUES ('4', '用户修改', '1', '/user/updateUser', 'C', 'user:update');
INSERT INTO `resource` VALUES ('5', '用户删除', '1', '/user/deleteUser', 'C', 'user:delete');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '管理整个系统');
INSERT INTO `role` VALUES ('2', '普通学生', '只能查询基本信息的学生');
INSERT INTO `role` VALUES ('3', '学院负责人', '提交教学计划');
INSERT INTO `role` VALUES ('4', '班级负责人', '提交书籍的领取情况');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('1', '2');
INSERT INTO `role_resource` VALUES ('1', '3');
INSERT INTO `role_resource` VALUES ('1', '4');
INSERT INTO `role_resource` VALUES ('1', '5');
INSERT INTO `role_resource` VALUES ('2', '2');
INSERT INTO `role_resource` VALUES ('4', '2');

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '学院负责人的用户id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1表示未删除，0表示已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '计算机学院', '13', '1');
INSERT INTO `school` VALUES ('2', '理学院', '14', '1');

-- ----------------------------
-- Table structure for stockin_book_supplier
-- ----------------------------
DROP TABLE IF EXISTS `stockin_book_supplier`;
CREATE TABLE `stockin_book_supplier` (
  `stock_in_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  PRIMARY KEY (`stock_in_id`,`book_id`,`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stockin_book_supplier
-- ----------------------------
INSERT INTO `stockin_book_supplier` VALUES ('1', '1', '1');
INSERT INTO `stockin_book_supplier` VALUES ('2', '1', '1');
INSERT INTO `stockin_book_supplier` VALUES ('3', '2', '1');
INSERT INTO `stockin_book_supplier` VALUES ('5', '1', '1');
INSERT INTO `stockin_book_supplier` VALUES ('6', '4', '6');

-- ----------------------------
-- Table structure for stockout_book
-- ----------------------------
DROP TABLE IF EXISTS `stockout_book`;
CREATE TABLE `stockout_book` (
  `stock_out_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  PRIMARY KEY (`stock_out_id`,`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stockout_book
-- ----------------------------
INSERT INTO `stockout_book` VALUES ('1', '1');
INSERT INTO `stockout_book` VALUES ('2', '2');
INSERT INTO `stockout_book` VALUES ('7', '2');

-- ----------------------------
-- Table structure for stock_in
-- ----------------------------
DROP TABLE IF EXISTS `stock_in`;
CREATE TABLE `stock_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_sum` varchar(255) NOT NULL,
  `stock_in_date` date NOT NULL,
  `price` double NOT NULL,
  `department_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock_in
-- ----------------------------
INSERT INTO `stock_in` VALUES ('1', '50', '2018-05-06', '25', '计算机学院教材部');
INSERT INTO `stock_in` VALUES ('2', '32', '2019-07-09', '54', '土木学院教材部');
INSERT INTO `stock_in` VALUES ('3', '45', '2017-09-30', '60', '音乐学院教材部');
INSERT INTO `stock_in` VALUES ('5', '1000', '2020-01-03', '43', '计算机学院教材部');
INSERT INTO `stock_in` VALUES ('6', '400', '2020-04-14', '42', '计算机学院教材部');

-- ----------------------------
-- Table structure for stock_out
-- ----------------------------
DROP TABLE IF EXISTS `stock_out`;
CREATE TABLE `stock_out` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_sum` varchar(255) NOT NULL,
  `stock_out_date` date NOT NULL,
  `department_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock_out
-- ----------------------------
INSERT INTO `stock_out` VALUES ('1', '30', '2019-08-07', '计算机学院教材部');
INSERT INTO `stock_out` VALUES ('2', '20', '2020-03-20', '音乐学院教材部');
INSERT INTO `stock_out` VALUES ('7', '20', '2020-03-02', '计算机学院教材部');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '学生姓名(跟登录的昵称相同)',
  `number` varchar(255) NOT NULL COMMENT '学生学号（与登录用户名相同）',
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `major_id` int(11) DEFAULT NULL COMMENT '专业id',
  `school_id` int(11) DEFAULT NULL COMMENT '学院id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1表示未删除，0表示已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '雨女', '18408001003', '3', '1', '1', '1');
INSERT INTO `student` VALUES ('2', '河童', '18408001004', '2', '1', '1', '1');
INSERT INTO `student` VALUES ('3', '座敷童子', '18408001005', '4', '1', '1', '0');
INSERT INTO `student` VALUES ('4', '鬼使黑', '18408001006', '4', '1', '1', '1');
INSERT INTO `student` VALUES ('5', '黑童子', '18408001007', '1', '1', '1', '1');
INSERT INTO `student` VALUES ('6', '久次良', '18408001005', '6', '2', '1', '1');
INSERT INTO `student` VALUES ('7', '桃花妖', '18408000101', '12', '2', '1', '1');
INSERT INTO `student` VALUES ('9', '白童子', '18408000202', '1', '1', '1', '1');
INSERT INTO `student` VALUES ('10', '孟婆', '18408001011', '4', '1', '1', '0');
INSERT INTO `student` VALUES ('12', '金鱼姬', '18408000910', '6', '2', '1', '1');
INSERT INTO `student` VALUES ('13', '小松丸', '18408001212', '15', '5', '2', '1');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `tel` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('1', '湖南中教产业发展股份有限公司', '12345678899');
INSERT INTO `supplier` VALUES ('2', '湖南省新教材有限责任公司', '12345678890');
INSERT INTO `supplier` VALUES ('3', '湖南省新华书店有限责任公司', '12345678891');
INSERT INTO `supplier` VALUES ('4', '湖南弘道文化传播有限公司', '12345678892');
INSERT INTO `supplier` VALUES ('5', '湖南书畅教育图书有限公司', '12345678893');
INSERT INTO `supplier` VALUES ('6', '长沙中南文化传播有限公司', '12345678894');
INSERT INTO `supplier` VALUES ('7', '湖南盛世教育文化传媒有限公司', '12345678895');
INSERT INTO `supplier` VALUES ('8', '湖北新华书店有限公司', '12345677789');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL COMMENT '教师工号',
  `school_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '不知火', '1000000001', '1');
INSERT INTO `teacher` VALUES ('3', '酒吞童子', '1000000003', '3');
INSERT INTO `teacher` VALUES ('4', '鬼童丸', '1000000004', '4');
INSERT INTO `teacher` VALUES ('5', '八岐大蛇', '1000000005', '5');
INSERT INTO `teacher` VALUES ('6', '不知火', '10000002', '1');
INSERT INTO `teacher` VALUES ('7', '不知火', '10002', '1');
INSERT INTO `teacher` VALUES ('8', '不知火', '1000000000001', '1');
INSERT INTO `teacher` VALUES ('9', '不知火', '1000003', '1');
INSERT INTO `teacher` VALUES ('10', '不知火', '1000000004', '1');
INSERT INTO `teacher` VALUES ('11', '茨木童子', '1000000002', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT '' COMMENT '用户昵称',
  `salt` varchar(50) NOT NULL,
  `email` varchar(255) DEFAULT '' COMMENT '用户邮箱',
  `sex` char(1) DEFAULT '男' COMMENT '性别',
  `tel` varchar(255) DEFAULT NULL COMMENT '用户电话号码',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1表示未删除，0表示已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhang', '195d91be1e3ba6f1c857d46f24c5a454', '李四', 'zhang', '123@qq.com', '男', '12323232323', '1');
INSERT INTO `user` VALUES ('2', '18408001003', '93c50021c6fb7e1732efc968be28a610', '雨女', '18408001003', '134@qq.com', '女', '12424242424', '1');
INSERT INTO `user` VALUES ('3', '18408001006', '9c60fcb6404567d6dc4a6f5b08984b23', '鬼使黑', '18408001006', '124@qq.com', '男', '13245678912', '1');
INSERT INTO `user` VALUES ('4', '1000000001', 'a8d8ab10b32f1ab8017b7013b030e35b', '不知火', '1000000001', '111@qq.com', '女', '12525252525', '1');
INSERT INTO `user` VALUES ('6', '18408001005', 'ecd8ee3cda20e24d923570867586fcd8', '久次良', '18408001005', '1050367616@qq.com', '女', '12727272727', '1');
INSERT INTO `user` VALUES ('7', '18408001007', '1ab407d686f268ad712e323fea5e09b9', '黑童子', '18408001007', '154@qq.com', '男', '12828282828', '1');
INSERT INTO `user` VALUES ('8', '18408001004', '24070388d7c775cecc8392db8d2a5f0d', '河童', '18408001004', '164@qq.com', '男', '12929292929', '1');
INSERT INTO `user` VALUES ('9', '10000002', 'cca287b3049cbad831bea31fe878e13f', '不知火', '10000002', '', '男', null, '1');
INSERT INTO `user` VALUES ('10', '10002', '710a92e1f5787e34dc0b3f0eb01545c1', '不知火', '10002', '', '男', null, '1');
INSERT INTO `user` VALUES ('11', '1000000000001', '77d3f7ef667bfa628cc1cc17f88b1f60', '不知火', '1000000000001', '', '男', null, '1');
INSERT INTO `user` VALUES ('12', '1000003', '1976213dcda84f5468d220018df55a88', '不知火', '1000003', '', '男', null, '1');
INSERT INTO `user` VALUES ('13', '1000000004', 'c88757098c9020a2741ad02120a1fd49', '不知火', '1000000004', '', '男', null, '1');
INSERT INTO `user` VALUES ('14', '1000000002', 'af21754394ef0b1cc185a658764c12fe', '茨木童子', '1000000002', '', '男', null, '1');
INSERT INTO `user` VALUES ('21', '18408000101', '21f21b9a67e41f357c02373879bb37a8', '桃花妖', '18408000101', '', '男', null, '1');
INSERT INTO `user` VALUES ('23', '18408000202', '87fd62e1d6019c7db593f438abf4bd3f', '白童子', '18408000202', '', '男', null, '1');
INSERT INTO `user` VALUES ('24', '18408001011', 'f52311a1bae0627e6bb63a7df3032de9', '孟婆', '18408001011', '', '男', null, '0');
INSERT INTO `user` VALUES ('27', '18408000910', '5b205c10767098ea2c3e28c1445899ec', '樱花妖', '18408000910', '', '男', null, '0');
INSERT INTO `user` VALUES ('30', '18408001212', 'b2f273c39bfa4f87a2df5ca888f14950', '小松丸', '18408001212', '', '男', null, '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '4');
INSERT INTO `user_role` VALUES ('3', '4');
INSERT INTO `user_role` VALUES ('4', '3');
INSERT INTO `user_role` VALUES ('5', '3');
INSERT INTO `user_role` VALUES ('6', '4');
INSERT INTO `user_role` VALUES ('7', '4');
INSERT INTO `user_role` VALUES ('8', '4');
INSERT INTO `user_role` VALUES ('9', '3');
INSERT INTO `user_role` VALUES ('10', '3');
INSERT INTO `user_role` VALUES ('11', '3');
INSERT INTO `user_role` VALUES ('12', '3');
INSERT INTO `user_role` VALUES ('13', '3');
INSERT INTO `user_role` VALUES ('14', '3');
INSERT INTO `user_role` VALUES ('21', '4');
INSERT INTO `user_role` VALUES ('22', '4');
INSERT INTO `user_role` VALUES ('23', '2');
INSERT INTO `user_role` VALUES ('24', '2');
INSERT INTO `user_role` VALUES ('27', '2');
INSERT INTO `user_role` VALUES ('30', '4');
