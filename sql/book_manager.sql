/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : book_manager

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-04-22 10:07:52
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
INSERT INTO `book` VALUES ('1', '《计算机组成原理》', '唐朔飞', '43', '高等教育出版社', '2008-01-01', '8003');
INSERT INTO `book` VALUES ('2', '《数据库系统概论》', ' 王珊/萨师煊', '39.6', '高等教育出版社', '2014-01-01', '1397');
INSERT INTO `book` VALUES ('3', '《毛泽东思想和中国特色社会主义理论体系概论》', ' 本书编写组', '25', '高等教育出版社', '2013-01-01', '540');
INSERT INTO `book` VALUES ('4', '《计算机应用基础》', '姜帆', '42', '武汉理工大学出版社', '2017-09-01', '800');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '班级名',
  `major_id` int(11) NOT NULL COMMENT '专业id',
  `principal_name` varchar(255) NOT NULL,
  `principal_tel` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('1', '软件工程1801班', '23', '黑童子', '13245678910');
INSERT INTO `class` VALUES ('2', '软件工程1802', '23', '白童子', '13245678911');
INSERT INTO `class` VALUES ('3', '软件工程1803', '23', '鬼使黑', '13245678912');
INSERT INTO `class` VALUES ('4', '软件工程1804', '23', '鬼使白', '13245678913');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

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
  `school_id` int(11) NOT NULL COMMENT '学院id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('1', '包装工程', '2');
