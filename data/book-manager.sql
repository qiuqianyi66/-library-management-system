/*
SQLyog Ultimate v9.60 
MySQL - 8.0.34 : Database - book-manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`book-manager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `book-manager`;

/*Table structure for table `tb_book` */

DROP TABLE IF EXISTS `tb_book`;

CREATE TABLE `tb_book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `isbn` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'ISBN号',
  `type` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `author` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `press` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `publish_date` date NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_count` int NOT NULL DEFAULT '0',
  `shelf_location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '书架位置',
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '封面图片',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '图书简介',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0上架 1下架）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `tb_book` */

insert  into `tb_book`(`book_id`,`book_name`,`isbn`,`type`,`author`,`press`,`publish_date`,`price`,`stock_count`,`shelf_location`,`cover_image`,`description`,`status`,`create_by`,`create_time`,`update_by`,`update_time`) values (1,'Java编程思想','978-7-111-21250-8','1','Bruce Eckel','机械工业出版社','2007-06-01',108.00,5,'A-01-01','','Java经典入门书籍，全面讲解Java编程核心概念','0','admin','2025-05-09 17:10:00','',NULL),(2,'数据结构与算法分析','978-7-111-52537-3','2','Mark Allen Weiss','机械工业出版社','2019-01-01',79.00,3,'A-01-02','','数据结构与算法经典教材','0','admin','2025-05-09 17:11:00','',NULL),(3,'高等数学','978-7-04-039663-7','3','同济大学数学系','高等教育出版社','2014-07-01',49.80,8,'B-02-01','','理工科基础数学教材','0','admin','2025-05-09 17:12:00','',NULL),(4,'大学英语精读','978-7-5446-4280-5','4','董亚芬','上海外语教育出版社','2017-03-01',45.00,6,'B-02-02','','大学英语精读教材','0','admin','2025-05-09 17:13:00','',NULL),(5,'人工智能导论','978-7-302-54685-4','5','李德毅','清华大学出版社','2020-06-01',69.00,4,'C-03-01','','人工智能领域入门指南','0','admin','2025-05-09 17:14:00','',NULL),(6,'数据库系统概论','978-7-04-040664-0','6','王珊','高等教育出版社','2014-09-01',39.90,5,'C-03-02','','数据库理论经典教材','0','admin','2025-05-09 17:15:00','',NULL);

/*Table structure for table `tb_config` */

DROP TABLE IF EXISTS `tb_config`;

CREATE TABLE `tb_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

/*Data for the table `tb_config` */

