/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - db_bookmanagesystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_bookmanagesystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_bookmanagesystem`;

/*Table structure for table `t_book` */

DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `bookDescribe` varchar(500) DEFAULT NULL COMMENT '描述',
  `bookTypeId` int(11) DEFAULT NULL COMMENT '图书类别id',
  `bookNumber` varchar(100) DEFAULT NULL COMMENT '书号',
  `state` int(11) DEFAULT NULL COMMENT '状态,1代表可借阅,2代表已借出,3代表不可借出(由管理员设置)',
  `location` varchar(100) DEFAULT NULL COMMENT '图书位置',
  PRIMARY KEY (`id`),
  KEY `bookTypeId` (`bookTypeId`),
  CONSTRAINT `t_book_ibfk_1` FOREIGN KEY (`bookTypeId`) REFERENCES `t_book_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_book` */

insert  into `t_book`(`id`,`name`,`bookDescribe`,`bookTypeId`,`bookNumber`,`state`,`location`) values (1,'Java编程思想第8版','Java编程思想第8版',1,'1',1,'一楼3排'),(2,'Java编程思想第8版','Java编程思想第8版',1,'2',1,'一楼3排'),(4,'Java编程思想第8版','Java编程思想第8版',1,'3',1,'一楼3排'),(5,'C程序设计(第四版) 谭浩强','C程序设计(第四版) 谭浩强',1,'4',1,'一楼4排'),(6,'C程序设计(第四版) 谭浩强','C程序设计(第四版) 谭浩强',1,'5',1,'一楼4排'),(7,'C程序设计(第四版) 谭浩强','C程序设计(第四版) 谭浩强',1,'6',1,'一楼4排'),(8,'平凡的世界','平凡的世界',6,'7',2,'二楼1排'),(9,'平凡的世界','平凡的世界',6,'8',1,'二楼1排'),(10,'平凡的世界','平凡的世界',6,'9',1,'二楼1排'),(11,'MySQL必知必会','MySQL必知必会',1,'10',1,'一楼4排'),(12,'MySQL必知必会','MySQL必知必会',1,'11',1,'一楼4排'),(13,'MySQL必知必会','MySQL必知必会',1,'12',1,'一楼4排'),(14,'二战简史','二战简史',5,'13',1,'二楼1排'),(15,'二战简史','二战简史',5,'14',1,'二楼1排');

/*Table structure for table `t_book_type` */

DROP TABLE IF EXISTS `t_book_type`;

CREATE TABLE `t_book_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_book_type` */

insert  into `t_book_type`(`id`,`name`) values (1,'计算机类'),(5,'历史类'),(6,'文学类');

/*Table structure for table `t_borrow_record` */

DROP TABLE IF EXISTS `t_borrow_record`;

CREATE TABLE `t_borrow_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `time` datetime DEFAULT NULL COMMENT '借阅时间',
  `day` int(11) DEFAULT NULL COMMENT '借阅天数',
  `state` int(11) DEFAULT NULL COMMENT '当前状态,1代表正在借阅,2代表已还书,3代表逾期',
  `userId` int(11) DEFAULT NULL COMMENT '借书人id',
  `bookId` int(11) DEFAULT NULL COMMENT 'bookId',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `bookId` (`bookId`),
  CONSTRAINT `t_borrow_record_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`),
  CONSTRAINT `t_borrow_record_ibfk_2` FOREIGN KEY (`bookId`) REFERENCES `t_book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_borrow_record` */

insert  into `t_borrow_record`(`id`,`time`,`day`,`state`,`userId`,`bookId`) values (4,'2022-01-24 11:09:59',44,2,2,1),(5,'2022-01-24 12:08:37',44,2,2,2),(6,'2022-01-24 20:03:25',37,2,2,8),(7,'2022-01-24 22:22:01',44,2,2,9),(8,'2022-01-24 22:51:58',30,2,2,4),(9,'2022-01-25 10:59:19',30,2,2,5),(10,'2022-01-25 11:01:03',44,2,2,6),(11,'2022-01-25 16:13:20',90,2,2,1),(12,'2022-01-25 16:13:32',74,2,2,8),(13,'2022-01-25 17:24:09',90,2,2,1),(14,'2022-01-25 17:42:17',44,2,2,1),(15,'2022-01-25 17:43:01',74,2,2,1),(16,'2022-01-25 17:43:55',60,2,2,1),(17,'2022-01-25 17:45:38',90,2,2,1),(18,'2022-01-25 17:46:02',74,2,2,1),(19,'2022-01-25 20:44:47',14,2,2,4),(20,'2022-01-26 11:16:18',74,2,2,1),(21,'2022-01-26 11:18:53',1,2,2,1),(22,'2022-01-26 18:17:02',44,2,3,2),(23,'2022-01-26 18:17:27',2,1,3,8);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userName` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `type` int(11) DEFAULT NULL COMMENT '用户类型,1代表管理员,2代表普通用户',
  `state` int(11) DEFAULT NULL COMMENT '用户状态,1代表正常,2代表被封禁',
  `isBorrow` int(11) DEFAULT NULL COMMENT '是否可借书,1代表是,2代表否',
  `nickName` varchar(100) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`,`type`,`state`,`isBorrow`,`nickName`) values (1,'admin','admin',1,1,1,'乐道'),(2,'1','1',2,1,2,'ledao'),(3,'2','2',2,1,1,'乐道乐');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