INSERT INTO `major` VALUES ('2', ' 印刷工程', '2');
INSERT INTO `major` VALUES ('3', ' 高分子材料与工程', '2');
INSERT INTO `major` VALUES ('4', ' 产品设计', '3');
INSERT INTO `major` VALUES ('5', '服装与服饰设计', '3');
INSERT INTO `major` VALUES ('6', ' 艺术设计', '3');
INSERT INTO `major` VALUES ('7', ' 视觉传达设计', '3');
INSERT INTO `major` VALUES ('8', ' 动画于数字媒体艺术', '3');
INSERT INTO `major` VALUES ('9', ' 包装设计', '3');
INSERT INTO `major` VALUES ('10', ' 环境设计', '3');
INSERT INTO `major` VALUES ('11', '无机非金属材料', '4');
INSERT INTO `major` VALUES ('12', ' 粉体材料科学与工程', '4');
INSERT INTO `major` VALUES ('13', ' 金属材料工程', '4');
INSERT INTO `major` VALUES ('14', ' 冶金工程', '4');
INSERT INTO `major` VALUES ('15', ' 道路与桥梁', '5');
INSERT INTO `major` VALUES ('16', ' 建筑工程', '5');
INSERT INTO `major` VALUES ('17', ' 建筑环境与能源应用工程', '5');
INSERT INTO `major` VALUES ('18', ' 给排水科学与工程', '5');
INSERT INTO `major` VALUES ('19', ' 工程管理', '5');
INSERT INTO `major` VALUES ('20', ' 城市地下工程', '5');
INSERT INTO `major` VALUES ('21', ' 计算机科学与技术', '1');
INSERT INTO `major` VALUES ('22', ' 通信工程', '1');
INSERT INTO `major` VALUES ('23', ' 软件工程', '1');
INSERT INTO `major` VALUES ('24', ' 网络工程', '1');
INSERT INTO `major` VALUES ('25', ' 物联网工程', '1');
INSERT INTO `major` VALUES ('26', ' 电气工程', '6');
INSERT INTO `major` VALUES ('27', ' 仪器科学与技术', '6');
INSERT INTO `major` VALUES ('28', ' 控制科学与工程', '6');
INSERT INTO `major` VALUES ('29', ' 电子科学与技术', '6');
INSERT INTO `major` VALUES ('30', ' 电子科学', '7');
INSERT INTO `major` VALUES ('31', ' 信息工程', '7');
INSERT INTO `major` VALUES ('32', ' 交通设备与控制工程', '7');
INSERT INTO `major` VALUES ('33', ' 生物技术', '21');
INSERT INTO `major` VALUES ('34', ' 生物医学工程', '21');
INSERT INTO `major` VALUES ('35', ' 应用化学', '21');
INSERT INTO `major` VALUES ('36', ' 化学工程', '21');
INSERT INTO `major` VALUES ('37', ' 机械设计制造工程', '22');
INSERT INTO `major` VALUES ('38', ' 机械电子工程', '22');
INSERT INTO `major` VALUES ('39', ' 材料成型及控制工程', '22');
INSERT INTO `major` VALUES ('40', ' 工业设计', '22');
INSERT INTO `major` VALUES ('41', ' 建筑学', '8');
INSERT INTO `major` VALUES ('42', ' 城乡规划', '8');
INSERT INTO `major` VALUES ('43', ' 自然地理与资源环境', '8');
INSERT INTO `major` VALUES ('44', ' 环境生态工程', '8');
INSERT INTO `major` VALUES ('45', ' 信息与计算科学', '9');
INSERT INTO `major` VALUES ('46', ' 应用物理学', '9');
INSERT INTO `major` VALUES ('47', ' 数学与应用数学', '9');
INSERT INTO `major` VALUES ('48', ' 工商管理', '10');
INSERT INTO `major` VALUES ('49', ' 市场营销', '10');
INSERT INTO `major` VALUES ('50', ' 公共管理', '10');
INSERT INTO `major` VALUES ('51', ' 信息管理', '10');
INSERT INTO `major` VALUES ('52', ' 会计学', '11');
INSERT INTO `major` VALUES ('53', ' 财务管理', '11');
INSERT INTO `major` VALUES ('54', ' 国际经济与贸易', '11');
INSERT INTO `major` VALUES ('55', ' 金融工程', '11');
INSERT INTO `major` VALUES ('56', ' 投资学', '11');
INSERT INTO `major` VALUES ('57', ' 广告学', '12');
INSERT INTO `major` VALUES ('58', ' 汉语言文学', '12');
INSERT INTO `major` VALUES ('59', ' 新闻学', '12');
INSERT INTO `major` VALUES ('60', ' 戏剧影视文学', '12');
INSERT INTO `major` VALUES ('61', ' 播音主持与艺术', '12');
INSERT INTO `major` VALUES ('62', '法学', '13');
INSERT INTO `major` VALUES ('63', ' 英语', '14');
INSERT INTO `major` VALUES ('64', ' 日语', '14');
INSERT INTO `major` VALUES ('65', ' 翻译', '14');
INSERT INTO `major` VALUES ('66', ' 体育教育', '15');
INSERT INTO `major` VALUES ('67', ' 社会体育指导与管理', '15');
INSERT INTO `major` VALUES ('68', ' 运动训练', '15');
INSERT INTO `major` VALUES ('69', ' 音乐学', '16');
INSERT INTO `major` VALUES ('70', ' 舞蹈表演', '16');
INSERT INTO `major` VALUES ('71', '视觉传达设计', '17');
INSERT INTO `major` VALUES ('72', '会计学', '17');
INSERT INTO `major` VALUES ('73', ' 市场营销', '17');
INSERT INTO `major` VALUES ('74', ' 艺术设计', '17');

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
INSERT INTO `major_course` VALUES ('1', '23', '1');
INSERT INTO `major_course` VALUES ('2', '23', '2');
INSERT INTO `major_course` VALUES ('3', '24', '1');
INSERT INTO `major_course` VALUES ('4', '24', '2');

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
  `principal_name` varchar(255) NOT NULL,
  `principal_tel` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '计算机学院', '不知火', '12345678910');
