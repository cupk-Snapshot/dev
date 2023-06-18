/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.40 : Database - snapshot_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`snapshot_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `snapshot_db`;

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源id集合',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) DEFAULT NULL COMMENT '申请权限范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '授权模式',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '回调地址',
  `authorities` varchar(256) DEFAULT NULL COMMENT '权限集合',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新令牌有效时间',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '额外信息',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '是否自动授权',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('snapshot',NULL,'$2a$10$m.kA/ok5SOY0dqU0lc7tzO.9mStMqQPA1bZtMM/ODd147RdnuYeEu','all','authorization_code,password,refresh_token,sms_code','https://www.baidu.com',NULL,7200,15,NULL,'false');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限表主键',
  `menu_name` varchar(20) NOT NULL COMMENT '权限名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`menu_name`,`create_time`,`update_time`,`is_deleted`) values (1,'insert',NULL,NULL,0),(2,'delete',NULL,NULL,0),(3,'select',NULL,NULL,0),(4,'update',NULL,NULL,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色代码',
  `role_desc` varchar(32) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`role_code`,`role_desc`,`create_time`,`update_time`,`is_deleted`) values (1,'管理员',NULL,NULL,NULL,NULL,0),(2,'用户',NULL,NULL,NULL,NULL,0);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色表主键',
  `menu_id` bigint(20) NOT NULL COMMENT '权限表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`,`create_time`,`update_time`,`is_deleted`) values (1,1,NULL,NULL,0),(1,2,NULL,NULL,0),(1,3,NULL,NULL,0),(1,4,NULL,NULL,0),(2,3,NULL,NULL,0);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户表主键',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` varchar(256) DEFAULT NULL COMMENT '用户头像地址',
  `username` varchar(64) DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `phone_num` varchar(32) DEFAULT NULL COMMENT '手机号',
  `is_accountNon_expired` int(1) DEFAULT '1' COMMENT '账号是否过期',
  `is_accountNon_locked` int(1) DEFAULT '1' COMMENT '账号锁定或未锁定',
  `is_credentialsNon_expired` int(1) DEFAULT '1' COMMENT '账号密码是否过期',
  `is_enabled` int(1) DEFAULT '1' COMMENT '账号启用或禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`nick_name`,`avatar_url`,`username`,`password`,`name`,`idcard`,`phone_num`,`is_accountNon_expired`,`is_accountNon_locked`,`is_credentialsNon_expired`,`is_enabled`,`create_time`,`update_time`,`is_deleted`) values (1,'Tom','http://hualuosakura.top/avatar/cat.jpeg','admin','$2a$10$NIViPI2vVjySpHOcynlFWe/vgI6uF/ii3f8SLViAvtEU1/s0t1izq','张三','430702199003075254','13975641124',1,1,1,1,NULL,NULL,0),(2,'Jerry','http://hualuosakura.top/avatar/fox.jpg','test','$2a$10$NIViPI2vVjySpHOcynlFWe/vgI6uF/ii3f8SLViAvtEU1/s0t1izq','李四','430702199003073179','18473612917',1,1,1,1,NULL,NULL,0);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户表主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`,`create_time`,`update_time`,`is_deleted`) values (1,1,NULL,NULL,NULL),(2,2,NULL,NULL,NULL);

/*Table structure for table `wfssp_accept` */

DROP TABLE IF EXISTS `wfssp_accept`;

