/*
SQLyog v10.2 
MySQL - 5.0.96-community-nt : Database - shop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shop` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `shop`;

/*Table structure for table `detail_guanggao` */

DROP TABLE IF EXISTS `detail_guanggao`;

CREATE TABLE `detail_guanggao` (
  `id` varchar(10) NOT NULL,
  `shopid` varchar(10) default NULL,
  `imgurl` varchar(80) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `detail_guanggao` */

insert  into `detail_guanggao`(`id`,`shopid`,`imgurl`) values ('1','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop6.jpg'),('10','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_4.jpg'),('11','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_5.jpg'),('12','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_6.jpg'),('13','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou1.jpg'),('14','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou2.jpg'),('15','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou3.jpg'),('16','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou4.jpg'),('17','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou5.jpg'),('18','103','http://192.168.191.1:8080/ECServer_D/images/luyou/luyou6.jpg'),('19','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan1.jpg'),('2','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop5.jpg'),('20','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan2.jpg'),('21','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan3.jpg'),('22','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan4.jpg'),('23','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan5.jpg'),('24','104','http://192.168.191.1:8080/ECServer_D/images/shouhuan/shouhuan6.jpg'),('3','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop4.jpg'),('4','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop3.jpg'),('5','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop2.jpg'),('6','101','http://192.168.191.1:8080/ECServer_D/images/huawei6_detail/detail_shop1.jpg'),('7','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_1.jpg'),('8','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_2.jpg'),('9','102','http://192.168.191.1:8080/ECServer_D/images/huawei7/huawei7_3.jpg');

/*Table structure for table `order_jiaoyi_tijiaodan` */

DROP TABLE IF EXISTS `order_jiaoyi_tijiaodan`;

CREATE TABLE `order_jiaoyi_tijiaodan` (
  `orderid` varchar(20) NOT NULL,
  `status` int(20) default NULL,
  PRIMARY KEY  (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_jiaoyi_tijiaodan` */

insert  into `order_jiaoyi_tijiaodan`(`orderid`,`status`) values ('20150520102000017',1),('20150520162400017',1),('20150521112100032',3),('20150522133900004',3),('20150522133900014',4),('20150522133900020',4),('20150526153500018',1),('20150531234800022',1),('20150603152200036',1);

/*Table structure for table `order_state` */

DROP TABLE IF EXISTS `order_state`;

CREATE TABLE `order_state` (
  `order_state_id` int(20) NOT NULL,
  `state_introduction` varchar(20) default NULL,
  PRIMARY KEY  (`order_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_state` */

insert  into `order_state`(`order_state_id`,`state_introduction`) values (0,'代付款'),(1,'代收货'),(2,'待评价'),(3,'返修/退换'),(4,'已完成');

/*Table structure for table `shop_cart` */

DROP TABLE IF EXISTS `shop_cart`;

CREATE TABLE `shop_cart` (
  `shopid` varchar(5) default NULL,
  `number` int(5) default NULL,
  `color` varchar(20) default NULL,
  `zhishi` varchar(20) default NULL,
  `userid` varchar(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `shop_cart` */

insert  into `shop_cart`(`shopid`,`number`,`color`,`zhishi`,`userid`) values ('101',1,'黑色','电信高配版套餐','123'),('104',1,'时尚红','','123'),('101',1,'黑色','电信标准版套餐','1'),('101',1,'黑色','电信标准版套餐','123');

/*Table structure for table `shop_order` */

DROP TABLE IF EXISTS `shop_order`;

CREATE TABLE `shop_order` (
  `orderid` varchar(20) NOT NULL,
  `status` varchar(20) default NULL,
  `price` varchar(20) default NULL,
  `time` varchar(20) default NULL,
  `adressid` varchar(20) default NULL,
  `shoplist` mediumtext,
  `userid` varchar(20) default NULL,
  PRIMARY KEY  (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `shop_order` */

insert  into `shop_order`(`orderid`,`status`,`price`,`time`,`adressid`,`shoplist`,`userid`) values ('20150519172700027','0','2899.0','20150519172727','7','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','1'),('20150520102000017','0','2899.0','20150520102017','7','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','1'),('20150520155700024','0','11596.0','20150520155724','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"2\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信高配版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"移动标准版套餐\"}]','1'),('20150520162400017','0','2899.0','20150520162417','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','123'),('20150520163300058','0','2899.0','20150520163358','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','3'),('20150520172700024','0','2899.0','20150520172724','14','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','3'),('20150520172800019','0','5798.0','20150520172819','14','[{\"color\":\"金色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"}]','3'),('20150521112100032','1','5798.0','20150521112132','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"},{\"color\":\"白色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"}]','123'),('20150522133900004','1','2899.0','20150522133904','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信高配版套餐\"}]','123'),('20150522133900009','4','5798.0','20150522133909','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"2\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"}]','123'),('20150522133900014','3','2899.0','20150522133914','11','[{\"color\":\"白色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"}]','123'),('20150522133900020','3','2899.0','20150522133920','11','[{\"color\":\"金色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"}]','123'),('20150524164300047','0','188.0','20150524164347','11','[{\"color\":\"白色\",\"imginfo\":\"独立WiFi引擎，单路由信号超强；设置简单，一步上网；发热小，稳定不掉线；\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huaweiluyou.png\",\"price\":\"188.00\",\"shopid\":\"103\",\"zhishi\":\"\"}]','123'),('20150526151000024','0','2899.0','20150526151024','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"5\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','123'),('20150526151000027','0','2899.0','20150526151027','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"5\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','123'),('20150526153500018','0','3787.0','20150526153518','11','[{\"color\":\"时尚红\",\"imginfo\":\"荣耀智能手环 能通话的健康手环\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huaweishouhuan.png\",\"price\":\"888.00\",\"shopid\":\"104\",\"zhishi\":\"\"},{\"color\":\"白色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"}]','123'),('20150531233400019','0','2899.0','20150531233419','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','1'),('20150531234800022','0','2899.0','20150531234822','2','[{\"color\":\"白色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"2\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"}]','1'),('20150601082800059','0','5798.0','20150601082859','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信高配版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"移动标准版套餐\"}]','1'),('20150601085400057','0','2899.0','20150601085457','2','[{\"color\":\"金色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"}]','1'),('20150601160300045','0','2899.0','20150601160345','11','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"2\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"电信标准版套餐\"}]','123'),('20150603152200036','0','5798.0','20150603152236','2','[{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"4\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"双4G版套餐\"},{\"color\":\"黑色\",\"imginfo\":\"华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版\",\"number\":\"1\",\"pic\":\"http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png\",\"price\":\"2899.00\",\"shopid\":\"101\",\"zhishi\":\"联通标准版套餐\"}]','1');

/*Table structure for table `shop_sort` */

DROP TABLE IF EXISTS `shop_sort`;

CREATE TABLE `shop_sort` (
  `sortid` int(20) NOT NULL auto_increment,
  `id` varchar(20) default NULL,
  `parentid` int(20) default NULL,
  `path` varchar(20) default NULL,
  PRIMARY KEY  (`sortid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `shop_sort` */

insert  into `shop_sort`(`sortid`,`id`,`parentid`,`path`) values (1,'手机',0,NULL),(2,'平板',0,NULL),(3,'其他',0,NULL),(4,'101',1,'1'),(5,'102',1,'1'),(6,'103',1,'2'),(7,'104',1,'2'),(8,'105',1,'2'),(9,'106',1,'3'),(10,'107',1,'3'),(11,'108',1,'3');

/*Table structure for table `shouye_guanggao` */

DROP TABLE IF EXISTS `shouye_guanggao`;

CREATE TABLE `shouye_guanggao` (
  `id` varchar(10) NOT NULL,
  `shopid` varchar(10) default NULL,
  `imgurl` varchar(80) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `shouye_guanggao` */

insert  into `shouye_guanggao`(`id`,`shopid`,`imgurl`) values ('101','101','http://192.168.191.1:8080/ECServer_D/images/huawei1.jpg'),('102','102','http://192.168.191.1:8080/ECServer_D/images/huawei2.jpg'),('103','103','http://192.168.191.1:8080/ECServer_D/images/huawei3.jpg'),('104','104','http://192.168.191.1:8080/ECServer_D/images/huawei4.jpg');

/*Table structure for table `shouye_products` */

DROP TABLE IF EXISTS `shouye_products`;

CREATE TABLE `shouye_products` (
  `prodNum` varchar(10) default NULL,
  `id` varchar(5) NOT NULL,
  `name` varchar(10) default NULL,
  `price` varchar(10) default NULL,
  `number` varchar(10) default NULL,
  `uplimit` varchar(10) default NULL,
  `pic` varchar(80) default NULL,
  `introduction` varchar(50) default NULL,
  `imginfo` varchar(50) character set utf8 collate utf8_estonian_ci default NULL,
  `zhishi` varchar(10000) default NULL,
  `color` varchar(10000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `shouye_products` */

insert  into `shouye_products`(`prodNum`,`id`,`name`,`price`,`number`,`uplimit`,`pic`,`introduction`,`imginfo`,`zhishi`,`color`) values ('2000','101','荣耀6 Plus套餐','2899.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/huawei_phone1.png','双眼看世界，荣耀独创后置平行双800万像素镜头设计，前置800万像素拍照神器！','华为 荣耀6 Plus 双卡双待双通 移动/联通双4G版 32GB存储 套餐版','[{\"name\":\"电信标准版套餐\"},{\"name\":\"移动标准版套餐\"},{\"name\":\"电信高配版套餐\"},{\"name\":\"双4G版套餐\"},{\"name\":\"联通标准版套餐\"}]','[{\"name\":\"黑色\"},{\"name\":\"白色\"},{\"name\":\"金色\"}]'),('1300','102','华为 Mate7','3699.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/huawei_phone2.png','购机即送莫塞尔红酒VIP金卡、华为手机会员VIP金卡、华为手机延保卡，全球独家首发。','华为，Mate7，6英寸大屏，超窄边框，金属机身设计，Cat6极速4G，智能超八核','[{\"name\":\"移动4G版套餐\"},{\"name\":\"移电信4G版套餐\"},{\"name\":\"移动/联通4G套餐\"}]','[{\"name\":\"曜石黑\"},{\"name\":\"月光银\"}]'),('1400','103','荣耀路由','188.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/huaweiluyou.png','业界首创分布式Wi-Fi方案，双路由一键组网完美覆盖大户豪宅。精致外观设计，11AC 1167Mbp','荣耀路由，独立WiFi引擎，单路由信号超强；设置简单，一步上网；发热小，稳定不掉线；','[]','[{\"name\":\"白色\"}]'),('1500','104','荣耀智能手环','888.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/huaweishouhuan.png','亦言亦行——IP57专业级防尘防水，微信连接，运动分享，能通话的健康智能手环！','荣耀智能手环 能通话的健康手环','[]','[{\"name\":\"活力蓝\"},{\"name\":\"石墨灰\"},{\"name\":\"时尚红\"},{\"name\":\"柠檬黄\"}]'),('1600','105','小米电视2','1999.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/xiaomiTV2.jpg','SDP X-Gen超晶屏具有5000:1的超高静态对比度和6毫秒的超快响应速度，搭配瑞仪光电的顶级背','全球唯一10代线屏幕 超级性价比智能电视','[]','[{\"name\":\"黑色\"}]'),('1500','106','小米平板','1299.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/xiaomipad.jpg','搭载 NVIDIA Tegra K1 处理器平板','全球首款搭载 NVIDIA Tegra K1 192 核 PC 架构的 GPU / 夏普、友达 7.','[{\"name\":\"16GB\"},{\"name\":\"64GB\"}]','[{\"name\":\"浅绿色\"},{\"name\":\"纯白色\"},{\"name\":\"淡粉色\"},{\"name\":\"柠檬黄\"},{\"name\":\"天蓝色\"}]'),('1300','107','小米移动电源1600','129.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/xiaomidianyuan.jpg','双USB输出 高品质电芯 全铝合金金属外壳','双USB输出 高品质电芯 全铝合金金属外壳','[]','[{\"name\":\"银白色\"}]'),('1400','108','小米头戴式耳机','499.00','3000','10','http://192.168.191.1:8080/ECServer_D/images/xiaomierji.jpg','50mm大尺寸航天金属振膜手机直推高保真音','50mm大尺寸航天金属振膜 手机直推高保真音质','[]','[{\"name\":\"黑金色\"}]');

/*Table structure for table `user_adress` */

DROP TABLE IF EXISTS `user_adress`;

CREATE TABLE `user_adress` (
  `userid` varchar(20) default NULL,
  `reciever` varchar(20) default NULL,
  `phone` varchar(20) default NULL,
  `adress` varchar(100) default NULL,
  `isdefault` varchar(20) default '0',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `user_adress` */

insert  into `user_adress`(`userid`,`reciever`,`phone`,`adress`,`isdefault`,`id`) values ('1','韩慧峰','15000000900','北京市朝阳路18号','0',2),('1','慧三','110','苍山','0',3),('1','韩慧峰','15000000900','北京市朝阳路18号','0',4),('1','韩慧峰','15000000900','北京市朝阳路18号','0',5),('1','三','111','算法','0',6),('1','测量','56358','拉土','0',7),('1','咯dll','53596','门','0',8),('1','测试人1','23145','测试地址1','0',9),('123','韩慧丰','15864140699','山东省济南市天桥区山东科技大学','0',11),('3','r','2','r','0',14);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userid` varchar(20) NOT NULL,
  `userpwd` varchar(50) default NULL,
  PRIMARY KEY  (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`userid`,`userpwd`) values ('1','a'),('123','123'),('3','a');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
