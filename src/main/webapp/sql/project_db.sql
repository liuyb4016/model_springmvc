# SQL-Front 5.1  (Build 4.16)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.01    Database: workflow
# ------------------------------------------------------
# Server version 5.5.30

DROP DATABASE IF EXISTS `workflow`;
CREATE DATABASE `workflow` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `workflow`;

#
# Source for table data_dict
#

DROP TABLE IF EXISTS `data_dict`;
CREATE TABLE `data_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `level` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `system_tag` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `parent_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table data_dict
#

LOCK TABLES `data_dict` WRITE;
/*!40000 ALTER TABLE `data_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_dict` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birthday` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `taobao_id` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `base_salary` double DEFAULT NULL,
  `dormitory` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Dumping data for table user
#

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2013-05-24','经理','liuyb4016@qq.com',NULL,'刘运傧','c0ffbc4e2f04b00f3e41aca6166d9629','18903004577','初级售前客服','2013-05-24',1,1,'B123123','2013-06-02 23:35:28','admin',0,'421');
INSERT INTO `user` VALUES (2,'2013-06-05','财务部','sad@qq.com',NULL,'liuyb','397e7af3a849ef718bd99e35bf7b5bb5','18930332232','初级售后客服','2013-05-26 00:40:29',2,1,'213456432','2013-06-05 00:00:27','liuyb',21,'401');
INSERT INTO `user` VALUES (3,'','网购部','',NULL,'liuyunbin','c0ffbc4e2f04b00f3e41aca6166d9629','18903004577','初级售前客服','2013-06-05 16:10:45',3,1,'123123','2013-06-05 16:16:25','ceshi',0,'');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user_function_model
#

DROP TABLE IF EXISTS `user_function_model`;
CREATE TABLE `user_function_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `model_id` varchar(255) DEFAULT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `model_order` varchar(255) DEFAULT NULL,
  `model_type` int(11) DEFAULT NULL,
  `model_url` varchar(255) DEFAULT NULL,
  `parent_model_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

#
# Dumping data for table user_function_model
#

LOCK TABLES `user_function_model` WRITE;
/*!40000 ALTER TABLE `user_function_model` DISABLE KEYS */;
INSERT INTO `user_function_model` VALUES (1,'生产流程管理系统','0001','生产流程管理系统','0001',1,'','0');
INSERT INTO `user_function_model` VALUES (2,'用户管理模块，这是模块的顶级头连接','00010001','用户管理','00010001',1,NULL,'0001');
INSERT INTO `user_function_model` VALUES (3,'用户管理模块，这是模块的顶级头连接','000100010001','用户管理','000100010001',0,'portal/user','00010001');
INSERT INTO `user_function_model` VALUES (4,'权限管理-模块管理','000100030002','模块管理','000100030002',0,'portal/userfuncmodel','00010003');
INSERT INTO `user_function_model` VALUES (5,'权限管理-角色管理','000100030003','角色管理','000100030003',0,'portal/userrole','00010003');
INSERT INTO `user_function_model` VALUES (6,'权限管理-角色权限管理','000100030004','角色权限管理','000100030004',0,'portal/userrolepermission','00010003');
INSERT INTO `user_function_model` VALUES (9,'修改用户自己的密码','000100030005','修改密码','000100030005',0,'portal/updateselfpwd','00010003');
INSERT INTO `user_function_model` VALUES (10,'系统管理','00010003','系统管理','00010003',1,'','0001');
INSERT INTO `user_function_model` VALUES (11,'数据字典管理','000100030001','数据字典','000100030001',0,'portal/datadict','00010003');
/*!40000 ALTER TABLE `user_function_model` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user_notices
#

DROP TABLE IF EXISTS `user_notices`;
CREATE TABLE `user_notices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `from_department` varchar(255) DEFAULT NULL,
  `from_name` varchar(255) DEFAULT NULL,
  `from_position` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `from_username` varchar(255) DEFAULT NULL,
  `is_see` bit(1) DEFAULT NULL,
  `is_valid` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notices` varchar(2000) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table user_notices
#

LOCK TABLES `user_notices` WRITE;
/*!40000 ALTER TABLE `user_notices` DISABLE KEYS */;
INSERT INTO `user_notices` VALUES (1,'2013-07-14 14:42:22','经理','经理','admin','初级售前客服',1,'admin',b'1',b'1','刘运傧','阿萨德发阿萨德','初级售前客服','阿萨德法师打发','2013-07-14 14:42:30',1,'admin');
/*!40000 ALTER TABLE `user_notices` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user_role
#

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role_level` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Dumping data for table user_role
#

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'普通管理员',1,'普通管理员',1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user_role_permission
#

DROP TABLE IF EXISTS `user_role_permission`;
CREATE TABLE `user_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `read_only` int(11) DEFAULT NULL,
  `user_function_model` varchar(255) DEFAULT NULL,
  `user_role` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2C1620C4C1778452` (`user_role`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8;

#
# Dumping data for table user_role_permission
#

LOCK TABLES `user_role_permission` WRITE;
/*!40000 ALTER TABLE `user_role_permission` DISABLE KEYS */;
INSERT INTO `user_role_permission` VALUES (260,0,'00010001',1);
INSERT INTO `user_role_permission` VALUES (261,0,'000100010001',1);
INSERT INTO `user_role_permission` VALUES (262,0,'00010003',1);
INSERT INTO `user_role_permission` VALUES (263,0,'000100030001',1);
INSERT INTO `user_role_permission` VALUES (264,0,'000100030002',1);
INSERT INTO `user_role_permission` VALUES (265,0,'000100030003',1);
INSERT INTO `user_role_permission` VALUES (266,0,'000100030004',1);
INSERT INTO `user_role_permission` VALUES (267,0,'000100030005',1);
INSERT INTO `user_role_permission` VALUES (268,0,'00010004',1);
INSERT INTO `user_role_permission` VALUES (269,0,'000100040001',1);
INSERT INTO `user_role_permission` VALUES (270,0,'000100040002',1);
INSERT INTO `user_role_permission` VALUES (271,0,'00010005',1);
INSERT INTO `user_role_permission` VALUES (272,0,'000100050001',1);
INSERT INTO `user_role_permission` VALUES (273,0,'000100050002',1);
INSERT INTO `user_role_permission` VALUES (274,0,'00010006',1);
INSERT INTO `user_role_permission` VALUES (275,0,'000100060001',1);
INSERT INTO `user_role_permission` VALUES (276,0,'00010007',1);
INSERT INTO `user_role_permission` VALUES (277,0,'000100070001',1);
INSERT INTO `user_role_permission` VALUES (278,0,'000100070002',1);
INSERT INTO `user_role_permission` VALUES (279,0,'000100070003',1);
INSERT INTO `user_role_permission` VALUES (280,0,'000100070004',1);
INSERT INTO `user_role_permission` VALUES (281,0,'00010008',1);
INSERT INTO `user_role_permission` VALUES (282,0,'000100080001',1);
INSERT INTO `user_role_permission` VALUES (283,0,'000100080002',1);
/*!40000 ALTER TABLE `user_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

#
#  Foreign keys for table user_role_permission
#

ALTER TABLE `user_role_permission`
ADD CONSTRAINT `FK2C1620C4C1778452` FOREIGN KEY (`user_role`) REFERENCES `user_role` (`id`);


/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