INSERT INTO `school` VALUES ('2', '包装与材料工程学院', '茨木童子', '12345678911');
INSERT INTO `school` VALUES ('3', '包装设计艺术学院', '酒吞童子', '12345678912');
INSERT INTO `school` VALUES ('4', '冶金与材料工程学院', '鬼童丸', '12345678913');
INSERT INTO `school` VALUES ('5', '土木工程学院', '八岐大蛇', '12345678914');
INSERT INTO `school` VALUES ('6', '电气与信息工程学院', '彼岸花', '12345678915');
INSERT INTO `school` VALUES ('7', '交通工程学院', '雪童子', '12345678916');
INSERT INTO `school` VALUES ('8', '城市与环境学院', '大天狗', '12345678917');
INSERT INTO `school` VALUES ('9', '理学院', '白藏主', '12345678918');
INSERT INTO `school` VALUES ('10', '商学院', ' 阎魔', '12345678919');
INSERT INTO `school` VALUES ('11', ' 经济与贸易学院', ' 两面佛', '12345678920');
INSERT INTO `school` VALUES ('12', ' 文学与新闻传播学院', ' 鬼切', '12345678921');
INSERT INTO `school` VALUES ('13', ' 法学院', ' 辉夜姬', '12345678922');
INSERT INTO `school` VALUES ('14', ' 外国语学院', '青行灯', '12345678923');
INSERT INTO `school` VALUES ('15', ' 体育学院', ' 玉藻前', '12345678924');
INSERT INTO `school` VALUES ('16', ' 音乐学院', ' 荒', '12345678925');
INSERT INTO `school` VALUES ('17', ' 国际学院', '面灵气', '12345678926');
INSERT INTO `school` VALUES ('21', ' 生命科学与化学学院', '大岳丸', '12345678930');
INSERT INTO `school` VALUES ('22', '机械工程学院', '缘结神', '12345678931');

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
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `major_id` int(11) NOT NULL COMMENT '专业id',
  `school_id` int(11) NOT NULL COMMENT '学院id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '雨女', '18408001003', '4', '23', '1');
INSERT INTO `student` VALUES ('2', ' 河童', '18408001004', '4', '23', '1');
INSERT INTO `student` VALUES ('3', ' 座敷童子', '18408001005', '4', '23', '1');
INSERT INTO `student` VALUES ('4', '鬼使黑', '18408001006', '4', '23', '1');
INSERT INTO `student` VALUES ('5', ' 黑童子', '18408001007', '1', '23', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '不知火', '1000000001', '1');
INSERT INTO `teacher` VALUES ('2', '茨木童子', '1000000002', '2');
INSERT INTO `teacher` VALUES ('3', '酒吞童子', '1000000003', '3');
INSERT INTO `teacher` VALUES ('4', '鬼童丸', '1000000004', '4');
INSERT INTO `teacher` VALUES ('5', '八岐大蛇', '1000000005', '5');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhang', '195d91be1e3ba6f1c857d46f24c5a454', '李四', 'zhang', '123@qq.com', '男');
INSERT INTO `user` VALUES ('2', '18408001003', '93c50021c6fb7e1732efc968be28a610', '雨女', '18408001003', '134@qq.com', '女');
INSERT INTO `user` VALUES ('3', '18408001006', '9c60fcb6404567d6dc4a6f5b08984b23', '鬼使黑', '18408001006', '124@qq.com', '男');
INSERT INTO `user` VALUES ('4', '1000000001', 'a8d8ab10b32f1ab8017b7013b030e35b', '不知火', '1000000001', '111@qq.com', '女');
INSERT INTO `user` VALUES ('5', '1000000002', 'af21754394ef0b1cc185a658764c12fe', '茨木童子', '1000000002', '112@qq.com', '男');
INSERT INTO `user` VALUES ('6', '18408001005', '1774b2bf6a1f5d6793906bed7669ae14', '久次良', '18408001005', '1050367616@qq.com', '女');

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
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '4');
INSERT INTO `user_role` VALUES ('4', '3');
INSERT INTO `user_role` VALUES ('5', '3');
INSERT INTO `user_role` VALUES ('6', '2');