insert  into `tb_config`(`config_id`,`config_name`,`config_key`,`config_value`,`config_type`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2025-04-20 15:08:34','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2025-04-20 15:08:34','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2025-04-20 15:08:34','',NULL,'深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','true','Y','admin','2025-04-20 15:08:34','',NULL,'是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2025-04-20 15:08:34','',NULL,'是否开启注册用户功能（true开启，false关闭）'),(6,'用户登录-黑名单列表','sys.login.blackIPList','','Y','admin','2025-04-20 15:08:34','',NULL,'设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）'),(7,'借阅管理-最大同时借阅数量','borrow.max_count','5','Y','admin','2025-05-10 10:00:00','',NULL,'每个读者最大同时借阅图书数量'),(8,'借阅管理-默认借阅天数','borrow.default_days','30','Y','admin','2025-05-10 10:00:00','',NULL,'借阅默认天数'),(9,'借阅管理-最大续借次数','borrow.renew_max_count','2','Y','admin','2025-05-10 10:00:00','',NULL,'单次借阅最大续借次数'),(10,'借阅管理-续借延后天数','borrow.renew_days','15','Y','admin','2025-05-10 10:00:00','',NULL,'每次续借延后的天数'),(11,'罚款管理-每日逾期罚款金额','fine.per_day','0.50','Y','admin','2025-05-10 10:00:00','',NULL,'每逾期一天罚款金额（元）');

/*Table structure for table `tb_dict_data` */

DROP TABLE IF EXISTS `tb_dict_data`;

CREATE TABLE `tb_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

/*Data for the table `tb_dict_data` */

insert  into `tb_dict_data`(`dict_code`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`list_class`,`is_default`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,1,'男','0','sys_user_sex','','','Y','0','admin','2025-04-20 15:08:33','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2025-04-20 15:08:33','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2025-04-20 15:08:33','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2025-04-20 15:08:33','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2025-04-20 15:08:33','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2025-04-20 15:08:33','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2025-04-20 15:08:33','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2025-04-20 15:08:33','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2025-04-20 15:08:33','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2025-04-20 15:08:33','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2025-04-20 15:08:33','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2025-04-20 15:08:33','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2025-04-20 15:08:33','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2025-04-20 15:08:33','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2025-04-20 15:08:33','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2025-04-20 15:08:33','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2025-04-20 15:08:33','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2025-04-20 15:08:33','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2025-04-20 15:08:33','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2025-04-20 15:08:33','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2025-04-20 15:08:33','',NULL,'停用状态'),(100,1,'正常','1','reader_status',NULL,NULL,'Y','0','admin','2025-05-03 11:34:17','',NULL,'读者状态正常'),(101,2,'暂停','2','reader_status',NULL,NULL,'N','0','admin','2025-05-03 11:36:25','',NULL,'读者状态暂停'),(102,3,'注销','3','reader_status',NULL,NULL,'N','0','admin','2025-05-03 11:52:25','',NULL,'读者状态注销'),(103,1,'数计学院','01','department_list',NULL,NULL,'Y','0','admin','2025-05-08 19:16:54','',NULL,'学院列表数计学院'),(104,2,'物电学院','02','department_list',NULL,NULL,'N','0','admin','2025-05-08 19:18:15','',NULL,'学院列表物电学院'),(105,3,'经管法学院','03','department_list',NULL,NULL,'N','0','admin','2025-05-08 19:19:05','',NULL,'学院列表经管法学院'),(106,4,'人文学院','04','department_list',NULL,NULL,'N','0','admin','2025-05-08 19:20:15','',NULL,'学院列表人文学院'),(107,1,'计算机类','01','book_type_list',NULL,NULL,'N','0','admin','2025-05-09 17:03:09','',NULL,'图书类别列表计算机类'),(108,2,'管理类','02','book_type_list',NULL,NULL,'N','0','admin','2025-05-09 17:04:11','',NULL,'图书类别列表管理类'),(109,3,'机械类','03','book_type_list',NULL,NULL,'N','0','admin','2025-05-09 17:05:14','',NULL,'图书类别列表机械类'),(110,1,'学生','0','reader_type',NULL,NULL,'Y','0','admin','2025-05-10 10:00:00','',NULL,'学生读者'),(111,2,'教师','1','reader_type',NULL,NULL,'N','0','admin','2025-05-10 10:00:00','',NULL,'教师读者'),(112,3,'校外','2','reader_type',NULL,NULL,'N','0','admin','2025-05-10 10:00:00','',NULL,'校外读者'),(113,1,'借阅中','0','borrow_status',NULL,'primary','Y','0','admin','2025-05-10 10:00:00','',NULL,'借阅中'),(114,2,'已归还','1','borrow_status',NULL,'success','N','0','admin','2025-05-10 10:00:00','',NULL,'已归还'),(115,3,'逾期未还','2','borrow_status',NULL,'danger','N','0','admin','2025-05-10 10:00:00','',NULL,'逾期未还'),(116,1,'微信支付','wechat','pay_method',NULL,NULL,'Y','0','admin','2025-05-10 10:00:00','',NULL,'微信支付'),(117,2,'支付宝','alipay','pay_method',NULL,NULL,'N','0','admin','2025-05-10 10:00:00','',NULL,'支付宝支付');

/*Table structure for table `tb_dict_type` */

DROP TABLE IF EXISTS `tb_dict_type`;

CREATE TABLE `tb_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

/*Data for the table `tb_dict_type` */

insert  into `tb_dict_type`(`dict_id`,`dict_name`,`dict_type`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'用户性别','sys_user_sex','0','admin','2025-04-20 15:08:33','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2025-04-20 15:08:33','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2025-04-20 15:08:33','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2025-04-20 15:08:33','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2025-04-20 15:08:33','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2025-04-20 15:08:33','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2025-04-20 15:08:33','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2025-04-20 15:08:33','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2025-04-20 15:08:33','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2025-04-20 15:08:33','',NULL,'登录状态列表'),(100,'读者状态','reader_status','0','admin','2025-05-03 11:32:36','',NULL,'读者状态列表'),(101,'学院列表','department_list','0','admin','2025-05-08 19:14:54','',NULL,'学院列表'),(102,'图书类别列表','book_type_list','0','admin','2025-05-09 17:01:50','',NULL,'图书类别列表'),(103,'读者类型','reader_type','0','admin','2025-05-10 10:00:00','',NULL,'读者类型列表'),(104,'借阅状态','borrow_status','0','admin','2025-05-10 10:00:00','',NULL,'借阅状态列表'),(105,'支付方式','pay_method','0','admin','2025-05-10 10:00:00','',NULL,'支付方式列表');

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2054 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

/*Data for the table `tb_menu` */

insert  into `tb_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`query`,`route_name`,`is_frame`,`is_cache`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (2030,'图书管理',0,4,'/bookmanager',NULL,NULL,'',1,0,'M','0','0',NULL,'documentation','admin','2025-05-02 22:45:06','',NULL,''),(2031,'读者管理',2030,1,'reader','bookmanager/reader/index',NULL,'',1,0,'C','0','0','','peoples','admin','2025-05-02 22:47:25','admin','2025-05-02 22:51:00',''),(2033,'图书管理',2030,2,'book','bookmanager/book/index',NULL,'',1,0,'C','0','0','','chart','admin','2025-05-02 22:51:51','admin','2025-05-02 22:53:36',''),(2034,'借阅管理',2030,3,'borrow','bookmanager/borrow/index',NULL,'',1,0,'C','0','0','','form','admin','2025-05-02 22:52:48','admin','2025-05-02 22:54:29',''),(2035,'系统',0,5,'system',NULL,NULL,'',1,0,'M','0','0',NULL,'system','admin','2025-05-02 22:57:17','',NULL,''),(2036,'修改密码',2035,1,'changepassword','system/changepassword/index',NULL,'',1,0,'C','0','0','','password','admin','2025-05-02 22:58:27','admin','2025-05-02 22:59:18',''),(2038,'读者管理',2031,1,'reader','bookmanager/reader/index',NULL,'',1,0,'C','0','0','bookmanager:reader:list','#','admin','2025-05-02 23:44:18','',NULL,'读者管理菜单'),(2039,'读者管理查询',2038,1,'#','',NULL,'',1,0,'F','0','0','bookmanager:reader:query','#','admin','2025-05-02 23:44:18','',NULL,''),(2040,'读者管理新增',2038,2,'#','',NULL,'',1,0,'F','0','0','bookmanager:reader:add','#','admin','2025-05-02 23:44:18','',NULL,''),(2041,'读者管理修改',2038,3,'#','',NULL,'',1,0,'F','0','0','bookmanager:reader:edit','#','admin','2025-05-02 23:44:18','',NULL,''),(2042,'读者管理删除',2038,4,'#','',NULL,'',1,0,'F','0','0','bookmanager:reader:remove','#','admin','2025-05-02 23:44:18','',NULL,''),(2044,'图书管理',2033,1,'book','bookmanager/book/index',NULL,'',1,0,'C','0','0','bookmanager:book:list','#','admin','2025-05-09 20:35:46','',NULL,'图书管理菜单'),(2045,'图书管理查询',2044,1,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:book:query','#','admin','2025-05-09 20:37:33','',NULL,''),(2046,'图书管理新增',2044,2,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:book:add','#','admin','2025-05-09 20:38:52','',NULL,''),(2047,'图书管理修改',2044,3,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:book:edit','#','admin','2025-05-09 20:40:00','',NULL,''),(2048,'图书管理删除',2044,4,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:reader:remove','#','admin','2025-05-09 20:40:57','',NULL,''),(2049,'借阅管理',2034,1,'borrow','bookmanager/borrow/index',NULL,'',1,0,'C','0','0','bookmanager:borrow:list','#','admin','2025-05-10 23:32:25','',NULL,'图书管理菜单'),(2050,'借阅管理查询',2049,1,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:borrow:query','#','admin','2025-05-10 23:34:31','',NULL,''),(2051,'借阅管理借阅',2049,2,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:borrow:add','#','admin','2025-05-10 23:36:12','',NULL,''),(2052,'借阅管理修改',2049,3,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:book:edit','#','admin','2025-05-10 23:37:17','',NULL,''),(2053,'借阅管理删除',2049,4,'#',NULL,NULL,'',1,0,'F','0','0','bookmanager:reader:remove','#','admin','2025-05-10 23:38:23','',NULL,'');

/*Table structure for table `tb_reader` */

DROP TABLE IF EXISTS `tb_reader`;

CREATE TABLE `tb_reader` (
  `reader_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `sex` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `department` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `reader_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '读者类型（0学生 1教师 2校外）',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像',
  `max_borrow_count` int DEFAULT '5' COMMENT '最大借阅数量',
  `blacklist_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '黑名单状态（0正常 1提醒 2限制）',
  `handle_date` date NOT NULL,
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`reader_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_reader` */

insert  into `tb_reader`(`reader_id`,`name`,`sex`,`department`,`reader_type`,`phone`,`avatar`,`max_borrow_count`,`blacklist_status`,`handle_date`,`status`,`create_by`,`create_time`,`update_by`,`update_time`) values (1,'李丽','1','01','0','13800000001','',5,'0','2011-09-08','1','admin','2025-05-03 11:30:00','',NULL),(2,'赵健','0','02','1','13800000002','',10,'0','2011-09-09','1','admin','2025-05-03 11:30:00','',NULL),(3,'李建林','0','03','0','13800000003','',5,'0','2011-09-10','1','admin','2025-05-03 11:30:00','',NULL),(4,'张飞','0','04','0','13800000004','',5,'0','2011-09-10','1','admin','2025-05-03 11:30:00','',NULL);

/*Table structure for table `tb_borrow_record` */

DROP TABLE IF EXISTS `tb_borrow_record`;

CREATE TABLE `tb_borrow_record` (
  `borrow_id` bigint NOT NULL AUTO_INCREMENT COMMENT '借阅ID',
  `reader_id` int NOT NULL COMMENT '读者ID',
  `book_id` int NOT NULL COMMENT '图书ID',
  `borrow_date` datetime NOT NULL COMMENT '借阅日期',
  `due_date` datetime NOT NULL COMMENT '应还日期',
  `return_date` datetime DEFAULT NULL COMMENT '实际归还日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0借阅中 1已归还 2逾期未还）',
  `renew_count` int DEFAULT '0' COMMENT '续借次数',
  `fine_amount` decimal(10,2) DEFAULT '0.00' COMMENT '罚款金额',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`borrow_id`) USING BTREE,
  KEY `idx_reader_id` (`reader_id`),
  KEY `idx_book_id` (`book_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='借阅记录表';

/*Data for the table `tb_borrow_record` */

/*Table structure for table `tb_fine_record` */

DROP TABLE IF EXISTS `tb_fine_record`;

CREATE TABLE `tb_fine_record` (
  `fine_id` bigint NOT NULL AUTO_INCREMENT COMMENT '罚款ID',
  `borrow_id` bigint NOT NULL COMMENT '借阅ID',
  `reader_id` int NOT NULL COMMENT '读者ID',
  `amount` decimal(10,2) NOT NULL COMMENT '罚款金额',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0未缴纳 1已缴纳）',
  `pay_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付方式（wechat/alipay）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`fine_id`) USING BTREE,
  KEY `idx_reader_id` (`reader_id`),
  KEY `idx_borrow_id` (`borrow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='罚款记录表';

/*Data for the table `tb_fine_record` */

/*Table structure for table `tb_payment_order` */

DROP TABLE IF EXISTS `tb_payment_order`;

CREATE TABLE `tb_payment_order` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `fine_id` bigint NOT NULL COMMENT '罚款ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `pay_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付方式（wechat/alipay）',
  `pay_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '支付状态（0待支付 1已支付 2已关闭）',
  `trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '交易流水号',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `idx_fine_id` (`fine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='支付订单表';

/*Data for the table `tb_payment_order` */

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`user_name`,`nick_name`,`user_type`,`email`,`phonenumber`,`sex`,`avatar`,`password`,`status`,`del_flag`,`login_ip`,`login_date`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-05-10 23:42:10','admin','2025-04-20 15:08:26','','2025-05-10 23:42:10','管理员'),(2,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-04-20 15:08:26','admin','2025-04-20 15:08:26','admin','2025-04-26 11:48:44','测试员'),(100,'笨笨熊','笨笨熊','00','','','0','','$2a$10$.DtRrcbXRsP7nqA7FmCTKOulXIokCLq4dA5WBvIYac0YDcxoPQckK','0','2','',NULL,'admin','2025-04-20 15:19:50','',NULL,NULL),(101,'bbx','笨笨熊','00','','','0','','$2a$10$H9EnpQkeggLuW7JfPMsOqeIQQ/df.0avF/lBIiLPLM0CdJKj1GMx6','0','0','127.0.0.1','2025-04-29 10:44:18','admin','2025-04-20 16:02:41','admin','2025-04-30 09:47:07',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