CREATE TABLE `wfssp_accept` (
  `accept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '受理表主键',
  `report_id` bigint(20) DEFAULT NULL COMMENT '举报表主键',
  `level` varchar(32) DEFAULT NULL COMMENT '违法级别',
  `examine_id` bigint(20) DEFAULT NULL COMMENT '审核人员主键',
  `examine_name` varchar(32) DEFAULT NULL COMMENT '审核人员姓名',
  `handler_id` bigint(20) DEFAULT NULL COMMENT '处理人员主键',
  `handler_name` varchar(32) DEFAULT NULL COMMENT '处理人员姓名',
  `is_public` int(1) DEFAULT '0' COMMENT '是否公开',
  `status` varchar(32) DEFAULT NULL COMMENT '审核状态（未通过审核，等待审核，通过审核）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建地点',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`accept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_accept` */

insert  into `wfssp_accept`(`accept_id`,`report_id`,`level`,`examine_id`,`examine_name`,`handler_id`,`handler_name`,`is_public`,`status`,`create_time`,`update_time`,`is_deleted`) values (1,1,NULL,1,'张三',NULL,NULL,0,'未通过审核',NULL,'2023-06-17 03:53:51',0),(2,2,'严重隐患',1,'张三',NULL,NULL,0,'通过审核',NULL,'2023-06-17 03:53:52',0),(3,3,NULL,NULL,NULL,NULL,NULL,0,'等待审核',NULL,NULL,0);

/*Table structure for table `wfssp_address` */

DROP TABLE IF EXISTS `wfssp_address`;

CREATE TABLE `wfssp_address` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址表主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户表主键',
  `name` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '收货人',
  `phone_num` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `area` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '所在地区',
  `address` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '详细地址',
  `is_default` int(1) DEFAULT '1' COMMENT '是否为默认收货地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_estonian_ci;

/*Data for the table `wfssp_address` */

insert  into `wfssp_address`(`address_id`,`user_id`,`name`,`phone_num`,`area`,`address`,`is_default`,`create_time`,`update_time`,`is_deleted`) values (1,2,'李四','13975641124','新疆维吾尔自治区-克拉玛依市-克拉玛依区','中国石油大学（北京）克拉玛依校区',1,NULL,NULL,0);

/*Table structure for table `wfssp_file` */

DROP TABLE IF EXISTS `wfssp_file`;

CREATE TABLE `wfssp_file` (
  `file_id` bigint(20) NOT NULL COMMENT '文件表主键',
  `file_name` varchar(256) DEFAULT NULL COMMENT '文件名称',
  `type` varchar(64) DEFAULT NULL COMMENT '文件类型（图片、文档、视频）',
  `tags` varchar(64) DEFAULT NULL COMMENT '标签',
  `bucket` varbinary(128) DEFAULT NULL COMMENT '存储目录',
  `file_path` varchar(256) DEFAULT NULL COMMENT '存储路径',
  `url` varchar(256) DEFAULT NULL COMMENT '访问地址',
  `username` varchar(32) DEFAULT NULL COMMENT '上传用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_file` */

/*Table structure for table `wfssp_goods` */

DROP TABLE IF EXISTS `wfssp_goods`;

CREATE TABLE `wfssp_goods` (
  `goods_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品表主键',
  `title` varchar(32) DEFAULT NULL COMMENT '商品描述',
  `pic_url` varchar(256) DEFAULT NULL COMMENT '图片地址',
  `point` int(11) DEFAULT NULL COMMENT '兑换积分',
  `stocks` int(11) DEFAULT NULL COMMENT '商品库存',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_goods` */

insert  into `wfssp_goods`(`goods_id`,`title`,`pic_url`,`point`,`stocks`,`create_time`,`update_time`,`is_deleted`) values (1,'蓝月亮洗衣液','https://img2.baidu.com/it/u=3793467079,2432045163&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',50,9999,NULL,NULL,0),(2,'AK-47火麒麟','https://img2.baidu.com/it/u=995498978,3702091743&fm=253&fmt=auto&app=138&f=GIF?w=550&h=400',648,9999,NULL,NULL,0),(3,'粤利粤饼干','https://img2.baidu.com/it/u=3499366741,3766352109&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=363',10,9999,NULL,NULL,0);

/*Table structure for table `wfssp_order` */

DROP TABLE IF EXISTS `wfssp_order`;

CREATE TABLE `wfssp_order` (
  `order_id` bigint(20) NOT NULL COMMENT '订单号',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '兑换商品主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '兑换人主键',
  `name` varchar(32) DEFAULT NULL COMMENT '兑换人姓名',
  `phone_num` varchar(32) DEFAULT NULL COMMENT '兑换人手机号',
  `address_id` bigint(20) DEFAULT NULL COMMENT '收货地址主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_order` */

insert  into `wfssp_order`(`order_id`,`goods_id`,`user_id`,`name`,`phone_num`,`address_id`,`create_time`,`update_time`,`is_deleted`) values (1,1,2,'李四','13975641124',1,NULL,NULL,0);

/*Table structure for table `wfssp_points` */

DROP TABLE IF EXISTS `wfssp_points`;

CREATE TABLE `wfssp_points` (
  `points_id` bigint(20) NOT NULL COMMENT '积分表主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户表主键',
  `description` varchar(64) DEFAULT NULL COMMENT '积分描述',
  `exchange` int(11) DEFAULT NULL COMMENT '积分变化',
  `balance` int(11) DEFAULT NULL COMMENT '积分余额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`points_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_points` */

insert  into `wfssp_points`(`points_id`,`user_id`,`description`,`exchange`,`balance`,`create_time`,`update_time`,`is_deleted`) values (1,2,'新用户注册赠送1000积分',1000,1000,NULL,NULL,0),(2,2,'兑换蓝月亮洗衣液',50,950,NULL,NULL,0);

/*Table structure for table `wfssp_report` */

DROP TABLE IF EXISTS `wfssp_report`;

CREATE TABLE `wfssp_report` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '举报表主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户表主键',
  `wf_time` datetime DEFAULT NULL COMMENT '违法时间',
  `wf_address` varchar(256) DEFAULT NULL COMMENT '违法地点',
  `wf_type` varchar(32) DEFAULT NULL COMMENT '违法类型',
  `wf_desc` varchar(256) DEFAULT NULL COMMENT '违法描述',
  `hphm` varchar(32) DEFAULT NULL COMMENT '号牌号码',
  `hpzl` varchar(32) DEFAULT NULL COMMENT '号牌种类',
  `pic1_url` varchar(256) DEFAULT NULL COMMENT '违法图片1地址',
  `pic2_url` varchar(256) DEFAULT NULL COMMENT '违法图片2地址',
  `pic3_url` varchar(256) DEFAULT NULL COMMENT '违法图片3地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '删除标识符',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `wfssp_report` */

insert  into `wfssp_report`(`report_id`,`user_id`,`wf_time`,`wf_address`,`wf_type`,`wf_desc`,`hphm`,`hpzl`,`pic1_url`,`pic2_url`,`pic3_url`,`create_time`,`update_time`,`is_deleted`) values (1,2,'2023-06-17 01:10:16','华美达酒店路口红绿灯','闯红灯',NULL,'桂JL2710','蓝底白字','http://hualuosakura.top/picture/%E6%A1%82JL2710%202023-6-17.jpg',NULL,NULL,NULL,NULL,0),(2,2,'2023-06-17 01:10:16','中华园红绿灯路口','逆行',NULL,'桂JRN950','蓝底白字','http://hualuosakura.top/picture/%E6%A1%82JAH090%202023-6-17.jpg',NULL,NULL,NULL,NULL,0),(3,2,'2023-06-17 01:10:16','曾屋路二高门口正对面','违法停车',NULL,'鄂DGQ031','蓝底白字','http://hualuosakura.top/picture/%E9%84%82DGQ031%202023-6-17.jpg',NULL,NULL,NULL,NULL,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
